package NetworkManager;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpReceiveConnection {
    public final static int portTcpReceive = 3650;
    public static void main(String args[]) throws Exception{
        ServerSocket portEcoute = new ServerSocket(portTcpReceive);
        while(true){
            Socket connexion = portEcoute.accept();
            //TODO Créer un nouveau Thread et lui assigner le numéro de port

        }
    }
}
