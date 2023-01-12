package chavardage;

import chavardage.chavardageManager.AlreadyUsedPseudoException;
import chavardage.chavardageManager.ChavardageManager;
import chavardage.chavardageManager.GestionUDPMessage;
import chavardage.chavardageManager.UsurpateurException;
import chavardage.conversation.ConversationManager;
import chavardage.networkManager.TCPServeur;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppOnPort {
    private static final Logger LOGGER = LogManager.getLogger(AppOnPort.class);

    private ListeUser listeUser;
    private ConversationManager conversationManager;
    private ChavardageManager chavardageManager;
    private GestionUDPMessage gestionUDPMessage;
    private UDPServeur udpServeur;
    private TCPServeur tcpServeur;


    public AppOnPort(int udpPort, int tcpPort, int udpPortDistant, int tcpPortDistant){
        try{
            listeUser=new ListeUser(true);
            conversationManager=new ConversationManager(true,listeUser,tcpPortDistant);
            chavardageManager=new ChavardageManager(udpPortDistant);
            gestionUDPMessage=new GestionUDPMessage(listeUser,udpPortDistant,chavardageManager);
            udpServeur = new UDPServeur(udpPort,gestionUDPMessage);
            tcpServeur = new TCPServeur(tcpPort,conversationManager);
        } catch (IllegalConstructorException e) {
            e.printStackTrace();
        }
    }

    private void changePseudo(String pseudo){
        try{
            listeUser.setMyPseudo(pseudo);
        } catch (AlreadyUsedPseudoException e) {
            LOGGER.error(e.getMessage());
            // TODO boucle avec interface
        }
        chavardageManager.notifyChangePseudo(listeUser.getMySelf());
    }

    private void clear(){
        listeUser.clear();
        conversationManager.clear();
    }

    private void closeApp(){
        // TODO fermer toutes les conversations en cours
        chavardageManager.disconnect(listeUser.getMySelf());
        udpServeur.interrupt();
        tcpServeur.interrupt();
    }

    public void start(){
        clear();
        // Configurator.setRootLevel(Level.INFO); // only show INFO message in the application (debug are ignored)
        LOGGER.info("démarrage de l'application");
        listeUser.setMyself(1,"Aude");
        try {
            chavardageManager.connectToApp(listeUser.getMySelf());
        } catch (InterruptedException | UsurpateurException | AlreadyUsedPseudoException e) {
            e.printStackTrace();
        }
        changePseudo("Aude mais mieux");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TODO comprendre pourquoi j'ai des erreurs de socket alors que je voulais les restreindre aux couches inférieures

        /*try {
            conversationManager.getSendData(1).envoyer(new TCPMessage(1,2,"si ça marche, je suis forte"));
        } catch (ConversationDoesNotExist conversationDoesNotExist) {
            conversationDoesNotExist.printStackTrace();
        }

        try {
            conversationManager.fermerConversation(1);
        } catch (ConversationDoesNotExist conversationDoesNotExist) {
            conversationDoesNotExist.printStackTrace();
        }*/


        closeApp();

    }
}
