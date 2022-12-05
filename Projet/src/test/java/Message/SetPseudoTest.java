package Message;
import NetworkManager.ThreadUdpReceive;
import UserList.ListeUser;
import UserList.UserItem;

import java.net.InetAddress;
import java.util.Scanner;

public class SetPseudoTest {

    public static void main(String args[]) throws Exception{
        ThreadUdpReceive threadUdp = new ThreadUdpReceive();

        String pseudoUtilise;
        try {
            ListeUser.addUser(0, "test", InetAddress.getLocalHost());
            if(!SetPseudo.pseudoConnexion()){
                Scanner sc = new Scanner(System.in);
                System.out.println(ListeUser.getMyPseudo() + " n'est pas un pseudo valide, veuillez saisir un nouveau Pseudo");
                pseudoUtilise = sc.nextLine();
                while(SetPseudo.changerPseudo(pseudoUtilise)){
                    System.out.println(pseudoUtilise + " n'est pas un pseudo valide, veuillez saisir un nouveau Pseudo");
                    pseudoUtilise = sc.nextLine();
                }
            }

        }catch (Exception e){throw e;}
    }
}
