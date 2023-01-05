package chavardage.networkManager;

import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.message.WrongConstructorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPReceiveDataTest {



    @Test
    public void threadTest() throws IOException {
        ServerSocket serverSocket = new ServerSocket(3876);
        Socket socketEnvoi = new Socket(InetAddress.getLocalHost(), 3876);
        Socket socketReception = serverSocket.accept();
        TCPReceiveData receiveData = new TCPReceiveData(socketReception);
        TCPSendData sendData = new TCPSendData(socketEnvoi);
        sendData.envoyer(new TCPMessage(3,"coucou"));
        receiveData.setSubscriber((mess)->System.out.println(mess.getData()));
        sendData.envoyer(new TCPMessage(3, "t'es la meilleure"));
    }


}
