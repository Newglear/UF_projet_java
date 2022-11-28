package NetworkManager;

public class InitUdpReceiverTest {

    public static void main(String[] args) {
        try {
            ThreadUdpReceive threadUdp = new ThreadUdpReceive();
            System.out.println("Server UDP Started");
        }catch (Exception e){e.printStackTrace();}
    }

}
