package NetworkManager;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import Message.TCPControlMessage;

public class TestTcpReceive extends Thread {
    ObjectInputStream in;
    public TestTcpReceive(Socket sock) throws  Exception{
        InputStream input = sock.getInputStream();
        in = new ObjectInputStream(input);
        start();
    }
    public void run(){
        try{
            System.out.println("Test Reception :");
            TCPControlMessage test = TcpReceiveData.ReceiveData(in);
            System.out.println(test.getDestinataireId() + " " + test.controlType);
        }catch (Exception e){}

    }
}
