
public class TCPControlMessage extends TCPMessage {


    public TCPControlMessage(int destinataireId) {

        super(destinataireId);
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }
}
