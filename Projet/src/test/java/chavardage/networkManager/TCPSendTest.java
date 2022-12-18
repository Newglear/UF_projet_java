package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TCPSendTest {

    private TCPServeur serveur ;



    @Test
    public void connectToTest() throws IOException, ServerAlreadyOpen {
        TCPServeur serveur = new TCPServeur();
        Socket socket = TCPConnect.connectTo(InetAddress.getLocalHost());
        socket.close();
        serveur.close();
    }

    /*@Test
    public void envoyerTest() throws IOException {
        Socket socket = TCPConnect.connectTo(InetAddress.getLocalHost());
        TCPConnect.envoyer(socket, new TCPMessage(1,"hola"));
        socket.close();
    }*/


}
