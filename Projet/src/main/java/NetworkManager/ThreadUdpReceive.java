package NetworkManager;

import java.net.*;
import java.nio.Buffer;

public class ThreadUdpReceive extends Thread{
    public static final int receivePort = 6751;
    public final static int tailleMax = 250; //TODO : modifier taille max message
    public boolean isFinished = false;

    public ThreadUdpReceive(){
        start();
    }

    public void run(){
        try{
            DatagramSocket receiveSocket = new DatagramSocket(receivePort);
            byte[] buffer = new byte[tailleMax];
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
