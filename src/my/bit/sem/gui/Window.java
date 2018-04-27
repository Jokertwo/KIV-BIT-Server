package my.bit.sem.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import my.bit.sem.server.ServerControler;
import net.miginfocom.swing.MigLayout;


public class Window extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ServerControler sCtrl;
    private JTextField tf;


    public Window(ServerControler sCtrl,JPanel loginfo) {
        super();
        this.sCtrl = sCtrl;

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout());
        add(loginfo,"grow,push,wrap");
        add(port(), "growx,pushx,wrap");
        add(run(), "growx,pushx,split");
        add(stop(), "growx,pushx");
        setPreferredSize(new Dimension(500, 400));
        pack();
        setVisible(true);

    }


    private JTextField port() {
        tf = new JTextField();
        tf.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
        return tf;
    }


    private JButton run() {
        JButton btn = new JButton("Run");
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = tf.getText().trim();
                if (!temp.isEmpty()) {
                    sCtrl.startServer(Integer.parseInt(temp));
                }
            }
        });
        return btn;
    }


    private JButton stop() {
        JButton btn = new JButton("Stop");
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sCtrl.stopServer();
            }
        });
        return btn;
    }

}
