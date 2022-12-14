package networkManager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ThreadUDPServeur extends Thread{
    public static final int PORT_UDP = 6751;
    public final static int TAILLE_MAX = 250; //TODO : modifier taille max message
    public boolean isFinished = false;

    public ThreadUDPServeur(){
        start();
    }

    public void run(){
        try{
            DatagramSocket receiveSocket = new DatagramSocket(PORT_UDP);
            byte[] buffer = new byte[TAILLE_MAX];
            DatagramPacket incomingPacket = new DatagramPacket(buffer,buffer.length);

            while(!isFinished){
                System.out.println("Etat d'écoute");
                receiveSocket.receive(incomingPacket);
                System.out.println("Packet Reçu");
                InetAddress clientAddress = incomingPacket.getAddress();
                //TODO User.traiterMessage(buffer,clientAddress);
            }
            receiveSocket.close();
        }catch(Exception e){e.printStackTrace();}

    }
}
