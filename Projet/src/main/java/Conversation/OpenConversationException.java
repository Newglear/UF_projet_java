package Conversation;

public class OpenConversationException extends Exception {


    private String message;

    public OpenConversationException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
