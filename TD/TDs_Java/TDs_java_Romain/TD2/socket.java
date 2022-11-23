import java.net.*;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class socket{


    public static void main(String args[]){
        try {
            
            LocalDateTime localDate = LocalDateTime.now();
            ServerSocket test = new ServerSocket(6000);

            Socket link = test.accept();

            InputStream input = link.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader NewRead = new BufferedReader(reader);
            PrintWriter out = new PrintWriter(link.getOutputStream(),true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
            out.println(localDate.format(dtf));

            link.close();
            test.close();

        } catch (Exception e) {

        }
        
    }


    
}