package chavardage;

import chavardage.connexion.ChavardageManager;
import chavardage.connexion.GestionUDPMessage;
import chavardage.conversation.ConversationDoesNotExist;
import chavardage.conversation.ConversationManager;
import chavardage.message.TCPMessage;
import chavardage.message.WrongConstructorException;
import chavardage.networkManager.TCPServeur;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import chavardage.userList.UserNotFoundException;
import org.junit.Test;

import java.io.IOException;

public class LocalAppTest {


    @Test
    public void localTest() throws IllegalConstructorException, InterruptedException, UserNotFoundException, AssignationProblemException, IOException, WrongConstructorException, ConversationDoesNotExist {
        int port_local_udp = 9473;
        int port_distant_udp = 9474;
        int port_local_tcp = 9475;
        int port_distant_tcp = 9476;
        UserItem userLocal = new UserItem(1,"Aude");
        UserItem userDistant = new UserItem(2,"Romain");
        ListeUser listeLocal = new ListeUser(true);
        ListeUser listeDistant = new ListeUser(true);
        ConversationManager convManLocal = new ConversationManager(true, listeLocal, port_local_tcp);
        ConversationManager convManDistant = new ConversationManager(true, listeDistant, port_distant_tcp);
        listeLocal.setMyself(userLocal);
        listeDistant.setMyself(userDistant);
        ChavardageManager chavManLocal= new ChavardageManager(port_distant_udp);
        ChavardageManager chavManDistant = new ChavardageManager(port_local_udp);
        GestionUDPMessage gestionUDPMessageLocal = new GestionUDPMessage(listeLocal, port_distant_udp,chavManLocal);
        GestionUDPMessage gestionUDPMessageDistant = new GestionUDPMessage(listeDistant, port_local_udp, chavManDistant);
        UDPServeur udpServeurLocal = new UDPServeur(port_local_udp,gestionUDPMessageLocal);
        UDPServeur udpServeurDistant= new UDPServeur(port_distant_udp,gestionUDPMessageDistant);
        TCPServeur tcpServeurLocal = new TCPServeur(port_local_tcp,convManLocal);
        TCPServeur tcpServeurDistant = new TCPServeur(port_distant_tcp,convManDistant);
        chavManDistant.connectToApp(userDistant);
        convManDistant.openConversation(1);
        convManDistant.getSendData(1).envoyer(new TCPMessage("coucou"));
       // convManLocal.getSendData(2).envoyer(new TCPMessage("eh salut toi"));
        // TODO convManLocal a pas la conv, vérifier dans les logs si elle se créé bien
    }
}
