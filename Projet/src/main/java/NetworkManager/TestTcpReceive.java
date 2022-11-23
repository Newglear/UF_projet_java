package NetworkManager;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import Messages.TCPMessage;

public class TestTcpReceive extends Thread {
    ObjectInputStream in;
    public TestTcpReceive(Socket sock) throws  Exception{
        InputStream input = sock.getInputStream();
        in = new ObjectInputStream(input);
        start();
    }
    public void run(){
        try{
            sleep(2000);
            System.out.println("Test Reception :");
            TCPMessage test = TcpReceiveData.ReceiveData(in);
            System.out.println(test.destinataireId);
        }catch (Exception e){}

    }
}
