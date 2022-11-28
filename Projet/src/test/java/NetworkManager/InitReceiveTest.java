package NetworkManager;

public class InitReceiveTest {
        public static void main(String[] args) {
            try {
                ThreadTcpReceiveConnection threadTcp = new ThreadTcpReceiveConnection();
                System.out.println("Server TCP Started");
                ThreadUdpReceive threadUdp = new ThreadUdpReceive();
                threadUdp.start();
                System.out.println("Server UDP Started");
            }catch (Exception e){}
        }
}
