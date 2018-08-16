import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class ChatClient
{
    private ChatClientThread client;
    private DataOutputStream out;
    private Socket socket;
    private TextChat textChat;
    private String name;
    
    public ChatClient(String addr, int num, String _name)
    {
        try
        {
            name = _name;
            socket = new Socket(addr, num);
            textChat = new TextChat(this);
            setupOut();
            out.writeUTF(_name + " has joined the room");
            clientThread(socket);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void writeToServer(String msg)
    {
        try
        {
        if(!(msg.equals("bye")))
        {
            out.writeUTF(name + ": " + msg);
            out.flush();
        }
        else
        {
            out.writeUTF("bye");
            out.flush();
            System.exit(0);
        }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void setupOut()
    {
        try
        {
            OutputStream outd = socket.getOutputStream();
            out = new DataOutputStream(outd);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void clientThread(Socket socket)
    {
        client = new ChatClientThread(this, socket, textChat);
    }
    
    public void print(String msg)
    {
        System.out.println(msg);
    }
    
    public static void doThis(String textField1, String textField2, String textField3)
    {
        String addr = textField1;
        int port = Integer.parseInt(textField2);
        String yourName = textField3;
        
        ChatClient chatClient = new ChatClient(addr, port, yourName);
    }
    
    public static void main(String args[])
    {
        
        JFrame frame = new JFrame();
        SpringLayout spring = new SpringLayout();
        JPanel layout = new JPanel(spring);
        Container contentPane = frame.getContentPane();
        JLabel label1 = new JLabel("Host address");
        JLabel label2 = new JLabel("port number");
        JLabel label3 = new JLabel("Name:");
        JTextField textField1 = new JTextField(15);
        JTextField textField2 = new JTextField(15);
        JTextField textField3 = new JTextField(15);
        JButton button = new JButton("Connect");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {doThis(textField1.getText(), textField2.getText(), textField3.getText());}
        });
        layout.add(label1);
        layout.add(label2);
        layout.add(label3);
        layout.add(textField1);
        layout.add(textField2);
        layout.add(textField3);
        layout.add(button);
        
        spring.putConstraint(SpringLayout.WEST, label1, 5, SpringLayout.WEST, layout);
        spring.putConstraint(SpringLayout.NORTH, label1, 5, SpringLayout.NORTH, layout);
        spring.putConstraint(SpringLayout.NORTH, textField1, 1, SpringLayout.NORTH, layout);
        spring.putConstraint(SpringLayout.WEST, textField1, 90, SpringLayout.WEST, label1);
        
        spring.putConstraint(SpringLayout.WEST, label2, 5, SpringLayout.WEST, layout);
        spring.putConstraint(SpringLayout.NORTH, label2, 10, SpringLayout.SOUTH, label1);
        spring.putConstraint(SpringLayout.NORTH, textField2, 1, SpringLayout.SOUTH, textField1);
        spring.putConstraint(SpringLayout.WEST, textField2, 90, SpringLayout.WEST, label2);
        
        spring.putConstraint(SpringLayout.WEST, label3, 5, SpringLayout.WEST, layout);
        spring.putConstraint(SpringLayout.NORTH, label3, 10, SpringLayout.SOUTH, label2);
        spring.putConstraint(SpringLayout.NORTH, textField3, 1, SpringLayout.SOUTH, textField2);
        spring.putConstraint(SpringLayout.WEST, textField3, 90, SpringLayout.WEST, label3);
        
        spring.putConstraint(SpringLayout.NORTH, button, 20, SpringLayout.SOUTH, textField3);
        spring.putConstraint(SpringLayout.WEST, button, 100, SpringLayout.WEST, layout);
        
        spring.putConstraint(SpringLayout.EAST, layout, 5, SpringLayout.EAST, textField3);
        spring.putConstraint(SpringLayout.SOUTH, layout, 5, SpringLayout.SOUTH, button);
        
        contentPane.add(layout);
        frame.pack();
        frame.setVisible(true);
        
    }
        
        
}
