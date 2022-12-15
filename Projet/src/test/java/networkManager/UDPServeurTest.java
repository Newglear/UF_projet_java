package networkManager;

import message.UDPControlType;
import message.UDPMessage;
import org.junit.Test;
import userList.UserItem;

public class UDPServeurTest {

    @Test
    public void serveurTest(){
        UDPServeur serveur = new UDPServeur();
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.Connexion, new UserItem(1,"aude")));
        // si on envoie pas le message après aucun des messages d'info ou de trace ne s'affichent à part le premier, chelou
    }
}
