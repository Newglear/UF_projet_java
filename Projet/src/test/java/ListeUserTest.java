import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ListeUserTest {

    ListeUser listeUser = new ListeUser();

    @Test
    public void addUserTest() throws UnknownHostException {
        listeUser.addUser(3, "romain", InetAddress.getLocalHost());
        assertEquals(3, listeUser.tabItems.get(0).id);
        assertEquals("romain", listeUser.tabItems.get(0).pseudo);
        assertEquals(InetAddress.getLocalHost(), listeUser.tabItems.get(0).address);
    }



}