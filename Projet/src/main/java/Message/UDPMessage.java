package Message;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class UDPMessage implements Serializable {

    public String pseudo;

    public UDPControlType controlType;


    public UDPMessage(UDPControlType controlType, String pseudo) {
        this.controlType=controlType;
        this.pseudo=pseudo;
    }

    public byte[] getBytes(){
        // TODO
        // byteArrayInputStream
        return new byte[0];
    }

}
