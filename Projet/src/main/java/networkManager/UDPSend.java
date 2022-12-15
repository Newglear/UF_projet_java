package networkManager;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import message.UDPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class UDPSend {

    private static final InetAddress BROADCAST_ADDRESS;
    private static final Logger LOGGER = LogManager.getLogger(UDPSend.class);

    static {
        try {
            BROADCAST_ADDRESS = InetAddress.getByName("255.255.255.255");
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
            sendPacket.setAddress(BROADCAST_ADDRESS);
            sendPacket.setPort(UDPServeur.PORT_UDP);
            socketSend.send(sendPacket);
            LOGGER.trace("le message UDP " + message + " a été envoyé en broadcast");
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
            sendPacket.setPort(UDPServeur.PORT_UDP);
            socketSend.send(sendPacket);
            LOGGER.trace("le message UDP " + message + " a été envoyé à " + receiverAddress);
            socketSend.close();
        }catch (Exception e){e.printStackTrace();}
    }

}
