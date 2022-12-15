package networkManager;

import message.UDPControlType;
import message.UDPMessage;
import org.junit.Test;
import userList.UserItem;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPSendTest {

    @Test
    public void envoyerBroadcastTest(){
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.Connexion, new UserItem(3, "aude")));
    }

    @Test
    public void envoyerUnicastTest() throws UnknownHostException {
        UDPSend.envoyerUnicast(new UDPMessage(UDPControlType.Connexion, new UserItem(3, "aude")), InetAddress.getLocalHost());
    }
}
