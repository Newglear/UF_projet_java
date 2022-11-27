package NetworkManager;

public class InitTcpReceiverTest {
        public static void main(String[] args) {
            try {
                ThreadTcpReceiveConnection threadTcp = new ThreadTcpReceiveConnection();
                System.out.println("Server TCP Started");
            }catch (Exception e){}
        }
}
