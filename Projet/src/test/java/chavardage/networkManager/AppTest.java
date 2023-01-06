package chavardage.networkManager;

import chavardage.connexion.ChavardageManager;
import chavardage.connexion.GestionUDPMessage;
import chavardage.conversation.ConversationManager;
import chavardage.userList.ListeUser;
import org.junit.Before;
import org.junit.Test;

public class AppTest {


    @Before
    public void clean(){
        ListeUser.getInstance().clear();
        ConversationManager.getInstance().clear();
    }

    @Test
    public void InitTest(){
        ConversationManager conversationManager = ConversationManager.getInstance();
        ChavardageManager chavardageManager = ChavardageManager.getInstance();
        GestionUDPMessage gestionUDPMessage = GestionUDPMessage.getInstance();
        TCPServeur tcpServeur = new TCPServeur(conversationManager);
        UDPServeur udpServeur = new UDPServeur(gestionUDPMessage);

    }
}
