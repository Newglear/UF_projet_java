package NetworkManager;

import java.io.ObjectInputStream;

import Message.TCPMessage;

public class TcpReceiveData {

    public static TCPMessage receiveData(ObjectInputStream in) throws  Exception{ //TODO : A tester avec Messages.TCPMessage implémenté
        return (TCPMessage) in.readObject();
    }
}
