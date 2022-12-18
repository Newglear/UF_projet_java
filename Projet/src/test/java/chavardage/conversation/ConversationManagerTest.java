package chavardage.conversation;

import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.message.WrongConstructorException;
import chavardage.networkManager.TCPConnect;
import chavardage.networkManager.TCPServeur;
import chavardage.userList.ListeUser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

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
    public void createConvTest() throws IOException, ConversationAlreadyExists, ConversationException, WrongConstructorException {
        TCPServeur serveur = new TCPServeur();
        InetAddress localhost = InetAddress.getLocalHost();
        Socket socket = new Socket(localhost, TCPServeur.PORT_TCP);
        ConversationManager.getInstance().createConversation(socket);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(new TCPMessage(3, TCPType.OuvertureSession, 5));
        out.close();
        socket.close();
        ConversationManager.getInstance().fermerConversation(5);
        serveur.interrupt();

    }

    @Test
    public void acceptTest() throws IOException, WrongConstructorException, InterruptedException {
        // attention on teste le accept (j'ai peur)
        TCPServeur serveur = new TCPServeur();
        serveur.setSubscriber(ConversationManager.getInstance());
        // j'envoie une demande de nouvelle conversation de 5
        InetAddress localhost = InetAddress.getLocalHost();
        Socket socket = new Socket(localhost, TCPServeur.PORT_TCP);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(new TCPMessage(3, TCPType.OuvertureSession, 5));
        out.close();
        socket.close();
        ConversationManager.getInstance().fermerConversation(5);
        sleep(100);
        serveur.interrupt();
    }



}
