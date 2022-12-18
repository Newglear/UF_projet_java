package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TCPServeurTest{

    @Test
    public void serveurTest() throws IOException, ServerAlreadyOpen {
        TCPServeur serveur = new TCPServeur();
        InetAddress localhost = InetAddress.getLocalHost();
        TCPSend.envoyer(localhost,new TCPMessage(1, "hola"));
        TCPSend.envoyer(localhost,new TCPMessage(1, "yo"));
        serveur.setSubscriber((sock -> System.out.println("le subscriber a bien été appelé")));
        TCPSend.envoyer(localhost,new TCPMessage(1, "coucou toi"));
        /* try {
            TCPServeur serveur1 = new TCPServeur();
        } catch (Exception e){
            System.out.println("l'exception " + e.getMessage()+ " a été levée");
        }*/
        serveur.close();
    }





}
