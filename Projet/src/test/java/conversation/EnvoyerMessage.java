package conversation;

import message.TCPMessage;
import message.TCPType;
import message.WrongConstructorException;
import networkManager.TCPSend;
import networkManager.ThreadTCPServeur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class EnvoyerMessage extends Thread{
    public EnvoyerMessage(){
        start();
    }

    public void run(){
        try {
            Thread.sleep(100); // pour laisser le temps au serveur de démarrer
            Socket socket = new Socket(InetAddress.getLocalHost(), ThreadTCPServeur.PORT_TCP);
            TCPSend.envoyerMessage(socket, new TCPMessage(1, TCPType.OuvertureSession));

        } catch (IOException | WrongConstructorException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
