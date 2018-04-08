package my.bit.sem.main;

import javax.swing.SwingUtilities;
import my.bit.sem.gui.Window;
import my.bit.sem.pm.PrimeNumbersE;
import my.bit.sem.rsa.RSAImpl;
import my.bit.sem.server.Server;
import my.bit.sem.server.ServerControler;
import my.bit.sem.server.ServerControlerImpl;
import my.bit.sem.server.ServerImpl;


public class Main {
    public static void main(String[] args) {
        Server server = new ServerImpl(new RSAImpl(PrimeNumbersE.pN3.getPn(), PrimeNumbersE.pN6.getPn()));
        ServerControler sCtrl = new ServerControlerImpl(server);
        SwingUtilities.invokeLater(() -> new Window(sCtrl));
    }
}
