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
    private final int destinataireId;
    private boolean isClosed = false;

    protected Conversation(int destinataireId) {
        this.destinataireId = destinataireId;
        LOGGER.trace("création d'une conversation avec " + destinataireId);
    }

    @Override
    public void accept(TCPMessage message) {
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
                ConversationException e = new ConversationException("Un message d'ouverture de session a été passé à cette conversation déjà ouverte");
                LOGGER.error(e.getMessage());
                e.printStackTrace();
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
