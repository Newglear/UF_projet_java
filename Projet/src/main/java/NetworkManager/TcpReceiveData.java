package NetworkManager;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import Message.TCPMessage;

public class TcpReceiveData {

    public static TCPMessage receiveData(Socket socket) throws  Exception{
        InputStream input = socket.getInputStream();
        ObjectInputStream in = new ObjectInputStream(input);
        return (TCPMessage) in.readObject();
    }
}
