package chavardage.networkManager;

import chavardage.conversation.NetworkException;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TCPConnectTest {




    @Test
    public void connectToTest() throws IOException, NetworkException {
        TCPServeur serveur = new TCPServeur(8765);
        Socket socket = TCPConnect.connectTo(InetAddress.getLocalHost(),8765);
        socket.close();
        serveur.interrupt();
    }



}
