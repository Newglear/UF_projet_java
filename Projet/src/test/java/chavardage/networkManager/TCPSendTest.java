package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPSendTest {

    private TCPServeur serveur ;

    @Before
    // lancement du serveur en réception
    public void lancement() throws ServerAlreadyOpen {
        this.serveur = new TCPServeur();
    }

    @Test
    public void envoyerTest() throws IOException {
        TCPSend.envoyer(InetAddress.getLocalHost(), new TCPMessage(1,"hola"));
    }

    @Test
    public void connectToTest() throws IOException {
        Socket socket = TCPSend.connectTo(InetAddress.getLocalHost());
        socket.close();
    }

    @After
    public void closeServ(){
        this.serveur.close();
    }
}
