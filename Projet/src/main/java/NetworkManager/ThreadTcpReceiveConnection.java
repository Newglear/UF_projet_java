package NetworkManager;
import java.net.*;

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
                //TcpReceiveTest test = new TcpReceiveTest(connexion);
                //TODO Créer un nouveau Thread pour la conversation et lui fournir le socket
            }
        }catch (Exception e){e.printStackTrace();}
    }
}
