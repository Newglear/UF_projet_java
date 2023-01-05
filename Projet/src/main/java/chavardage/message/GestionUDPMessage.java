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



    public void handleConnexion(UserItem user){
        UDPMessage ack;
        ListeUser listeUser = ListeUser.getInstance();
        try{
            UserItem self = new UserItem(listeUser.getMyId(),listeUser.getMyPseudo(),InetAddress.getLocalHost());
            if(listeUser.getMyPseudo().equals(user.getPseudo())){
                ack = new UDPMessage(UDPControlType.AckPseudoPasOK,self);
                LOGGER.trace(user.getPseudo() + " est mon pseudo, je renvoie le ack pas ok");
            }else{
                ack = new UDPMessage(UDPControlType.AckPseudoOk,self);
                LOGGER.trace(user.getPseudo() + " n'est pas mon pseudo, je renvoie le ack ok");
            }
            UDPSend.envoyerUnicast(ack,user.getAddress(), UDPServeur.DEFAULT_PORT_UDP);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void accept(UDPMessage udpMessage) {
        ListeUser listeUser = ListeUser.getInstance();
        try {
            if(udpMessage.getUser().getId() != listeUser.getMyId()) { // pour pas traiter mes propres messages
                switch (udpMessage.getControlType()) {
                    case Connexion:
                        handleConnexion(udpMessage.getUser());
                        break;
                    case Deconnexion:
                        listeUser.removeUser(udpMessage.getUser().getId());
                        LOGGER.trace(udpMessage.getUser().getPseudo() + " s'est déconnecté");
                        break;
                    case AckPseudoOk:
                    case AckNewUserSurReseau:
                        listeUser.addUser(udpMessage.getUser());
                        LOGGER.trace("ajout de " + udpMessage.getUser().getPseudo() + " dans la liste user");
                        break;
                    case AckPseudoPasOK:
                        /*SetPseudo.ackPasOkRecu = true;
                        listeUser.addUser(udpMessage.getUser().getId(), udpMessage.getUser().getPseudo(), adresseClient);
                        */break;
                    case ChangementPseudo:
                        listeUser.modifyUserPseudo(udpMessage.getUser().getId(), udpMessage.getUser().getPseudo());
                        LOGGER.trace(udpMessage.getUser().getId() + " a changé son pseudo à " + udpMessage.getUser().getPseudo());
                        break;
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }
}
