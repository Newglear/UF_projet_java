package chavardage.conversation;

public class ConversationAlreadyExists extends Exception {

    private final int id;

    public ConversationAlreadyExists(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "la conversation avec " + id + " existe déjà";
    }

}
