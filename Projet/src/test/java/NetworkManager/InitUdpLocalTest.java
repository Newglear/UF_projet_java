package NetworkManager;

public class InitUdpLocalTest {

    public static void main(String[] args) {
        try {
            ThreadUdpReceive threadUdp = new ThreadUdpReceive();
            System.out.println("Server UDP Started");
            UdpSendTest testUdp = new UdpSendTest();
        }catch (Exception e){e.printStackTrace();}
    }

}
