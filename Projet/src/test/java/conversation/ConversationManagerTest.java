package conversation;

import message.TCPMessage;
import message.TCPType;
import message.WrongConstructorException;
import networkManager.TCPSend;
import networkManager.ThreadTCPServeur;
import org.junit.Before;
import org.junit.Test;
import userList.AssignationProblemException;
import userList.ListeUser;
import userList.UserNotFoundException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

public class ConversationManagerTest {


    @Before
    public void clearInstance(){
        ConversationManager.getInstance().clear();
    }

    @Test
    public void createConvIntTest() throws IOException, ConversationAlreadyExists {
        ConversationManager conversations = ConversationManager.getInstance();
        ThreadTCPServeur tcpServeur = new ThreadTCPServeur();
        ListeUser.addUser(1, "romain", InetAddress.getLocalHost());
        conversations.createConv(1);
    }

    @Test (expected = ConversationAlreadyExists.class)
    public void createConvIntTwice() throws UnknownHostException, ConversationAlreadyExists {
        ConversationManager conversations = ConversationManager.getInstance();
        ThreadTCPServeur tcpServeur = new ThreadTCPServeur();
        ListeUser.addUser(1, "romain", InetAddress.getLocalHost());
        conversations.createConv(1);
        conversations.createConv(1);
    }

    @Test
    public void createConvSocketTest() throws IOException, WrongConstructorException {
        ThreadTCPServeur threadTCPServeur = new ThreadTCPServeur(); // le serveur crée la conversation
        Socket socket = new Socket(InetAddress.getLocalHost(), ThreadTCPServeur.PORT_TCP);
        TCPMessage message = new TCPMessage(1, TCPType.OuvertureSession);
        TCPSend.envoyerMessage(socket,message);
    }

    @Test // TODO ce test fait des trucs bizarres
    public void createConvSocketTwice() throws IOException, WrongConstructorException {
        ThreadTCPServeur threadTCPServeur = new ThreadTCPServeur(); // le serveur crée la conversation
        Socket socket = new Socket(InetAddress.getLocalHost(), ThreadTCPServeur.PORT_TCP);
        TCPMessage message = new TCPMessage(1, TCPType.OuvertureSession);
        TCPSend.envoyerMessage(socket,message);
        TCPSend.envoyerMessage(socket,message);
    }

    @Test
    public void getConvTest() throws UnknownHostException, AssignationProblemException, UserNotFoundException, ConversationAlreadyExists {
        ConversationManager conversations = ConversationManager.getInstance();
        ThreadTCPServeur tcpServeur = new ThreadTCPServeur();
        ListeUser.addUser(1, "romain", InetAddress.getLocalHost());
        conversations.createConv(1);
        Conversation conv = conversations.getConv(1);
        assertEquals(1, conv.getDestinataireId());
        assertEquals("romain", ListeUser.getUser(conv.getDestinataireId()).getPseudo()); // on reteste la liste user parce que pourquoi pas
    }
}
