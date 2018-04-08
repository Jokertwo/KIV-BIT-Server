package my.bit.sem.server;

public class ServerControlerImpl implements ServerControler {

    
    private Server server;
    
    
    public ServerControlerImpl(Server server) {
        super();
        this.server = server;
    }


    @Override
    public void startServer(int port) {
        server.setPort(port);
        new Thread(server).start();
    }


    @Override
    public void stopServer() {
        server.stop();
    }

}
