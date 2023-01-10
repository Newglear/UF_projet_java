package chavardage;

import chavardage.chavardageManager.AlreadyUsedPseudoException;
import chavardage.chavardageManager.ChavardageManager;
import chavardage.chavardageManager.GestionUDPMessage;
import chavardage.chavardageManager.UsurpateurException;
import chavardage.conversation.ConversationAlreadyExists;
import chavardage.conversation.ConversationDoesNotExist;
import chavardage.chavardageManager.UsurpateurException;
import chavardage.conversation.ConversationDoesNotExist;
import chavardage.conversation.ConversationManager;
import chavardage.message.TCPMessage;
import chavardage.networkManager.TCPServeur;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserNotFoundException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.IOException;


public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private static final ListeUser listeUser = ListeUser.getInstance();
    private static final ConversationManager conversationManager = ConversationManager.getInstance();
    private static final ChavardageManager chavardageManager = ChavardageManager.getInstance();
    private static final GestionUDPMessage gestionUDPMessage = GestionUDPMessage.getInstance();
    private static final UDPServeur udpServeur = new UDPServeur(gestionUDPMessage);
    private static final TCPServeur tcpServeur = new TCPServeur(conversationManager);


    private static void changePseudo(String pseudo){
        try{
            listeUser.setMyPseudo(pseudo);
        } catch (AlreadyUsedPseudoException e) {
            LOGGER.error(e.getMessage());
            // TODO boucle avec interface
        }
        chavardageManager.notifyChangePseudo(listeUser.getMySelf());
    }

    private static void clear(){
        listeUser.clear();
        conversationManager.clear();
    }

    private static void closeApp(){
        // TODO fermer toutes les conversations en cours
        chavardageManager.disconnect(listeUser.getMySelf());
        udpServeur.interrupt();
        tcpServeur.interrupt();
    }

    public static void main(String[] args){
        clear();
        // Configurator.setRootLevel(Level.INFO); // only show INFO message in the application (debug are ignored)
        LOGGER.info("démarrage de l'application");



        listeUser.setMyself(2,"Romain");

        try {
            chavardageManager.connectToApp(listeUser.getMySelf());
        } catch (InterruptedException | UsurpateurException | AlreadyUsedPseudoException e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
