/*
 Author: Rees de la Houssaye
 Date: 7/25/18
 Description: A server class for reading and writing a file sent by a client.
 */
import java.net.*;
import java.io.*;

public class FileTran
{
    private final static String fileOutput = "C:\\green";
    
    public static void main(String args[])
    {
        byte[] aByte = new byte[1]; //array used for reading in the data one byte at a time
        int bytesRead;
        try
        {
            ServerSocket serverSocket = new ServerSocket(6066);
            
            while(true)
            {
                System.out.println("Waiting...");
                Socket server = serverSocket.accept();
                System.out.println("Connected.");
                
                InputStream is = server.getInputStream();
                
                ByteArrayOutputStream bytes = new ByteArrayOutputStream(); // used to write from the aByte array
                
                if(is != null)
                {
                    FileOutputStream file = new FileOutputStream(fileOutput);
                    BufferedOutputStream buff = new BufferedOutputStream(file);
                    
                    bytesRead = is.read(aByte, 0, aByte.length);
                    do{
                        bytes.write(aByte);
                        bytesRead = is.read(aByte);
                    } while(bytesRead != -1);
                    buff.write(bytes.toByteArray());
                    buff.flush();
                    buff.close();
                    server.close();
                    break;
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
