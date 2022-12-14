package Message;

import java.io.*;
import java.net.InetAddress;
import UserList.ListeUser;
import UserList.UserItem;
import NetworkManager.UdpSend;
public class GestionMessageUDP extends Thread {

    public byte[] buffer;


    public InetAddress adresseClient;
    public GestionMessageUDP(byte[] buffer,InetAddress addressClient){
        try {
            this.adresseClient = addressClient;
            this.buffer = buffer;
            start();
        }catch (Exception e){e.printStackTrace();}
    }
    public void run(){
        try {
            ObjectInputStream IStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
            UDPMessage mess = (UDPMessage) IStream.readObject();
            if(mess.user.getId() != ListeUser.getMyId()) {
                switch (mess.controlType) {
                    case Connexion:
                        handleConnexion(mess.user.getPseudo(), adresseClient);
                        break;
                    case Deconnexion:
                        ListeUser.removeUser(mess.user.getId());
                        break;
                    case AckPseudoOk:
                        ListeUser.addUser(mess.user.getId(), mess.user.getPseudo(), adresseClient);
                        break;
                    case AckNewUserSurReseau:
                        ListeUser.addUser(mess.user.getId(), mess.user.getPseudo(), adresseClient);
                        break;
                    case AckPseudoPasOK:
                        SetPseudo.ackPasOkRecu = true;
                        ListeUser.addUser(mess.user.getId(), mess.user.getPseudo(), adresseClient);
                        break;
                    case ChangementPseudo:
                        ListeUser.modifyUserPseudo(mess.user.getId(), mess.user.getPseudo());
                        break;
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void handleConnexion(String pseudoRecu, InetAddress adresseClient){
        UDPMessage ack;
        try{
            UserItem self = new UserItem(ListeUser.getMyId(),ListeUser.getMyPseudo());
            if(ListeUser.getMyPseudo().equals(pseudoRecu)){
                ack = new UDPMessage(UDPControlType.AckPseudoPasOK,self);
            }else{
                ack = new UDPMessage(UDPControlType.AckPseudoOk,self);
            }
            UdpSend.envoyerUnicast(ack, adresseClient);
        }catch (Exception e){e.printStackTrace();}
    }


}
