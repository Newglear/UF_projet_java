package chavardage.networkManager;

import chavardage.message.UDPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.function.Consumer;

public class UDPServeur extends Thread{
    public static final int DEFAULT_PORT_UDP = 6751;
    public final static int TAILLE_MAX = 2048;
    private static final Logger LOGGER = LogManager.getLogger(UDPServeur.class);
    private DatagramSocket receiveSocket;
    Consumer<UDPMessage> subscriber;

    /** crée le serveur sur le port par défaut*/
    public UDPServeur() {
        try {
            receiveSocket = new DatagramSocket(DEFAULT_PORT_UDP);
            LOGGER.trace("création du serveur UDP");
            start();
        } catch (SocketException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }


    /** crée le serveur sur le port donné*/
    public UDPServeur(int port){
        try {
            receiveSocket = new DatagramSocket(port);
            LOGGER.trace("création du serveur UDP");
            start();
        } catch (SocketException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void setSubscriber(Consumer<UDPMessage> subscriber){
        this.subscriber = subscriber;
        LOGGER.trace("le subscriber a été set à " + subscriber);
    }

    public void run(){ // même si c'est tentant, pour une raison que j'ignore le run en synchronized il est pas fan fan
        if (this.subscriber==null){
            this.subscriber=(msg) -> LOGGER.trace("default subscriber : "+msg);
        }
        try{
            byte[] buffer = new byte[TAILLE_MAX];
            DatagramPacket incomingPacket = new DatagramPacket(buffer,buffer.length);
            LOGGER.info("démarrage du serveur UDP");
            while(!this.isInterrupted()){
                receiveSocket.receive(incomingPacket);
                InetAddress clientAddress = incomingPacket.getAddress();
                ObjectInputStream IStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
                UDPMessage mess = (UDPMessage) IStream.readObject();
                mess.getUser().setAddress(clientAddress); // récupérer l'adresse de celui qui nous a envoyé le paquet pour la passer plus haut
                LOGGER.trace("paquet reçu : " + mess);
                subscriber.accept(mess);
            }
            receiveSocket.close();
            LOGGER.info("fermeture du serveur UDP");
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();}
    }

}
