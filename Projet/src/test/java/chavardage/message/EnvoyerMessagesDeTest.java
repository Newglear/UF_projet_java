package chavardage.message;

import chavardage.networkManager.UDPSend;
import chavardage.userList.UserItem;

public class EnvoyerMessagesDeTest extends Thread{

    public EnvoyerMessagesDeTest(){
        start();
    }

    public void run(){
        UserItem distant = new UserItem(3,"romain");
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.Connexion,distant));
        distant.setPseudo("romainMaisMieux");
        UDPSend.envoyerBroadcast(new UDPMessage(UDPControlType.ChangementPseudo,distant));
    }

}
