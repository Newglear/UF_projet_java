package NetworkManager;

import java.net.*;

public class ThreadUdpReceive extends Thread{
    public static final int receivePort = 6751;
    private InetAddress clientAddress;
    //private int clientPort;
    public final static int tailleMax = 250; //TODO : modifier taille max message
    public boolean isFinished = false;

    public ThreadUdpReceive(){
        start();
    }

    public void run(){
        try{
            DatagramSocket receiveSocket = new DatagramSocket();
            byte[] buffer = new byte[tailleMax];
            DatagramPacket incomingPacket = new DatagramPacket(buffer,buffer.length);

            while(!isFinished){
                receiveSocket.receive(incomingPacket);
                clientAddress = incomingPacket.getAddress();
                // clientPort = incomingPacket.getPort();

                //TODO User.traiterMessage(buffer,clientAddress);
            }
            receiveSocket.close();
        }catch(Exception e){e.printStackTrace();}

    }
}
