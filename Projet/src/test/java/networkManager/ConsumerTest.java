package networkManager;

import message.UDPMessage;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.function.Consumer;

public class ConsumerTest implements Consumer<UDPMessage> {
    public static final Logger LOGGER = LogManager.getLogger(ConsumerTest.class);
    @Override
    public void accept(UDPMessage udpMessage) {
        LOGGER.trace("ConsumerTest a bien été appelée");
    }

}
