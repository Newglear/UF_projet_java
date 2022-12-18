package chavardage.networkManager;

import chavardage.message.UDPControlType;
import chavardage.message.UDPMessage;
import chavardage.userList.UserItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UDPServeurTest {

    @Test
    public void serveurTest() {
        UDPServeur serveur = new UDPServeur();
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.Connexion, new UserItem(1,"aude")));
        ConsumerTest consumer = new ConsumerTest();
        serveur.setSubscriber(consumer);
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.Connexion, new UserItem(1,"aude")));
        serveur.interrupt();
    }

}
