package NetworkManager;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.io.PrintWriter;
public class TcpSend {
    public void EnvoyerMessage(ObjectOutputStream out, TCPMessage message) throws IOException { //TODO Voir après implémentation TCPMessage
        out.writeObject(message);
    }

    public void TcpConnect(){

    }
}
