import java.net.*;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class client {
    public static void main(String args[]){
        try{
            InetAddress address = InetAddress.getLocalHost();
            Socket server = new Socket(address,6000);

            InputStream input = server.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader NewRead = new BufferedReader(reader);
            PrintWriter out = new PrintWriter(server.getOutputStream(),true);

            String receptionDate = NewRead.readLine();


            System.out.println("La date et l'heure de la machine est : " + receptionDate);

            server.close();
        }catch(Exception e){}
    }
}
