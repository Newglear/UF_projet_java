package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Consumer;


public class TCPReceiveData extends Thread {

    private final Socket socket ;
    private static final Logger LOGGER = LogManager.getLogger(TCPReceiveData.class);
    Consumer<TCPMessage> subscriber;

    public void setSubscriber(Consumer<TCPMessage> subscriber){
        this.subscriber = subscriber;
        LOGGER.debug("le subscriber a été set à " + subscriber);
    }

    /** thread de réception sur un socket*/
    public TCPReceiveData(Socket socket) {
        this.socket = socket;
        LOGGER.trace("création d'un thread de réception sur le socket " + socket.toString());
        start();
    }

    /** constructeur qui initialise le subscriber*/
    public TCPReceiveData(Socket socket, Consumer<TCPMessage> subscriber){
        this.subscriber=subscriber;
        LOGGER.debug("le subscriber a été set à " + subscriber);
        this.socket=socket;
        LOGGER.trace("création d'un thread de réception sur le socket " + socket.toString());
        start();
    }

    @Override
    public void run() {
        if (this.subscriber==null){
            this.subscriber=(mess) -> LOGGER.debug("default subscriber : " + mess.toString());
        }
        try {
            InputStream input = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(input);
            while (!isInterrupted()) {
                try {
                    TCPMessage message = (TCPMessage) in.readObject();
                    LOGGER.debug("passage du message au subscriber");
                    subscriber.accept(message);
                }catch (SocketException e){
                    // quand on interromp le thread le socket se ferme pas de suite, c'est pas très grave
                } catch (EOFException e){
                    LOGGER.error(e.getMessage());
                    this.interrupt(); // si le read object nous lance une EOFException, il n'y a plus personne en face : on arrête le thread
                } catch (ClassNotFoundException e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            this.interrupt();
        }
    }



}
