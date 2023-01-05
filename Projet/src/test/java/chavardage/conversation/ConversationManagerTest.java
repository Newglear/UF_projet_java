package chavardage.conversation;

import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.message.WrongConstructorException;
import chavardage.networkManager.TCPReceiveData;
import chavardage.networkManager.TCPSendData;
import chavardage.userList.ListeUser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
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
    public void createConvTest() throws IOException, ConversationAlreadyExists, ConversationException, InterruptedException, WrongConstructorException {
        // TODO
        ConversationManager convManager = ConversationManager.getInstance();
        ServerSocket serverSocket = new ServerSocket(4987);
        Socket socketEnvoi = new Socket(InetAddress.getLocalHost(),4987);
        Socket socketReception = serverSocket.accept();
        TCPSendData sendData = new TCPSendData(socketEnvoi);
        convManager.createConversation(socketReception);
        sendData.envoyer(new TCPMessage(3, TCPType.OuvertureSession,6));
    }

    @Test
    public void acceptTest() {
        // TODO mettre le accept en suscripteur du serveur
    }



}
