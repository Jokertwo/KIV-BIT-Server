package my.bit.sem.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import my.bit.sem.key.Key;
import my.bit.sem.message.Message;
import my.bit.sem.rsa.RSA;


public class ClientThread extends Thread implements Client {

    Socket socket;
    ObjectInputStream sInput;
    ObjectOutputStream sOutput;
    private Message message;

    private String date;
    private boolean run = true;
    private String name;
    private RSA rsa;
    private Key clientPublicKey;


    public ClientThread(Socket socket, RSA rsa) {
        this.socket = socket;
        this.rsa = rsa;
        try {
            sOutput = new ObjectOutputStream(socket.getOutputStream());
            sInput = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        date = new Date().toString() + "\n";
        sendPublicKey();
    }


    private void sendPublicKey() {
        send(Message.PUBLIC_KEY, "", rsa.getPublicKey());
    }


    private void send(int type, String mesage, Key key) {
        try {
            sOutput.writeObject(new Message(type, mesage, key));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (run) {
            try {
                message = (Message) sInput.readObject();
                switch (message.getType()) {
                    case (Message.LOGOUT):
                        close();
                        break;
                    case (Message.LOGIN):
                        clientPublicKey = message.getKey();
                        name = new String(rsa.decription(message.getMessage()).toByteArray());
                        break;
                    case (Message.MESSAGE):
                        String temp = new String(rsa.decription(message.getMessage()).toByteArray());
                        send(Message.MESSAGE,
                            new String(rsa.encryption(temp, clientPublicKey).toByteArray()), null);
                        break;

                }

                sOutput.writeObject(message);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                run = false;
                disconect();
            }
        }

    }


    @Override
    public void disconect() {
        try {
            sOutput.writeObject(new Message(Message.LOGOUT,
                new String(rsa.encryption("test", clientPublicKey).toByteArray()), null));
        } catch (NullPointerException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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


    @Override
    public String getCliName() {
        return name;
    }


    @Override
    public String getConTime() {
        return date;
    }
}
