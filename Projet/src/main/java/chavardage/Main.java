package chavardage;

import chavardage.chavardageManager.AlreadyUsedPseudoException;
import chavardage.chavardageManager.ChavardageManager;
import chavardage.chavardageManager.GestionUDPMessage;
import chavardage.conversation.ConversationManager;
import chavardage.networkManager.TCPServeur;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;


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



    public static void main(String[] args){
        Configurator.setRootLevel(Level.INFO); // only show INFO message in the application (debug are ignored)
        LOGGER.info("démarrage de l'application");

        UDPServeur udpServeur = new UDPServeur(gestionUDPMessage);
        TCPServeur tcpServeur = new TCPServeur(conversationManager);
    }

}
