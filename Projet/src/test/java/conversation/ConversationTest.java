package conversation;

import networkManager.ThreadTCPServeur;
import org.junit.Test;
import userList.AssignationProblemException;
import userList.ListeUser;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

public class ConversationTest {

    @Test
    public void constructorIntTest() throws Exception {
        ThreadTCPServeur tcpServeur = new ThreadTCPServeur(); // je mets le serveur en localhost aussi
        ListeUser.addUser(1, "romain", InetAddress.getLocalHost());
        Conversation conversation1 = new Conversation(1);
        conversation1.fermerConversation();
        tcpServeur.interrupt();
    }

    @Test
    public void constructorSocketTest() throws Exception {
        EnvoyerMessage envoyerMessage = new EnvoyerMessage(); // envoi d'un message d'ouverture de conversation
        ServerSocket ecoute = new ServerSocket(ThreadTCPServeur.PORT_TCP); // mini serveur en localhost
        Socket socket = ecoute.accept();
        Conversation conversation = new Conversation(socket);
        conversation.fermerConversation();
        ecoute.close();
    }


    @Test
    public void sendMessageTest() throws IOException {
        ThreadTCPServeur tcpServeur = new ThreadTCPServeur(); // je mets le serveur en localhost aussi
        ListeUser.addUser(1, "romain", InetAddress.getLocalHost());
        Conversation conversation1 = new Conversation(1);
        conversation1.sendMessage("coucou");
        conversation1.fermerConversation();
        // c'est un peu chelou parce que c'est la même conversation qui envoie et qui reçoit, mais c'est ok
        tcpServeur.interrupt();
    }





}
