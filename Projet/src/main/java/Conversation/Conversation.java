package Conversation;

import Message.TCPMessage;
import Message.TCPType;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class Conversation {

    private int destinataireId;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Conversation(Socket socket) throws IOException {
        this.socket = socket;
        InputStream input = socket.getInputStream();
        this.in = new ObjectInputStream(input);
        OutputStream outputStream = socket.getOutputStream();
        this.out = new ObjectOutputStream(outputStream);
        ThreadConversationReceive threadRcv = new ThreadConversationReceive(this);
        ThreadConversationSend threadSnd = new ThreadConversationSend(this);

    }


    public void traiterMessageEntrant(TCPMessage message) throws OpenConversationException {         // TODO
        switch (message.type){
            case UserData: System.out.println(message.getData()); break; // TODO faire des trucs avec la DB
            case OuvertureSession: throw new OpenConversationException("vous avez fait n'importe quoi avec les types de message");
            case FermetureSession: this.fermerConversation(); break;
        }
    }

    public void fermerConversation(){
        try {
            this.socket.close();
        } catch (IOException e) {
            System.out.println("Il y a eu un problème avec un socket, maintenant tu peux pleurer");
            e.printStackTrace();
        }
        // TODO  : fermer les thread avec le threadManager
    }

    public Socket getSocket(){
        return this.socket;
    }

    public int getDestinataireId(){
        return this.destinataireId;
    }

    public void setDestinataireId(int id){
        this.destinataireId=id;
    }

    public ObjectInputStream getIn(){
        return this.in;
    }

    public ObjectOutputStream getOut(){
        return this.out;
    }
}
