package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

public class TCPServeurTest{


    private TCPServeur serveur ;


    @Before
    // lancement du serveur en réception
    public void lancement() throws ServerAlreadyOpen {
        this.serveur = new TCPServeur();
    }

    @Test
    public void serveurTest() throws IOException {
        TCPSend.envoyer(InetAddress.getLocalHost(),new TCPMessage(1, "hola"));
        TCPSend.envoyer(InetAddress.getLocalHost(),new TCPMessage(1, "yo"));
        serveur.setSubscriber((socket -> System.out.println("le subscriber a bien été appelé")));
        TCPSend.envoyer(InetAddress.getLocalHost(),new TCPMessage(1, "coucou toi"));
    }

    @Test (expected = ServerAlreadyOpen.class)
    public void openOtherServ() throws ServerAlreadyOpen {
        TCPServeur serveur = new TCPServeur();
    }

    @After
    public void closeServ(){
        this.serveur.close();
    }
}
