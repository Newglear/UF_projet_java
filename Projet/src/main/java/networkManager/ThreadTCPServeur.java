package networkManager;

import conversation.ConversationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.ServerSocket;
import java.net.Socket;

public class ThreadTCPServeur extends Thread{

    private static final Logger LOGGER = LogManager.getLogger(ThreadTCPServeur.class);

    public final static int portTcpReceive = 4753;
    public static boolean isFinished;

    public ThreadTCPServeur(){
        start();
    }
    public void run(){
        try {
            ServerSocket portEcoute = new ServerSocket(portTcpReceive);
            LOGGER.info("démarrage du serveur TCP");
            while (!isFinished) {
                Socket connexion = portEcoute.accept();
                LOGGER.trace("nouvelle connexion détectée");
                ConversationManager.createConv(connexion);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();}
    }
}
