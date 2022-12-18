package chavardage.networkManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class TCPServeur extends Thread{

    private static final Logger LOGGER = LogManager.getLogger(TCPServeur.class);

    public final static int PORT_TCP = 4753;
    private static int nbInstances=0;

    Consumer<Socket> subscriber;
    private boolean isClose=false;

    public void setSubscriber(Consumer<Socket> subscriber){
        this.subscriber = subscriber;
        LOGGER.trace("le subscriber a été set à " + subscriber);
    }

    public TCPServeur() throws ServerAlreadyOpen {
        if (nbInstances!=0) {
            ServerAlreadyOpen e = new ServerAlreadyOpen("TCPServeur");
            LOGGER.error(e.getMessage());
            throw e;
        }
        nbInstances+=1;
        start();
    }


    public void run(){
        if (this.subscriber==null){
            this.subscriber=(sock) -> LOGGER.trace("default subscriber : " + sock.toString());
        }
        try {
            ServerSocket portEcoute = new ServerSocket(PORT_TCP);
            LOGGER.info("démarrage du serveur TCP");
            while (!isClose) {
                Socket connexion = portEcoute.accept();
                LOGGER.trace("nouvelle connexion détectée");
                subscriber.accept(connexion);
                // TODO refaire mieux
                //ConversationManager conversations = ConversationManager.getInstance();
                // conversations.createConv(connexion);
            }
            portEcoute.close();
            LOGGER.info("fermeture du serveur TCP");
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();}
    }

    public synchronized void close(){
        nbInstances-=1;
        this.isClose=true;
    }
}
