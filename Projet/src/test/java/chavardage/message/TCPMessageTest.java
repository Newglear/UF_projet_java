package chavardage.message;

import org.junit.Test;

import java.util.Date;

public class TCPMessageTest {
    @Test
    public void dateTest() {
        Date date = new Date();
        System.out.println(date);
        TCPMessage message = new TCPMessage(2,3,"yo");
        TCPMessage message1 = new TCPMessage(2,3,TCPType.OuvertureSession);
        System.out.println("date : " + message.getDate());
        System.out.println("date : " + message1.getDate());

    }
}
