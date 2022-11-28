package NetworkManager;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InitUdpSendTest {

    public static InetAddress adresseClient;

    {
        try {
            adresseClient = InetAddress.getByName("insa-10223.insa-toulouse.fr");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ThreadUdpReceive threadUdp = new ThreadUdpReceive();
            System.out.println("Server UDP Started");
            UdpSendTest testUdp = new UdpSendTest();
        }catch (Exception e){e.printStackTrace();}
    }

}
