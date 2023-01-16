package chavardage.networkManager;

import chavardage.conversation.NetworkException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TCPConnect {

    private static final Logger LOGGER = LogManager.getLogger(TCPConnect.class);


    /** se connecte sur le port donné, retourne un socket et throw une NetworkException si quelque chose se passe mal*/
    public static Socket connectTo(InetAddress address, int port) throws NetworkException {
        try {
            Socket socket = new Socket(address, port);
            LOGGER.trace("Connection réalisée avec " + address + " sur le socket " + socket);
            return socket;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new NetworkException("TCPConnect");
        }
    }
}
