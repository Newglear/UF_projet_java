package networkManager;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import conversation.ConversationManager;
import message.TCPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ThreadTCPReceiveData extends Thread {

    private boolean isFinished = false;
    private final int destinataireId;
    private static final Logger LOGGER = LogManager.getLogger(ThreadTCPReceiveData.class);

    public ThreadTCPReceiveData(int destinataireId) {
        LOGGER.trace("creátion du thread de reception avec " + destinataireId);
        this.destinataireId = destinataireId;
    }

    public void run(Socket socket) throws  Exception{
        InputStream input = socket.getInputStream();
        ObjectInputStream in = new ObjectInputStream(input);
        while (!isFinished) {
            try {
                TCPMessage message = (TCPMessage) in.readObject();
                // on passe le message à la conversation
                LOGGER.trace("nouveau message reçu de " + destinataireId + " : " + message.getData());
                ConversationManager.getConv(destinataireId).traiterMessageEntrant(message); // TODO ici j'ai un nullpointerexception avec le conv manager
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
        socket.close();
        LOGGER.trace("fermeture de la connexion en réception avec " + destinataireId);
    }

    public void setFinished(){
        this.isFinished=true;
    }


}
