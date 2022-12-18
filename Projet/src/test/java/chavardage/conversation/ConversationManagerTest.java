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
    public void createConvTest() throws ServerAlreadyOpen {
        // TCPServeur serveur = new TCPServeur();

    }

    /* @Test
    public void acceptTest() throws IOException, ServerAlreadyOpen, WrongConstructorException {
        // attention on teste le accept (j'ai peur)
        TCPServeur serveur = new TCPServeur();
        // serveur.setSubscriber(ConversationManager.getInstance());
        // j'envoie une demande de nouvelle conversation de 5
        InetAddress localhost = InetAddress.getLocalHost();
        Socket socket = new Socket(localhost, TCPServeur.PORT_TCP);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(new TCPMessage(3,TCPType.OuvertureSession, 5));
        out.close();
        socket.close();
    }*/



}
