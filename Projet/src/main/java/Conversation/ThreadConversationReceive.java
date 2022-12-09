package Conversation;

import Message.TCPMessage;
import Message.TCPType;
import NetworkManager.TcpReceiveData;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.atomic.AtomicReference;

public class ThreadConversationReceive extends Thread{

    private final Conversation conversation;
    private boolean isFinished = false;

    public ThreadConversationReceive(Conversation conversation){
        this.conversation=conversation;

        start();
    }


    public void run(){
        // init des paramètres de la conversation (destinataire)
        try {
            TCPMessage message = TcpReceiveData.receiveData(this.conversation.getSocket());
            conversation.setDestinataireId(message.getDestinataireId());
            if (message.type!= TCPType.OuvertureSession) {
                throw new OpenConversationException("Le message passé n'est pas un OuvertureSession");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        while (!isFinished){ // TODO : changer ce while true moche
            try {
                TCPMessage message = TcpReceiveData.receiveData(this.conversation.getSocket());
                conversation.traiterMessageEntrant(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setFinished(){
        this.isFinished=true;
    }

}
