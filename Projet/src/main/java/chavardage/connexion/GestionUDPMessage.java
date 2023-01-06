package chavardage.connexion;

import chavardage.message.UDPControlType;
import chavardage.message.UDPMessage;
import chavardage.networkManager.UDPSend;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;


public class GestionUDPMessage implements Consumer<UDPMessage> {

    public byte[] buffer;
    private static final Logger LOGGER = LogManager.getLogger(GestionUDPMessage.class);
    private static final GestionUDPMessage instance = new GestionUDPMessage();

    private static int nbAcks = 0;

    private final ListeUser listeUser;
    private final int port;
    private final ChavardageManager chavardageManager;

    /**singleton*/
    private GestionUDPMessage(){
        this.listeUser = ListeUser.getInstance();
        this.port = UDPServeur.DEFAULT_PORT_UDP;
        this.chavardageManager = ChavardageManager.getInstance();
    }

    /** constructeur protected pour les tests, envoi sur port au lieu du port UDP par défaut*/
    public GestionUDPMessage(ListeUser listeUser, int port, ChavardageManager chavardageManager){
        this.listeUser = listeUser;
        this.port = port;
        this.chavardageManager=chavardageManager;
    }

    public static GestionUDPMessage getInstance(){
        return instance;
    }



    @Override
    /* tout s'envoie sur le port UDP par défaut */
    public void accept(UDPMessage udpMessage) {
        try {
            if(udpMessage.getEnvoyeur().getId() != listeUser.getMyId()) { // pour pas traiter mes propres messages
                switch (udpMessage.getControlType()) {
                    case DemandeConnexion:
                        if (listeUser.pseudoDisponible(udpMessage.getEnvoyeur().getPseudo())){
                            LOGGER.trace("envoi d'un AckPseudoOk");
                            UDPSend.envoyerUnicast(new UDPMessage(UDPControlType.AckPseudoOk, listeUser.getMySelf()),udpMessage.getEnvoyeur().getAddress(),port);
                        }else{
                            LOGGER.trace("envoi d'un AckPseudoPasOk");
                            UDPSend.envoyerUnicast(new UDPMessage(UDPControlType.AckPseudoPasOK, listeUser.getMySelf()),udpMessage.getEnvoyeur().getAddress(),port);
                        }
                        break;
                    case Deconnexion:
                        listeUser.removeUser(udpMessage.getEnvoyeur().getId());
                        LOGGER.trace(udpMessage.getEnvoyeur().getPseudo() + " s'est déconnecté");
                        break;
                    case AckPseudoOk:
                    case AckPseudoPasOK:
                        LOGGER.trace("ajout de " + udpMessage.getEnvoyeur() + " dans la liste");
                        listeUser.addUser(udpMessage.getEnvoyeur());
                        if (nbAcks==0){
                            LOGGER.trace("premier ack reçu, je passe au chavardage manager");
                            chavardageManager.accept(udpMessage);
                        }
                        nbAcks++;
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
