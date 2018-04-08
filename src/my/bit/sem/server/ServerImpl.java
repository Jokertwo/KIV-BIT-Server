package my.bit.sem.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import my.bit.sem.rsa.RSA;


public class ServerImpl implements Server {

    private boolean run = true;
    private List<Client> listOfClient;
    private int port;
    private RSA rsa;


    public ServerImpl(RSA rsa) {
        this.rsa = rsa;
        listOfClient = new LinkedList<>();
    }


    private void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (run) {
                Socket socket = serverSocket.accept();
                if (!run) {
                    break;
                }
                ClientThread th = new ClientThread(socket, rsa);
                listOfClient.add(th);
                th.start();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (Client cl : listOfClient) {
            cl.disconect();
        }
    }


    @Override
    public void stop() {
        run = false;
    }


    @Override
    public void run() {
        start();
    }


    @Override
    public void setPort(int port) {
        this.port = port;
    }

}
