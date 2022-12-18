package chavardage.networkManager;

import chavardage.message.UDPControlType;
import chavardage.message.UDPMessage;
import chavardage.userList.UserItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UDPServeurTest {

    private UDPServeur serveur ;


    @Before
    // lancement du serveur en réception
    public void lancement() throws ServerAlreadyOpen {
        this.serveur = new UDPServeur();
    }

    @Test
    public void serveurTest() {
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.Connexion, new UserItem(1,"aude")));
        ConsumerTest consumer = new ConsumerTest();
        serveur.setSubscriber(consumer);
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.Connexion, new UserItem(1,"aude")));
    }

    @Test (expected = ServerAlreadyOpen.class)
    public void openOtherServ() throws ServerAlreadyOpen {
        UDPServeur serveur = new UDPServeur();
    }

    @After
    public void closeServ(){
        this.serveur.close();
    }
}
