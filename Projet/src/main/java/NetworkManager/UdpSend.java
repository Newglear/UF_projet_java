package NetworkManager;
import javax.xml.crypto.Data;
import java.net.*;

public class UdpSend {
    private InetAddress broadcastAddress = InetAddress.getByName("127.0.0.1");
    private static DatagramSocket socketSend;
    private DatagramPacket sendPacket;

    public UdpSend() throws Exception{
        socketSend = new DatagramSocket();
        socketSend.setBroadcast(true);
    }

    public void envoyerBroadcast(byte[] message) throws Exception{
        sendPacket = new DatagramPacket(message, message.length);
        sendPacket.setAddress(broadcastAddress);
        sendPacket.setPort(UdpReceive.receivePort);
        socketSend.send(sendPacket);
    }

    public void envoyerUnicast(byte[] message, InetAddress receiverAddress) throws Exception{
        sendPacket = new DatagramPacket(message,message.length);
        sendPacket.setAddress(receiverAddress);
        sendPacket.setPort(UdpReceive.receivePort);
        socketSend.send(sendPacket);
    }

    public void finApplication(){ //TODO rajouter une classe pour la fermeture app
        socketSend.close();
    }
}
