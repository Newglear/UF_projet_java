package chavardage.conversation;

import chavardage.AssignationProblemException;
import chavardage.IllegalConstructorException;
import chavardage.message.TCPMessage;
import chavardage.message.WrongConstructorException;
import chavardage.networkManager.TCPSendData;
import chavardage.userList.ListeUser;
import chavardage.userList.UserNotFoundException;
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
    public void createConvTest() throws IOException, ConversationAlreadyExists, ConversationException, InterruptedException, ConversationDoesNotExist {
        ConversationManager convManager = ConversationManager.getInstance();
        ServerSocket serverSocket = new ServerSocket(4987);
        SocketDistant socketDistant = new SocketDistant();
        socketDistant.start();
        Socket socketReception = serverSocket.accept();
        convManager.createConversation(socketReception);
        convManager.getSendData(6).envoyer(new TCPMessage(6, 3,"coucou"));
        convManager.fermerConversation(6);
    }

    @Test
    public void openConversationTest() throws IOException, IllegalConstructorException, UserNotFoundException, AssignationProblemException, ConversationAlreadyExists, WrongConstructorException, ConversationDoesNotExist {
        int port = 5784;
        ListeUser liste = new ListeUser(true);
        ConversationManager conversationManager = new ConversationManager(true,liste,port);
        ServerSocket serverSocket = new ServerSocket(port);
        liste.setMyself(1,"Aude");
        liste.addUser(3,"Romain",InetAddress.getLocalHost());
        conversationManager.openConversation(3);
        conversationManager.getSendData(3).envoyer(new TCPMessage(3,1,"coucou"));
    }

    @Test
    public void acceptTest() {
        // TODO mettre le accept en suscripteur du serveur
    }

    @Test
    public void getSendDataTest() throws IOException, ConversationDoesNotExist {
        int port = 9387;
        ConversationManager conversationManager = ConversationManager.getInstance();
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socketDist = new Socket(InetAddress.getLocalHost(),port);
        Socket socket = serverSocket.accept();
        TCPSendData sendData = new TCPSendData(socket);
        conversationManager.addSendData(1,sendData);
        conversationManager.getSendData(1).envoyer(new TCPMessage(1,2,"coucou"));
    }



}
