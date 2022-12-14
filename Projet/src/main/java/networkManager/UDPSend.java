package networkManager;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import message.UDPMessage;


public class UDPSend {
    private static final InetAddress broadcastAddress;

    static {
        try {
            broadcastAddress = InetAddress.getByName("255.255.255.255");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static DatagramSocket socketSend;
    private static DatagramPacket sendPacket;

    public static void envoyerBroadcast(UDPMessage message){
        try {
            socketSend = new DatagramSocket();
            socketSend.setBroadcast(true);
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bstream);
            oo.writeObject(message);
            oo.close();
            byte[] sentMessage = bstream.toByteArray();
            sendPacket = new DatagramPacket(sentMessage, sentMessage.length);
            sendPacket.setAddress(broadcastAddress);
            sendPacket.setPort(ThreadUDPServeur.receivePort);
            socketSend.send(sendPacket);
            socketSend.close();
        }catch (Exception e){e.printStackTrace();}
    }

    public static void envoyerUnicast(UDPMessage message, InetAddress receiverAddress){
        try {
            socketSend = new DatagramSocket();
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bstream);
            oo.writeObject(message);
            oo.close();
            byte[] sentMessage = bstream.toByteArray();
            sendPacket = new DatagramPacket(sentMessage,sentMessage.length);
            sendPacket.setAddress(receiverAddress);
            sendPacket.setPort(ThreadUDPServeur.receivePort);
            socketSend.send(sendPacket);
            socketSend.close();
        }catch (Exception e){e.printStackTrace();}
    }

}
