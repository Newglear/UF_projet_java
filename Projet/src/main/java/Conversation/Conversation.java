package Conversation;

import Message.TCPMessage;
import Message.TCPType;
import NetworkManager.TcpSend;
import NetworkManager.ThreadTcpReceiveData;
import UserList.AssignationProblemException;
import UserList.ListeUser;

import java.io.*;
import java.net.Socket;


public class Conversation {

    private final int destinataireId;
    private final Socket socket;
    private ThreadTcpReceiveData reception;

    public Conversation(Socket socket) throws Exception {
        this.socket = socket;
        // récupération des paramètres (destinataire id) dans le premier message d'initiation de connexion
        InputStream input = socket.getInputStream();
        ObjectInputStream in = new ObjectInputStream(input);
        TCPMessage firstMessage = (TCPMessage) in.readObject();
        this.destinataireId = firstMessage.getDestinataireId();
        if (firstMessage.type!= TCPType.OuvertureSession) {
            throw new OpenConversationException("Le message passé n'est pas un OuvertureSession");
        }
        // lancement du thread de reception des messages
        this.reception = new ThreadTcpReceiveData(destinataireId);
        reception.run(socket, this);
    }


    public Conversation(int destinataireId) throws Exception {
        this.destinataireId= destinataireId;
        this.socket= TcpSend.tcpConnect(ListeUser.getUser(destinataireId).getAddress());
    }

    public void traiterMessageEntrant(TCPMessage message) throws OpenConversationException {
        switch (message.type){
            case UserData: System.out.println(message.getData()); break; // TODO faire des trucs avec la DB
            case OuvertureSession: throw new OpenConversationException("vous avez fait n'importe quoi avec les types de message");
            case FermetureSession: this.fermerConversation(); break;
        }
    }

    public void fermerConversation(){
        this.reception.setFinished();
    }

    public Socket getSocket(){
        return this.socket;
    }

    public int getDestinataireId() throws AssignationProblemException {
        if (this.destinataireId == -1){
            throw new AssignationProblemException();
        }
        return this.destinataireId;
    }


    public void sendMessage(String data) throws IOException {
        TCPMessage message = new TCPMessage(this.destinataireId, data);
        TcpSend.envoyerMessage(this.socket, message);
        // TODO : faire des trucs avec la database
    }

}
