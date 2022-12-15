package chavardage.conversation;

public class ConversationDoesNotExist extends Exception {

    private final int destinataireId;

    public ConversationDoesNotExist(int destinataireId){
        this.destinataireId=destinataireId;
    }


    public String toString(){
        return "la conversation avec " + destinataireId + " n'existe pas";
    }
}
