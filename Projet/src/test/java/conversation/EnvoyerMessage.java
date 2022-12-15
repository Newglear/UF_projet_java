package conversation;

public class EnvoyerMessage extends Thread{
    public EnvoyerMessage(){
        start();
    }

    /*public void run(){
        try {
            Thread.sleep(100); // pour laisser le temps au serveur de démarrer
            Socket socket = new Socket(InetAddress.getLocalHost(), ThreadTCPServeur.PORT_TCP);
            TCPSend.envoyerMessage(new TCPMessage(1, TCPType.OuvertureSession), );

        } catch (IOException | WrongConstructorException | InterruptedException e) {
            e.printStackTrace();
        }
    }*/
}
