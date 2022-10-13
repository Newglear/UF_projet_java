import java.net.*;

public class scanner {
    
    public static void tester(int i){
        try{
            ServerSocket test = new ServerSocket(i);
            System.out.println("Server is not listening on port " + i + " of localhost");
            test.close();
        }catch(Exception e){
            System.out.println("Server is listening on port " + i + " of localhost");
        }
    }

    public static void main(String args[]){
        for(int indice = 1;indice<1027;indice++){
            tester(indice);
        }
    }
}
