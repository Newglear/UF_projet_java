package message;

import userList.UserItem;

import java.io.*;

public class UDPMessage implements Serializable {

    private final UserItem user;

    private final UDPControlType controlType;

    public UDPMessage(UDPControlType controlType, UserItem user) {
        this.controlType=controlType;
        this.user=user;
    }

    public String toString(){
        return "UDPMessage {" +
                "type='" + controlType + '\'' +
                ", user=" + user +
                '}';
    }

    public UserItem getUser(){
        return this.user;
    }

    public UDPControlType getControlType(){return this.controlType;}

}
