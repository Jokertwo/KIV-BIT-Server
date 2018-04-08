package my.bit.sem.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import my.bit.sem.message.KindOfM;
import my.bit.sem.message.Message;


public class ClientThread extends Thread implements Client {

    Socket socket;
    ObjectInputStream sInput;
    ObjectOutputStream sOutput;
    private Message message;

    private String date;
    private boolean run = true;


    public ClientThread(Socket socket) {
        this.socket = socket;
        try {
            sOutput = new ObjectOutputStream(socket.getOutputStream());
            sInput = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        date = new Date().toString() + "\n";
        System.out.println(date);
    }


    @Override
    public void run() {
        while (run) {
            try {
                message = (Message) sInput.readObject();
                if(message.getType() == KindOfM.LOGOUT.getKind()){
                    break;
                }
                sOutput.writeObject(message);
            } catch (IOException | ClassNotFoundException e ) {
                e.printStackTrace();
                run = false;
            }
        }
        disconect();
    }


    @Override
    public void disconect() {        
        close();
    }


    private void close() {
        try {
            if (sOutput != null) {
                sOutput.close();
            }
            if (sInput != null) {
                sInput.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
