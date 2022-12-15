package networkManager;

import conversation.ConversationManager;
import message.UDPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class TCPServeur extends Thread{

    private static final Logger LOGGER = LogManager.getLogger(TCPServeur.class);

    public final static int PORT_TCP = 4753;

    Consumer<Socket> subscriber;

    public void setSubscriber(Consumer<Socket> subscriber){
        this.subscriber = subscriber;
    }

    public TCPServeur(){
        start();
    }
    public void run(){
        if (this.subscriber==null){
            this.subscriber=(sock) -> LOGGER.trace("socket: " + sock.toString());
        }
        try {
            ServerSocket portEcoute = new ServerSocket(PORT_TCP);
            LOGGER.info("démarrage du serveur TCP");
            while (true) {
                Socket connexion = portEcoute.accept();
                LOGGER.trace("nouvelle connexion détectée");
                subscriber.accept(connexion);
                // TODO refaire mieux
                //ConversationManager conversations = ConversationManager.getInstance();
                // conversations.createConv(connexion);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();}
    }
}
