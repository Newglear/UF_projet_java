package chavardage.message;

import chavardage.networkManager.UDPSend;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
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
                        if (udpMessage.getEnvoyeur().getPseudo().equals(ListeUser.getInstance().getMyPseudo())){
                            LOGGER.trace("envoi d'un AckPseudoPasOk");
                            UDPSend.envoyerUnicast(new UDPMessage(UDPControlType.AckPseudoPasOK, ListeUser.getInstance().getMySelf()),udpMessage.getEnvoyeur().getAddress());
                        }else{
                            LOGGER.trace("envoi d'un AckPseudoOk");
                            UDPSend.envoyerUnicast(new UDPMessage(UDPControlType.AckPseudoOk, ListeUser.getInstance().getMySelf()),udpMessage.getEnvoyeur().getAddress());
                        }
                        break;
                    case Deconnexion:
                        listeUser.removeUser(udpMessage.getEnvoyeur().getId());
                        LOGGER.trace(udpMessage.getEnvoyeur().getPseudo() + " s'est déconnecté");
                        break;
                    case AckPseudoOk:
                        // TODO
                        break;
                    case NewUser:
                        listeUser.addUser(udpMessage.getEnvoyeur());
                        LOGGER.trace("ajout de " + udpMessage.getEnvoyeur().getPseudo() + " dans la liste user");
                        break;
                    case AckPseudoPasOK:
                        /*SetPseudo.ackPasOkRecu = true;
                        listeUser.addUser(udpMessage.getUser());*/ //TODO
                        break;
                    case ChangementPseudo:
                        listeUser.modifyUserPseudo(udpMessage.getEnvoyeur().getId(), udpMessage.getEnvoyeur().getPseudo());
                        LOGGER.trace(udpMessage.getEnvoyeur().getId() + " a changé son pseudo à " + udpMessage.getEnvoyeur().getPseudo());
                        break;
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }
}
