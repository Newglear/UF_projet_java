package Message;

import java.io.Serializable;

public class TCPMessage implements Serializable {

    private int destinataireId;

    public TCPMessage(int destinataireId){

        this.destinataireId=destinataireId;

    }

    public int getDestinataireId(){
        return this.destinataireId;
    }

}
