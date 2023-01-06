package chavardage;

import chavardage.connexion.ChavardageManager;
import chavardage.connexion.GestionUDPMessage;
import chavardage.conversation.ConversationManager;
import chavardage.networkManager.TCPServeur;
import chavardage.networkManager.UDPServeur;
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
    public void InitTest() throws InterruptedException {
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
