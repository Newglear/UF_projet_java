package NetworkManager;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import Conversation.ConversationManager;
import Message.TCPMessage;


public class ThreadTcpReceiveData extends Thread {

    private final int destinataireId;
    private boolean isFinished = false ;

    public ThreadTcpReceiveData(int destinataireId) {
        this.destinataireId = destinataireId;
    }

    public void run(Socket socket) throws  Exception{
        InputStream input = socket.getInputStream();
        ObjectInputStream in = new ObjectInputStream(input);
        while (!isFinished) {
            try {
                TCPMessage message = (TCPMessage) in.readObject();
                // on passe le message à la conversation
                ConversationManager.getConv(destinataireId).traiterMessageEntrant(message);
            } catch (Exception e) {
                System.out.println("oups l'exception");
            }
        }
        socket.close();

    }

    public void setFinished(){
        this.isFinished=true;
    }


}
