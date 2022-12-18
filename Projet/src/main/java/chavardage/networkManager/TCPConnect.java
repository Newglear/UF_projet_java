package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPConnect {

    private static final Logger LOGGER = LogManager.getLogger(TCPConnect.class);

    // TODO attention à qui utilise ça
    /*public static void envoyer(InetAddress address, TCPMessage message) throws IOException {
        Socket socket = new Socket(address, TCPServeur.PORT_TCP);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(message);
        LOGGER.trace("Message envoyé à " + message.getDestinataireId() + " : " + message.getData());
        out.close();
        socket.close();
    }*/


    public static Socket connectTo(InetAddress address){
        try {
            Socket socket = new Socket(address, TCPServeur.PORT_TCP);
            LOGGER.trace("Connection réalisée avec " + address);
            // envoi du message de demande d'ouverture session TODO trouver où le faire ça
            // TCPSend.envoyer(new TCPMessage(address, TCPType.OuvertureSession), );
            return socket;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
