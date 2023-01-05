package chavardage.message;

import chavardage.networkManager.UDPSend;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;

public class SetPseudo {
    public static int delaiAttenteMs = 2000;
    public static boolean ackPasOkRecu = false;

    public static boolean  connexionEtablie = false;
    public static boolean pseudoConnexion(){
        ListeUser listeUser = ListeUser.getInstance();
        try{
            UserItem identity= new UserItem(listeUser.getMyId(),listeUser.getMyPseudo());
            UDPMessage pseudoConnexion = new UDPMessage(UDPControlType.DemandeConnexion,identity);
            UDPSend.envoyerBroadcast(pseudoConnexion, UDPServeur.DEFAULT_PORT_UDP);
            Thread.sleep(delaiAttenteMs);
            if(ackPasOkRecu){
                ackPasOkRecu = false;
                return false;
            }else{
                UDPMessage newUserConnected = new UDPMessage(UDPControlType.NewUser, identity);
                UDPSend.envoyerBroadcast(newUserConnected, UDPServeur.DEFAULT_PORT_UDP);
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
                    pseudoConnexion = new UDPMessage(UDPControlType.ChangementPseudo,newIdentity);
                }else{
                    pseudoConnexion = new UDPMessage(UDPControlType.NewUser,newIdentity);
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
