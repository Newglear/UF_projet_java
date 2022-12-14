package conversation;

public class OpenConversationException extends Exception {


    private final String message;

    public OpenConversationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
