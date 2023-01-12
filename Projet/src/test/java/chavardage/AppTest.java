package chavardage;

import org.junit.Test;

public class AppTest {

    int udp1 = 6584;
    int udp2 = 8974;
    int tcp1 = 8478;
    int tcp2 = 7493;


    @Test
    public void appTest(){
        AppOnPort app1 = new AppOnPort(udp1,tcp1, udp2, tcp2);
        AppOnPort app2 = new AppOnPort(udp2,tcp2,udp1,tcp2);
        app1.start();
        app2.start();

    }


}
