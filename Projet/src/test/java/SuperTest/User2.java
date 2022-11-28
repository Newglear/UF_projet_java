package SuperTest;

import Conversation.Conversation;
import Conversation.ConversationManager;
import NetworkManager.ThreadTcpReceiveConnection;
import UserList.ListeUser;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class User2 implements Runnable{

    public void run(){
        ConversationManager conversationManager = new ConversationManager();
        ListeUser listeUser = new ListeUser();
        try {
            listeUser.addUser(3456, "aude", InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ThreadTcpReceiveConnection threadRcv = new ThreadTcpReceiveConnection();
        // TODO  :utiliser le conversationManager
        // ConversationManager.getConv(bidule).send("coucou");

    }
}
