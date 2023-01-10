package chavardage;

import chavardage.connexion.AlreadyUsedPseudoException;
import chavardage.connexion.ChavardageManager;
import chavardage.connexion.GestionUDPMessage;
import chavardage.connexion.UsurpateurException;
import chavardage.conversation.ConversationAlreadyExists;
import chavardage.conversation.ConversationDoesNotExist;
import chavardage.conversation.ConversationManager;
import chavardage.message.TCPMessage;
import chavardage.networkManager.TCPServeur;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import chavardage.userList.UserNotFoundException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Test;

import java.io.IOException;

public class LocalAppTest {


    @Test
    public void localTest() throws IllegalConstructorException, InterruptedException, UserNotFoundException, AssignationProblemException, IOException, ConversationDoesNotExist, ConversationAlreadyExists, UsurpateurException, AlreadyUsedPseudoException {
        // Configurator.setRootLevel(Level.INFO);
        int port_local_udp = 9473;
        int port_local_tcp = 9475;

        int port_distant_udp = 9474;
        int port_distant_tcp = 9476;

        // simulation application 1
        UserItem userLocal = new UserItem(1,"Aude");
        ListeUser listeLocal = new ListeUser(true);
        ConversationManager convManLocal = new ConversationManager(true,listeLocal,port_distant_tcp);
        listeLocal.setMyself(userLocal);
        ChavardageManager chavManLocal= new ChavardageManager(port_distant_udp);
        GestionUDPMessage gestionUDPMessageLocal = new GestionUDPMessage(listeLocal, port_distant_udp,chavManLocal);
        UDPServeur udpServeurLocal = new UDPServeur(port_local_udp,gestionUDPMessageLocal);
        TCPServeur tcpServeurLocal = new TCPServeur(port_local_tcp,convManLocal);


        // simulation application 2
        UserItem userDistant = new UserItem(2,"Romain");
        ListeUser listeDistant = new ListeUser(true);
        ConversationManager convManDistant = new ConversationManager(true, listeDistant, port_local_tcp);
        listeDistant.setMyself(userDistant);
        ChavardageManager chavManDistant = new ChavardageManager(port_local_udp);
        GestionUDPMessage gestionUDPMessageDistant = new GestionUDPMessage(listeDistant, port_local_udp, chavManDistant);
        UDPServeur udpServeurDistant= new UDPServeur(port_distant_udp,gestionUDPMessageDistant);
        TCPServeur tcpServeurDistant = new TCPServeur(port_distant_tcp,convManDistant);


        // tests
        chavManDistant.connectToApp(userDistant);
        convManDistant.openConversation(1);
        convManDistant.getSendData(1).envoyer(new TCPMessage(1,2, "coucou"));
        convManLocal.getSendData(2).envoyer(new TCPMessage(2,1,"eh salut toi"));
    }


    @Test
    public void localTestAppSeule() throws IllegalConstructorException, InterruptedException, UsurpateurException, AlreadyUsedPseudoException {
        int port_local_udp = 9477;
        int port_distant_udp = 9478;
        UserItem userLocal = new UserItem(1,"Aude");
        ListeUser listeLocal = new ListeUser(true);
        listeLocal.setMyself(userLocal);
        ChavardageManager chavManLocal= new ChavardageManager(port_distant_udp);
        GestionUDPMessage gestionUDPMessageLocal = new GestionUDPMessage(listeLocal, port_distant_udp,chavManLocal);
        UDPServeur udpServeurLocal = new UDPServeur(port_local_udp,gestionUDPMessageLocal);
        chavManLocal.connectToApp(userLocal); // on teste comment réagit l'appli quand un user tout seul essaie de se connecter
    }
}
