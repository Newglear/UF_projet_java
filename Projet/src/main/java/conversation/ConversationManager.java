package conversation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConversationManager {

    // constructeur privé car personne ne doit pouvoir l'appeler (singleton)
    private ConversationManager() {
    }

    private static final ConversationManager instance = new ConversationManager();

    public static ConversationManager getInstance() {
        return instance;
    }

    private static final Logger LOGGER = LogManager.getLogger(ConversationManager.class);


    protected Map<Integer, Conversation> mapConversations = Collections.synchronizedMap(new HashMap<>());

    public synchronized void createConv(int destinataireId) throws ConversationAlreadyExists {
        if (mapConversations.containsKey(destinataireId)){
            throw new ConversationAlreadyExists(destinataireId);
        }
        try {
            Conversation conversation = new Conversation(destinataireId);
            mapConversations.put(conversation.getDestinataireId(), conversation);
            LOGGER.trace("création d'une conversation avec " + destinataireId + " : connexion sortante");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void createConv(Socket socket) {
        try {
            Conversation conversation = new Conversation(socket);
            mapConversations.put(conversation.getDestinataireId(), conversation);
            LOGGER.trace("création d'une conversation avec " + conversation.getDestinataireId() + " : connexion entrante");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized Conversation getConv(int destinataireId) {
        return mapConversations.get(destinataireId);
    }

    public synchronized void clear() {
        this.mapConversations.clear();
    }


}
