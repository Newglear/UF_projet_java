package UserList;

import java.util.ArrayList;
import java.net.InetAddress;
import java.util.Iterator;

public class ListeUser{

    ArrayList<UserItem> tabItems = new ArrayList<>();


    public void addUser(int id, String pseudo, InetAddress address){
        tabItems.add(new UserItem(pseudo, address, id));
    }

    public void modifyUserPseudo(int id, String newPseudo) throws EmptyUserListException{
       try{
            tabItems.get(this.getIndex(id)).setPseudo(newPseudo);
       } catch (IndexOutOfBoundsException | UserNotFoundException e) {
            throw new EmptyUserListException();
       }
    }

    public void removeUser(int id) throws UserNotFoundException {
        try {
            tabItems.remove(this.getIndex(id));
        } catch (IndexOutOfBoundsException e){
            throw new UserNotFoundException();
        }
    }

    // TODO  : coder ça mieux si possible
    protected int getIndex(int id) throws UserNotFoundException {
        for (int i = 0; i<tabItems.size(); i++){
            if (tabItems.get(i).getId()==id){
                return i;
            }
        }
        throw new UserNotFoundException();
    }

    public UserItem getUser(int id) throws UserNotFoundException {
        for (UserItem user : tabItems) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }


}
