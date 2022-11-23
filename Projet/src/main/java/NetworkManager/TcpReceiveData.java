package NetworkManager;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpReceiveData {

    public static TCPMessage ReceiveData(ObjectInputStream in) throws  Exception{ //TODO : A tester avec TCPMessage implémenté
        TCPMessage messageRecu = (TCPMessage) in.readObject();
        return messageRecu;
    }
}
