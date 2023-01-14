package chavardage.conversation;

import chavardage.AssignationProblemException;
import chavardage.IllegalConstructorException;
import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.networkManager.TCPConnect;
import chavardage.networkManager.TCPReceiveData;
import chavardage.networkManager.TCPSendData;
import chavardage.networkManager.TCPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import chavardage.userList.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ConversationManager implements Consumer<Socket> {

    private final ListeUser listeUser;
    private final int port ;


    /**singleton*/
    private ConversationManager() {
        this.listeUser=ListeUser.getInstance();
        this.port = TCPServeur.DEFAULT_PORT_TCP;
    }

    /**constructeur public pour tests*/
    public ConversationManager(boolean test, ListeUser listeUser, int portDistant) throws IllegalConstructorException {
        if (!test){
            throw new IllegalConstructorException();
        }
        this.listeUser=listeUser;
        this.port = portDistant;
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


    /* en protected pour les tests*/
    protected synchronized void addSendData(int destinataireId, TCPSendData sendData) {
        sendDataMap.put(destinataireId, sendData);
    }

    protected synchronized void addReceiveData(int destinataireId, TCPReceiveData receiveData) {
        receiveDataMap.put(destinataireId, receiveData);
    }


    /** créer une conversation quand une demande d'ouverture de session a été reçue*/
    protected void createConversation(Socket socket) throws ConversationAlreadyExists, ConversationException{
        LOGGER.trace("appel de createConversation");
        Conversation conversation = new Conversation(this);
        TCPReceiveData receiveData = new TCPReceiveData(socket, conversation);
        try {
            synchronized (conversation){ // je locke la conversation pour pouvoir l'attendre
                LOGGER.trace("j'attends que le destinataire id de la conv soit set");
                conversation.wait();
            }
            LOGGER.trace("je continue mon exécution");
            int destinataireId = conversation.getDestinataireId();
            synchronized (this) { // localiser les synchronized là où c'est strictement nécessaire pour éviter les deadlock
                if (mapConversations.containsKey(destinataireId)) {
                    throw new ConversationAlreadyExists(destinataireId);
                }
            }
            TCPSendData sendData = new TCPSendData(socket);
            this.addConv(destinataireId, conversation);
            LOGGER.debug("add conv " + destinataireId + " : " + conversation);
            this.addReceiveData(destinataireId, receiveData);
            this.addSendData(destinataireId, sendData);
            LOGGER.debug("add send data " + destinataireId + " : " + sendData);
            LOGGER.trace("la conversation avec " + destinataireId + " a bien été créée ");
        } catch (NetworkException e) {
            LOGGER.error("impossible de créer la conversation avec " + socket);
            safeRemove(conversation.getDestinataireId());
        } catch (InterruptedException ignored) {
        }
    }


    /** ouvrir une conversation avec un user distant*/
    public void openConversation(int destinataireId) throws UserNotFoundException, AssignationProblemException, ConversationAlreadyExists {
        if (!listeUser.contains(destinataireId)){
            throw new UserNotFoundException(destinataireId);
        }
        synchronized (this) { // localiser les synchronized là où c'est strictement nécessaire pour éviter les deadlock
            if (mapConversations.containsKey(destinataireId)) {
                throw new ConversationAlreadyExists(destinataireId);
            }
        }
        try {
            Conversation conversation = new Conversation(destinataireId, this);
            Socket socket = TCPConnect.connectTo(listeUser.getUser(destinataireId).getAddress(),port);
            TCPReceiveData tcpReceiveData = new TCPReceiveData(socket, conversation);
            TCPSendData tcpSendData = new TCPSendData(socket);
            this.addConv(destinataireId,conversation);
            this.addReceiveData(destinataireId,tcpReceiveData);
            this.addSendData(destinataireId,tcpSendData);
            tcpSendData.envoyer(new TCPMessage(destinataireId, listeUser.getMyId(), TCPType.OuvertureSession));
        } catch (NetworkException e) {
            LOGGER.error("impossible de créer la conversation avec " + destinataireId);
            safeRemove(destinataireId);
        }
    }

    public synchronized Conversation getConv(int destinataireId) throws ConversationDoesNotExist {
        if (mapConversations.get(destinataireId)==null) throw new ConversationDoesNotExist(destinataireId);
        return mapConversations.get(destinataireId);
    }

    public synchronized TCPSendData getSendData(int destinataireId) throws ConversationDoesNotExist {
        TCPSendData sendData = sendDataMap.get(destinataireId);
        LOGGER.debug("get send data " + destinataireId + " : " + sendData);
        if (sendData==null) throw new ConversationDoesNotExist(destinataireId);
        return sendData;

    }

    private synchronized TCPReceiveData getReceiveData(int destinataireId) throws ConversationDoesNotExist {
        if (receiveDataMap.get(destinataireId)==null) throw new ConversationDoesNotExist(destinataireId);
        return receiveDataMap.get(destinataireId);

    }

    public synchronized void clear() {
        this.mapConversations.clear();
        this.receiveDataMap.clear();
        this.sendDataMap.clear();
    }


    @Override
    public void accept(Socket socket) {
        try {
            this.createConversation(socket);
        } catch (ConversationAlreadyExists | ConversationException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /** supprime les objets des hashmap si il y a eu un problème à la création de la conversation*/
    protected synchronized void safeRemove(int destinataireId){
        mapConversations.remove(destinataireId);
        sendDataMap.remove(destinataireId);
        receiveDataMap.remove(destinataireId);
    }

    /** fermer la conversation quand un message de fermeture de session a été reçu ou après avoir envoyé ledit message*/
    protected void fermerConversation(int destinataireId){
        // fermeture thread reception
        LOGGER.trace("fermeture de la conversation avec " + destinataireId);
        try {
            this.getReceiveData(destinataireId).interrupt();
            this.getSendData(destinataireId).close();
            safeRemove(destinataireId);
        } catch (ConversationDoesNotExist | NetworkException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /** inicier la demande de fermeture de session*/
    public void closeConversation(int destinataireId){
        try {
            this.getSendData(destinataireId).envoyer(new TCPMessage(destinataireId,listeUser.getMyId(),TCPType.FermetureSession));
            fermerConversation(destinataireId);
        } catch (ConversationDoesNotExist e) {
            LOGGER.error(e.getMessage());
        }
    }


    /** fermer toutes les conversations*/
    public void closeAll(){
        for (Map.Entry<Integer, Conversation> entry : mapConversations.entrySet()){
            closeConversation(entry.getValue().getDestinataireId());
        }
    }
}
