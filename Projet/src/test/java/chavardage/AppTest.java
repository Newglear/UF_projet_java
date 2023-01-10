package chavardage;

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
    public void InitTest() {
        /*ConversationManager conversationManager = ConversationManager.getInstance();
        ListeUser listeUser = ListeUser.getInstance();
        ChavardageManager chavardageManager = ChavardageManager.getInstance();
        GestionUDPMessage gestionUDPMessage = GestionUDPMessage.getInstance();
        TCPServeur tcpServeur = new TCPServeur(conversationManager);
        UDPServeur udpServeur = new UDPServeur(gestionUDPMessage);
        listeUser.setMyself(2,"Romain");
        Thread.sleep(30000);*/
    }
}
