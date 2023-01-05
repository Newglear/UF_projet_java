package chavardage.userList;

public class UserAlreadyInListException extends Exception {
    public final UserItem user;

    public UserAlreadyInListException(UserItem user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return ("User already in list : " + user);
    }
}
