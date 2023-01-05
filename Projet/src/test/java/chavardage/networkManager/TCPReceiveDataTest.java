package chavardage.networkManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPReceiveDataTest {



    @Test
    public void threadTest() throws UnknownHostException {
        TCPServeur serveur = new TCPServeur(5678);
        Socket socket = TCPConnect.connectTo(InetAddress.getLocalHost(),5678);
        TCPReceiveData thread = new TCPReceiveData(socket);
        thread.interrupt();
        serveur.interrupt();
    }


}
