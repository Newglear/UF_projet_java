
public class TCPControlMessage extends TCPMessage {

    private ControlType controlType;

    public TCPControlMessage(int destinataireId, ControlType controlType) {
        super(destinataireId);
        this.controlType=controlType;


        // TODO  : envoyer avec networkManager.TCPSend.envoyer()
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }
}
