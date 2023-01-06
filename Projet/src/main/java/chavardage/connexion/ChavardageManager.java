package chavardage.connexion;

import chavardage.message.UDPControlType;
import chavardage.message.UDPMessage;
import chavardage.networkManager.UDPSend;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.UserItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

public class ChavardageManager implements Consumer<UDPMessage> {

    private static final Logger LOGGER = LogManager.getLogger(ChavardageManager.class);
    private final static int TIMEOUT = 10000;
    private UDPControlType received;
    private final int port;

    /** singleton */
    private ChavardageManager(){
        this.port= UDPServeur.DEFAULT_PORT_UDP;
    }

    /** protected constructor for test, envoi sur port au lieu du port par défaut*/
    public ChavardageManager(int port){
        this.port=port;
    }

    private static final ChavardageManager instance = new ChavardageManager();

    public static ChavardageManager getInstance(){return instance;}

    public void connectToApp(UserItem mySelf) throws InterruptedException {
        UDPMessage demandeConnexion = new UDPMessage(UDPControlType.DemandeConnexion,mySelf);
        UDPSend.envoyerBroadcast(demandeConnexion,port);
        if (received==null){ // si le ack n'a pas encore été reçu
            synchronized (this) {
                LOGGER.trace("j'attends");
                wait(TIMEOUT);
            }
        }
        if (received!=null) { // on a bien reçu un ack
            switch (received){
                case AckPseudoOk:
                    LOGGER.trace("mon pseudo est ok, j'envoie le NewUser");
                    UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.NewUser, mySelf),port);
                    LOGGER.info("connexion au réseau réussie");
                    break;
                case AckPseudoPasOK:
                    // TODO redemander un pseudo via l'interface
                    LOGGER.trace("échec de la connexion, le pseudo est déjà utilisé");
                    break;
            }
        } else { // bah on est seul sur le réseau
            LOGGER.info("aucun autre utilisateur pour le moment");
        }

    }


    @Override
    public synchronized void accept(UDPMessage udpMessage) {
        LOGGER.trace("je set received");
        this.received = udpMessage.getControlType();
        this.notify();
    }
}
