package chavardage.conversation;

import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.message.WrongConstructorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

public class RecupConvFirstMessage implements Consumer<TCPMessage> {

    private int destinataireId;
    private TCPType tcpType;
    private boolean isSet = false;
    private static final Logger LOGGER = LogManager.getLogger(RecupConvFirstMessage.class);


    @Override
    public void accept(TCPMessage tcpMessage) {
        LOGGER.trace("je récupère le premier message");
        this.destinataireId = tcpMessage.getDestinataireId();
        this.tcpType=tcpMessage.getType();
        this.isSet = true;
        LOGGER.trace("j'ai récupéré le premier message");
    }

    public synchronized TCPMessage getFirstMessage(){
        while(!isSet){
        }
        try {
            return new TCPMessage(destinataireId,tcpType);
        } catch (WrongConstructorException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
