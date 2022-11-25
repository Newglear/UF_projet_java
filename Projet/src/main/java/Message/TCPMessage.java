package Message;

import java.io.Serializable;

public class TCPMessage implements Serializable {

    private int destinataireId;
    public TCPType type;
    private String data;


    public TCPMessage(int destinataireId, TCPType type) throws WrongConstructorException {
        this.destinataireId=destinataireId;
        if (type==TCPType.UserData) throw new WrongConstructorException();
        this.type=type;
        this.data="";
    }

    public TCPMessage(int destinataireId, String data){
        this.destinataireId=destinataireId;
        this.type=TCPType.UserData;
        this.data=data;
    }

    public int getDestinataireId(){
        return this.destinataireId;
    }

}
