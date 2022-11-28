package NetworkManager;

public class InitTcpSendTest {

    public static void main(String args[]){
        try{
            ThreadTcpReceiveConnection threadTcp = new ThreadTcpReceiveConnection();
            System.out.println("Server TCP Started");
            TcpSendTest testTcp = new TcpSendTest();
        }catch (Exception e){}

    }

}
