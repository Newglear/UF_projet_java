import java.net.*;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 

class Serveur {


    public static void main(String args[]) throws Exception{

        int port = 4321; 

        // creating a ServerSocket instance
        ServerSocket serverCalcetin = new ServerSocket(port);

        // waiting for prospective clients (blocking)
        Socket calcetin = serverCalcetin.accept();

        // get input and output flow
        //buffer où on va lire les entrées
        BufferedReader buffer = new BufferedReader(new InputStreamReader(calcetin.getInputStream()));
        // truc où on va écrire les sorties 
        PrintWriter out = new PrintWriter(calcetin.getOutputStream(),true);

        String inputMessage = buffer.readLine(); 
        System.out.println("Je suis le serveur et j'ai reçu "+ inputMessage);

        LocalDateTime date = LocalDateTime.now(); 

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        out.println(date.format(myFormatObj));
        // closing the connection
        calcetin.close();
        serverCalcetin.close(); 

    }
}