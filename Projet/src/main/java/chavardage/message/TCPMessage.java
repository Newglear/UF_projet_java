package chavardage.message;

import chavardage.AssignationProblemException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TCPMessage implements Serializable {

    private final int envoyeurId;
    private final int destinataireId;
    private final TCPType type;
    private final String texte;
    private final Date date;



    /** creates a control TCP message (open or close conversation) without data*/
    public TCPMessage(int destinataireId, int envoyeurId, TCPType type) {
        this.destinataireId = destinataireId;
        this.type=type;
        this.envoyeurId=envoyeurId;
        this.texte ="";
        date = new Date();
    }

    /** creates an user TCP message (type : UserData) with user text message*/
    public TCPMessage(int destinataireId, int envoyeurId, String data){
        this.destinataireId = destinataireId;
        this.type=TCPType.UserData;
        this.envoyeurId=envoyeurId;
        this.texte =data;
        date = new Date();
    }

    public String getTexte(){
        return this.texte;
    }

    public TCPType getType() {
        return type;
    }

    public int getEnvoyeurId() throws AssignationProblemException {
        return envoyeurId;
    }

    public int getDestinataireId() {
        return destinataireId;
    }

    public String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(this.date);
    }

    public String toString(){
        return "TCPMessage {" +
                "destinataireId='" + destinataireId + '\'' +
                ", envoyeurId='" + envoyeurId + '\'' +
                ", type='" + type + '\'' +
                ", data='" + texte + '\'' +
                '}';
    }
}
