package chavardage.conversation;

public class ConversationException extends Exception {


    private final String message;

    public ConversationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
