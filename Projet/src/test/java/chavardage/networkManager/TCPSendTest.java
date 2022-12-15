package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class TCPSendTest {

    @Test
    public void envoyerTest() throws IOException {
        // mini serveur en localhost
        ServerSocket server = new ServerSocket(TCPServeur.PORT_TCP);
        TCPSend.envoyer(InetAddress.getLocalHost(), new TCPMessage(1,"hola"));
    }
}
