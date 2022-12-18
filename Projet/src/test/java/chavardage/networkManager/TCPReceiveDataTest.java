package chavardage.networkManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPReceiveDataTest {

    private TCPServeur serveur;

    @Before
    // lancement du serveur en réception
    public void lancement() throws ServerAlreadyOpen {
        this.serveur = new TCPServeur();
    }

    @Test
    public void threadTest() throws UnknownHostException {
        Socket socket = TCPSend.connectTo(InetAddress.getLocalHost());
        TCPReceiveData thread = new TCPReceiveData(socket);
        thread.close();
    }

    @After
    public void fermer(){
        this.serveur.close();
    }

}
