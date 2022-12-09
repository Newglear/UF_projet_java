package Message;

import UserList.ListeUser;
import NetworkManager.UdpSend;
import UserList.UserItem;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class SetPseudo {
    public static int delaiAttenteMs = 2000;
    public static boolean ackPasOkRecu = false;

    public static boolean pseudoConnexion(){
        try{
            UserItem identity= new UserItem(ListeUser.getMyId(),ListeUser.getMyPseudo());
            UDPMessage pseudoConnexion = new UDPMessage(UDPControlType.Connexion,identity);
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bstream);
            oo.writeObject(pseudoConnexion);
            byte[] sentMessage = bstream.toByteArray();
            UdpSend.envoyerBroadcast(sentMessage);
            Thread.sleep(delaiAttenteMs);
            if(ackPasOkRecu){
                ackPasOkRecu = false;
                return false;
            }else{
                UDPMessage newUserConnected = new UDPMessage(UDPControlType.AckNewUserSurReseau);
                ByteArrayOutputStream newOut = new ByteArrayOutputStream();
                ObjectOutput noo = new ObjectOutputStream(newOut);
                noo.writeObject(newUserConnected);
                byte[] ackNewUser = bstream.toByteArray();
                UdpSend.envoyerBroadcast(ackNewUser);
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changerPseudo(String newPseudo){
        if(ListeUser.pseudoDisponible(newPseudo)){
            try {
                ListeUser.modifyUserPseudo(ListeUser.getMyId(), newPseudo);
                UserItem newIdentity= new UserItem(ListeUser.getMyId(),newPseudo);
                UDPMessage pseudoConnexion = new UDPMessage(UDPControlType.ChangementPseudo,newIdentity);
                ByteArrayOutputStream bstream = new ByteArrayOutputStream();
                ObjectOutput oo = new ObjectOutputStream(bstream);
                oo.writeObject(pseudoConnexion);
                byte[] sentMessage = bstream.toByteArray();
                UdpSend.envoyerBroadcast(sentMessage);
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
