package chavardage.networkManager;

import chavardage.message.UDPType;
import chavardage.message.UDPMessage;
import chavardage.userList.UserItem;
import org.junit.Test;

public class UDPServeurTest {

    @Test
    public void serveurTest() {
        UDPServeur serveur = new UDPServeur(34567);
        UDPSend.envoyerBroadcast(new UDPMessage(UDPType.DemandeConnexion, new UserItem(1,"aude")), 34567);
        ConsumerTest consumer = new ConsumerTest();
        serveur.setSubscriber(consumer);
        UDPSend.envoyerBroadcast(new UDPMessage(UDPType.DemandeConnexion, new UserItem(1,"aude")), 34567);
        serveur.interrupt();
    }

}
