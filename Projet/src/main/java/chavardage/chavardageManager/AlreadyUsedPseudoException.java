package chavardage.chavardageManager;

public class AlreadyUsedPseudoException extends Exception {


    String pseudo;
    public AlreadyUsedPseudoException(String pseudo){
        this.pseudo=pseudo;
    }

    public String toString(){
        return "pseudo déjà utilisé : " + pseudo;
    }

}
