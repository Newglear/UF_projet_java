package NetworkManager;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpReceiveConnection extends Thread{
    public final static int portTcpReceive = 3650;
    public void run(String args[]) throws Exception{
        ServerSocket portEcoute = new ServerSocket(portTcpReceive);
        while(true){
            Socket connexion = portEcoute.accept();
            TestTcpReceive test = new TestTcpReceive(connexion);
            //TODO Créer un nouveau Thread pour la conversation et lui fournir le socket
        }
    }
}
