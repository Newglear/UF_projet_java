package SuperTest;

import Conversation.ConversationManager;
import UserList.ListeUser;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UserLanceRequeteTest {

    @Test
    public void lanceRequeteTest() throws UnknownHostException{
        InetAddress addressUserRecoitRequete = InetAddress.getByName(""); // TODO
        ListeUser.addUser(2345, "romain", addressUserRecoitRequete);
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
