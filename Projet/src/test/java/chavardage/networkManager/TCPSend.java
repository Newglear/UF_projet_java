package chavardage.networkManager;

import chavardage.message.TCPMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPSend {

    /** fonction d'envoi simple pour les tests*/

    public static void envoyer(InetAddress address, TCPMessage message) throws IOException {
        Socket socket = new Socket(address, TCPServeur.PORT_TCP);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(message);
        out.close();
        socket.close();
    }


}
