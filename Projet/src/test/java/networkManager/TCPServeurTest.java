package networkManager;

import message.TCPMessage;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

public class TCPServeurTest{

    @Test
    public void serveurTest() throws IOException {
        TCPServeur serveur = new TCPServeur();
        TCPSend.envoyer(InetAddress.getLocalHost(),new TCPMessage(1, "hola"));
        TCPSend.envoyer(InetAddress.getLocalHost(),new TCPMessage(1, "yo"));
        serveur.setSubscriber((socket -> System.out.println("le subscriber a bien été appelé")));
        TCPSend.envoyer(InetAddress.getLocalHost(),new TCPMessage(1, "coucou toi"));

    }
}
