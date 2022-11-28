package NetworkManager;

import Message.TCPMessage;
import Message.TCPType;
import Message.WrongConstructorException;

import java.net.*;
import java.io.OutputStream;
import java.io.ObjectOutputStream;

public class TestTcpSend extends Thread{

    TCPMessage test;
    public TestTcpSend(){
        try {
            test = new TCPMessage(3, TCPType.OuvertureSession);
        } catch (WrongConstructorException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            sleep(2000);
            System.out.println("Test Envoi TCP");

            Socket sockTest = TcpSend.TcpConnect(InetAddress.getByName("insa-10223.insa-toulouse.fr"));
            System.out.println("Connexion établie");
            OutputStream outputStream = sockTest.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            TcpSend.EnvoyerMessage(out,test);
            System.out.println("Message envoyé");
            sockTest.close();
        }catch (Exception e){e.printStackTrace();}

    }
}
