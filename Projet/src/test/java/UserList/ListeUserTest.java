package UserList;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import static org.junit.Assert.assertEquals;

public class ListeUserTest {

    ListeUser listeUser = new ListeUser();

    @Test
    public void addUserTest() throws UnknownHostException {
        listeUser.addUser("romain", InetAddress.getLocalHost());
        assertEquals("romain", listeUser.tabItems.get(0).getPseudo());
        assertEquals(InetAddress.getLocalHost(), listeUser.tabItems.get(0).getAddress());
    }


    @Test (expected = EmptyUserListException.class)
    public void emptyExceptionTest() throws UnknownHostException, EmptyUserListException {
        listeUser.modifyUser(0, "romainMaisMieux", InetAddress.getLocalHost());
    }

    @Test
    public void modifyUserTest() throws UnknownHostException, EmptyUserListException {
        listeUser.addUser("romain", InetAddress.getLocalHost());
        listeUser.modifyUser(0,"romainMaisMieux", InetAddress.getLocalHost());
        assertEquals("romainMaisMieux", listeUser.tabItems.get(0).getPseudo());
        assertEquals(InetAddress.getLocalHost(), listeUser.tabItems.get(0).getAddress());
    }

    @Test (expected = UserNotFoundException.class)
    public void removeUserTest() throws UnknownHostException, UserNotFoundException {
        listeUser.addUser("romain", InetAddress.getLocalHost());
        listeUser.removeUser(0);
        listeUser.removeUser(0);
    }

    @Test
    public void getUserIdTest() throws UnknownHostException, UserNotFoundException {
        listeUser.addUser("romain", InetAddress.getLocalHost());
        listeUser.addUser("aude", InetAddress.getLocalHost());
        listeUser.addUser("evan", InetAddress.getLocalHost());
        listeUser.addUser("gwen", InetAddress.getLocalHost());
        assertEquals(2,listeUser.getUserId("evan"));
    }


    @Test (expected = UserNotFoundException.class)
    public void getUserIdExceptionTest() throws UserNotFoundException, UnknownHostException {
        listeUser.addUser("aude", InetAddress.getLocalHost());
        listeUser.getUserId("evan");
    }


}