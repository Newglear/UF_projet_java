package Message;

public class TCPControlMessage extends TCPMessage {

    public TCPControlType controlType;

    public TCPControlMessage(int destinataireId, TCPControlType controlType) {
        super(destinataireId);
        this.controlType=controlType;
    }


}
