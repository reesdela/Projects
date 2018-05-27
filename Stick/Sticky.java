/*
 Author: Rees de la Houssaye
 Date: 5/26/18
 Description: A simple stick notes-like program. You can add images and sound as well as import and export text(much like the sticky notes program)
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javafx.scene.media.*;

public class Sticky
{
    private JFrame frame;
    private JTextPane textPane;
    private JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
    private static Point POINT = new Point(0,0);
    private static int frameUp = 0;
    private static Clip clip;
    private static boolean musicOn = false;
    private boolean isPlaying = false;
    
   public Sticky()
    {
        textPane = new JTextPane();
        textPane.setBackground(Color.yellow);
        makeFrame();
    }
    
    private void makeFrame()
    {
        frame = new JFrame("");
        Container contentPane = frame.getContentPane();
        frame.setLayout(new BorderLayout());
        Dimension dem = new Dimension(250,220);
        textPane.setPreferredSize(dem);
        JScrollPane scrollPane = new JScrollPane(textPane);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        makeMenu();
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) { doThis(); }
        });
        frame.pack();
    }
    
    public void doThis()
    {
        if(isPlaying)
        {
            Sticky.clip.close();
            Sticky.frameUp--;
            Sticky.musicOn = false;
        }
        else
        {
            Sticky.frameUp--;
        }
    }
    
    private void makeMenu()
    {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem newItem = new JMenuItem("New Note");
        newItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { newNote(); }
        });
        fileMenu.add(newItem);
        JMenuItem importItem = new JMenuItem("Import Text");
        importItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { importText(); }
        });
        fileMenu.add(importItem);
        JMenuItem exportItem = new JMenuItem("Export Text");
        exportItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { exportText(); }
        });
        fileMenu.add(exportItem);
        
        JMenu insertMenu = new JMenu("Insert...");
        JMenuItem imageInsert = new JMenuItem("Image");
        imageInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { addImage(); }
        });
        insertMenu.add(imageInsert);
        JMenuItem soundInsert = new JMenuItem("Sound");
        soundInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { addSound(); }
        });
        insertMenu.add(soundInsert);
        fileMenu.add(insertMenu);
        
    }
    
    private void addSound()
    {
        int val = chooser.showOpenDialog(frame);
        if(val != JFileChooser.APPROVE_OPTION)
        {
            return;
        }
       // if(!chooser.getSelectedFile().getName().contains(".wav"))
        //{
            //error
         //   return;
        //}
        ImageIcon icon = new ImageIcon("sound.png");
        JButton button = new JButton(icon);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { playSound(chooser.getSelectedFile()); }
        });
        textPane.insertComponent(button);
    }
    
    private void playSound(File file)
    {
        if(Sticky.musicOn)
        {
            Sticky.musicOn = false;
            isPlaying = false;
            Sticky.clip.close();
            return;
        }
        try
        {
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            Sticky.clip = AudioSystem.getClip();
            Sticky.clip.open(audio);
            Sticky.clip.start();
            Sticky.musicOn = true;
            isPlaying = true;
        }
        catch(UnsupportedAudioFileException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }
    
    private void addImage()
    {
        int val = chooser.showOpenDialog(frame);
        if(val != JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        ImageIcon icon = new ImageIcon(chooser.getSelectedFile().getPath());
        textPane.insertIcon(icon);
        textPane.setCaretPosition(textPane.getText().length());
    }
    
    private void exportText()
    {
        try
        {
            int val = chooser.showSaveDialog(frame);
            if(val != JFileChooser.APPROVE_OPTION)
            {
                return;
            }
            File file = chooser.getSelectedFile();
            FileWriter writer = new FileWriter(file.getPath() + ".txt");
            writer.write(textPane.getText());
            writer.close();
        }
        catch(IOException e)
        {
            //jtextpane
        }
    }
    
    private void importText()
    {
        int val = chooser.showOpenDialog(frame);
        if(val != JFileChooser.APPROVE_OPTION)
        {
            //jtextpane
            return;
        }
        File file = chooser.getSelectedFile();
        try
        {
            textPane.read(new BufferedReader(new FileReader(file)), file);
            textPane.setCaretPosition(textPane.getText().length());
        }
        catch(IOException e)
        {
            //jtextpane
        }
    }
    
    private void newNote()
    {
        Sticky.POINT = frame.getLocation();
        if(Sticky.POINT.getY() >= 500)
        {
            Sticky.POINT = new Point((int)Sticky.POINT.getX()+300, 0);
        }
        else
        {
            Sticky.POINT = new Point((int)Sticky.POINT.getX(), (int)Sticky.POINT.getY()+250);
        }
        Sticky stick = new Sticky();
        stick.frame.setLocation(Sticky.POINT);
        stick.frame.setVisible(true);
        Sticky.frameUp++;
    }
    
    private static void openFrame()
    {
        if(Sticky.frameUp == 0)
        {
            Sticky stick = new Sticky();
            stick.frame.setVisible(true);
            Sticky.frameUp = 1;
        }
    }
    
    public static void main(String args[])
    {
        try
        {
            SystemTray tray = SystemTray.getSystemTray();
            ImageIcon image = new ImageIcon("note.jpg");
            TrayIcon icon = new TrayIcon(image.getImage());
            icon.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) { openFrame(); }
            });
            tray.add(icon);
        }
    catch(AWTException e)
    {
        return;
    }
        Sticky.musicOn = false;
        Sticky stick = new Sticky();
        stick.frame.setVisible(true);
        Sticky.frameUp = 1;
    }
}
