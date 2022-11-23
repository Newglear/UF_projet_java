import java.net.*; 
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class IPBox {
    
    


    public static void main(String args[]) throws Exception {
        // création socket send-config 
        InetAddress add = InetAddress.getByName("localhost");
        int portSendConfig = 1234; 
        Socket sendConfig = new Socket(add, portSendConfig); 

       
        PrintWriter out = new PrintWriter(sendConfig.getOutputStream(),true);
        out.println("ready-to-test");
        sendConfig.close(); 
  
        // création socket receive-config 
        int portRecvConfig = 1235 ; 
        ServerSocket waitForRecvConfig = new ServerSocket(portRecvConfig); 
        Socket recvConfig = waitForRecvConfig.accept(); 
        BufferedReader buffer = new BufferedReader(new InputStreamReader(recvConfig.getInputStream()));
        PrintWriter out2 = new PrintWriter(recvConfig.getOutputStream(),true);

        String inputMessage = buffer.readLine(); 
        System.out.println("je suis IPBox et j'ai reçu "+ inputMessage);
        out2.println("/n");
        recvConfig.close(); 
        waitForRecvConfig.close();

        

    

    }

}
