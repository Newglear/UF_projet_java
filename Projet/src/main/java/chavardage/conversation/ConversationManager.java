package chavardage.conversation;

import chavardage.AssignationProblemException;
import chavardage.userList.ListeUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ConversationManager implements Consumer<Socket> {

    // constructeur privé car personne ne doit pouvoir l'appeler (singleton)
    private ConversationManager() {
    }

    private static final ConversationManager instance = new ConversationManager();

    public static ConversationManager getInstance() {
        return instance;
    }

    private static final Logger LOGGER = LogManager.getLogger(ConversationManager.class);

    protected Map<Integer, Conversation> mapConversations = Collections.synchronizedMap(new HashMap<>());

    public synchronized void addConv(int destinataireId) throws ConversationAlreadyExists, ConvWithSelf {
        if (mapConversations.containsKey(destinataireId)){
            throw new ConversationAlreadyExists(destinataireId);
        }
        try {
            if (destinataireId == ListeUser.getInstance().getMyId()){
                throw new ConvWithSelf("vous ne pouvez pas créer de conversation avec vous-même");
            }
        } catch (AssignationProblemException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        Conversation conversation = new Conversation(destinataireId);
        mapConversations.put(conversation.getDestinataireId(), conversation);
    }


    public synchronized Conversation getConv(int destinataireId) throws ConversationDoesNotExist {
        try {
            return mapConversations.get(destinataireId);
        }catch (Exception e){
            throw new ConversationDoesNotExist(destinataireId);
        }
    }

    public synchronized void clear() {
        this.mapConversations.clear();
    }


    @Override
    public void accept(Socket socket) {
        // TODO créer conversation associée au socket

    /* public synchronized void createConv(Socket socket) {
        try {
            Conversation conversation = new Conversation(socket);
            mapConversations.put(conversation.getDestinataireId(), conversation);
            LOGGER.trace("création d'une conversation avec " + conversation.getDestinataireId() + " : connexion entrante");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }*/

    }
}
