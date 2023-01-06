package chavardage;

import chavardage.connexion.ChavardageManager;
import chavardage.connexion.GestionUDPMessage;
import chavardage.conversation.ConversationManager;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import org.junit.Test;

public class LocalAppTest {


    @Test
    public void localTest() throws IllegalConstructorException, InterruptedException {
        int port_local = 9473;
        int port_distant= 9474;
        UserItem userLocal = new UserItem(1,"Aude");
        UserItem userDistant = new UserItem(2,"Romain");
        ListeUser listeLocal = new ListeUser(true);
        ListeUser listeDistant = new ListeUser(true);
        ConversationManager convManLocal = new ConversationManager(true);
        ConversationManager convManDistant = new ConversationManager(true);
        listeLocal.setMyself(userLocal);
        listeDistant.setMyself(userDistant);
        ChavardageManager chavManLocal= new ChavardageManager(port_distant);
        ChavardageManager chavManDistant = new ChavardageManager(port_local);
        GestionUDPMessage gestionUDPMessageLocal = new GestionUDPMessage(listeLocal, port_distant,chavManLocal);
        GestionUDPMessage gestionUDPMessageDistant = new GestionUDPMessage(listeDistant, port_local, chavManDistant);
        UDPServeur udpServeurLocal = new UDPServeur(port_local,gestionUDPMessageLocal);
        UDPServeur udpServeurDistant= new UDPServeur(port_distant,gestionUDPMessageDistant);
        chavManDistant.connectToApp(userDistant);
        convManDistant.
    }
}
