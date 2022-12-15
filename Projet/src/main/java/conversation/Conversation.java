package conversation;

import message.TCPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import userList.AssignationProblemException;
import userList.ListeUser;

import java.io.IOException;

public class Conversation {

    private static final Logger LOGGER = LogManager.getLogger(Conversation.class);
    private final int destinataireId;


    // TODO supprimer
    /* protected Conversation(Socket socket) {
        // récupération des paramètres (destinataire id) dans le premier message d'initiation de connexion
        try {
            InputStream input = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(input);
            TCPMessage firstMessage = (TCPMessage) in.readObject();
            this.destinataireId = firstMessage.getDestinataireId();
            if (firstMessage.getType() != TCPType.OuvertureSession) {
                throw new OpenConversationException("Le message passé n'est pas un OuvertureSession");
            }
            LOGGER.trace("création d'une conversation avec " + destinataireId);
            // lancement du thread de reception des messages
            this.reception = new ThreadTCPReceiveData(destinataireId, socket);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

    }*/



    protected Conversation(int destinataireId) {
        this.destinataireId = destinataireId;
        ListeUser listeUser = ListeUser.getInstance();
        // TODO this.socket = TCPSend.tcpConnect(listeUser.getUser(destinataireId).getId());
        LOGGER.trace("création d'une conversation avec " + destinataireId);
        // TODO this.reception = new ThreadTCPReceiveData(destinataireId, socket);
    }

    public void traiterMessageEntrant(TCPMessage message) throws ConversationException {

        try {
            if (message.getDestinataireId()!=ListeUser.getInstance().getMyId()){
                throw new ConversationException("Un message destiné à un autre utilisateur a été reçu");
            }
        } catch (AssignationProblemException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        switch (message.getType()) {
            case UserData:
                LOGGER.trace("message reçu : " + message.getData() + ", traitement du message en cours"); break; // TODO faire des trucs avec la DB
            case OuvertureSession:
                throw new ConversationException("Un message d'ouverture de session a été passé à cette conversation déjà ouverte");
            case FermetureSession:
                try {
                    this.fermerConversation();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
                break;
        }
    }

    public void fermerConversation() throws IOException {
        LOGGER.trace("fermeture de la conversation avec " + destinataireId);
    }

    public int getDestinataireId() {
        return this.destinataireId;
    }


    public void sendMessage(String data) {
        TCPMessage message = new TCPMessage(this.destinataireId, data);
        // TODO TCPSend.envoyerMessage(message, );
        // TODO : faire des trucs avec la database
    }

}
