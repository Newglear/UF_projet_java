package chavardage;

import chavardage.chavardageManager.AlreadyUsedPseudoException;
import chavardage.chavardageManager.ChavardageManager;
import chavardage.chavardageManager.GestionUDPMessage;
import chavardage.chavardageManager.UsurpateurException;
import chavardage.conversation.ConversationManager;
import chavardage.networkManager.TCPServeur;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppOnPort {
    private static final Logger LOGGER = LogManager.getLogger(AppOnPort.class);

    protected ListeUser listeUser;
    protected ConversationManager conversationManager;
    private ChavardageManager chavardageManager;
    private GestionUDPMessage gestionUDPMessage;
    private UDPServeur udpServeur;
    private TCPServeur tcpServeur;


    public AppOnPort(int udpPort, int tcpPort, int udpPortDistant, int tcpPortDistant, UserItem myself){
        try{
            listeUser=new ListeUser(true);
            listeUser.setMyself(myself);
            conversationManager=new ConversationManager(listeUser,tcpPortDistant);
            chavardageManager=new ChavardageManager(udpPortDistant);
            gestionUDPMessage=new GestionUDPMessage(listeUser,udpPortDistant,chavardageManager);
            udpServeur = new UDPServeur(udpPort,gestionUDPMessage);
            tcpServeur = new TCPServeur(tcpPort,conversationManager);
        } catch (IllegalConstructorException e) {
            e.printStackTrace();
        }
    }

    public void changePseudo(String pseudo){
        try{
            listeUser.setMyPseudo(pseudo);
        } catch (AlreadyUsedPseudoException e) {
            LOGGER.error(e.getMessage());
            // TODO boucle avec interface
        }
        chavardageManager.notifyChangePseudo(listeUser.getMySelf());
    }

    public void clear(){
        listeUser.clear();
        conversationManager.clear();
    }

    public void closeApp(){
        conversationManager.closeAll();
        chavardageManager.disconnect(listeUser.getMySelf());
        udpServeur.interrupt();
        tcpServeur.interrupt();
    }

    public void start(){
        // Configurator.setRootLevel(Level.INFO); // only show INFO message in the application (debug are ignored)
        LOGGER.info("démarrage de l'application");
        try {
            chavardageManager.connectToApp(listeUser.getMySelf());
        } catch (InterruptedException | UsurpateurException | AlreadyUsedPseudoException e) {
            e.printStackTrace();
        }




    }
}
