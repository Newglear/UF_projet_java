package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPSend {

    private static final Logger LOGGER = LogManager.getLogger(TCPSend.class);
    public static void envoyer(InetAddress address, TCPMessage message) throws IOException {
        Socket socket = new Socket(address, TCPServeur.PORT_TCP);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(message);
        // LOGGER.trace("Message envoyé à " + message.getDestinataireId() + " : " + message.getData());
    }

    public static void tcpConnect(int destinataireId){
        /* try {
            ListeUser listeUser = ListeUser.getInstance();
            InetAddress address = listeUser.getUser(destinataireId).getAddress();
            Socket socket = new Socket(address, ThreadTCPServeur.PORT_TCP);
            LOGGER.trace("Connection réalisée avec " + destinataireId);
            // envoi du message de demande d'ouverture session
            TCPSend.envoyerMessage(new TCPMessage(destinataireId, TCPType.OuvertureSession), );
        } catch (UserNotFoundException | IOException | WrongConstructorException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }*/
    }
}
