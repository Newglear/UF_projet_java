package NetworkManager;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.util.Arrays;
import Message.UDPMessage;

public class UdpReceiveTest extends Thread{

    public byte[] message;
    private final InetAddress clientAddress;
    public UdpReceiveTest(InetAddress clientAddress, byte[] buffer){
        this.message = Arrays.copyOf(buffer,buffer.length); //TODO A vérifier
        this.clientAddress = clientAddress;
        start();

    }

    public void run(){
        try {
            ObjectInputStream IStream = new ObjectInputStream(new ByteArrayInputStream(this.message));
            UDPMessage mess = (UDPMessage) IStream.readObject();
            // System.out.println("Pseudo : " + mess.pseudo + " ControlType: " + mess.controlType);
            System.out.println("Adresse du client : " + this.clientAddress);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
