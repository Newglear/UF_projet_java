package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.function.Consumer;


public class TCPReceiveData extends Thread {

    private final Socket socket ;
    private static final Logger LOGGER = LogManager.getLogger(TCPReceiveData.class);
    Consumer<TCPMessage> subscriber;
    private boolean isClosed = false;

    public void setSubscriber(Consumer<TCPMessage> subscriber){
        this.subscriber = subscriber;
    }


    public TCPReceiveData(Socket socket) {
        this.socket = socket;
        LOGGER.trace("création d'un thread de réception sur le socket " + socket.toString());
        start();
    }

    @Override
    public void run() {
        if (this.subscriber==null){
            this.subscriber=(mess) -> LOGGER.trace("message: " + mess.toString());
        }
        try {
            InputStream input = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(input);
            while (!isClosed) {
                try {
                    TCPMessage message = (TCPMessage) in.readObject();
                    subscriber.accept(message);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
            }
            in.close();
            this.socket.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

    }


    public synchronized void close(){
        this.isClosed=true;
    }


}
