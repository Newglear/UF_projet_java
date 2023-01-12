package chavardage;

public class AssignationProblemException extends Exception {

    public final String nomClasse ;
    public final String nomVariable;


    public AssignationProblemException(String nomClasse, String nomVariable) {
        this.nomVariable = nomVariable;
        this.nomClasse= nomClasse;
    }

    @Override
    public String getMessage(){
        return ("la variable "+ nomVariable + " de la classe " + nomClasse + "n'a pas été assignée correctement") ;
    }
}
