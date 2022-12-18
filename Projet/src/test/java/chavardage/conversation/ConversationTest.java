package chavardage.conversation;

import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.message.WrongConstructorException;
import chavardage.userList.ListeUser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConversationTest {



    @Test
    public void conversationTest() throws WrongConstructorException {
        // TODO
        ListeUser.getInstance().setMyId(3); // j'ai l'id 3
        Conversation conversation = new Conversation(5); // je crée une conversation avec 5
        conversation.sendMessage("coucou");
        assertEquals(5, conversation.getDestinataireId());
        conversation.accept(new TCPMessage(3, "hola"));
        // on teste que les exceptions se lancent bien quand il faut
        System.out.println("3 conversationException sont attendues : ");
        conversation.accept(new TCPMessage(3, TCPType.OuvertureSession, 6));
        conversation.accept(new TCPMessage(4, "ahah"));
        conversation.accept(new TCPMessage(3, TCPType.FermetureSession));
        conversation.accept(new TCPMessage(3, TCPType.FermetureSession));
    }


}
