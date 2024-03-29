package chavardage;

import chavardage.chavardageManager.AlreadyUsedPseudoException;
import chavardage.chavardageManager.ChavardageManager;
import chavardage.chavardageManager.GestionUDPMessage;
import chavardage.chavardageManager.UsurpateurException;
import chavardage.conversation.ConversationAlreadyExists;
import chavardage.conversation.ConversationDoesNotExist;
import chavardage.conversation.ConversationManager;
import chavardage.message.TCPMessage;
import chavardage.networkManager.TCPServeur;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.SamePseudoAsOld;
import chavardage.userList.UserItem;
import chavardage.userList.UserNotFoundException;
import org.junit.Test;

import java.io.IOException;

public class LocalAppTest {


    @Test
    public void localTest() throws IllegalConstructorException, InterruptedException, UserNotFoundException, AssignationProblemException, ConversationDoesNotExist, ConversationAlreadyExists, UsurpateurException, AlreadyUsedPseudoException, SamePseudoAsOld {
        // Configurator.setRootLevel(Level.INFO);
        int port_local_udp = 4589;
        int port_local_tcp = 2164;

        int port_distant_udp = 4965;
        int port_distant_tcp = 3258;

        // simulation application 1
        UserItem userLocal = new UserItem(1,"Aude");
        ListeUser listeLocal = new ListeUser(true);
        ConversationManager convManLocal = new ConversationManager(listeLocal,port_distant_tcp);
        listeLocal.setMyself(userLocal);
        ChavardageManager chavManLocal= new ChavardageManager(port_distant_udp);
        GestionUDPMessage gestionUDPMessageLocal = new GestionUDPMessage(listeLocal, port_distant_udp,chavManLocal);
        UDPServeur udpServeurLocal = new UDPServeur(port_local_udp,gestionUDPMessageLocal);
        TCPServeur tcpServeurLocal = new TCPServeur(port_local_tcp,convManLocal);


        // simulation application 2
        UserItem userDistant = new UserItem(2,"Romain");
        ListeUser listeDistant = new ListeUser(true);
        ConversationManager convManDistant = new ConversationManager(listeDistant, port_local_tcp);
        listeDistant.setMyself(userDistant);
        ChavardageManager chavManDistant = new ChavardageManager(port_local_udp);
        GestionUDPMessage gestionUDPMessageDistant = new GestionUDPMessage(listeDistant, port_local_udp, chavManDistant);
        UDPServeur udpServeurDistant= new UDPServeur(port_distant_udp,gestionUDPMessageDistant);
        TCPServeur tcpServeurDistant = new TCPServeur(port_distant_tcp,convManDistant);


        // tests
        chavManDistant.connectToApp(userDistant);
        convManDistant.openConversation(1);
        convManDistant.getSendData(1).envoyer(new TCPMessage(1,2, "coucou"));
    }


    @Test
    public void localTestAppSeule() throws IllegalConstructorException, InterruptedException, UsurpateurException, AlreadyUsedPseudoException, SamePseudoAsOld {
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
