package my.bit.sem.server;

public interface Server extends Runnable{

    void stop();
    
    void setPort(int port);
}
