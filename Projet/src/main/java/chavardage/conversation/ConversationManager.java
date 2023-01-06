package chavardage.conversation;

import chavardage.AssignationProblemException;
import chavardage.IllegalConstructorException;
import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.message.WrongConstructorException;
import chavardage.networkManager.TCPConnect;
import chavardage.networkManager.TCPReceiveData;
import chavardage.networkManager.TCPSendData;
import chavardage.networkManager.TCPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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
    public ConversationManager(boolean test, ListeUser listeUser, int port) throws IllegalConstructorException {
        if (!test){
            throw new IllegalConstructorException();
        }
        this.listeUser=listeUser;
        this.port = port;
    }


    private static final ConversationManager instance = new ConversationManager();

    public static ConversationManager getInstance() {
        return instance;
    }

    private static final Logger LOGGER = LogManager.getLogger(ConversationManager.class);

    protected Map<Integer, Conversation> mapConversations = Collections.synchronizedMap(new HashMap<>());
    protected Map<Integer, TCPSendData> sendDataMap = Collections.synchronizedMap(new HashMap<>());
    protected Map<Integer, TCPReceiveData> receiveDataMap = Collections.synchronizedMap(new HashMap<>());

    private synchronized void addConv(int destinataireId,Conversation conversation ) {
        mapConversations.put(destinataireId,conversation);
    }


    private synchronized void addSendData(int destinataireId, TCPSendData sendData) {
        sendDataMap.put(destinataireId, sendData);
    }

    private synchronized void addReceiveData(int destinataireId, TCPReceiveData receiveData) {
        receiveDataMap.put(destinataireId, receiveData);
    }


    protected void createConversation(Socket socket) throws ConversationAlreadyExists, ConversationException, InterruptedException {
        LOGGER.trace("appel de createConversation");
        Conversation conversation = new Conversation();
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
            this.addReceiveData(destinataireId, receiveData);
            this.addSendData(destinataireId, sendData);
            LOGGER.trace("la conversation avec " + destinataireId + " a bien été créée ");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void openConversation(int destinataireId) throws UserNotFoundException, AssignationProblemException, IOException, WrongConstructorException, ConversationAlreadyExists {
        synchronized (this) { // localiser les synchronized là où c'est strictement nécessaire pour éviter les deadlock
            if (mapConversations.containsKey(destinataireId)) {
                throw new ConversationAlreadyExists(destinataireId);
            }
        }
        Conversation conversation = new Conversation(destinataireId);
        Socket socket = TCPConnect.connectTo(listeUser.getUser(destinataireId).getAddress(),port);
        TCPReceiveData tcpReceiveData = new TCPReceiveData(socket, conversation);
        TCPSendData tcpSendData = new TCPSendData(socket);
        this.addConv(destinataireId,conversation);
        this.addReceiveData(destinataireId,tcpReceiveData);
        this.addSendData(destinataireId,tcpSendData);
        tcpSendData.envoyer(new TCPMessage(destinataireId, listeUser.getMyId(), TCPType.OuvertureSession));
    }

    public synchronized Conversation getConv(int destinataireId) throws ConversationDoesNotExist {
        try {
            return mapConversations.get(destinataireId);
        }catch (Exception e){
            throw new ConversationDoesNotExist(destinataireId);
        }
    }

    public synchronized TCPSendData getSendData(int destinataireId) throws ConversationDoesNotExist {
        try {
            return sendDataMap.get(destinataireId);
        }catch (Exception e){
            throw new ConversationDoesNotExist(destinataireId);
        }
    }

    public synchronized TCPReceiveData getReceiveData(int destinataireId) throws ConversationDoesNotExist {
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
            this.createConversation(socket);
        } catch (ConversationAlreadyExists | ConversationException | InterruptedException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }



    public synchronized void fermerConversation(int destinataireId){
        // fermeture thread reception
        LOGGER.trace("fermeture de la conversation avec " + destinataireId);
        try {
            this.getReceiveData(destinataireId).interrupt();
            this.getSendData(destinataireId).close();
        } catch (ConversationDoesNotExist e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
