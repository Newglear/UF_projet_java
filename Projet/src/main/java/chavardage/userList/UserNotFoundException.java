package chavardage.userList;

public class UserNotFoundException extends Exception{

    public final int destinataireId;

    public UserNotFoundException(int id) {
        this.destinataireId = id;
    }

    @Override
    public String toString() {
        return ("UserNotFound : " + destinataireId);
    }
}
