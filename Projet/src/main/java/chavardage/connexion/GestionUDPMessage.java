package chavardage.connexion;

import chavardage.message.UDPControlType;
import chavardage.message.UDPMessage;
import chavardage.networkManager.UDPSend;
import chavardage.userList.ListeUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;


public class GestionUDPMessage implements Consumer<UDPMessage> {

    public byte[] buffer;
    private static final Logger LOGGER = LogManager.getLogger(GestionUDPMessage.class);

    private static final GestionUDPMessage instance = new GestionUDPMessage();

    /**singleton*/
    private GestionUDPMessage(){}

    public static GestionUDPMessage getInstance(){
        return instance;
    }


    @Override
    /* tout s'envoie sur le port UDP par défaut */
    public void accept(UDPMessage udpMessage) {
        ListeUser listeUser = ListeUser.getInstance();
        try {
            if(udpMessage.getEnvoyeur().getId() != listeUser.getMyId()) { // pour pas traiter mes propres messages
                switch (udpMessage.getControlType()) {
                    case DemandeConnexion:
                        if (listeUser.pseudoDisponible(udpMessage.getEnvoyeur().getPseudo())){
                            LOGGER.trace("envoi d'un AckPseudoOk");
                            UDPSend.envoyerUnicast(new UDPMessage(UDPControlType.AckPseudoOk, ListeUser.getInstance().getMySelf()),udpMessage.getEnvoyeur().getAddress());
                        }else{
                            LOGGER.trace("envoi d'un AckPseudoPasOk");
                            UDPSend.envoyerUnicast(new UDPMessage(UDPControlType.AckPseudoPasOK, ListeUser.getInstance().getMySelf()),udpMessage.getEnvoyeur().getAddress());
                        }
                        break;
                    case Deconnexion:
                        listeUser.removeUser(udpMessage.getEnvoyeur().getId());
                        LOGGER.trace(udpMessage.getEnvoyeur().getPseudo() + " s'est déconnecté");
                        break;
                    case AckPseudoOk:
                    case AckPseudoPasOK:
                        LOGGER.trace("ack reçu, je passe au chavardage manager");
                        ChavardageManager.getInstance().accept(udpMessage);
                        break;
                    case NewUser:
                        listeUser.addUser(udpMessage.getEnvoyeur());
                        LOGGER.trace("ajout de " + udpMessage.getEnvoyeur().getPseudo() + " dans la liste user");
                        break;
                    case ChangementPseudo:
                        listeUser.modifyUserPseudo(udpMessage.getEnvoyeur().getId(), udpMessage.getEnvoyeur().getPseudo());
                        LOGGER.trace(udpMessage.getEnvoyeur().getId() + " a changé son pseudo à " + udpMessage.getEnvoyeur().getPseudo());
                        break;
                }
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
