public class UserMessage extends TCPMessage {

    public UserMessage(int destinataireId) {
        super(destinataireId);

    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }
}
