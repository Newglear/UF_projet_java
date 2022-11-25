package Conversation;

import Message.TCPMessage;
import NetworkManager.TcpReceiveData;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.atomic.AtomicReference;

public class ThreadConversationReceive extends Thread{

    Conversation conversation;
    ObjectInputStream in;

    public ThreadConversationReceive(Conversation conversation) throws IOException {
        this.conversation=conversation;
        AtomicReference<InputStream> input = null;
        try {
            input.set(conversation.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.in = new ObjectInputStream(input.get());
        start();
    }


    public void run(){
        while (true){ // TODO : changer ce while true moche
            try {
                TCPMessage message = TcpReceiveData.receiveData(in);
                conversation.traiterMessageEntrant(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
