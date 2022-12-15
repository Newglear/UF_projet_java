package networkManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServeur extends Thread{
    public static final int PORT_UDP = 6751;
    public final static int TAILLE_MAX = 2048;
    private static final Logger LOGGER = LogManager.getLogger(UDPServeur.class);

    public UDPServeur(){
        start();
    }

    public void run(){
        try{
            LOGGER.info("démarrage du serveur UDP");
            DatagramSocket receiveSocket = new DatagramSocket(PORT_UDP);
            LOGGER.trace("socket de réception créé");
            byte[] buffer = new byte[TAILLE_MAX];
            DatagramPacket incomingPacket = new DatagramPacket(buffer,buffer.length);
            LOGGER.trace("paquet en réception créé");
            while(true){
                LOGGER.trace("serveur en état d'écoute ");
                receiveSocket.receive(incomingPacket);
                LOGGER.trace("paquet reçu");
                InetAddress clientAddress = incomingPacket.getAddress();
                //TODO User.traiterMessage(buffer,clientAddress);
            }
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();}
    }
}
