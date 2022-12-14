package NetworkManager;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

import Message.UDPControlType;
import Message.UDPMessage;
import UserList.UserItem;

public class UdpSendTest extends Thread{

    UDPMessage message;

    InetAddress adresseClient;
    public UdpSendTest(){
        try {
            this.message = new UDPMessage(UDPControlType.Connexion, new UserItem(3, "PseudoTest", InetAddress.getLocalHost()));
        }catch (Exception e){e.printStackTrace();}
        start();
    }

    public void run(){
        try{
            System.out.println("Test Envoi en Unicast : LocalHost");
            sleep(2000);
            UdpSend.envoyerUnicast(message,adresseClient);


            /*
            sleep(2000);
            System.out.println("#################################");
            System.out.println("Envoi en Unicast : Hôte distant");
            sleep(2000);
            this.adresseClient = InitUdpSendTest.adresseClient;
            UdpSend.envoyerUnicast(message,adresseClient);
           */

            sleep(2000);
            System.out.println("#################################");
            System.out.println("Envoi en broadcast");
            sleep(2000);
            UdpSend.envoyerBroadcast(message);


        }catch (Exception e){e.printStackTrace();}
    }
}
