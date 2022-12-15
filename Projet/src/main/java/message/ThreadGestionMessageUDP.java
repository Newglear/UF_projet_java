package message;

import networkManager.UDPSend;
import userList.ListeUser;
import userList.UserItem;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
public class ThreadGestionMessageUDP extends Thread {

    public byte[] buffer;


    public InetAddress adresseClient;
    public ThreadGestionMessageUDP(byte[] buffer, InetAddress addressClient){
        try {
            this.adresseClient = addressClient;
            this.buffer = buffer;
            start();
        }catch (Exception e){e.printStackTrace();}
    }
    public void run(){
        ListeUser listeUser = ListeUser.getInstance();
        try {
            ObjectInputStream IStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
            UDPMessage mess = (UDPMessage) IStream.readObject();
            if(mess.user.getId() != listeUser.getMyId()) {
                switch (mess.controlType) {
                    case Connexion:
                        handleConnexion(mess.user.getPseudo(), adresseClient);
                        break;
                    case Deconnexion:
                        listeUser.removeUser(mess.user.getId());
                        break;
                    case AckPseudoOk:
                        listeUser.addUser(mess.user.getId(), mess.user.getPseudo(), adresseClient);
                        break;
                    case AckNewUserSurReseau:
                        listeUser.addUser(mess.user.getId(), mess.user.getPseudo(), adresseClient);
                        break;
                    case AckPseudoPasOK:
                        SetPseudo.ackPasOkRecu = true;
                        listeUser.addUser(mess.user.getId(), mess.user.getPseudo(), adresseClient);
                        break;
                    case ChangementPseudo:
                        listeUser.modifyUserPseudo(mess.user.getId(), mess.user.getPseudo());
                        break;
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void handleConnexion(String pseudoRecu, InetAddress adresseClient){
        UDPMessage ack;
        ListeUser listeUser = ListeUser.getInstance();
        try{
            UserItem self = new UserItem(listeUser.getMyId(),listeUser.getMyPseudo());
            if(listeUser.getMyPseudo().equals(pseudoRecu)){
                ack = new UDPMessage(UDPControlType.AckPseudoPasOK,self);
            }else{
                ack = new UDPMessage(UDPControlType.AckPseudoOk,self);
            }
            UDPSend.envoyerUnicast(ack, adresseClient);
        }catch (Exception e){e.printStackTrace();}
    }


}
