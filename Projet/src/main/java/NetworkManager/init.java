package NetworkManager;

import NetworkManager.TcpReceiveConnection;
import NetworkManager.TestTcpSend;
import NetworkManager.UdpReceive;

public class init {
    public static void main(String args[]){
        try{
            TcpReceiveConnection threadTcp = new TcpReceiveConnection();
            threadTcp.start();
            System.out.println("Server TCP Start");
            //UdpReceive threadUdp = new UdpReceive();
            //threadUdp.start();
            //System.out.println("Serve Udp start");
            TestTcpSend testTcp = new TestTcpSend();
            testTcp.start();
            //TestUdp testUdp = new TestUdp();
            //testUdp.start();

        }catch (Exception e){}

    }
}
