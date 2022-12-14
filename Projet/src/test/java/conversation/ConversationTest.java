package conversation;

import userList.AssignationProblemException;
import userList.ListeUser;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

// TODO (aucun de ces tests ne passent)
public class ConversationTest {

    @Test
    public void constructorIntTest() throws Exception {
        ListeUser.addUser(1, "romain", InetAddress.getLocalHost());
        Conversation conversation1 = new Conversation(1);
    }

    @Test
    public void constructorSocketTest() throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(),1234);
        Conversation conversation = new Conversation(socket);
    }



    @Test
    public void createConvTest() throws IOException {
        ConversationManager.createConv(3);
        Socket socket = new Socket(InetAddress.getLocalHost(),1235);
        ConversationManager.createConv(socket);
    }

    @Test
    public void getConvTest() throws AssignationProblemException {
        Conversation conv1 = ConversationManager.getConv(2345);
        Conversation conv2 = ConversationManager.getConv(5432);
        assertEquals(2345, conv1.getDestinataireId());
        assertEquals(5432, conv2.getDestinataireId());
    }

}
