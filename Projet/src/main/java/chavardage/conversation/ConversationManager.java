package chavardage.conversation;

import chavardage.AssignationProblemException;
import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.networkManager.TCPReceiveData;
import chavardage.networkManager.TCPSendData;
import chavardage.userList.ListeUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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
    protected Map<Integer, TCPSendData> sendDataMap = Collections.synchronizedMap(new HashMap<>());
    protected Map<Integer, TCPReceiveData> receiveDataMap = Collections.synchronizedMap(new HashMap<>());

    protected synchronized void addConv(int destinataireId,Conversation conversation ) {
        mapConversations.put(destinataireId,conversation);
    }


    protected synchronized void addSendData(int destinataireId, TCPSendData sendData) {
        sendDataMap.put(destinataireId, sendData);
    }

    protected synchronized void addReceiveData(int destinataireId, TCPReceiveData receiveData) {
        receiveDataMap.put(destinataireId, receiveData);
    }


    protected synchronized void createConversation(Socket socket) throws ConversationAlreadyExists, ConversationException, InterruptedException {
        LOGGER.trace("appel de createConversation");
        Conversation conversation = new Conversation();
        TCPReceiveData receiveData = new TCPReceiveData(socket, conversation);
        try {
            /*if (conversation.getDestinataireId()==Conversation.DEFAULT_DESTINATAIRE){
                wait();
            }*/
            if (conversation.getDestinataireId() == ListeUser.getInstance().getMyId()){
                throw new ConversationException("vous ne pouvez pas créer de conversation avec vous-même");
            }
            if (mapConversations.containsKey(conversation.getDestinataireId())){
                throw new ConversationAlreadyExists(conversation.getDestinataireId());
            }
            TCPSendData sendData = new TCPSendData(socket);
            getInstance().addConv(conversation.getDestinataireId(), conversation);
            getInstance().addReceiveData(conversation.getDestinataireId(), receiveData);
            getInstance().addSendData(conversation.getDestinataireId(), sendData);
            LOGGER.trace("la conversation avec " + conversation.getDestinataireId() + " a bien été créée ");
        } catch (IOException | AssignationProblemException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }


    protected synchronized Conversation getConv(int destinataireId) throws ConversationDoesNotExist {
        try {
            return mapConversations.get(destinataireId);
        }catch (Exception e){
            throw new ConversationDoesNotExist(destinataireId);
        }
    }

    protected synchronized TCPSendData getSendData(int destinataireId) throws ConversationDoesNotExist {
        try {
            return sendDataMap.get(destinataireId);
        }catch (Exception e){
            throw new ConversationDoesNotExist(destinataireId);
        }
    }

    protected synchronized TCPReceiveData getReceiveData(int destinataireId) throws ConversationDoesNotExist {
        try {
            return receiveDataMap.get(destinataireId);
        }catch (Exception e){
            throw new ConversationDoesNotExist(destinataireId);
        }
    }

    public synchronized void clear() {
        this.mapConversations.clear();
        this.receiveDataMap.clear();
        this.sendDataMap.clear();
    }


    @Override
    public void accept(Socket socket) {
        try {
            getInstance().createConversation(socket);
            // TODO se démerder avec le socket pour la réception ah ah bon courage
        } catch (ConversationAlreadyExists | ConversationException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public synchronized void fermerConversation(int destinataireId){
        // fermeture thread reception
        try {
            getInstance().getReceiveData(destinataireId).interrupt();
            getInstance().getSendData(destinataireId).close();
            getInstance().getConv(destinataireId).fermer();
        } catch (ConversationDoesNotExist | IOException | ConversationException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
