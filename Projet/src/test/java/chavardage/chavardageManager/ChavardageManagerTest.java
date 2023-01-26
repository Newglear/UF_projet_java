package chavardage.chavardageManager;

import chavardage.networkManager.UDPServeur;
import chavardage.IllegalConstructorException;
import chavardage.userList.ListeUser;
import chavardage.userList.SamePseudoAsOld;
import chavardage.userList.UserItem;
import org.junit.Test;

public class ChavardageManagerTest {



    @Test
    public void ConnectToAppTest() throws IllegalConstructorException, InterruptedException, UsurpateurException, AlreadyUsedPseudoException, SamePseudoAsOld {
        int port_local = 6589;
        int port_distant= 7458;

        // simulation appli 1
        UserItem userLocal = new UserItem(1,"Aude");
        ListeUser listeLocal = new ListeUser(true);
        listeLocal.setMyself(userLocal);
        ChavardageManager chavManLocal= new ChavardageManager(port_distant);
        GestionUDPMessage gestionUDPMessageLocal = new GestionUDPMessage(listeLocal, port_distant,chavManLocal);
        UDPServeur udpServeurLocal = new UDPServeur(port_local,gestionUDPMessageLocal);

        // simulation appli 2
        UserItem userDistant = new UserItem(2,"Romain");
        ListeUser listeDistant = new ListeUser(true);
        listeDistant.setMyself(userDistant);
        ChavardageManager chavManDistant = new ChavardageManager(port_local);
        GestionUDPMessage gestionUDPMessageDistant = new GestionUDPMessage(listeDistant, port_local, chavManDistant);
        UDPServeur udpServeurDistant= new UDPServeur(port_distant,gestionUDPMessageDistant);

        // test
        chavManDistant.connectToApp(userDistant);
        chavManLocal.connectToApp(userLocal); // test de 2 utilisateurs qui se connectent en parallèle

    }
}
