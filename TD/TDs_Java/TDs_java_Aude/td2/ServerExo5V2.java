import java.net.*;

// cf TDs Romain 

class ServerExo5 {



    public static void main(String args[]) throws Exception{

        int port = 4321; 
        ServerSocket serverSocket = new ServerSocket(port); 
        System.out.println("serveur démarré");

        for (int i = 0 ; i < 3; i++){
            Socket socket = serverSocket.accept(); 
            System.out.print("demande de connection détectée");
            TreatClient client = new TreatClient(socket); 
        }

        serverSocket.close(); 




    }
}