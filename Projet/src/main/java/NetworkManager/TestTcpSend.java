package NetworkManager;

import Message.TCPControlMessage;
import Message.TCPControlType;
import Message.TCPMessage;

import java.net.*;
import java.io.OutputStream;
import java.io.ObjectOutputStream;

public class TestTcpSend extends Thread{

    TCPMessage test;
    public TestTcpSend(){
        test = new TCPControlMessage(3, TCPControlType.OuvertureSession);
    }

    public void run(){
        try{
            sleep(2000);
            System.out.println("Test Envoi TCP");

            Socket sockTest = TcpSend.TcpConnect(InetAddress.getByName("insa-10224.insa-toulouse.fr"));
            System.out.println("Connexion dans TestTcpSend");
            OutputStream outputStream = sockTest.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            TcpSend.EnvoyerMessage(out,test);
            sockTest.close();
        }catch (Exception e){e.printStackTrace();}

    }
}
