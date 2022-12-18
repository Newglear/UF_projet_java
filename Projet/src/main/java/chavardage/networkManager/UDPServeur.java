package chavardage.networkManager;

import chavardage.message.UDPMessage;
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
    private boolean isClose=false;
    private static int nbInstances=0;



    public UDPServeur() throws ServerAlreadyOpen {
        /*if (nbInstances!=0){
            ServerAlreadyOpen e = new ServerAlreadyOpen("UDPServeur");
            LOGGER.error(e.getMessage());
            throw e;
        }else{
            nbInstances=1;
            }
         */
        LOGGER.trace("création du serveur UDP");

    }

    Consumer<UDPMessage> subscriber;

    public synchronized void setSubscriber(Consumer<UDPMessage> subscriber){
        LOGGER.trace("le subscriber a été set à " + subscriber);
        this.subscriber = subscriber;
    }

    public void run(){ // même si c'est tentant, pour une raison que j'ignore le run en synchronized il est pas fan fan
        if (this.subscriber==null){
            this.subscriber=(msg) -> LOGGER.trace("default subscriber : "+msg);
        }
        try{
            DatagramSocket receiveSocket = new DatagramSocket(PORT_UDP);
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
            this.nbInstances=0;
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();}
    }

    /*public synchronized void close(){
        nbInstances=0;
        this.isClose=true;
    }*/


}
