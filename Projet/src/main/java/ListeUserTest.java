import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class ListeUserTest {

    ListeUser test = new ListeUser();


    @org.junit.jupiter.api.Test
    void addUser() throws UnknownHostException {
        test.addUser(4,"romain", InetAddress.getLocalHost());
        assertEquals(new UserItem(4,"romain", InetAddress.getLocalHost()),test.tabItems.get(0));
    }

    @org.junit.jupiter.api.Test
    void addUsertest() throws UnknownHostException {
        test.addUser(4,"romain", InetAddress.getLocalHost());
        assertEquals(3,3);
    }



    @org.junit.jupiter.api.Test
    void modifyUser() {
    }

    @org.junit.jupiter.api.Test
    void getUserById() {
    }
}