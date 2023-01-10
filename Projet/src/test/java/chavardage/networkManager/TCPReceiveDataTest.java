package chavardage.networkManager;

import chavardage.message.TCPMessage;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPReceiveDataTest {



    @Test
    public void threadTest() throws IOException {
        ServerSocket serverSocket = new ServerSocket(3876);
        Socket socketEnvoi = new Socket(InetAddress.getLocalHost(), 3876);
        Socket socketReception = serverSocket.accept();
        TCPReceiveData receiveData = new TCPReceiveData(socketReception);
        TCPSendData sendData = new TCPSendData(socketEnvoi);
        sendData.envoyer(new TCPMessage(1,2, "coucou" ));
        receiveData.setSubscriber((mess)->System.out.println(mess.getTexte()));
        sendData.envoyer(new TCPMessage(1,2, "t'es la meilleure"));
    }


}
