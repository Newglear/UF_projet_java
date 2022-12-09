package NetworkManager;

import Conversation.ConversationManager;
import org.junit.Test;

import java.net.ServerSocket;
import java.net.Socket;

import static NetworkManager.ThreadTcpReceiveConnection.portTcpReceive;
import static java.lang.Thread.sleep;

public class TcpReceiveDataTest {

    // utiliser TcpSendTest pour tester
    @Test
    public void receiveDataTest() throws Exception {
        ServerSocket portEcoute = new ServerSocket(portTcpReceive);
        System.out.println("lancement du serveur d'écoute TCP");
        Socket connexion = portEcoute.accept();
        System.out.println("j'ai reçu une connexion ");
        ThreadTcpReceiveData thread = new ThreadTcpReceiveData(2345);
        thread.run(connexion);
        sleep(30000);
    }
}
