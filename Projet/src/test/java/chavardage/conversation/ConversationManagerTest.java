package chavardage.conversation;

import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.message.WrongConstructorException;
import chavardage.networkManager.*;
import chavardage.userList.ListeUser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ConversationManagerTest {


    @Before
    public void clearInstance(){
        ConversationManager.getInstance().clear();
        ListeUser.getInstance().setMyId(3);
    }

    @Test
    public void addConvTest() {
        ConversationManager convManager = ConversationManager.getInstance();
        Conversation conversation1 = new Conversation(5);
        Conversation conversation2 = new Conversation(6);
        convManager.addConv(5, conversation1);
        convManager.addConv(6, conversation2);

    }

    @Test
    public void acceptTest() throws IOException, ServerAlreadyOpen, WrongConstructorException {
        // attention on teste le accept (j'ai peur)
        TCPServeur serveur = new TCPServeur();
        serveur.setSubscriber(ConversationManager.getInstance());
        // j'envoie une demande de nouvelle conversation de 5
        InetAddress localhost = InetAddress.getLocalHost();
        Socket socket = new Socket(localhost, TCPServeur.PORT_TCP);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(new TCPMessage(3,TCPType.OuvertureSession, 5));
        out.close();
        socket.close();
    }


    /* @Test
    public void createConvIntTest() throws IOException, ConversationAlreadyExists {
        ConversationManager conversations = ConversationManager.getInstance();
        TCPServeur tcpServeur = new TCPServeur();
        ListeUser listeUser = ListeUser.getInstance();
        listeUser.addUser(1, "romain", InetAddress.getLocalHost());
        conversations.createConv(1);
    }

    @Test (expected = ConversationAlreadyExists.class)
    public void createConvIntTwice() throws UnknownHostException, ConversationAlreadyExists {
        ConversationManager conversations = ConversationManager.getInstance();
        TCPServeur tcpServeur = new TCPServeur();
        ListeUser listeUser = ListeUser.getInstance();
        listeUser.addUser(1, "romain", InetAddress.getLocalHost());
        conversations.createConv(1);
        conversations.createConv(1);
    }*/

    /*@Test
    public void createConvSocketTest() throws IOException, WrongConstructorException {
        ThreadTCPServeur threadTCPServeur = new ThreadTCPServeur(); // le serveur crée la conversation
        Socket socket = new Socket(InetAddress.getLocalHost(), ThreadTCPServeur.PORT_TCP);
        TCPMessage message = new TCPMessage(1, TCPType.OuvertureSession);
        TCPSend.envoyerMessage(message, );
    }

    @Test // TODO ce test fait des trucs bizarres
    public void createConvSocketTwice() throws IOException, WrongConstructorException {
        ThreadTCPServeur threadTCPServeur = new ThreadTCPServeur(); // le serveur crée la conversation
        Socket socket = new Socket(InetAddress.getLocalHost(), ThreadTCPServeur.PORT_TCP);
        TCPMessage message = new TCPMessage(1, TCPType.OuvertureSession);
        TCPSend.envoyerMessage(message, );
        TCPSend.envoyerMessage(message, );
    }

    @Test
    public void getConvTest() throws UnknownHostException, AssignationProblemException, UserNotFoundException, ConversationAlreadyExists {
        ConversationManager conversations = ConversationManager.getInstance();
        ThreadTCPServeur tcpServeur = new ThreadTCPServeur();
        ListeUser listeUser = ListeUser.getInstance();
        listeUser.addUser(1, "romain", InetAddress.getLocalHost());
        conversations.createConv(1);
        Conversation conv = conversations.getConv(1);
        assertEquals(1, conv.getDestinataireId());
        assertEquals("romain", listeUser.getUser(conv.getDestinataireId()).getPseudo()); // on reteste la liste user parce que pourquoi pas
    }*/
}
