package UserList;

import java.util.ArrayList;
import java.net.InetAddress;
import java.util.Iterator;

public class ListeUser{

    ArrayList<UserItem> tabItems = new ArrayList<>();


    public void addUser(String pseudo, InetAddress address){
        tabItems.add(new UserItem(pseudo, address));
    }

    // l'id est l'index dans la liste des users
    public void modifyUser(int id, String newPseudo, InetAddress address) throws EmptyUserListException{
       try{
            tabItems.set(id, (new UserItem(newPseudo, address)));
       } catch (IndexOutOfBoundsException e) {
            throw new EmptyUserListException();
       }
    }

    public void removeUser(int id) throws UserNotFoundException {
        try {
            tabItems.remove(id);
        } catch (IndexOutOfBoundsException e){
            throw new UserNotFoundException();
        }
    }

    // TODO  : coder ça mieux si possible
    public int getUserId(String pseudo) throws UserNotFoundException {
        Iterator<UserItem> iter = tabItems.iterator();
        for (int i = 0; i<tabItems.size(); i++){
            if (tabItems.get(i).getPseudo().equals(pseudo)){
                return i;
            }
        }
        throw new UserNotFoundException();
    }

}
