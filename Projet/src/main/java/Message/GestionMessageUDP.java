package Message;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;

public class GestionMessageUDP {

    public static void handleMessage(byte[] buffer, InetAddress adresseClient){
        try {
            ObjectInputStream IStream = new ObjectInputStream(new ByteArrayInputStream(this.message));
            UDPMessage mess = (UDPMessage) IStream.readObject();
            switch (mess.controlType){
                case Connexion:
                    break;
                case Deconnexion:
                    break;
                case AckPseudoOk:
                    break;
                case AckPseudoPasOK:
                    break;
            }
        }catch (Exception e){e.printStackTrace();}
    }


}
