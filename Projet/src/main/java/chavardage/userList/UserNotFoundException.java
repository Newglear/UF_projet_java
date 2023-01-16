package chavardage.userList;

public class UserNotFoundException extends Exception{

    public final int destinataireId;

    public UserNotFoundException(int id) {
        this.destinataireId = id;
    }

    @Override
    public String getMessage() {
        return ("UserNotFound : " + destinataireId);
    }
}
