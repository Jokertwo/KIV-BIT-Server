package my.bit.sem.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;


public class Server {

    private final int port;
    private boolean run = true;
    private List<Client> listOfClient;


    public Server(int port) {
        this.port = port;
        listOfClient = new LinkedList<>();
        start();
    }


    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (run) {
                Socket socket = serverSocket.accept();
                if (!run) {
                    break;
                }
                ClientThread th = new ClientThread(socket);
                listOfClient.add(th);
                th.start();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for(Client cl : listOfClient){
            cl.disconect();
        }
    }


    public void stop() {
        run = false;
    }
    
    
    public static void main(String[] args) {
        new Server(1501);
    }

}
