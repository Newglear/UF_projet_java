package NetworkManager;

import NetworkManager.TcpReceiveData;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class TestTcpReceive extends Thread {
    TCPMessage test;
    ObjectInputStream in;
    public TestTcpReceive(Socket sock) throws  Exception{
        InputStream input = sock.getInputStream();
        in = new ObjectInputStream(input);
        start();
    }
    public void run(){
        try{
            test = TcpReceiveData.ReceiveData(in);
            System.out.println(test.toString());
        }catch (Exception e){}

    }
}
