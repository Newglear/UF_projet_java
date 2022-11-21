import java.net.*;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;


class Client {

    public static void main(String args[]) throws Exception{

        int port = 4321; 
        // adresse du serveur
        InetAddress add = InetAddress.getByName("localhost");

        Socket socket = new Socket(add, port);
        System.out.println("client opérationnel");
        // get input and output flow

        BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        
        out.println("salut le serveur je peux avoir la date stp");

        String inputMessage = buffer.readLine(); 
        System.out.println("je suis le client j'ai reçu "+ inputMessage);



        socket.close(); 
    }

}