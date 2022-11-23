import java.net.*;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.time.*;
import java.time.format.DateTimeFormatter;


public class treatclientV2 extends Thread{
    Socket link;
    public treatclientV2(Socket s){
        super();
        this.link = s; 
    }
    public void run(){
        try{
            LocalDateTime localDate = LocalDateTime.now();
            InputStream input = link.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader NewRead = new BufferedReader(reader);
            PrintWriter out = new PrintWriter(link.getOutputStream(),true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
            out.println(localDate.format(dtf));
    
            link.close();
        } catch(Exception e){}
    }
}