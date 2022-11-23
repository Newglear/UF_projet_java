public abstract class TCPMessage implements Message {

    int destinataireId;

    public TCPMessage(int destinataireId){

        this.destinataireId=destinataireId;

    }

}
