package NetworkManager;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import Message.TCPMessage;

public class TcpReceiveTest extends Thread {
    ObjectInputStream in;
    public TcpReceiveTest(Socket sock) throws  Exception{
        InputStream input = sock.getInputStream();
        in = new ObjectInputStream(input);
        start();
    }
    public void run(){
        try{
            System.out.println("Test Reception :");
            TCPMessage test = TcpReceiveData.ReceiveData(in);
            System.out.println("DestinataireId = " + test.getDestinataireId());
        }catch (Exception e){e.printStackTrace();}

    }
}
