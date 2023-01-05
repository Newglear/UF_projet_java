package chavardage.message;

import chavardage.userList.UserItem;

import java.io.Serializable;

public class UDPMessage implements Serializable {

    private final UserItem envoyeur;

    private final UDPControlType controlType;

    public UDPMessage(UDPControlType controlType, UserItem envoyeur) {
        this.controlType=controlType;
        this.envoyeur = envoyeur;
    }

    public String toString(){
        return "UDPMessage {" +
                "type='" + controlType + '\'' +
                ", user=" + envoyeur +
                '}';
    }

    public UserItem getEnvoyeur(){
        return this.envoyeur;
    }

    public UDPControlType getControlType(){return this.controlType;}

}
