package Conversation;

import Message.TCPMessage;
import Message.TCPType;

import java.io.IOException;
import java.net.Socket;

public class Conversation {

    int destinataireId;
    Socket socket;

    public Conversation(Socket socket, TCPMessage controlMessage) throws OpenConversationException {
        this.socket = socket;
        if (controlMessage.type!= TCPType.OuvertureSession) {
            throw new OpenConversationException("Le message passé n'est pas un OuvertureSession");
        }
        this.destinataireId=controlMessage.getDestinataireId();

        // TODO  : demander au ThreadManager un thread pour la réception et un pour l'envoi
        // TODO : récupérer les messages de la conversation dans la database
        // TODO : créer méthode thread pour la réception

    }


    public void traiterMessageEntrant(TCPMessage message) throws OpenConversationException {         // TODO
        switch (message.type){
            case UserData: System.out.println(message.getData()); break; // TODO faire des trucs avec la DB
            case OuvertureSession: throw new OpenConversationException("vous avez fait n'importe quoi avec les types de message");
            case FermetureSession: this.fermerConversation(); break;
        }
    }

    public void fermerConversation(){
        // TODO  : fermer les thread avec le threadManager
        try {
            this.socket.close();
        } catch (IOException e) {
            System.out.println("Il y a eu un problème avec un socket, maintenant tu peux pleurer");
            e.printStackTrace();
        }
    }

}
