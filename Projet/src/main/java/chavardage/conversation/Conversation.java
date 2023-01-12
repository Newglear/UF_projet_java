package chavardage.conversation;

import chavardage.AssignationProblemException;
import chavardage.message.TCPMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

public class Conversation implements Consumer<TCPMessage> {

    private static final Logger LOGGER = LogManager.getLogger(Conversation.class);
    public static final int DEFAULT_DESTINATAIRE = 0;
    private int destinataireId;
    private boolean isOpen = false;

    private final ConversationManager conversationManager;

    protected Conversation(int destinataireId, ConversationManager conversationManager) {
        this.destinataireId = destinataireId;
        this.conversationManager = conversationManager;
        LOGGER.trace("création d'une conversation avec " + destinataireId);
    }

    protected Conversation(ConversationManager conversationManager){
        this.destinataireId = DEFAULT_DESTINATAIRE;
        this.conversationManager = conversationManager;
        LOGGER.trace("création d'une conversation avec un destinataire par défaut");
    }


    @Override
    public void accept(TCPMessage message) {
        switch (message.getType()) {
            case UserData:
                // TODO faire des trucs avec la DB
                LOGGER.info("message : " + message.getTexte());
                LOGGER.trace("message reçu : " + message + ", traitement du message en cours"); break;
            case OuvertureSession:
                if (!isOpen){
                    this.isOpen=true;
                    try {
                        this.destinataireId=message.getEnvoyeurId();
                        LOGGER.trace("le destinataireId a été set à " + this.destinataireId + ", je notifie");
                        synchronized (this){ // je locke pour pouvoir notifier
                            this.notify(); // c'est bon, le destinataire id a été set
                        }
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
                conversationManager.fermerConversation(destinataireId);
                break;
        }
    }



    public synchronized int getDestinataireId() {
        return this.destinataireId;
    }


    public void sendMessage(String data) {
        // TODO : faire des trucs avec la database
    }

}
