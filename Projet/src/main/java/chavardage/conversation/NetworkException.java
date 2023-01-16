package chavardage.conversation;

public class NetworkException extends Exception{
    private final String classe;
    public NetworkException(String classe){
        this.classe=classe;
    }

    public String toString(){
        return "problème de réseau avec la classe " + classe;
    }
}
