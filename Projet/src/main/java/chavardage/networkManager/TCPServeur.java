package chavardage.networkManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Consumer;

public class TCPServeur extends Thread {

    private static final Logger LOGGER = LogManager.getLogger(TCPServeur.class);

    public final static int PORT_TCP = 4753;

    private ServerSocket serverSocket;

    Consumer<Socket> subscriber;

    public TCPServeur() {
        LOGGER.trace("création du serveur TCP");
        start();
    }


    public synchronized void setSubscriber(Consumer<Socket> subscriber) {
        this.subscriber = subscriber;
        LOGGER.trace("le subscriber a été set à " + subscriber);
    }


    public void run() {
        if (this.subscriber == null) {
            this.subscriber = (sock) -> LOGGER.trace("default subscriber : " + sock.toString());
        }
        try {
            serverSocket = new ServerSocket(PORT_TCP);
            LOGGER.info("démarrage du serveur TCP");
            while (!isInterrupted()) {
                Socket connexion = serverSocket.accept(); // accept est bloquant et ne réagit au interrupt
                LOGGER.trace("nouvelle connexion détectée envoyée sur " + connexion);
                subscriber.accept(connexion);
            }
        } catch (SocketException e){
            LOGGER.trace("une exception s'est levée et j'ai décidé de l'ignorer dans ma grande sagesse");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }



    @Override
    public void interrupt() { // https://codeahoy.com/java/How-To-Stop-Threads-Safely/
        try {
            serverSocket.close();
            LOGGER.info("fermeture du serveur TCP");
        } catch (IOException e) {
            LOGGER.error(" l'exception que je vais ignorer : " + e.getMessage());
        } finally {
            super.interrupt();
        }
    }

}
