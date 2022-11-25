package Conversation;

import Message.TCPMessage;
import NetworkManager.TcpSend;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

public class ThreadConversationSend extends Thread{


    Conversation conversation;
    ObjectOutputStream out;


    public ThreadConversationSend(Conversation conversation){
        this.conversation=conversation;
        // création objet pour envoyer les messages
        AtomicReference<OutputStream> outputStream = null; // intelliJ a fait ces trucs chelous tout seul mais si ça marche c'est cool
        try {
            outputStream.set(conversation.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.out = new ObjectOutputStream(outputStream.get());
        } catch (IOException e) {
            e.printStackTrace();
        }

        start();
    }


    public void run(){
        // TODO : un observer qui notifie quand un message doit être envoyé
    }


    public void sendMessage(String data) throws IOException {
        TCPMessage message = new TCPMessage(this.conversation.destinataireId, data);
        TcpSend.EnvoyerMessage(this.out, message);
        // TODO : faire des trucs avec la database
    }
}
