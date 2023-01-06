package chavardage.networkManager;

import chavardage.message.UDPType;
import chavardage.message.UDPMessage;
import chavardage.userList.UserItem;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPSendTest {

    @Test
    public void envoyerBroadcastTest(){
        UDPSend.envoyerBroadcast(new UDPMessage(UDPType.DemandeConnexion, new UserItem(3, "aude")), 2345);
    }

    @Test
    public void envoyerUnicastTest() throws UnknownHostException {
        UDPSend.envoyerUnicast(new UDPMessage(UDPType.DemandeConnexion, new UserItem(3, "aude")), InetAddress.getLocalHost(), 2345);
    }
}
