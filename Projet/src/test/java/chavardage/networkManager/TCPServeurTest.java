package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

public class TCPServeurTest{

    @Test
    public void serveurTest() throws IOException {
        TCPServeur serveur = new TCPServeur(3456);
        InetAddress localhost = InetAddress.getLocalHost();
        TCPSend.envoyer(localhost,new TCPMessage("hola"), 3456);
        TCPSend.envoyer(localhost,new TCPMessage("yo"), 3456);
        serveur.setSubscriber((sock -> System.out.println("le subscriber a bien été appelé")));
        TCPSend.envoyer(localhost,new TCPMessage("coucou toi"), 3456);
        serveur.interrupt();
    }

}
