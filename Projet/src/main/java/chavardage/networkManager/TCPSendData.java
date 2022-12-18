package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class TCPSendData implements Consumer<TCPMessage> {

    private static final Logger LOGGER = LogManager.getLogger(TCPSendData.class);
    private final ObjectOutputStream out;

    public TCPSendData(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        this.out = new ObjectOutputStream(outputStream);
    }


    @Override
    public void accept(TCPMessage message) {
        try {
            out.writeObject(message);
            LOGGER.trace("Message envoyé à " + message.getDestinataireId() + " : " + message.getData());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }


    public synchronized void close(){
        try {
            this.out.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
