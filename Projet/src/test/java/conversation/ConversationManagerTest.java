package conversation;

import networkManager.ThreadTCPServeur;
import org.junit.Test;
import userList.AssignationProblemException;
import userList.ListeUser;
import userList.UserNotFoundException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

public class ConversationManagerTest {

    @Test
    public void createConvIntTest() throws IOException {
        ThreadTCPServeur tcpServeur = new ThreadTCPServeur();
        ListeUser.addUser(1, "romain", InetAddress.getLocalHost());
        ConversationManager.createConv(1);
    }

    @Test
    public void createConvSocketTest() {
        EnvoyerMessage envoyerMessage = new EnvoyerMessage(); // envoi d'un message d'ouverture de conversation
        ThreadTCPServeur threadTCPServeur = new ThreadTCPServeur();
    }

    @Test
    public void getConvTest() throws UnknownHostException, AssignationProblemException, UserNotFoundException {
        ThreadTCPServeur tcpServeur = new ThreadTCPServeur();
        ListeUser.addUser(1, "romain", InetAddress.getLocalHost());
        ConversationManager.createConv(1);
        Conversation conv = ConversationManager.getConv(1);
        assertEquals(1, conv.getDestinataireId());
        assertEquals("romain", ListeUser.getUser(conv.getDestinataireId()).getPseudo()); // on reteste la liste user parce que pourquoi pas
    }
}
