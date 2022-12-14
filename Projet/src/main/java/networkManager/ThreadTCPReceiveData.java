package networkManager;

import conversation.ConversationManager;
import message.TCPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;


public class ThreadTCPReceiveData extends Thread {

    private final int destinataireId;
    private final Socket socket ;
    private static final Logger LOGGER = LogManager.getLogger(ThreadTCPReceiveData.class);

    public ThreadTCPReceiveData(int destinataireId, Socket socket) {
        LOGGER.trace("création du thread de reception avec " + destinataireId);
        this.destinataireId = destinataireId;
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(input);
            while (true) {
                try {
                    TCPMessage message = (TCPMessage) in.readObject();
                    // on passe le message à la conversation
                    LOGGER.trace("nouveau message reçu de " + destinataireId + " : " + message.getData());
                    ConversationManager.getConv(destinataireId).traiterMessageEntrant(message);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

    }



}
