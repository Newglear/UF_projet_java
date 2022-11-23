package NetworkManager;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;
import java.io.PrintWriter;
public class TcpSend {
    public static void EnvoyerMessage(ObjectOutputStream out, TCPMessage message) throws IOException { //TODO Voir après implémentation TCPMessage
        out.writeObject(message);
    }

    public static Socket TcpConnect(InetAddress address) throws Exception{
        Socket link =new Socket(address,TcpReceiveConnection.portTcpReceive);
        return link;
    }
}
