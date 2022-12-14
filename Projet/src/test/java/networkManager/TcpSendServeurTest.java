package networkManager;

import message.TCPMessage;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static networkManager.ThreadTCPServeur.PORT_TCP;

// pour tester TcpSend
public class TcpSendServeurTest {

    @Test
    public void TcpReceiveTest() throws IOException, ClassNotFoundException {
        ServerSocket portEcoute = new ServerSocket(PORT_TCP);
        System.out.println("lancement du serveur d'écoute TCP");
        Socket connexion = portEcoute.accept();
        InputStream input = connexion.getInputStream();
        ObjectInputStream in = new ObjectInputStream(input);
        TCPMessage messageRecu = (TCPMessage) in.readObject();
        System.out.println("message reçu : "+ messageRecu.getData());
    }
}
