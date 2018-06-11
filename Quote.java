/*
 * Author: Rees de la Houssaye
 * Date: 06/05/18
 * Description: A program that gets data from yahoo finances and then shows stock prices and other info. Uses JSoup library
 */
package quote;

/**
 *
 * @author juby
 * //make all frame spring 
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Quote
{
    private JFrame frame;
    private JButton button;
    private SpringLayout layout;
    private JTextField textField;
    private JLabel price;
    private JLabel name;
    private JLabel arrow;
    private ImageIcon icon;
    private JLabel percent;
    
    public Quote()
    {
        makeFrame();
    }
    
    private void search(String text)
    {
        try
        {
            text = text.toUpperCase();
            Document doc = Jsoup.connect("https://finance.yahoo.com/quote/" + text + "?p=" + text).get();
          
            Element tempP = doc.select("span[data-reactid='35']").first();
            price.setText(tempP.text() + " USD");
            
            Element tempN = doc.select("h1[data-reactid='7']").first();
            name.setText(tempN.text());
           
            
            Element tempA = doc.select("div[data-reactid='25']").select("span[data-reactid='36']").first();
  
           
            if(tempA.text().contains("+"))
            {
                icon = new ImageIcon("/Users/reesdelahoussaye/Documents/Java/greenarrow.png");
                arrow.setIcon(icon);
                percent.setText(tempA.text());
            }
            else
            {
                icon = new ImageIcon("/Users/reesdelahoussaye/Documents/Java/redarrow.jpg");
                arrow.setIcon(icon);
                percent.setText(tempA.text());
            }
          
            if(tempN.text().length() > 42)
            {
                layout.putConstraint(SpringLayout.EAST, frame.getContentPane(), tempN.text().length()+50, SpringLayout.EAST, button);
                frame.pack();
            }
            else
            {
                layout.putConstraint(SpringLayout.EAST, frame.getContentPane(), 5, SpringLayout.EAST, button);
                frame.pack();
            }
           
            textField.setText("");
            
            
        }
        catch(Exception e)
        {
            price.setText("Stock does not exist!");
            arrow.setIcon(new ImageIcon(""));
            percent.setText("");
            name.setText("");
            textField.setText("");
            return;
        }
    }
    
    private void makeFrame()
    {
        frame = new JFrame("Quote Tracker");
       Container contentPane = frame.getContentPane();
        layout = new SpringLayout();
        contentPane.setLayout(layout);
        
        textField = new JTextField(15);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { search(e.getActionCommand()); }
        });
        button = new JButton("Search");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { search(textField.getText()); }
        });
        
        contentPane.add(textField);
        contentPane.add(button);
        percent = new JLabel("");
        
        layout.putConstraint(SpringLayout.WEST, textField, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, textField, 5, SpringLayout.NORTH, contentPane);
        
        layout.putConstraint(SpringLayout.WEST, button, 5, SpringLayout.EAST, textField);
        layout.putConstraint(SpringLayout.NORTH, button, 5, SpringLayout.NORTH, contentPane);
        
        layout.putConstraint(SpringLayout.EAST, contentPane, 5, SpringLayout.EAST, button);
        layout.putConstraint(SpringLayout.SOUTH, contentPane, 5, SpringLayout.SOUTH, button);
        
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panel2 = new JPanel(new BorderLayout());
        price = new JLabel("");
        name = new JLabel("");
        arrow = new JLabel("");
        panel2.add(name, BorderLayout.WEST);
        panel2.add(arrow, BorderLayout.CENTER);
        panel2.add(percent,BorderLayout.SOUTH);
        panel.add(panel2, BorderLayout.CENTER);
        panel.add(price, BorderLayout.NORTH);
        
        contentPane.add(panel);
        layout.putConstraint(SpringLayout.WEST, panel, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, panel, 50, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, contentPane, 90, SpringLayout.SOUTH, button);
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    
    
    
    
    public static void main(String args[])
    {
        Quote quote = new Quote();
    }
}

