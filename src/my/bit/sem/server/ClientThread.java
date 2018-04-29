package my.bit.sem.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import my.bit.sem.key.Key;
import my.bit.sem.message.Message;
import my.bit.sem.message.MessageType;
import my.bit.sem.rsa.RSA;


public class ClientThread extends Thread implements Client {
    public static final Logger logger = LogManager.getLogger();
    private Socket socket;

    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;

    private String date;
    private boolean run = true;
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
        Message message = new Message("", rsa.getPublicKey(), MessageType.PUBLIC_KEY);
        try {
            sOutput.writeObject(message);
        } catch (IOException e) {
            logger.error(e);
        }
    }


    @Override
    public void run() {
        while (run) {
            try {
                Message message = (Message) sInput.readObject();
                switch (message.getType()) {
                    case LOGOUT:
                        logger.info("Client " + socket.toString() + "' logOut from server");
                        close();
                        return;
                    case LOGIN:
                        clientPublicKey = message.getKey();
                        logger.info("Client " + socket.toString() + "' logIn from server");
                        break;
                    case MESSAGE:
                        logger.trace("Recieve message from clinet " + socket.toString() + "'");
                        logger.debug("Undecoded  message: " + message.getMessage());
                        String temp = new String(rsa.decription(message.getMessage()).toByteArray());
                        logger.debug("Decoded message: " + temp);
                        temp = new String(rsa.encryption(temp, clientPublicKey).toString());

                        Message newMessage = new Message(temp, null, MessageType.MESSAGE);
                        sOutput.writeObject(newMessage);
                        break;
                    default:
                        break;

                }

            } catch (ClassNotFoundException | IOException e) {
                logger.error("Error during recieve message.", e);
                run = false;
                disconect();
            }
        }

    }


    @Override
    public void disconect() {
        try {
            sOutput.writeObject(new Message(new String(rsa.encryption("test", clientPublicKey).toByteArray()), null,
                MessageType.LOGOUT));
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
    public String getConTime() {
        return date;
    }
}
