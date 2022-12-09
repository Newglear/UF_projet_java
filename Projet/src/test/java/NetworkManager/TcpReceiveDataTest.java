package NetworkManager;

import Message.TCPMessage;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static NetworkManager.ThreadTcpReceiveConnection.portTcpReceive;

public class TcpReceiveDataTest {

    // utiliser TcpSendTest pour tester
    @Test
    public void receiveDataTest() throws Exception {
        ServerSocket portEcoute = new ServerSocket(portTcpReceive);
        System.out.println("lancement du serveur d'écoute TCP");
        Socket connexion = portEcoute.accept();
        System.out.println("j'ai reçu une connexion ");
        TCPMessage message1 = TcpReceiveData.receiveData(connexion);
        TCPMessage message2 = TcpReceiveData.receiveData(connexion);
        System.out.println("message reçu : " + message2.getData());
    }
}
