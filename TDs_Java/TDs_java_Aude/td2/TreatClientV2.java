import java.net.*; 
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 

// cf TDs Romain  

class TreatClient extends Thread{

    static Socket socket; 

    public TreatClient(Socket socket){
        System.out.println("Thread bien démarré");
        this.socket = socket; 
    }

    public void run() {
        try{
            // get input and output flow
            //buffer où on va lire les entrées
            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // truc où on va écrire les sorties 
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

            String inputMessage = buffer.readLine(); 
            System.out.println("Je suis le serveur et j'ai reçu "+ inputMessage);

            
            out.println("bonjour, client");

            socket.close(); 
        
        } catch (Exception e) {
            System.out.println("je ne dirais pas que c'était un échec : ça n'a pas marché");
        }
        }

}
