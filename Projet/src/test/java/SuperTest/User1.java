package SuperTest;

import Conversation.Conversation;
import NetworkManager.TcpSend;
import UserList.ListeUser;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class User1 implements Runnable{

    public void run(){
        ListeUser listeUser = new ListeUser();
        try {
            listeUser.addUser(2345, "romain", InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            Conversation conv = new Conversation(2345);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
