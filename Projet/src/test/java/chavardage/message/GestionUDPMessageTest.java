package chavardage.message;

import chavardage.networkManager.UDPSend;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class GestionUDPMessageTest {

    @Before
    public void init(){
        ListeUser.getInstance().clear();
        ListeUser.getInstance().setMyId(1);
        ListeUser.getInstance().setMyPseudo("aude");
    }

    @Test
    public void gestionTest() {
        UDPServeur udpServeur = new UDPServeur(6896, GestionUDPMessage.getInstance());
        UserItem distant = new UserItem(3,"romain");
        // test réception des acks
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.AckPseudoOk, distant),6896);
        // test changement pseudo
        distant.setPseudo("gwen");
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.ChangementPseudo, distant),6896);
        // test de la déconnexion
         UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.Deconnexion,distant),6896);



    }

}
