package chavardage.connexion;

import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import org.junit.Before;
import org.junit.Test;

public class ChavardageManagerTest {


    @Before
    public void init(){
        ListeUser.getInstance().clear();
        ListeUser.getInstance().setMyId(1);
        ListeUser.getInstance().setMyPseudo("aude");
    }

    @Test
    public void ConnectToAppTest(){
        // TODO
    }
}
