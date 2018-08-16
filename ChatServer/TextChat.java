import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.text.*;

public class TextChat
{
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private ChatClient chatClient;
    
    public TextChat(ChatClient client)
    {
        chatClient = client;
        makeFrame();
        textArea.setEditable(false);
    }
    
    public void makeFrame()
    {
        frame = new JFrame("Chat Interface");
        frame.setLayout(new BorderLayout());
        Container contentPane = frame.getContentPane();
        textArea = new JTextArea(20, 50);
        JScrollPane scrollPane = new JScrollPane(textArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        textField = new JTextField(50);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { textField.setText(""); chatClient.writeToServer(e.getActionCommand()); }
        });
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) { chatClient.writeToServer("bye");}
        });
        contentPane.add(textField, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void displayText(String msg)
    {
        textArea.append(msg);
        textArea.append("\n\n");
    }
}

