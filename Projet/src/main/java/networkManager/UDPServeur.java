package networkManager;

import message.UDPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.function.Consumer;

public class UDPServeur extends Thread{
    public static final int PORT_UDP = 6751;
    public final static int TAILLE_MAX = 2048;
    private static final Logger LOGGER = LogManager.getLogger(UDPServeur.class);

    public UDPServeur(){
        start();
    }

    Consumer<UDPMessage> subscriber;

    public void setSubscriber(Consumer<UDPMessage> subscriber){
        this.subscriber = subscriber;
    }

    public void run(){
        if (this.subscriber==null){
            this.subscriber=(msg) -> LOGGER.trace("received: "+msg);
        }
        try{
            DatagramSocket receiveSocket = new DatagramSocket(PORT_UDP);
            byte[] buffer = new byte[TAILLE_MAX];
            DatagramPacket incomingPacket = new DatagramPacket(buffer,buffer.length);
            LOGGER.info("démarrage du serveur UDP");
            while(true){
                receiveSocket.receive(incomingPacket);
                InetAddress clientAddress = incomingPacket.getAddress();
                ObjectInputStream IStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
                UDPMessage mess = (UDPMessage) IStream.readObject();
                mess.getUser().setAddress(clientAddress);
                LOGGER.trace("paquet reçu : " + mess);
                subscriber.accept(mess);
            }
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();}
    }
}
