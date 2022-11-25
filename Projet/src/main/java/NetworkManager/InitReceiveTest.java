package NetworkManager;

public class InitReceiveTest {
        public static void main(String args[]) {
            try {
                TcpReceiveConnection threadTcp = new TcpReceiveConnection();
                threadTcp.start();
                System.out.println("Server TCP Started");
                UdpReceive threadUdp = new UdpReceive();
                threadUdp.start();
                System.out.println("Server UDP Started");
            }catch (Exception e){}
        }
}
