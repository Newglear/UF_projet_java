package Conversation;

import Message.TCPControlMessage;
import UserList.UserItem;

import java.net.Socket;

public class Conversation {

    int destinataireId;
    Socket socket;

    public Conversation(Socket socket, TCPControlMessage controlMessage){
        this.socket = socket;
        this.destinataireId=controlMessage.getDestinataireId();
        // TODO  : demander au ThreadManager un thread
    }


    public void sendMessage(String data){


    }



}
