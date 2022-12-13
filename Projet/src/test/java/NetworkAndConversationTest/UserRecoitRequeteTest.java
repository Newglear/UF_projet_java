package NetworkAndConversationTest;

import Conversation.Conversation;
import Conversation.ConversationManager;
import NetworkManager.ThreadTcpReceiveConnection;
import UserList.ListeUser;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import static NetworkManager.ThreadTcpReceiveConnection.portTcpReceive;
import static java.lang.Thread.sleep;

public class UserRecoitRequeteTest {

    @Test
    public void recoitRequeteTest() throws Exception {
        InetAddress addressUserLanceRequete = InetAddress.getByName("insa-20551.insa-toulouse.fr"); // TODO
        ListeUser.addUser(3456, "aude", addressUserLanceRequete);
        ServerSocket portEcoute = new ServerSocket(portTcpReceive);
        Socket connexion = portEcoute.accept();
        Conversation conversation = new Conversation(connexion);
        conversation.sendMessage("coucou user 1");
    }
}
