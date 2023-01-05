package chavardage.message;

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
    public void gestionTest(){
        UDPServeur udpServeur = new UDPServeur(GestionUDPMessage.getInstance());
        UserItem distant = new UserItem(3,"romain");
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.Connexion,distant));
    }

}
