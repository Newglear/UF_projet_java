package NetworkManager;
import Conversation.Conversation;
import Conversation.ConversationManager;
import Message.TCPMessage;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.*;

public class ThreadTcpReceiveConnection extends Thread{
    public final static int portTcpReceive = 4753;
    public static boolean isFinished;

    public ThreadTcpReceiveConnection(){
        start();
    }
    public void run(){
        try {
            ServerSocket portEcoute = new ServerSocket(portTcpReceive);
            while (!isFinished) {
                Socket connexion = portEcoute.accept();
                ConversationManager.createConv(connexion);
            }
        }catch (Exception e){e.printStackTrace();}
    }
}
