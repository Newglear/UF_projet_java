package Message;

import UserList.UserItem;

import java.io.*;

public class UDPMessage implements Serializable {

    public UserItem user;

    public UDPControlType controlType;


    public UDPMessage(UDPControlType controlType, UserItem user) {
        this.controlType=controlType;
        this.user=user;
    }

    public byte[] getBytes() throws UDPGetBytesException {
        ByteArrayOutputStream bstream = new ByteArrayOutputStream();
        ObjectOutput oo;
        try {
            oo = new ObjectOutputStream(bstream);
            oo.writeObject(this);
            return bstream.toByteArray();
        } catch (IOException e) {
            throw new UDPGetBytesException();
        }
    }

}
