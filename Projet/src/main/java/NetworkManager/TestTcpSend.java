package NetworkManager;

import Message.TCPMessage;

import java.net.*;
import java.io.OutputStream;
import java.io.ObjectOutputStream;

public class TestTcpSend extends Thread{

    TCPMessage test;
    public TestTcpSend(){
        test = new TCPMessage(3);
    }

    public void run(){
        try{
            sleep(2000);
            System.out.println("Test Envoi TCP");

            Socket sockTest = TcpSend.TcpConnect(InetAddress.getLocalHost());
            System.out.println("Connexion dans TestTcpSend");
            OutputStream outputStream = sockTest.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            TcpSend.EnvoyerMessage(out,test);
            sockTest.close();
        }catch (Exception e){e.printStackTrace();}

    }
}
