package Message;

import java.io.*;

public class UDPMessage implements Serializable {

    public String pseudo;

    public UDPControlType controlType;


    public UDPMessage(UDPControlType controlType, String pseudo) {
        this.controlType=controlType;
        this.pseudo=pseudo;
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
