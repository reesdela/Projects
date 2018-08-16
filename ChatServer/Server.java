import java.net.*;
import java.io.*;

public class Server
{
    private ServerSocket serverSocket;
    private ServerThread client[] = new ServerThread[10];
    private int numC = 0;
    
    public Server(int port)
    {
        try
        {
            serverSocket = new ServerSocket(port);
            start();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void start()
    {
        while(true)
        {
            try
            {
                System.out.println("Waiting...");
                newClient(serverSocket.accept());
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public synchronized void writeClient(String message)
    {
        for(int x = 0; x < numC; x++)
        {
            client[x].send(message);
        }
    }
    
    public void newClient(Socket socket)
    {
            System.out.println("Connected!");
            numC++;
            client[numC-1] = new ServerThread(this, socket);
            client[numC-1].start();
        
    }
    
    public void remove(ServerThread sThread)
    {
        if(numC == 1 || numC == 10)
        {
            client[numC-1] = null;
            numC--;
        }
        for(int i = 0; i < numC; i++)
        {
            if(sThread.getNames().equals(client[i].getNames()))
            {
                for(int x = i; x <= numC; x++)
                {
                    client[x] = client[x+1];
                }
                numC = numC - 1;
            }
                
        }
        writeClient(sThread.getNames() + " has left");
        try
        {
            sThread.close();
            //sThread.stop();
        }
        catch(IOException e)
        {
            
        }
    }
    
    public static void main(String args[])
    {
        Server server = new Server(6066);
    }
}
