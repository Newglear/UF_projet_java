package chavardage.conversation;

import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.networkManager.TCPReceiveData;
import chavardage.networkManager.TCPSendData;
import chavardage.userList.ListeUser;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ConversationTest {



    @Test
    public void conversationTest() {
        ListeUser.getInstance().setMyId(3); // j'ai l'id 3
        Conversation conversation = new Conversation(5); // je crée une conversation avec 5
        conversation.sendMessage("coucou");
        assertEquals(5, conversation.getDestinataireId());
        conversation.accept(new TCPMessage(5,3, "hola"));
        // on teste que les exceptions se lancent bien quand il faut
        System.out.println("2 conversationException sont attendues : ");
        conversation.accept(new TCPMessage(5, 3, TCPType.OuvertureSession));
        conversation.accept(new TCPMessage(5, 3, TCPType.OuvertureSession));
        conversation.accept(new TCPMessage(5,3, "ahah" ));
        /* conversation.accept(new TCPMessage(3, TCPType.FermetureSession));
        conversation.accept(new TCPMessage(3, TCPType.FermetureSession));*/ // ça fait tout buguer à cause du conv manager
        Conversation defaultConv = new Conversation();
        assertEquals(0, defaultConv.getDestinataireId());
        defaultConv.accept(new TCPMessage(5,3, TCPType.OuvertureSession));
        assertEquals(3, defaultConv.getDestinataireId());
    }


    @Test
    public void acceptTest() throws IOException, NetworkException {
        ListeUser.getInstance().setMyId(3);
        ServerSocket serverSocket = new ServerSocket(8476);
        Conversation conversation = new Conversation();
        Socket socketEnvoi = new Socket(InetAddress.getLocalHost(), 8476);
        Socket socketReception = serverSocket.accept();
        TCPReceiveData receiveData = new TCPReceiveData(socketReception);
        receiveData.setSubscriber(conversation);
        assertEquals(0,conversation.getDestinataireId());
        TCPSendData sendData = new TCPSendData(socketEnvoi);
        sendData.envoyer(new TCPMessage(6, 3, TCPType.OuvertureSession));
    }

}
