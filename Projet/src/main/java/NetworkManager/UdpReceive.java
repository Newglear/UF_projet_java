package NetworkManager;

import java.net.*;

public class UdpReceive {
    public final static int receivePort = 6751;
    private static InetAddress clientAddress;
    private static int clientPort;
    public static boolean isFinished = false;
    public static void main(String arg[]) throws Exception{
        DatagramSocket receiveSocket = new DatagramSocket();
        byte[] buffer = new byte[250];
        DatagramPacket incomingPacket = new DatagramPacket(buffer,buffer.length);

        while(!isFinished){
            receiveSocket.receive(incomingPacket);
            clientAddress = incomingPacket.getAddress();
            clientPort = incomingPacket.getPort();
            //TODO User.traiterMessage(buffer,clientAddress,clientPort);
        }
        receiveSocket.close();
    }
}
