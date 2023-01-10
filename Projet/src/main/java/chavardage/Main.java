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


    public static void main(String[] args){
        clear();
        Configurator.setRootLevel(Level.INFO); // only show INFO message in the application (debug are ignored)
        LOGGER.info("démarrage de l'application");

        UDPServeur udpServeur = new UDPServeur(gestionUDPMessage);
        TCPServeur tcpServeur = new TCPServeur(conversationManager);

        listeUser.setMyself(1,"Aude");

        try {
            chavardageManager.connectToApp(listeUser.getMySelf());
        } catch (InterruptedException | UsurpateurException | AlreadyUsedPseudoException e) {
            e.printStackTrace();
        }

        try {
            conversationManager.openConversation(2);
        } catch (UserNotFoundException | AssignationProblemException | ConversationAlreadyExists | IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            conversationManager.getSendData(2).envoyer(new TCPMessage(1,2,"hello"));
        } catch (ConversationDoesNotExist conversationDoesNotExist) {
            conversationDoesNotExist.printStackTrace();
        }

        try {
            conversationManager.fermerConversation(2);
        } catch (ConversationDoesNotExist conversationDoesNotExist) {
            conversationDoesNotExist.printStackTrace();
        }

        chavardageManager.disconnect(listeUser.getMySelf());

    }

}
