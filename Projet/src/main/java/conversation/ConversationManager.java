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

    private static final Logger LOGGER = LogManager.getLogger(ConversationManager.class);


    protected static Map<Integer, Conversation> mapConversations = Collections.synchronizedMap(new HashMap<>());

    public static Conversation createConv(int destinataireId) {
        try {
            Conversation conversation = new Conversation(destinataireId); // TODO la création de la conversation a beson de récupérer la conversation dans la hashmap et du coup ça fait de la merde
            mapConversations.put(conversation.getDestinataireId(), conversation);
            LOGGER.trace("création d'une conversation avec " + destinataireId);
            return conversation;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static Conversation createConv(Socket socket) {
        try {
            Conversation conversation = new Conversation(socket);
            mapConversations.put(conversation.getDestinataireId(), conversation);
            LOGGER.trace("création d'une conversation avec " + conversation.getDestinataireId());
            return conversation;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static Conversation getConv(int destinataireId) {
        return mapConversations.get(destinataireId);
    }

}
