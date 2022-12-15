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
import java.net.InetAddress;
import java.net.Socket;

public class Conversation {

    private static final Logger LOGGER = LogManager.getLogger(Conversation.class);
    private int destinataireId;
    private InetAddress destinataireAddress;
    private ThreadTCPReceiveData reception;


    /**
     * on a reçu une demande de connexion externe et on crée la conversation
     */
    protected Conversation(Socket socket) {
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
            this.reception = new ThreadTCPReceiveData(destinataireId, socket);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

    }


    /**
     * Le user local veut créer une conversation avec un autre user
     */
    protected Conversation(int destinataireId) {
        this.destinataireId = destinataireId;
        ListeUser listeUser = ListeUser.getInstance();
        try {
            // TODO this.socket = TCPSend.tcpConnect(listeUser.getUser(destinataireId).getId());
            LOGGER.trace("création d'une conversation avec " + destinataireId);
            // TODO this.reception = new ThreadTCPReceiveData(destinataireId, socket);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

    }

    public void traiterMessageEntrant(TCPMessage message) throws OpenConversationException {
        switch (message.type) {
            case UserData:
                LOGGER.trace("message reçu : " + message.getData() + ", traitement du message en cours"); break; // TODO faire des trucs avec la DB
            case OuvertureSession:
                throw new OpenConversationException("Un message d'ouverture de session a été passé à cette conversation");
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
        this.reception.interrupt();
        LOGGER.trace("fermeture de la conversation avec " + destinataireId);
    }

    public int getDestinataireId() throws AssignationProblemException {
        if (this.destinataireId == -1) {
            throw new AssignationProblemException("Conversation", "destinataireId");
        }
        return this.destinataireId;
    }


    public void sendMessage(String data) {
        TCPMessage message = new TCPMessage(this.destinataireId, data);
        // TODO TCPSend.envoyerMessage(message, );
        // TODO : faire des trucs avec la database
    }

}
