package message;
import networkManager.UDPServeur;
import userList.ListeUser;

import java.util.Scanner;

public class SetPseudoTest {

    public static void main(String[] args){
        String pseudoUtilise;
        ListeUser listeUser = ListeUser.getInstance();
        try {
            listeUser.setMyId(0);
            listeUser.setMyPseudo("Test");
            UDPServeur ThreadReceive = new UDPServeur();
            if(!SetPseudo.pseudoConnexion()){
                Scanner sc = new Scanner(System.in);
                System.out.println(listeUser.getMyPseudo() + " n'est pas un pseudo valide, veuillez saisir un nouveau Pseudo");
                pseudoUtilise = sc.nextLine();
                while(!(SetPseudo.changerPseudo(pseudoUtilise))){
                    System.out.println(pseudoUtilise + " n'est pas un pseudo valide, veuillez saisir un nouveau Pseudo");
                    pseudoUtilise = sc.nextLine();
                }
                System.out.println("UserID : " + listeUser.getUser(1).getId() + " Pseudo : " + listeUser.getUser(1).getPseudo() + " addresse " + listeUser.getUser(1).getAddress() );
            }else{
                System.out.println("UserID : " + listeUser.getUser(1).getId() + " Pseudo : " + listeUser.getUser(1).getPseudo() + " addresse " + listeUser.getUser(1).getAddress() );
            }

        }catch (Exception e){e.printStackTrace();}
    }
}
