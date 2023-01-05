package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        LOGGER.trace("le subscriber a été set à " + subscriber);
    }


    public TCPReceiveData(Socket socket) {
        this.socket = socket;
        LOGGER.trace("création d'un thread de réception sur le socket " + socket.toString());
        start();
    }

    public TCPReceiveData(Socket socket, Consumer<TCPMessage> subscriber){
        this.subscriber=subscriber;
        LOGGER.trace("le subscriber a été set à " + subscriber);
        this.socket=socket;
        LOGGER.trace("création d'un thread de réception sur le socket " + socket.toString());
        start();
    }

    @Override
    public void run() {
        if (this.subscriber==null){
            this.subscriber=(mess) -> LOGGER.trace("default subscriber : " + mess.toString());
        }
        try {
            InputStream input = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(input);
            while (!isInterrupted()) {
                try {
                    TCPMessage message = (TCPMessage) in.readObject();
                    LOGGER.trace("passage du message au subscriber");
                    subscriber.accept(message);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (SocketException e){
            LOGGER.trace("je vais ignorer cette exception, opération réalisée par une professionnelle");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void interrupt() { // https://codeahoy.com/java/How-To-Stop-Threads-Safely/
        try {
            this.socket.close();
            LOGGER.trace("fermeture du thread de reception");
        } catch (IOException e) {
            LOGGER.error(" l'exception que je vais ignorer : " + e.getMessage());
        } finally {
            super.interrupt();
        }
    }

}
