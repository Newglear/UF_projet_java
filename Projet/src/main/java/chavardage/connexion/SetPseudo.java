package chavardage.connexion;

import chavardage.message.UDPType;
import chavardage.message.UDPMessage;
import chavardage.networkManager.UDPSend;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;

public class SetPseudo {
    public final static int TIMEOUT = 2000;
    public static boolean ackPasOkRecu = false;

    public static boolean  connexionEtablie = false;

    public static boolean pseudoConnexion(){
        ListeUser listeUser = ListeUser.getInstance();
        try{
            UserItem identity= listeUser.getMySelf();
            UDPMessage demandeConnexion = new UDPMessage(UDPType.DemandeConnexion,identity);
            UDPSend.envoyerBroadcast(demandeConnexion);
            Thread.sleep(TIMEOUT);
            if(ackPasOkRecu){
                ackPasOkRecu = false;
                return false;
            }else{
                UDPMessage newUserConnected = new UDPMessage(UDPType.NewUser, identity);
                UDPSend.envoyerBroadcast(newUserConnected);
                connexionEtablie = true;
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changerPseudo(String newPseudo){
        UDPMessage pseudoConnexion;
        ListeUser listeUser = ListeUser.getInstance();
        if(listeUser.pseudoDisponible(newPseudo)){
            try {
                listeUser.setMyPseudo(newPseudo);
                UserItem newIdentity= new UserItem(listeUser.getMyId(),listeUser.getMyPseudo());
                if(connexionEtablie){
                    pseudoConnexion = new UDPMessage(UDPType.ChangementPseudo,newIdentity);
                }else{
                    pseudoConnexion = new UDPMessage(UDPType.NewUser,newIdentity);
                }
                UDPSend.envoyerBroadcast(pseudoConnexion, UDPServeur.DEFAULT_PORT_UDP);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }
}
