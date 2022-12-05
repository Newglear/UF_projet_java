package NetworkAndConversationTest;

import Conversation.ConversationManager;
import NetworkManager.ThreadTcpReceiveConnection;
import UserList.ListeUser;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

public class UserRecoitRequeteTest {

    @Test
    public void recoitRequeteTest() throws UnknownHostException, InterruptedException {
        InetAddress addressUserLanceRequete = InetAddress.getByName("insa-20670.insa-toulouse.fr"); // TODO
        ThreadTcpReceiveConnection threadRcv = new ThreadTcpReceiveConnection();
        ListeUser.addUser(3456, "aude", addressUserLanceRequete);
        sleep(30000);
        try {
            ConversationManager.getConv(3456).sendMessage("coucou user1");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
