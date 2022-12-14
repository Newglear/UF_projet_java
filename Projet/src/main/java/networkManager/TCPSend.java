package networkManager;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;
import message.TCPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import userList.ListeUser;
import userList.UserNotFoundException;

public class TCPSend {
    private static final Logger LOGGER = LogManager.getLogger(TCPSend.class);
    public static void envoyerMessage(Socket socket, TCPMessage message) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(message);
        LOGGER.trace("Message envoyé à " + message.getDestinataireId() + " : " + message.getData());
    }

    public static Socket tcpConnect(int destinataireId){
        InetAddress address = null;
        try {
            address = ListeUser.getUser(destinataireId).getAddress();
        } catch (UserNotFoundException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        try {
            Socket socket = new Socket(address, ThreadTCPReceiveConnection.portTcpReceive);
            LOGGER.trace("Connection réalisée avec " + destinataireId);
            return socket;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
