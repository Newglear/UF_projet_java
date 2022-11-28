package NetworkManager;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTcpReceiveConnection extends Thread{
    public final static int portTcpReceive = 4753;

    public ThreadTcpReceiveConnection(){
        start();
    }
    public void run(){
        try {
            ServerSocket portEcoute = new ServerSocket(portTcpReceive);
            while (true) {  //TODO Modifier
                Socket connexion = portEcoute.accept();
                //TestTcpReceive test = new TestTcpReceive(connexion);
                //TODO Créer un nouveau Thread pour la conversation et lui fournir le socket
            }
        }catch (Exception e){e.printStackTrace();}
    }
}
