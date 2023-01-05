package chavardage.conversation;

import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.message.WrongConstructorException;
import chavardage.networkManager.TCPSendData;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketEnvoi extends Thread{

    public void run(){
        try{
            Socket socketEnvoi = new Socket(InetAddress.getLocalHost(),4987);
            TCPSendData sendData = new TCPSendData(socketEnvoi);
            sendData.envoyer(new TCPMessage(3, TCPType.OuvertureSession,6));
        } catch (IOException | WrongConstructorException e) {
            e.printStackTrace();
        }

    }


}
