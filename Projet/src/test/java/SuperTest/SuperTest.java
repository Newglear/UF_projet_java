package SuperTest;

import org.junit.Test;

public class SuperTest {

    @Test
    public void superTest(){
        User1 user1 = new User1();
        User2 user2 = new User2();
        user1.start();
        user2.start();
    }

}
