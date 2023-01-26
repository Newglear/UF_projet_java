package chavardage.chavardageManager;

import chavardage.IllegalConstructorException;
import chavardage.message.UDPMessage;
import chavardage.message.UDPType;
import chavardage.networkManager.UDPSend;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.SamePseudoAsOld;
import chavardage.userList.UserItem;
import org.junit.Test;

public class GestionUDPMessageTest {


    @Test
    public void gestionTest() throws IllegalConstructorException, AlreadyUsedPseudoException, SamePseudoAsOld {
        int port = 6896;
        ListeUser listeUser = new ListeUser(true);
        listeUser.setMyself(1,"Aude");
        ChavardageManager chavardageManager = new ChavardageManager(port);
        GestionUDPMessage gestionUDPMessage = new GestionUDPMessage(listeUser,port,chavardageManager);
        UDPServeur udpServeur = new UDPServeur(port, gestionUDPMessage);
        UserItem distant = new UserItem(3,"romain");
        UserItem usurpateur = new UserItem(3,"Aude");
        // demande de connexion
        UDPSend.envoyerBroadcast(new UDPMessage(UDPType.DemandeConnexion, distant),port);
        // le nouvel user a pu avoir son pseudo et se connecte
        UDPSend.envoyerBroadcast(new UDPMessage(UDPType.NewUser, distant),port);
        // test usurpateur
        UDPSend.envoyerBroadcast(new UDPMessage(UDPType.DemandeConnexion,usurpateur),port);
        // test changement pseudo
        distant.setPseudo("gwen");
        UDPSend.envoyerBroadcast(new UDPMessage(UDPType.ChangementPseudo, distant),port);
        // test de la déconnexion
         UDPSend.envoyerBroadcast(new UDPMessage(UDPType.Deconnexion,distant),port);
    }

}
