public class TCPControlMessage extends TCPMessage {

    private ControlType controlType;

    public TCPControlMessage(int destinataireId, ControlType controlType) {
        super(destinataireId);
        this.controlType=controlType;
    }
}
