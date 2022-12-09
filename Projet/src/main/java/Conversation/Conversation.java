package Conversation;

import Message.TCPMessage;
import Message.TCPType;
import NetworkManager.TcpReceiveData;
import NetworkManager.TcpSend;
import UserList.ListeUser;
import UserList.UserNotFoundException;

import java.io.*;
import java.net.Socket;


public class Conversation {

    private int destinataireId = 0;
    private final Socket socket;

    public Conversation(Socket socket) throws Exception {
        this.socket = socket;
        TCPMessage message = TcpReceiveData.receiveData(socket);
        this.destinataireId = message.getDestinataireId();
        if (message.type!= TCPType.OuvertureSession) {
            throw new OpenConversationException("Le message passé n'est pas un OuvertureSession");
        }
        ThreadConversationReceive threadRcv = new ThreadConversationReceive(this);

    }


    public Conversation(int destinataireId) throws Exception {
        this.destinataireId= destinataireId;
        this.socket= TcpSend.tcpConnect(ListeUser.getUser(destinataireId).getAddress());
         ThreadConversationReceive threadRcv = new ThreadConversationReceive(this);
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


    public void sendMessage(String data) throws IOException {
        TCPMessage message = new TCPMessage(this.destinataireId, data);
        TcpSend.envoyerMessage(this.socket, message);
        // TODO : faire des trucs avec la database
    }

}
