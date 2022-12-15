package message;

import userList.UserItem;

import java.io.*;

public class UDPMessage implements Serializable {

    public UserItem user;

    public UDPControlType controlType;

    public UDPMessage(UDPControlType controlType, UserItem user) {
        this.controlType=controlType;
        this.user=user;
    }

}
