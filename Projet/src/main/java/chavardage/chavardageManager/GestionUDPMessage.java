package chavardage.chavardageManager;

import chavardage.AssignationProblemException;
import chavardage.message.UDPType;
import chavardage.message.UDPMessage;
import chavardage.networkManager.UDPSend;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

// TODO les usurpateur font de la merde

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

    /** constructeur public pour les tests, envoi sur port au lieu du port UDP par défaut*/
    public GestionUDPMessage(ListeUser listeUser, int portDistant, ChavardageManager chavardageManager){
        this.listeUser = listeUser;
        this.port = portDistant;
        this.chavardageManager=chavardageManager;
    }

    public static GestionUDPMessage getInstance(){
        return instance;
    }



    @Override
    public void accept(UDPMessage udpMessage) {
        try {

            if (udpMessage.getEnvoyeur().getId() != listeUser.getMyId() && !udpMessage.getEnvoyeur().getPseudo().equals(listeUser.getMyPseudo())) { // pour pas traiter mes propres messages
                switch (udpMessage.getControlType()) {
                    case DemandeConnexion:
                        if (listeUser.contains(udpMessage.getEnvoyeur().getId())) { // l'id est déjà dans la liste
                            if (listeUser.getUser(udpMessage.getEnvoyeur().getId()).getPseudo().equals(udpMessage.getEnvoyeur().getPseudo())) {
                                // même id, même pseudo : already connected
                                LOGGER.trace("envoi d'un already connected");
                                UDPSend.envoyerUnicast(new UDPMessage(UDPType.AlreadyConnected, listeUser.getMySelf()), udpMessage.getEnvoyeur().getAddress(), port);
                            } else { // même id, pseudo différent
                                LOGGER.trace("détection d'un usurpateur");
                                UDPSend.envoyerUnicast(new UDPMessage(UDPType.Usurpateur, listeUser.getMySelf()), udpMessage.getEnvoyeur().getAddress(), port);
                            }
                            break;
                        }
                        if (listeUser.pseudoDisponible(udpMessage.getEnvoyeur().getPseudo())) {
                            // nouvel user, pseudo dispo
                            LOGGER.trace("envoi d'un AckPseudoOk");
                            UDPSend.envoyerUnicast(new UDPMessage(UDPType.AckPseudoOk, listeUser.getMySelf()), udpMessage.getEnvoyeur().getAddress(), port);
                        } else {
                            // nouvel user, pseudo pas dispo
                            LOGGER.trace("envoi d'un AckPseudoPasOk");
                            UDPSend.envoyerUnicast(new UDPMessage(UDPType.AckPseudoPasOK, listeUser.getMySelf()), udpMessage.getEnvoyeur().getAddress(), port);
                        }
                        break;
                    case Deconnexion:
                        listeUser.removeUser(udpMessage.getEnvoyeur().getId());
                        LOGGER.trace(udpMessage.getEnvoyeur().getPseudo() + " s'est déconnecté-e");
                        break;
                    case AckPseudoOk:
                    case AckPseudoPasOK:
                        LOGGER.trace("ajout de " + udpMessage.getEnvoyeur() + " dans la liste");
                        listeUser.addUser(udpMessage.getEnvoyeur());
                        LOGGER.trace("premier ack reçu, je passe au chavardage manager");
                        chavardageManager.accept(udpMessage);

                        nbAcks++;
                        break;
                    case AlreadyConnected:
                        LOGGER.trace("tout va bien, j'étais connecté-e");
                        chavardageManager.accept(udpMessage);
                        nbAcks++;
                        break;
                    case Usurpateur:
                        LOGGER.trace("je suis un usurpateur, oups, je passe au chavardageManager");
                        chavardageManager.accept(udpMessage);
                        break;
                    case NewUser:
                        listeUser.addUser(udpMessage.getEnvoyeur());
                        LOGGER.trace("ajout de " + udpMessage.getEnvoyeur() + " dans la liste");
                        break;
                    case ChangementPseudo:
                        listeUser.modifyUserPseudo(udpMessage.getEnvoyeur().getId(), udpMessage.getEnvoyeur().getPseudo());
                        LOGGER.trace(udpMessage.getEnvoyeur().getId() + " a changé son pseudo à " + udpMessage.getEnvoyeur().getPseudo());
                        break;
                }
            }

        }catch (UserNotFoundException e){
            // on a essayé de retirer de la liste un user qui n'y était plus, ya pas de problème
        }catch (AssignationProblemException e){ // ne pas traiter mon propre message de déconnexion
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
