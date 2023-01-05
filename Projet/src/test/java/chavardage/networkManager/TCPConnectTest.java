package chavardage.networkManager;

import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TCPConnectTest {




    @Test
    public void connectToTest() throws IOException {
        TCPServeur serveur = new TCPServeur(8765);
        Socket socket = TCPConnect.connectTo(InetAddress.getLocalHost(),8765);
        socket.close();
        serveur.interrupt();
    }



}
