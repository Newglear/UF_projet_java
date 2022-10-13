import java.net.*;


public class server {
    public static void main(String args[]) throws Exception{
        ServerSocket test = new ServerSocket(6000);


        for(int k=0;k<3;k++){
            Socket link = test.accept();
            treatclient Thread = new treatclient(link);
        }
        test.close();
    }
}
