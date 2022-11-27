package NetworkManager;

public class InitSendTest {

    public static void main(String args[]){
        try{
            TestTcpSend testTcp = new TestTcpSend();
            testTcp.start();
            UdpSendTest testUdp = new UdpSendTest();
        }catch (Exception e){}

    }

}
