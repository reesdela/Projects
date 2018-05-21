/*
 Author: Rees de la Houssaye
 Date: 5/20/18
 Descript: A notepad program that uses a JTextArea(Yes i know its the wrong structure to use) with syntax highlighting(this is still error prone and I am planning on fixing it) and the ability to open and save documents
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import javax.swing.text.*;
import java.util.Locale;

//concunrency

public class Notepad
{
    private JFrame frame;
    private JTextArea textArea;
    private JFileChooser fileChoose = new JFileChooser(System.getProperty("user.dir"));
    private JLabel label;
    private Highlighter.HighlightPainter redPaint = new DefaultHighlighter.DefaultHighlightPainter(Color.red);
    private Highlighter.HighlightPainter normalPaint = new DefaultHighlighter.DefaultHighlightPainter(Color.black);
    
    public Notepad() throws InterruptedException
    {
        frame = new JFrame("Untitled");
        textArea = new JTextArea(35,40);
        textArea.setWrapStyleWord(true);
        makeFrame();
        while(true)
        {
            Thread.sleep(10);
            wordCount();
            Thread.sleep(10);
            if(textArea.getText().isEmpty())
            {
                
            }
            else if(Character.isWhitespace(textArea.getText().charAt(textArea.getCaretPosition()-1)))
            {
                readySpell();
            }
        }
    }
    
   /* public Notepad(File file) throws IOException, InterruptedException
    {
        textArea = new JTextArea(35,40);
        textArea.read(new BufferedReader(new FileReader(file.getPath())), file.getPath());
        textArea.setWrapStyleWord(true);
        makeFrame();
        while(true)
        {
            Thread.sleep(10);
            wordCount();
        }
    }*/
    
    private void readySpell()
    {
        int val = textArea.getText().lastIndexOf(' ', textArea.getCaretPosition()-2);
        if(val == -1)
        {
            try
            {
                String word = textArea.getText().substring(0, textArea.getCaretPosition()-1);
                if(!(spellCheck(word)))
                {
                    try
                    {
                        textArea.getHighlighter().addHighlight(0, textArea.getCaretPosition()-1, redPaint);
                    }
                    catch(BadLocationException e)
                    {
                        System.out.println("Error");
                    }
                }
            }
            catch(FileNotFoundException e)
            {
                System.out.println("ERROR");
            }
        }
        else
        {
            try
            {
                String word = textArea.getText().substring(val+1, textArea.getCaretPosition()-1);
                if(!(spellCheck(word)))
                {
                    try
                    {
                        textArea.getHighlighter().addHighlight(val+1, textArea.getCaretPosition()-1, redPaint);
                    }
                    catch(BadLocationException e)
                    {
                        System.out.println("Error");
                    }
                }
            }
            catch(FileNotFoundException e)
            {
                System.out.println("ERROR");
            }
        }
    }
    
    private boolean spellCheck(String word) throws FileNotFoundException
    {
        word = word.toLowerCase();
        System.out.println(word);
        try
        {
            BufferedReader read = new BufferedReader(new FileReader("/Users/reesdelahoussaye/Documents/web2.txt"));
            String str;
            while((str = read.readLine()) != null)
            {
                //System.out.println(word);
                //System.out.println(str);
                if(word.length()-1 == -1)
                {
                    return true;
                }
                if(str.equals(word) || str.equals(word.substring(0,word.length()-1)))
                {
                    return true;
                }
            }
            read.close();
        }
        catch(IOException e)
        {
            System.out.println("ERROR");
        }
        return false;
    }
    
    private void wordCount()
    {
        int counter = 0;
        int i = 0;
        boolean delim = false;
        while(i < textArea.getText().length())
        {
            if(!(Character.isWhitespace(textArea.getText().charAt(i))) && delim == false)
            {
                counter++;
                delim = true;
            }
            if(Character.isWhitespace(textArea.getText().charAt(i)))
            {
                delim = false;
            }
            i++;
        }
        label.setText(Integer.toString(counter) + " words");
    }
    
    private void makeFrame()
    {
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        label = new JLabel("");
        contentPane.add(label, BorderLayout.SOUTH);
        makeMenu();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dim.getWidth() - frame.getWidth()) / 4);
        int y = (int) ((dim.getHeight() - frame.getHeight()) / 5);
        frame.setLocation(x,y);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    private void makeMenu()
    {
        int short_cut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem openItem = new JMenuItem("Open");
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, short_cut));
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { openFile(); }
        });
        fileMenu.add(openItem);
        JMenuItem saveItem = new JMenuItem("Save...");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, short_cut));
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { try{ saveFile(); } catch(IOException err) { JOptionPane.showMessageDialog(frame, "Error saving file!", "", JOptionPane.ERROR_MESSAGE);} }
        });
        fileMenu.add(saveItem);
        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){ textArea.setText(""); }
        });
        fileMenu.add(newItem);
    }
    
    private void openFile()
    {
        try
        {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text documents", "txt");
        fileChoose.setFileFilter(filter);
        int val = fileChoose.showOpenDialog(null);
        if(val != JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        File file = fileChoose.getSelectedFile();
        loadFile(file);
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(frame, "Error locating file!", "", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadFile(File file) throws IOException
    {
        textArea.read(new BufferedReader(new FileReader(file.getPath())), file.getPath());
        frame.setTitle(file.getName());
            while(textArea.getCaretPosition() < textArea.getText().length())
            {
                if(Character.isWhitespace(textArea.getText().charAt(textArea.getCaretPosition())))
                {
                    textArea.setCaretPosition(textArea.getCaretPosition()+1);
                    readySpell();
                    textArea.setCaretPosition(textArea.getCaretPosition()-1);
                }
                textArea.setCaretPosition(textArea.getCaretPosition()+1);
            }
    }
    
    private void saveFile() throws IOException
    {
        int val = fileChoose.showSaveDialog(frame);
        if(val != JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        File file = fileChoose.getSelectedFile();
        FileWriter writer = new FileWriter(file.getPath());
        writer.write(textArea.getText());
        writer.close();
        frame.setTitle(file.getName());
    }
    
    public static void main(String args[])
    {
        try
        {
            Notepad note = new Notepad();
        }
        catch(InterruptedException e)
        {
            
        }
    }
}
