package NetworkManager;

import Message.TCPMessage;

import java.net.*;
import java.io.OutputStream;
import java.io.ObjectOutputStream;

public class TcpSendTest extends Thread{

    InetAddress adresseClien = InetAddress.getByName("insa-10223.insa-toulouse.fr");
    TCPMessage test;
    public TcpSendTest() throws UnknownHostException {
        test = new TCPMessage(3);
        start();
    }

    public void run(){
        try{

            System.out.println("Test Envoi TCP: LocalHost");
            sleep(2000);
            Socket sockTest = TcpSend.TcpConnect(InetAddress.getLocalHost());
            System.out.println("Connexion établie");
            sleep(2000);
            OutputStream outputStream = sockTest.getOutputStream();
            ObjectOutputStream outLocal = new ObjectOutputStream(outputStream);
            TcpSend.EnvoyerMessage(outLocal,test);
            System.out.println("Message envoyé");
            sockTest.close();

            sleep(2000);
            System.out.println("####################################");
            System.out.println("Test Envoi TCP: Hôte distant");
            sleep(2000);
            sockTest = TcpSend.TcpConnect(InitUdpSendTest.adresseClient);
            System.out.println("Connexion établie");
            sleep(2000);
            outputStream = sockTest.getOutputStream();
            ObjectOutputStream out2 = new ObjectOutputStream(outputStream);
            TcpSend.EnvoyerMessage(out2,test);
            System.out.println("Message envoyé");
            sockTest.close();


        }catch (Exception e){e.printStackTrace();}

    }
}
