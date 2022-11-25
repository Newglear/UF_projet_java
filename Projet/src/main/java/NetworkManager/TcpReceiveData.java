package NetworkManager;

import java.io.ObjectInputStream;

import Message.TCPMessage;

public class TcpReceiveData {

    public static TCPMessage ReceiveData(ObjectInputStream in) throws  Exception{ //TODO : A tester avec Messages.TCPMessage implémenté
        TCPMessage messageRecu = (TCPMessage) in.readObject();
        return messageRecu;
    }
}
