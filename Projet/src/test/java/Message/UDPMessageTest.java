package Message;

import UserList.UserItem;
import org.junit.Test;

import NetworkManager.UdpSend;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

public class UDPMessageTest {


    @Test
    public void getBytesTest() throws UDPGetBytesException, UnknownHostException {
        UDPMessage message = new UDPMessage(UDPControlType.Deconnexion, new UserItem(4, "Aude", InetAddress.getLocalHost()));
        UdpSend.envoyerBroadcast(message.getBytes());
        System.out.println("tout s'est bien passé je crois");
    }
}
