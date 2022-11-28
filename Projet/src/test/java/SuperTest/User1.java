package SuperTest;

import Conversation.ConversationManager;
import UserList.ListeUser;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class User1 extends Thread{

    public void run(){
        ListeUser listeUser = new ListeUser();
        try {
            listeUser.addUser(2345, "romain", InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            ConversationManager.createConv(2345);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ConversationManager.getConv(2345).sendMessage("coucou user2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
