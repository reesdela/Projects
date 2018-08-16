import java.net.*;
import java.io.*;

public class ChatClientThread extends Thread
{
    private ChatClient client;
    private Socket socket;
    private TextChat textChat;
    
    public ChatClientThread(ChatClient _client, Socket _socket, TextChat _textChat)
    {
        client = _client;
        socket = _socket;
        textChat = _textChat;
        start();
    }
    
    public void run()
    {
        try
        {
        InputStream ind = socket.getInputStream();
        DataInputStream in = new DataInputStream(ind);
        while(true)
        {
            textChat.displayText(in.readUTF());
        }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
