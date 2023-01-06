package chavardage.conversation;

import chavardage.message.TCPMessage;
import chavardage.userList.ListeUser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
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
    public void acceptTest() {
        // TODO mettre le accept en suscripteur du serveur
    }

    // TODO tester les add et les puts parce que visiblement ils marchent pas



}
