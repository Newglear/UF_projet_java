package message;

import networkManager.ThreadUDPReceive;
import userList.ListeUser;

public class ChangementPseudoReceptionTest {

    public static void main(String[] args){
        ListeUser.setMyPseudo("Test");
        ListeUser.setMyId(1);
        ThreadUDPReceive ThreadReceive = new ThreadUDPReceive();
        try {
            Thread.sleep(20000);
        }catch (Exception e){e.printStackTrace();}
        try{
            System.out.println("UserID : " + ListeUser.getUser(0).getId() + " Pseudo : " + ListeUser.getUser(0).getPseudo() + " addresse " + ListeUser.getUser(0).getAddress() );
        }catch (Exception e){System.out.println("Utilisateur n'est pas dans la HashMap");}
    }
}
