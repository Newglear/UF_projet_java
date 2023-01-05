package chavardage.conversation;

import chavardage.AssignationProblemException;
import chavardage.message.TCPMessage;
import chavardage.userList.ListeUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.function.Consumer;

public class Conversation implements Consumer<TCPMessage> {

    private static final Logger LOGGER = LogManager.getLogger(Conversation.class);
    public static final int DEFAULT_DESTINATAIRE = 0;
    private int destinataireId;
    private boolean isClosed = false;
    private boolean isOpen = false;

    protected Conversation(int destinataireId) {
        this.destinataireId = destinataireId;
        LOGGER.trace("création d'une conversation avec " + destinataireId);
    }

    protected Conversation(){
        this.destinataireId = DEFAULT_DESTINATAIRE;
        LOGGER.trace("création d'une conversation avec un destinataire par défaut");
    }

    @Override
    public synchronized void accept(TCPMessage message) {
        try {
            if (message.getDestinataireId()!=ListeUser.getInstance().getMyId()){
                ConversationException e = new ConversationException("Un message destiné à un autre utilisateur a été reçu");
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                return;
            }
        } catch (AssignationProblemException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        switch (message.getType()) {
            case UserData:
                LOGGER.trace("message reçu : " + message.getData() + ", traitement du message en cours"); break; // TODO faire des trucs avec la DB
            case OuvertureSession:
                if (!isOpen){
                    this.isOpen=true;
                    try {
                        this.destinataireId=message.getEnvoyeurId();
                        LOGGER.trace("le destinataireId a été set à " + this.destinataireId);
                        notifyAll(); // c'est bon, le destinataire id a été set
                    } catch (AssignationProblemException e) {
                        LOGGER.error(e.getMessage());
                        e.printStackTrace();
                    }
                }else {
                    ConversationException e = new ConversationException("Un message d'ouverture de session a été passé à cette conversation déjà ouverte");
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
                break;
            case FermetureSession:
                try {
                    this.fermer();
                } catch (IOException | ConversationException e1) {
                    LOGGER.error(e1.getMessage());
                    e1.printStackTrace();
                }
                break;
        }
    }

    public synchronized void fermer() throws IOException, ConversationException {
        if (this.isClosed){
            throw new ConversationException("cette conversation a déjà été fermée");
        }
        isClosed=true;
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
