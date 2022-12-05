package NetworkAndConversationTest;

import Conversation.ConversationManager;
import UserList.ListeUser;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UserRecoitRequeteTest {

    @Test
    public void recoitRequeteTest() throws UnknownHostException {
        InetAddress addressUserLanceRequete = InetAddress.getByName(""); // TODO
        ListeUser.addUser(3456, "aude", addressUserLanceRequete);
        try {
            ConversationManager.getConv(3456).sendMessage("coucou user1");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
