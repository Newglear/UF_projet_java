package chavardage.conversation;

import chavardage.AssignationProblemException;
import chavardage.IllegalConstructorException;
import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.networkManager.TCPSend;
import chavardage.networkManager.TCPSendData;
import chavardage.networkManager.TCPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConversationManagerTest {


    @Before
    public void clearInstance(){
        ConversationManager.getInstance().clear();
        ListeUser.getInstance().setMyId(3);
    }


    @Test
    public void createConvTest() throws IOException, ConversationAlreadyExists, ConversationException, ConversationDoesNotExist {
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
    public void openConversationTest() throws IOException, IllegalConstructorException, UserNotFoundException, AssignationProblemException, ConversationAlreadyExists, ConversationDoesNotExist {
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
    public void getSendDataTest() throws IOException, ConversationDoesNotExist, NetworkException {
        int port = 9387;
        ConversationManager conversationManager = ConversationManager.getInstance();
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socketDist = new Socket(InetAddress.getLocalHost(),port);
        Socket socket = serverSocket.accept();
        TCPSendData sendData = new TCPSendData(socket);
        conversationManager.addSendData(1,sendData);
        conversationManager.getSendData(1).envoyer(new TCPMessage(1,2,"coucou"));
    }

    @Test (expected = ConversationDoesNotExist.class)
    public void removeTest() throws ConversationDoesNotExist {
        ConversationManager conversationManager = ConversationManager.getInstance();
        conversationManager.addConv(3,new Conversation(3));
        conversationManager.safeRemove(3);
        conversationManager.getConv(3);
    }



}
