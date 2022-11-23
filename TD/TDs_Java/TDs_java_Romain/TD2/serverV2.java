import java.net.*;
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  

public class serverV2 {
    public static void main(String args[]) throws Exception{
        ServerSocket test = new ServerSocket(6000);
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for(int k=0;k<3;k++){
            Socket link = test.accept();
            treatclientV2 Thread = new treatclientV2(link);
            executor.execute(Thread);
        }
        executor.shutdown();  
        while (!executor.isTerminated()) {   }  
        test.close();
    }
}