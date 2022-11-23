import java.util.ArrayList;
import java.util.Iterator;
import java.net.InetAddress;

public class ListeUser{

    ArrayList<UserItem> tabItems = new ArrayList<>();


    public void addUser(int id, String pseudo, InetAddress address){
        tabItems.add(new UserItem(id, pseudo, address));
    }

    public void modifyUser(int id, String oldPseudo, String newPseudo, InetAddress address) throws EmptyUserListException{
        if (tabItems.isEmpty()) {
            throw new EmptyUserListException();
        } else {
            tabItems.set(tabItems.indexOf(new UserItem(id, oldPseudo, address)), new UserItem(id, newPseudo, address));

        }
    }

    public UserItem getUserById(String pseudo) throws UserNotFoundException{
        Iterator<UserItem> iterator = this.tabItems.iterator();
        while (iterator.hasNext()){
            if (iterator.next().pseudo.equals(pseudo)){
                return iterator.next();
            }
        }

        throw new UserNotFoundException();

    }

}
