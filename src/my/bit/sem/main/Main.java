package my.bit.sem.main;

import javax.swing.SwingUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import my.bit.sem.gui.Window;
import my.bit.sem.pm.PrimeNumbersE;
import my.bit.sem.rsa.RSAImpl;
import my.bit.sem.server.Server;
import my.bit.sem.server.ServerControler;
import my.bit.sem.server.ServerControlerImpl;
import my.bit.sem.server.ServerImpl;


public class Main {
    
    static {
        System.setProperty("log4j.configurationFile",
                "log/log_config.xml");
    }
    public static final Logger logger = LogManager.getLogger();
    
    
    public static void main(String[] args) {
        logger.info("Start server application");
        Server server = new ServerImpl(new RSAImpl(PrimeNumbersE.pN3.getPn(), PrimeNumbersE.pN6.getPn()));
        ServerControler sCtrl = new ServerControlerImpl(server);
        SwingUtilities.invokeLater(() -> new Window(sCtrl));
    }
}
