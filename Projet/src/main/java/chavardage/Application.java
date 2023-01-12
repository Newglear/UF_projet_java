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

public class Application {
    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    private final ListeUser listeUser = ListeUser.getInstance();
    private final ConversationManager conversationManager = ConversationManager.getInstance();
    private final ChavardageManager chavardageManager = ChavardageManager.getInstance();
    private final GestionUDPMessage gestionUDPMessage = GestionUDPMessage.getInstance();
    private final UDPServeur udpServeur = new UDPServeur(gestionUDPMessage);
    private final TCPServeur tcpServeur = new TCPServeur(conversationManager);


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
        conversationManager.closeAll();
        chavardageManager.disconnect(listeUser.getMySelf());
        udpServeur.interrupt();
        tcpServeur.interrupt();
    }

    public void start(){

    }
}
