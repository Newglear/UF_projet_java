package NetworkManager;

public class InitReceiveTest {
        public static void main(String args[]) {
            try {
                TcpReceiveConnection threadTcp = new TcpReceiveConnection();
                threadTcp.start();
                System.out.println("Server TCP Start");
            }catch (Exception e){}
        }
}
