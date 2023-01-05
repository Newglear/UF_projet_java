package chavardage.connexion;

import chavardage.message.UDPControlType;
import chavardage.message.UDPMessage;
import chavardage.networkManager.UDPSend;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

public class ChavardageManager implements Consumer<UDPMessage> {

    private static final Logger LOGGER = LogManager.getLogger(ChavardageManager.class);
    private static final int TIMEOUT = 1000;

    private UDPControlType received;

    /** singleton */
    private ChavardageManager(){}

    private static final ChavardageManager instance = new ChavardageManager();

    public static ChavardageManager getInstance(){return instance;}

    public synchronized void ConnectToApp(UserItem mySelf) {
        UDPMessage demandeConnexion = new UDPMessage(UDPControlType.DemandeConnexion,mySelf);
        UDPSend.envoyerBroadcast(demandeConnexion);
        switch (received){
            case AckPseudoOk:
                LOGGER.trace("mon pseudo est ok, j'envoie le NewUser");
                UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.NewUser, mySelf));
                break;
            case AckPseudoPasOK:
                // TODO redemander un pseudo via l'interface
                LOGGER.trace("échec de la connexion, le pseudo est déjà utilisé");
                break;
        }

    }


    @Override
    public synchronized void accept(UDPMessage udpMessage) {
        this.received = udpMessage.getControlType();
    }
}
