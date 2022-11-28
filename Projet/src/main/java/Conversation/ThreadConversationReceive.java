package Conversation;

import Message.TCPMessage;
import Message.TCPType;
import NetworkManager.TcpReceiveData;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.atomic.AtomicReference;

public class ThreadConversationReceive extends Thread{

    private Conversation conversation;

    public ThreadConversationReceive(Conversation conversation){
        this.conversation=conversation;

        start();
    }


    public void run(){
        // init des paramètres de la conversation (destinataire)
        try {
            TCPMessage message = TcpReceiveData.receiveData(this.conversation.getIn());
            conversation.setDestinataireId(message.getDestinataireId());
            if (message.type!= TCPType.OuvertureSession) {
                throw new OpenConversationException("Le message passé n'est pas un OuvertureSession");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        while (true){ // TODO : changer ce while true moche
            try {
                TCPMessage message = TcpReceiveData.receiveData(this.conversation.getIn());
                conversation.traiterMessageEntrant(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
