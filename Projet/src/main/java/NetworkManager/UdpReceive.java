package NetworkManager;

import java.net.*;

public class UdpReceive extends Thread{
    public static final int receivePort = 6751;
    private InetAddress clientAddress;
    private int clientPort;
    private final int tailleMax = 250; //TODO : modifier taille max message
    public boolean isFinished = false;
    public void run(){
        try{
            DatagramSocket receiveSocket = new DatagramSocket();
            byte[] buffer = new byte[tailleMax];
            DatagramPacket incomingPacket = new DatagramPacket(buffer,buffer.length);

            while(!isFinished){
                receiveSocket.receive(incomingPacket);
                clientAddress = incomingPacket.getAddress();
                clientPort = incomingPacket.getPort();
                //TODO User.traiterMessage(buffer,clientAddress,clientPort);
            }
            receiveSocket.close();
        }catch(Exception e){e.printStackTrace();}

    }
}
