package NetworkManager;

import java.io.ObjectInputStream;

import Message.TCPControlMessage;

public class TcpReceiveData {

    public static TCPControlMessage ReceiveData(ObjectInputStream in) throws  Exception{ //TODO : A tester avec Messages.TCPMessage implémenté
        TCPControlMessage messageRecu = (TCPControlMessage) in.readObject();
        return messageRecu;
    }
}
