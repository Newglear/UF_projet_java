package chavardage.connexion;

import chavardage.networkManager.UDPServeur;
import chavardage.userList.IllegalConstructorException;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import org.junit.Before;
import org.junit.Test;

public class ChavardageManagerTest {



    @Test
    public void ConnectToAppTest() throws IllegalConstructorException, InterruptedException {
        int port_local = 9473;
        int port_distant= 9474;
        UserItem userLocal = new UserItem(1,"Aude");
        UserItem userDistant = new UserItem(2,"Romain");
        ListeUser listeLocal = new ListeUser(true);
        ListeUser listeDistant = new ListeUser(true);
        listeLocal.setMyself(userLocal);
        listeDistant.setMyself(userDistant);
        ChavardageManager chavManLocal= new ChavardageManager(port_distant);
        ChavardageManager chavManDistant = new ChavardageManager(port_local);
        GestionUDPMessage gestionUDPMessageLocal = new GestionUDPMessage(listeLocal, port_distant,chavManLocal);
        GestionUDPMessage gestionUDPMessageDistant = new GestionUDPMessage(listeDistant, port_local, chavManDistant);
        UDPServeur udpServeurLocal = new UDPServeur(port_local,gestionUDPMessageLocal);
        UDPServeur udpServeurDistant= new UDPServeur(port_distant,gestionUDPMessageDistant);
        chavManDistant.ConnectToApp(userDistant);
    }
}
