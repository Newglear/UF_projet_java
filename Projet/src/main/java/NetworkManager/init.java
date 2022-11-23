package NetworkManager;

import NetworkManager.TcpReceiveConnection;
import NetworkManager.TestTcpSend;
import NetworkManager.UdpReceive;

public class init {

    public static void main(String args[]){
        try{
            TcpReceiveConnection threadTcp = new TcpReceiveConnection();
            threadTcp.start();
            UdpReceive threadUdp = new UdpReceive();
            threadUdp.start();
            TestTcpSend testTcp = new TestTcpSend();
            testTcp.start();
            TestUdp testUdp = new TestUdp();
            testUdp.start();

        }catch (Exception e){}

    }
}
