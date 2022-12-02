package Message;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import UserList.ListeUser;
import NetworkManager.UdpSend;
public class GestionMessageUDP extends Thread {

    UDPMessage mess;
    InetAddress adresseClient;
    public GestionMessageUDP(byte[] buffer,InetAddress addressClient){
        try {
            this.adresseClient = addressClient;
            ObjectInputStream IStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
            this.mess = (UDPMessage) IStream.readObject();
            start();
        }catch (Exception e){e.printStackTrace();}
    }
    public void run(){
        try {
            switch (mess.controlType){
                case Connexion:
                    handleConnexion(mess.user.getPseudo(),adresseClient);
                    break;
                case Deconnexion:
                    ListeUser.removeUser(mess.user.getId());
                    break;
                case AckPseudoOk:

                    break;
                case AckPseudoPasOK:

                    break;
                case AckNewUserSurReseau:
                    ListeUser.addUser(mess.user.getId(),mess.user.getPseudo(),adresseClient);
                    break;
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void handleConnexion(String pseudoRecu, InetAddress adresseClient){
        UDPMessage ack;
        if(ListeUser.getMyPseudo().equals(pseudoRecu)){
            ack = new UDPMessage(UDPControlType.AckPseudoPasOK);
        }else{
            ack = new UDPMessage(UDPControlType.AckPseudoOk);
        }
        try{
            UdpSend.envoyerUnicast(ack.getBytes(), adresseClient);
        }catch (Exception e){e.printStackTrace();}
    }


}
