package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPSendDataTest {


    @Test
    public void sendDataTest() throws ServerAlreadyOpen, IOException {
        TCPServeur tcpServeur = new TCPServeur();
        Socket socket = TCPConnect.connectTo(InetAddress.getLocalHost());
        TCPSendData sendData = new TCPSendData(socket);
        sendData.accept(new TCPMessage(1, "hola"));
        sendData.accept(new TCPMessage(1, "coucou"));
        sendData.accept(new TCPMessage(1, "encore un message"));
        tcpServeur.close();
    }
}
