package NetworkManager;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

import Message.UDPControlType;
import Message.UDPMessage;

public class UdpSendTest extends Thread{

    UDPMessage message;

    InetAddress adresseClient;
    public UdpSendTest(){
        try {
            this.message = new UDPMessage(UDPControlType.Connexion, "PseudoTest");
        }catch (Exception e){e.printStackTrace();}
        start();
    }

    public void run(){
        try{
            System.out.println("Test Envoi en Unicast : LocalHost");
            sleep(5000);
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bstream);
            oo.writeObject(message);
            this.adresseClient = InetAddress.getLocalHost();
            byte[] sentMessage = bstream.toByteArray();
            UdpSend.envoyerUnicast(sentMessage,adresseClient);
            System.out.println("Envoi en Unicast : Hôte distant");
            sleep(2000);
            //this.adresseClient = InetAddress.getByName();
            //UdpSend.envoyerUnicast(sentMessage,adresseClient);
            System.out.println("Envoi en broadcast");
            sleep(2000);
            UdpSend.envoyerBroadcast(sentMessage);
        }catch (Exception e){e.printStackTrace();}
    }
}
