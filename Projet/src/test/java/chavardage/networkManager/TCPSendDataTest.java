package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPSendDataTest {


    @Test
    public void sendDataTest() throws IOException {
        TCPServeur tcpServeur = new TCPServeur(9876);
        Socket socket = TCPConnect.connectTo(InetAddress.getLocalHost(),9876);
        TCPSendData sendData = new TCPSendData(socket);
        sendData.envoyer(new TCPMessage(1, "hola"));
        sendData.envoyer(new TCPMessage(1, "coucou"));
        sendData.envoyer(new TCPMessage(1, "encore un message"));
        sendData.close();
        tcpServeur.interrupt();
    }
}
