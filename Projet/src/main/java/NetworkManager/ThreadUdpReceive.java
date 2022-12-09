package NetworkManager;

import Message.GestionMessageUDP;

import java.net.*;
import java.nio.Buffer;

public class ThreadUdpReceive extends Thread{
    public static final int receivePort = 6751;
    private InetAddress clientAddress;
    public final static int tailleMax = 2048; //TODO : modifier taille max message
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
                GestionMessageUDP handleMessage = new GestionMessageUDP(incomingPacket.getData(),incomingPacket.getAddress());
                //UdpReceiveTest test = new UdpReceiveTest(clientAddress, buffer);
            }
            receiveSocket.close();
        }catch(Exception e){e.printStackTrace();}

    }
}
