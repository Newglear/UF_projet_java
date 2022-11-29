package UserList;

import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import static org.junit.Assert.assertEquals;

public class ListeUserTest extends ListeUser{ // le extends juste pour pouvoir tester le getIndex en protected

    ListeUser listeUser = new ListeUser();

    @Before
    public void remplirListe() throws UnknownHostException, UserAlreadyInListException {
        listeUser.addUser(2345,"romain", InetAddress.getLocalHost());
        listeUser.addUser(4567,"aude", InetAddress.getLocalHost());
        listeUser.addUser(5678, "evan", InetAddress.getLocalHost());
        listeUser.addUser(9876, "gwen", InetAddress.getLocalHost());

    }


    @Test
    public void addUserTest() throws UnknownHostException {
        assertEquals("romain", listeUser.tabItems.get(2345).getPseudo());
        assertEquals(InetAddress.getLocalHost(), listeUser.tabItems.get(2345).getAddress());
        assertEquals(2345, listeUser.tabItems.get(2345).getId());
    }


    @Test (expected = UserNotFoundException.class)
    public void emptyExceptionTest() throws UserNotFoundException {
        listeUser.modifyUserPseudo(0, "romainMaisMieux");
    }

    @Test
    public void modifyUserTest() throws UnknownHostException, UserNotFoundException {
        listeUser.modifyUserPseudo(2345,"romainMaisMieux");
        assertEquals("romainMaisMieux", listeUser.tabItems.get(2345).getPseudo());
        assertEquals(InetAddress.getLocalHost(), listeUser.tabItems.get(2345).getAddress());
        assertEquals(2345, listeUser.tabItems.get(2345).getId());
    }

    @Test (expected = UserNotFoundException.class)
    public void removeUserTest() throws UserNotFoundException {
        listeUser.removeUser(2345);
        listeUser.modifyUserPseudo(2345, "hola");
    }

    // TODO : à tester
    @Test (expected = UserAlreadyInListException.class)
    public void addUserWithSameKey() throws UnknownHostException, UserAlreadyInListException {
        listeUser.addUser(2345, "Aude", InetAddress.getLocalHost());
    }

    @Test
    public void getUserTest() throws UserNotFoundException {
        assertEquals("romain",listeUser.getUser(2345).getPseudo());
        assertEquals("aude",listeUser.getUser(4567).getPseudo());
        assertEquals("gwen",listeUser.getUser(9876).getPseudo());
        assertEquals("evan",listeUser.getUser(5678).getPseudo());
    }


    @Test
    public void UserItemTest() throws UnknownHostException {
        UserItem user = new UserItem(5432, "romain", InetAddress.getLocalHost());
        user.setPseudo("romain2");
        assertEquals("romain2", user.getPseudo());
    }

}