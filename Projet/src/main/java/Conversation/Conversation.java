package Conversation;

import Message.TCPMessage;
import Message.TCPType;
import NetworkManager.TcpSend;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class Conversation {

    int destinataireId;
    Socket socket;
    ObjectOutputStream out;

    public Conversation(Socket socket, TCPMessage controlMessage) throws OpenConversationException {
        this.socket = socket;
        if (controlMessage.type!= TCPType.OuvertureSession) {
            throw new OpenConversationException("Le message passé n'est pas un OuvertureSession");
        }
        this.destinataireId=controlMessage.getDestinataireId();
        // création objet pour envoyer les messages
        AtomicReference<OutputStream> outputStream = null; // intelliJ a fait ces trucs chelous tout seul mais si ça marche c'est cool
        try {
            outputStream.set(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.out = new ObjectOutputStream(outputStream.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO  : demander au ThreadManager un thread




    }


    public void sendMessage(String data) throws IOException {
        TCPMessage message = new TCPMessage(this.destinataireId, data);
        TcpSend.EnvoyerMessage(this.out, message);
    }



}
