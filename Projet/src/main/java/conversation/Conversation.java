package conversation;

import message.TCPMessage;
import message.TCPType;
import networkManager.TCPSend;
import networkManager.ThreadTCPReceiveData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import userList.AssignationProblemException;
import userList.ListeUser;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Conversation {

    private static final Logger LOGGER = LogManager.getLogger(Conversation.class);
    private int destinataireId;
    private Socket socket;
    private ThreadTCPReceiveData reception;


    /**
     * on a reçu une demande de connexion externe et on crée la conversation
     */
    public Conversation(Socket socket) {
        this.socket = socket;
        // récupération des paramètres (destinataire id) dans le premier message d'initiation de connexion
        try {
            InputStream input = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(input);
            TCPMessage firstMessage = (TCPMessage) in.readObject();
            this.destinataireId = firstMessage.getDestinataireId();
            if (firstMessage.type != TCPType.OuvertureSession) {
                throw new OpenConversationException("Le message passé n'est pas un OuvertureSession");
            }
            LOGGER.trace("création d'une conversation avec " + destinataireId);
            // lancement du thread de reception des messages
            this.reception = new ThreadTCPReceiveData(destinataireId);
            reception.run(socket);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

    }


    /**
     * Le user local veut créer une conversation avec un autre user
     */
    public Conversation(int destinataireId) {
        this.destinataireId = destinataireId;
        try {
            this.socket = TCPSend.tcpConnect(ListeUser.getUser(destinataireId).getId());
            LOGGER.trace("création d'une conversation avec " + destinataireId);
            this.reception = new ThreadTCPReceiveData(destinataireId);
            reception.run(socket);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

    }

    public void traiterMessageEntrant(TCPMessage message) throws OpenConversationException {
        switch (message.type) {
            case UserData:
                System.out.println(message.getData());
                break; // TODO faire des trucs avec la DB
            case OuvertureSession:
                throw new OpenConversationException("vous avez fait n'importe quoi avec les types de message");
            case FermetureSession:
                this.fermerConversation();
                break;
        }
    }

    public void fermerConversation() {
        this.reception.setFinished();
    }

    public int getDestinataireId() throws AssignationProblemException {
        if (this.destinataireId == -1) {
            throw new AssignationProblemException("Conversation", "destinataireId");
        }
        return this.destinataireId;
    }


    public void sendMessage(String data) throws IOException {
        TCPMessage message = new TCPMessage(this.destinataireId, data);
        TCPSend.envoyerMessage(this.socket, message);
        // TODO : faire des trucs avec la database
    }

}
