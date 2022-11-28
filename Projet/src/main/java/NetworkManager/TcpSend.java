package NetworkManager;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

import Message.TCPMessage;
public class TcpSend {
    public static void envoyerMessage(ObjectOutputStream out, TCPMessage message) throws IOException { //TODO Voir après implémentation Message.TCPMessage
        out.writeObject(message);
    }

    public static Socket tcpConnect(InetAddress address) throws Exception{
        Socket link =new Socket(address, ThreadTcpReceiveConnection.portTcpReceive);
        return link;
    }
}
