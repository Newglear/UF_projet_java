package chavardage.conversation;

import chavardage.AssignationProblemException;
import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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
        mapConversations.put(destinataireId, new Conversation(destinataireId));
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
        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(inputStream);
            TCPMessage firstMessage = (TCPMessage) in.readObject(); // le premier message reçu, normalement un ouverture connexion
            if (firstMessage.getType()!= TCPType.OuvertureSession){
                ConversationException e = new ConversationException("le message passé n'est pas un ouverture session");
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
            getInstance().addConv(firstMessage.getEnvoyeurId());
            // TODO se démerder avec le socket pour la réception ah ah bon courage
        } catch (IOException | ClassNotFoundException | AssignationProblemException | ConversationAlreadyExists | ConvWithSelf e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

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
