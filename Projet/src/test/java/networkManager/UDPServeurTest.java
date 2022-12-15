package networkManager;

import message.UDPControlType;
import message.UDPMessage;
import org.junit.Test;
import userList.UserItem;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPServeurTest {

    @Test
    public void serveurTest() throws UnknownHostException {
        UDPServeur serveur = new UDPServeur();
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.Connexion, new UserItem(1,"aude", InetAddress.getLocalHost())));
        // si on envoie pas le message après aucun des messages d'info ou de trace ne s'affichent à part le premier, chelou
    }
}
