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
        TCPServeur serveur = new TCPServeur();
        Socket socket = TCPConnect.connectTo(InetAddress.getLocalHost());
        TCPReceiveData thread = new TCPReceiveData(socket);
        thread.close();
        serveur.interrupt();
    }


}
