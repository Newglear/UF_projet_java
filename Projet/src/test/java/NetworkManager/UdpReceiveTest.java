package NetworkManager;

import java.net.InetAddress;

public class UdpReceiveTest extends Thread{

    public byte[] message = new byte[ThreadUdpReceive.tailleMax];
    private InetAddress clientAddress;
    public UdpReceiveTest(InetAddress clientAddress, byte[] buffer){
        this.message = buffer; //TODO A vérifier
        this.clientAddress = clientAddress;
        start();

    }

    public void run(){
        System.out.println();
    }
}
