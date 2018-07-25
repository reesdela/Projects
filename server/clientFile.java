/*
 Author: Rees de la Houssaye
 Date: 7/25/18
 Description: a client program that sends a selected file (txt or image) to the server.
 */
import java.net.*;
import java.io.*;

public class clientFile
{
    public static void main(String[] args)
    {
        String serverName = args[0];
        int port = Integer.parseInt(args[1]);
        File tranFile = new File(args[2]);
        
        try
        {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);
            BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
            
            byte[] arry = new byte[(int) tranFile.length()];
            
            FileInputStream fin = new FileInputStream(tranFile);
            
            BufferedInputStream in = new BufferedInputStream(fin);
            
            in.read(arry, 0, arry.length);
            out.write(arry, 0, arry.length);
            out.flush();
            out.close();
            
            
        }
        catch(IOException e)
        {
            System.out.println("fuck");
        }
    }
}
