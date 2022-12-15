package networkManager;

import conversation.ConversationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.ServerSocket;
import java.net.Socket;

public class ThreadTCPServeur extends Thread{

    private static final Logger LOGGER = LogManager.getLogger(ThreadTCPServeur.class);

    public final static int PORT_TCP = 4753;

    public ThreadTCPServeur(){
        start();
    }
    public void run(){
        try {
            ServerSocket portEcoute = new ServerSocket(PORT_TCP);
            LOGGER.info("démarrage du serveur TCP");
            while (true) {
                Socket connexion = portEcoute.accept();
                LOGGER.trace("nouvelle connexion détectée");
                // TODO refaire mieux
                ConversationManager conversations = ConversationManager.getInstance();
                conversations.createConv(connexion);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();}
    }
}
