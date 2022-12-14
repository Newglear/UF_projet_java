package message;
import networkManager.ThreadUDPReceive;
import userList.ListeUser;

import java.util.Scanner;

public class SetPseudoTest {

    public static void main(String[] args){
        String pseudoUtilise;
        try {
            ListeUser.setMyId(0);
            ListeUser.setMyPseudo("Test");
            ThreadUDPReceive ThreadReceive = new ThreadUDPReceive();
            if(!SetPseudo.pseudoConnexion()){
                Scanner sc = new Scanner(System.in);
                System.out.println(ListeUser.getMyPseudo() + " n'est pas un pseudo valide, veuillez saisir un nouveau Pseudo");
                pseudoUtilise = sc.nextLine();
                while(!(SetPseudo.changerPseudo(pseudoUtilise))){
                    System.out.println(pseudoUtilise + " n'est pas un pseudo valide, veuillez saisir un nouveau Pseudo");
                    pseudoUtilise = sc.nextLine();
                }
                System.out.println("UserID : " + ListeUser.getUser(1).getId() + " Pseudo : " + ListeUser.getUser(1).getPseudo() + " addresse " + ListeUser.getUser(1).getAddress() );
            }else{
                System.out.println("UserID : " + ListeUser.getUser(1).getId() + " Pseudo : " + ListeUser.getUser(1).getPseudo() + " addresse " + ListeUser.getUser(1).getAddress() );
            }

        }catch (Exception e){e.printStackTrace();}
    }
}
