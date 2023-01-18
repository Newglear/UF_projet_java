package chavardage.networkManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Consumer;

public class TCPServeur extends Thread {

    private static final Logger LOGGER = LogManager.getLogger(TCPServeur.class);

    public final static int DEFAULT_PORT_TCP = 4753;

    private ServerSocket serverSocket;

    Consumer<Socket> subscriber;

    /** serveur avec le consumer sur le port par défaut*/
    public TCPServeur(Consumer<Socket> consumer){
        setSubscriber(consumer);
        try {
            setDaemon(true);
            serverSocket = new ServerSocket(DEFAULT_PORT_TCP);
            LOGGER.trace("création du serveur TCP");
            start();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }


    /** crée le serveur sur le port donné*/
    public TCPServeur(int port) {
        try {
            setDaemon(true);
            serverSocket = new ServerSocket(port);
            LOGGER.trace("création du serveur TCP");
            start();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /** crée le serveur sur le port donné avec le subscriber*/
    public TCPServeur(int port, Consumer<Socket> cons) {
        setSubscriber(cons);
        try {
            setDaemon(true);
            serverSocket = new ServerSocket(port);
            LOGGER.trace("création du serveur TCP");
            start();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void setSubscriber(Consumer<Socket> subscriber) {
        this.subscriber = subscriber;
        LOGGER.debug("le subscriber a été set à " + subscriber);
    }


    public void run() {
        if (this.subscriber == null) {
            this.subscriber = (sock) -> LOGGER.debug("default subscriber : " + sock.toString());
        }
        try {
            LOGGER.info("démarrage du serveur TCP");
            while (!isInterrupted()) {
                Socket connexion = serverSocket.accept(); // accept est bloquant et ne réagit au interrupt
                LOGGER.trace("nouvelle connexion détectée envoyée sur " + connexion);
                subscriber.accept(connexion);
            }
        } catch (SocketException e){ // quand on interromp le thread le socket ne se ferme pas de suite, pas très grave
            LOGGER.trace("fermeture du serveur TCP");
            this.interrupt();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }




}
