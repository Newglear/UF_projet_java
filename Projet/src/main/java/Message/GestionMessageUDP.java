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
            System.out.println("Mon ID :" + ListeUser.getMyId() + " ID reçu " + mess.user.getId());
            System.out.println(adresseClient);
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
            }else{
                System.out.println("Paquet rejeté");
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void handleConnexion(String pseudoRecu, InetAddress adresseClient){
        UDPMessage ack;
        try{
            UserItem self = new UserItem(ListeUser.getMyId(),ListeUser.getMyPseudo());
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bstream);
            if(ListeUser.getMyPseudo().equals(pseudoRecu)){
                ack = new UDPMessage(UDPControlType.AckPseudoPasOK,self);
            }else{
                ack = new UDPMessage(UDPControlType.AckPseudoOk,self);
            }
            oo.writeObject(ack);
            oo.close();
            byte[] sentMessage = bstream.toByteArray();
            UdpSend.envoyerUnicast(sentMessage, adresseClient);
        }catch (Exception e){e.printStackTrace();}
    }


}
