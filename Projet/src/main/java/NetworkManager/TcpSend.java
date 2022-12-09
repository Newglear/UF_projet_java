package NetworkManager;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;

import Message.TCPMessage;
public class TcpSend {
    public static void envoyerMessage(Socket socket, TCPMessage message) throws IOException { //TODO Voir après implémentation Message.TCPMessage
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(message);
    }

    public static Socket tcpConnect(InetAddress address) throws Exception{
        return new Socket(address, ThreadTcpReceiveConnection.portTcpReceive);
    }
}
