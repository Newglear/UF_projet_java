package chavardage.connexion;

public class UsurpateurException extends Exception {

    private final String message;

    public UsurpateurException(String s) {
        this.message=s;
    }

    public String getMessage(){
        return message;
    }
}
