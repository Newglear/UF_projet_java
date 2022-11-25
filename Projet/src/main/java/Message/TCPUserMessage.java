package Message;


public class TCPUserMessage extends TCPMessage{

    private String data;

    public TCPUserMessage(int destinataireId, String data){
        super(destinataireId);
        this.data=data;
    }
}
