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

    private int destinataireId = -1;
    private final Socket socket;

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
        ThreadTcpReceiveData reception = new ThreadTcpReceiveData(destinataireId);
        reception.run(socket);
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
