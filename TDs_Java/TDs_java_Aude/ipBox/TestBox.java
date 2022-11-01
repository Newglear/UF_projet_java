import java.net.*; 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TestBox {


    public static void main(String args[]) throws Exception{
        int portSendConfig = 1234; 
        int portRecvConfig = 1235 ; 
        InetAddress add = InetAddress.getByName("localhost");


        ServerSocket waitForSendConfig = new ServerSocket(portSendConfig); 
        Socket letsConnect = waitForSendConfig.accept(); 
        BufferedReader buffer = new BufferedReader(new InputStreamReader(letsConnect.getInputStream()));
        String inputMessage = buffer.readLine(); 
        System.out.println("je suis testBox et j'ai reçu "+ inputMessage);

        letsConnect = new Socket(add, portRecvConfig); 

        PrintWriter out = new PrintWriter(letsConnect.getOutputStream(),true);
        out.println("/n");

        // il veut pas m'afficher les trucs si je mets le même buffer que tout à l'heure, ça met null et j'ai 
        // la flemme de chercher plus loin 
        BufferedReader buffer2 = new BufferedReader(new InputStreamReader(letsConnect.getInputStream()));
        
        inputMessage = buffer2.readLine(); 
        System.out.println("je suis testBox et j'ai reçu "+ inputMessage);


        letsConnect.close(); 
        waitForSendConfig.close();

    




    }

}