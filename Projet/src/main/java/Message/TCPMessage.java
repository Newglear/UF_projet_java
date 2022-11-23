package Message;

public class TCPMessage{

    private int destinataireId;

    public TCPMessage(int destinataireId){

        this.destinataireId=destinataireId;

    }

    public int getDestinataireId(){
        return this.destinataireId;
    }

}
