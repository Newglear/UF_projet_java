package chavardage.connexion;

import chavardage.message.UDPType;
import chavardage.message.UDPMessage;
import chavardage.networkManager.UDPSend;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import org.junit.Before;
import org.junit.Test;

public class GestionUDPMessageTest {

    @Before
    public void init(){
        ListeUser.getInstance().clear();
        ListeUser.getInstance().setMyId(1);
        ListeUser.getInstance().setMyPseudo("aude");
    }

    @Test
    public void gestionTest() {
        int port = 6896;
        UDPServeur udpServeur = new UDPServeur(port, GestionUDPMessage.getInstance());
        UserItem distant = new UserItem(3,"romain");
        // demande de connexion
        UDPSend.envoyerBroadcast(new UDPMessage(UDPType.DemandeConnexion, distant),port);
        // le nouvel user a pu avoir son pseudo et se connecte
        UDPSend.envoyerBroadcast(new UDPMessage(UDPType.NewUser, distant),port);
        // test changement pseudo
        distant.setPseudo("gwen");
        UDPSend.envoyerBroadcast(new UDPMessage(UDPType.ChangementPseudo, distant),port);
        // test de la déconnexion
         UDPSend.envoyerBroadcast(new UDPMessage(UDPType.Deconnexion,distant),port);
    }

}
