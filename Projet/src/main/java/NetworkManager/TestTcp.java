package NetworkManager;

import NetworkManager.TcpSend;

import java.net.*;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
public class TestTcpSend extends Thread{
    public void run(){
        try{
            Socket sockTest = TcpSend.TcpConnect(InetAddress.getLocalHost());
            OutputStream outputStream = sockTest.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            TcpSend.EnvoyerMessage(out,);
        }catch (Exception e){}

    }
}
