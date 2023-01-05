package chavardage.networkManager;

import chavardage.message.UDPControlType;
import chavardage.message.UDPMessage;
import chavardage.userList.UserItem;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPSendTest {

    @Test
    public void envoyerBroadcastTest(){
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.Connexion, new UserItem(3, "aude")), 2345);
    }

    @Test
    public void envoyerUnicastTest() throws UnknownHostException {
        UDPSend.envoyerUnicast(new UDPMessage(UDPControlType.Connexion, new UserItem(3, "aude")), InetAddress.getLocalHost(), 2345);
    }
}
