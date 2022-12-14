package networkManager;

import message.TCPMessage;
import message.TCPType;
import message.WrongConstructorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import userList.ListeUser;
import userList.UserNotFoundException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPSend {

    private static final Logger LOGGER = LogManager.getLogger(TCPSend.class);
    public static void envoyerMessage(Socket socket, TCPMessage message) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(message);
        LOGGER.trace("Message envoyé à " + message.getDestinataireId() + " : " + message.getData());
    }

    public static Socket tcpConnect(int destinataireId){
        try {
            InetAddress address = ListeUser.getUser(destinataireId).getAddress();
            Socket socket = new Socket(address, ThreadTCPServeur.PORT_TCP);
            LOGGER.trace("Connection réalisée avec " + destinataireId);
            // envoi du message de demande d'ouverture session
            TCPSend.envoyerMessage(socket, new TCPMessage(destinataireId, TCPType.OuvertureSession));
            return socket;
        } catch (UserNotFoundException | IOException | WrongConstructorException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
