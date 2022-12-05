package Message;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import UserList.ListeUser;
import NetworkManager.UdpSend;
public class GestionMessageUDP extends Thread {

    public UDPMessage mess;

    public InetAddress adresseClient;
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
                    ListeUser.addUser(mess.user.getId(),mess.user.getPseudo(),adresseClient);
                    break;
                case AckNewUserSurReseau:
                    ListeUser.addUser(mess.user.getId(),mess.user.getPseudo(),adresseClient);
                    break;
                case AckPseudoPasOK:
                    SetPseudo.ackPasOkRecu = true;
                    ListeUser.addUser(mess.user.getId(),mess.user.getPseudo(),adresseClient);
                    break;
                case NewPseudo:
                    ListeUser.modifyUserPseudo(mess.user.getId(),mess.user.getPseudo());
                    break;
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void handleConnexion(String pseudoRecu, InetAddress adresseClient){
        UDPMessage ack;
        try{
            if(ListeUser.getUser(0).getPseudo().equals(pseudoRecu)){
                ack = new UDPMessage(UDPControlType.AckPseudoPasOK);
            }else{
                ack = new UDPMessage(UDPControlType.AckPseudoOk);
            }
                UdpSend.envoyerUnicast(ack.getBytes(), adresseClient);
        }catch (Exception e){e.printStackTrace();}
    }


}
