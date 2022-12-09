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
    public void recoitRequeteTest() throws IOException, InterruptedException {
        InetAddress addressUserLanceRequete = InetAddress.getByName("insa-20551.insa-toulouse.fr"); // TODO
        ListeUser.addUser(3456, "aude", addressUserLanceRequete);
        ThreadTcpReceiveConnection threadRcv = new ThreadTcpReceiveConnection();
        sleep(10000);
        ConversationManager.getConv(3456).sendMessage("coucou user1");

    }
}
