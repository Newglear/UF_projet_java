package chavardage;

import chavardage.connexion.ChavardageManager;
import chavardage.connexion.GestionUDPMessage;
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

    public static void main(String[] args){
        Configurator.setRootLevel(Level.INFO); // only show INFO message in the application (debug are ignored)
        LOGGER.info("démarrage de l'application");
        ListeUser listeUser = ListeUser.getInstance();
        ConversationManager conversationManager = ConversationManager.getInstance();
        ChavardageManager chavardageManager = ChavardageManager.getInstance();
        GestionUDPMessage gestionUDPMessage = GestionUDPMessage.getInstance();
        UDPServeur udpServeur = new UDPServeur(gestionUDPMessage);
        TCPServeur tcpServeur = new TCPServeur(conversationManager);


    }

}
