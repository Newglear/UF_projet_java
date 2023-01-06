package chavardage.conversation;

import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.message.WrongConstructorException;
import chavardage.networkManager.TCPSendData;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class SocketDistant extends Thread{


    public void run(){
        try{
            Socket socketEnvoi = new Socket(InetAddress.getLocalHost(),4987);
            TCPSendData sendData = new TCPSendData(socketEnvoi);
            sendData.envoyer(new TCPMessage(3, 6, TCPType.OuvertureSession));
            sendData.envoyer(new TCPMessage(3,6, "enfin ça marche" ));
        } catch (IOException | WrongConstructorException e) {
            e.printStackTrace();
        }

    }


}
