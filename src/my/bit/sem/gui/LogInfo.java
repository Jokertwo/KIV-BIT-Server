package my.bit.sem.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import my.bit.sem.main.JTextAreaAppender;

@SuppressWarnings("serial")
public class LogInfo extends JPanel {

    public LogInfo() {
        setLayout(new BorderLayout());
        add(init(), BorderLayout.CENTER);
    }


    private JScrollPane init() {
        JTextArea jLoggingConsole = new JTextArea(2, 0); // 5 lines high here
        jLoggingConsole.setLineWrap(true);
        jLoggingConsole.setWrapStyleWord(true);
        jLoggingConsole.setEditable(false);
        jLoggingConsole.setFont(new Font("Courier", Font.PLAIN, 12));

        // Make scrollable console pane
        JScrollPane jConsoleScroll = new JScrollPane(jLoggingConsole);
        jConsoleScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

//         Subscribe the text area to JTextAreaAppender
        JTextAreaAppender.addTextArea(jLoggingConsole);
        return jConsoleScroll;
    }
}

