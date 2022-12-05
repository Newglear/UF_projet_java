package NetworkManager;

public class InitSendTest {

    public static void main(String[] args){
        try{
            TcpSendTest testTcp = new TcpSendTest();
            testTcp.start();
        }catch (Exception e){e.printStackTrace();}

    }

}
