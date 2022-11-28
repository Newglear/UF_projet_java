package SuperTest;

import Conversation.Conversation;
import Conversation.ConversationManager;
import NetworkManager.ThreadTcpReceiveConnection;
import UserList.ListeUser;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class User2 extends Thread{

    public void run(){
        ListeUser listeUser = new ListeUser();
        try {
            listeUser.addUser(3456, "aude", InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ThreadTcpReceiveConnection threadRcv = new ThreadTcpReceiveConnection();
        try {
            ConversationManager.getConv(3456).sendMessage("coucou user1");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
