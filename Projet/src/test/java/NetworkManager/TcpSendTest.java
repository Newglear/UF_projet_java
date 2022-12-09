package NetworkManager;

import Message.TCPMessage;
import Message.TCPType;
import org.junit.Test;

import java.net.InetAddress;
import java.net.Socket;

public class TcpSendTest {

    // lancer d'abord TcpSendServeurTest ou TcpReceiveDataTest sur une autre machine (compléter l'adresse)
    @Test
    public void TcpSendTest() throws Exception {
        String addressString = "localhost"; // TODO
        InetAddress address = InetAddress.getByName(addressString);
        System.out.println("test d'un envoi de message à " + addressString);
        Socket socket = TcpSend.tcpConnect(address);
        TCPMessage message = new TCPMessage(2345, TCPType.OuvertureSession);
        TcpSend.envoyerMessage(socket,message);
        TCPMessage coucou = new TCPMessage(2345, "coucou");
        TcpSend.envoyerMessage(socket, coucou);
        socket.close();
    }


}
