package message;

import userList.ListeUser;
import networkManager.UDPSend;
import userList.UserItem;

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
            oo.close();
            byte[] sentMessage = bstream.toByteArray();
            UDPSend.envoyerBroadcast(sentMessage);
            Thread.sleep(delaiAttenteMs);
            if(ackPasOkRecu){
                ackPasOkRecu = false;
                return false;
            }else{
                UDPMessage newUserConnected = new UDPMessage(UDPControlType.AckNewUserSurReseau, identity);
                ByteArrayOutputStream newStream = new ByteArrayOutputStream();
                ObjectOutput noo = new ObjectOutputStream(newStream);
                noo.writeObject(newUserConnected);
                oo.close();
                byte[] ackNewUser = newStream.toByteArray();
                UDPSend.envoyerBroadcast(ackNewUser);
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
                ListeUser.setMyPseudo(newPseudo);
                UserItem newIdentity= new UserItem(ListeUser.getMyId(),ListeUser.getMyPseudo());
                UDPMessage pseudoConnexion = new UDPMessage(UDPControlType.AckNewUserSurReseau,newIdentity);
                ByteArrayOutputStream bstream = new ByteArrayOutputStream();
                ObjectOutput oo = new ObjectOutputStream(bstream);
                oo.writeObject(pseudoConnexion);
                byte[] sentMessage = bstream.toByteArray();
                UDPSend.envoyerBroadcast(sentMessage);
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
