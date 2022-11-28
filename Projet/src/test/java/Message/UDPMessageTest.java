package Message;

import org.junit.Test;

import NetworkManager.UdpSend;

import static org.junit.Assert.assertEquals;

public class UDPMessageTest {


    @Test
    public void getBytesTest() throws UDPGetBytesException {
        UDPMessage message = new UDPMessage(UDPControlType.Deconnexion, "aude");
        UdpSend.envoyerBroadcast(message.getBytes());
        System.out.println("tout s'est bien passé je crois");
    }
}
