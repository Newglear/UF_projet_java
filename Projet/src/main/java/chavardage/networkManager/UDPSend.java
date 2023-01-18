package chavardage.networkManager;

import chavardage.message.UDPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class UDPSend {

    private static final InetAddress BROADCAST_ADDRESS;
    private static final Logger LOGGER = LogManager.getLogger(UDPSend.class);

    static {
        try {
            BROADCAST_ADDRESS = InetAddress.getByName("255.255.255.255");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e); // c'est pas censé arriver, si ça arrive tant pis ça fera tout crash
        }
    }

    private static DatagramSocket socketSend;
    private static DatagramPacket sendPacket;

    public static void envoyerBroadcast(UDPMessage message, int port){
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
            sendPacket.setPort(port);
            socketSend.send(sendPacket);
            LOGGER.trace(message + " envoyé en broadcast sur le port " + port);
            socketSend.close();
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void envoyerUnicast(UDPMessage message, InetAddress receiverAddress, int port){
        try {
            socketSend = new DatagramSocket();
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bstream);
            oo.writeObject(message);
            oo.close();
            byte[] sentMessage = bstream.toByteArray();
            sendPacket = new DatagramPacket(sentMessage,sentMessage.length);
            sendPacket.setAddress(receiverAddress);
            sendPacket.setPort(port);
            socketSend.send(sendPacket);
            LOGGER.trace(message + " envoyé à " + receiverAddress + ":" + port);
            socketSend.close();
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
