package Message;

import NetworkManager.ThreadUdpReceive;
import UserList.ListeUser;

public class ChangementPseudoReceptionTest {

    public static void main(String args){
        ListeUser.setMyPseudo("Oui");
        ListeUser.setMyId(1);
        ThreadUdpReceive ThreadReceive = new ThreadUdpReceive();
        try {
            Thread.sleep(10000);
        }catch (Exception e){e.printStackTrace();}
        try{
            System.out.println("UserID : " + ListeUser.getUser(0).getId() + " Pseudo : " + ListeUser.getUser(0).getPseudo() + " addresse " + ListeUser.getUser(0).getAddress() );
        }catch (Exception e){System.out.println("Utilisateur n'est pas dans la HashMap");}
    }
}
