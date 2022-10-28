import java.net.*;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;

class ThreadTCPReceive extends Thread{


    public ThreadTCPReceive(){
        super();
        start(); 
    }


    public void run(){
        try{
            int port = 4321; 
            ServerSocket serverSocket = new ServerSocket(port); 
            Socket socket = serverSocket.accept(); 
            InputStream input = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader NewRead = new BufferedReader(reader);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

        } catch (Exception e){
            System.out.println("je ne dirais pas que ce fut un échec : ça n'a pas marché");
        }
        
    }


}