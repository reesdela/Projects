import java.net.*;
import java.io.*;

public class ServerThread extends Thread
{
    private Server server;
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private String word;
    private String name;
    private boolean first = false;
    private DataInputStream ind;
    private DataOutputStream outd;
    
    public ServerThread(Server server1, Socket socket1)
    {
        server = server1;
        socket = socket1;
    }
    
    public String getNames()
    {
        return name;
    }
    
    public void send(String message)
    {
        try
        {
            out = socket.getOutputStream();
            outd = new DataOutputStream(out);
            outd.writeUTF(message);
            outd.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void close() throws IOException
    {
        if(socket != null)
        {
            socket.close();
        }
        if(ind != null)
        {
            ind.close();
        }
        if(outd != null)
        {
            outd.close();
        }
    }
    
    public void run()
    {
        try
        {
            in = socket.getInputStream();
            ind = new DataInputStream(in);
            word = ind.readUTF();
            if(first != true)
            {
                for(int i = 0; i < word.length(); i++)
                {
                    if(word.charAt(i) == ' ')
                    {
                        name = word.substring(0, i);
                        break;
                    }
                }
                first = true;
                server.writeClient(word);
                word = ind.readUTF();
            }
        while(!(word.equals("bye")))
        {
            System.out.println(word);
            server.writeClient(word);
            word = ind.readUTF();
        }
            System.out.println(word);
            server.remove(this);
            return;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}

