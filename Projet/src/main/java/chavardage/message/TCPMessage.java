package chavardage.message;

import chavardage.AssignationProblemException;

import java.io.Serializable;
import java.util.Date;

public class TCPMessage implements Serializable { // TODO ajouter date et heure

    private final int envoyeurId;
    private final int destinataireId;
    private final TCPType type;
    private final String data;
    private final Date date;



    /** creates a control TCP message (open or close conversation) without data*/
    public TCPMessage(int destinataireId, int envoyeurId, TCPType type) throws WrongConstructorException {
        this.destinataireId = destinataireId;
        this.type=type;
        this.envoyeurId=envoyeurId;
        this.data="";
        date = new Date();
    }

    /** creates an user TCP message (type : UserData) with user text message*/
    public TCPMessage(int destinataireId, int envoyeurId, String data){
        this.destinataireId = destinataireId;
        this.type=TCPType.UserData;
        this.envoyeurId=envoyeurId;
        this.data=data;
        date = new Date();
    }

    public String getData(){
        return this.data;
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


    public String toString(){
        return "TCPMessage {" +
                "destinataireId='" + destinataireId + '\'' +
                ", envoyeurId='" + envoyeurId + '\'' +
                ", type='" + type + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
