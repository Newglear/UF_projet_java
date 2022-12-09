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
