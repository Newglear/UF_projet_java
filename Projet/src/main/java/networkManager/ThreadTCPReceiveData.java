package networkManager;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import conversation.Conversation;
import message.TCPMessage;


public class ThreadTCPReceiveData extends Thread {

    private boolean isFinished;

    public ThreadTCPReceiveData(int destinataireId) {
        isFinished = false;
    }

    public void run(Socket socket, Conversation conversation) throws  Exception{
        InputStream input = socket.getInputStream();
        ObjectInputStream in = new ObjectInputStream(input);
        while (!isFinished) {
            try {
                TCPMessage message = (TCPMessage) in.readObject();
                // on passe le message à la conversation
                conversation.traiterMessageEntrant(message); // TODO ici j'ai un nullpointerexception avec le conv manager
            } catch (Exception e) {
                e.printStackTrace(); // il ferme le socket un peu trop souvent oupsy
                System.out.println("je me termine ");
                isFinished = true;
                socket.close();
            }
        }

    }

    public void setFinished(){
        this.isFinished=true;
    }


}
