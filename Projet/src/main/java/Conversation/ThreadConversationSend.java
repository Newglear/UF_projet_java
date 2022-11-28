package Conversation;

import Message.TCPMessage;
import NetworkManager.TcpSend;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

public class ThreadConversationSend extends Thread{


    private Conversation conversation;


    public ThreadConversationSend(Conversation conversation){
        this.conversation=conversation;


        start();
    }


    public void run(){
        // TODO : un observer qui notifie quand un message doit être envoyé
    }


    public void sendMessage(String data) throws IOException {
        TCPMessage message = new TCPMessage(this.conversation.getDestinataireId(), data);
        TcpSend.envoyerMessage(this.conversation.getOut(), message);
        // TODO : faire des trucs avec la database
    }
}
