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
                try {
                    ConversationManager.getInstance().fermerConversation(destinataireId);
                } catch (ConversationDoesNotExist conversationDoesNotExist) {
                    LOGGER.error(conversationDoesNotExist.getMessage());
                    conversationDoesNotExist.printStackTrace();
                }
                break;
        }
    }



    public int getDestinataireId() {
        return this.destinataireId;
    }


    public void sendMessage(String data) {
        // TODO : faire des trucs avec la database
    }

}
