package networkManager;

import message.UDPControlType;
import message.UDPMessage;
import org.junit.Test;
import userList.UserItem;

public class UDPServeurTest {

    @Test
    public void serveurTest() {
        UDPServeur serveur = new UDPServeur();
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.Connexion, new UserItem(1,"aude")));
        ConsumerTest consumer = new ConsumerTest();
        serveur.setSubscriber(consumer);
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.Connexion, new UserItem(1,"aude")));
    }
}
