package chavardage.networkManager;

import chavardage.conversation.NetworkException;
import chavardage.message.TCPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPSendData {

    private static final Logger LOGGER = LogManager.getLogger(TCPSendData.class);
    private final ObjectOutputStream out;
    private final Socket socket;

    public TCPSendData(Socket socket) throws NetworkException {
        this.socket = socket;
        try {
            OutputStream outputStream = socket.getOutputStream();
            this.out = new ObjectOutputStream(outputStream);
            LOGGER.trace("création d'un objet d'envoi sur le socket " + socket);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new NetworkException("TCPSendData");
        }

    }


    public void envoyer(TCPMessage message) {
        try {
            out.writeObject(message);
            LOGGER.debug(message + " envoyé à "+socket);
        } catch (IOException e) { // on affiche l'exception, pas de traitement particulier
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }


    public synchronized void close() throws NetworkException {
        try {
            this.out.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new NetworkException("TCPSendData");
        }
    }

    public String toString(){
        return "TCPSendData {" +
                "socket='" + socket + '\'' +
                '}';
    }

}
