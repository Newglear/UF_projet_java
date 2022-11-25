package UserList;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import static org.junit.Assert.assertEquals;

public class ListeUserTest extends ListeUser{ // le extends juste pour pouvoir tester le getIndex en protected
    ListeUser listeUser = new ListeUser();

    @Test
    public void addUserTest() throws UnknownHostException {
        listeUser.addUser(2345,"romain", InetAddress.getLocalHost());
        assertEquals("romain", listeUser.tabItems.get(0).getPseudo());
        assertEquals(InetAddress.getLocalHost(), listeUser.tabItems.get(0).getAddress());
        assertEquals(2345, listeUser.tabItems.get(0).getId());
    }


    @Test (expected = EmptyUserListException.class)
    public void emptyExceptionTest() throws EmptyUserListException {
        listeUser.modifyUserPseudo(0, "romainMaisMieux");
    }

    @Test
    public void modifyUserTest() throws EmptyUserListException, UnknownHostException {
        listeUser.addUser(2345,"romain", InetAddress.getLocalHost());
        listeUser.modifyUserPseudo(2345,"romainMaisMieux");
        assertEquals("romainMaisMieux", listeUser.tabItems.get(0).getPseudo());
        assertEquals(InetAddress.getLocalHost(), listeUser.tabItems.get(0).getAddress());
        assertEquals(2345, listeUser.tabItems.get(0).getId());
    }

    @Test (expected = UserNotFoundException.class)
    public void removeUserTest() throws UnknownHostException, UserNotFoundException {
        listeUser.addUser(2345,"romain", InetAddress.getLocalHost());
        listeUser.removeUser(0);
        listeUser.removeUser(0);
    }

    @Test
    public void getIndexTest() throws UnknownHostException, UserNotFoundException {
        listeUser.addUser(2345, "romain", InetAddress.getLocalHost());
        listeUser.addUser(4567,"aude", InetAddress.getLocalHost());
        listeUser.addUser(5678, "evan", InetAddress.getLocalHost());
        listeUser.addUser(9876, "gwen", InetAddress.getLocalHost());
        assertEquals(0,listeUser.getIndex(2345));
        assertEquals(1,listeUser.getIndex(4567));
        assertEquals(2,listeUser.getIndex(5678));
        assertEquals(3,listeUser.getIndex(9876));
    }


    @Test (expected = UserNotFoundException.class)
    public void getIndexExceptionTest() throws UserNotFoundException, UnknownHostException {
        listeUser.addUser(3456,"aude", InetAddress.getLocalHost());
        listeUser.getIndex(678);
    }

    @Test
    public void getUserTest() throws UserNotFoundException, UnknownHostException {
        listeUser.addUser(2345, "romain", InetAddress.getLocalHost());
        listeUser.addUser(4567,"aude", InetAddress.getLocalHost());
        listeUser.addUser(5678, "evan", InetAddress.getLocalHost());
        listeUser.addUser(9876, "gwen", InetAddress.getLocalHost());
        assertEquals("romain",listeUser.getUser(2345).getPseudo());
        assertEquals("aude",listeUser.getUser(4567).getPseudo());
        assertEquals("gwen",listeUser.getUser(9876).getPseudo());
        assertEquals("evan",listeUser.getUser(5678).getPseudo());
    }


    @Test
    public void UserItemTest() throws UnknownHostException, UserNotFoundException {
        listeUser.addUser(2345,"romain", InetAddress.getLocalHost());
        listeUser.getUser(2345).setPseudo("romainmaismieux");
        assertEquals("romainmaismieux", listeUser.tabItems.get(0).getPseudo());
    }

}