import java.net.*;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;


public class ThreadTCPSend extends Thread {

    Socket socket;
    //mettre dans le constructeur l'addresse de la machine tcp
    public ThreadTCPSend(Socket socket){
        super(); 
        this.socket = socket; 
        start(); 
    }


    public void run(){
        try{
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            System.out.println("traitement de la requête de configuration..."); 
            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = buffer.readLine(); 
            System.out.println(message);

            new ThreadTCPReceive();
        }
        catch (Exception e){}
    }

}
