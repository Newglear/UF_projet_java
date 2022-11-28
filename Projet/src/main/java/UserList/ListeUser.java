package UserList;

import java.net.InetAddress;
import java.util.HashMap;

public class ListeUser{

    static HashMap<Integer, UserItem> tabItems = new HashMap<>();

    public void addUser(int id, String pseudo, InetAddress address){
        tabItems.put(id, new UserItem(id, pseudo, address));
    }

    public void modifyUserPseudo(int id, String newPseudo) throws UserNotFoundException {
       try{
            tabItems.get(id).setPseudo(newPseudo);
       } catch (Exception e) {
            throw new UserNotFoundException();
       }
    }

    public void removeUser(int id) throws UserNotFoundException {
        try {
            tabItems.remove(id);
        } catch (Exception e){
            throw new UserNotFoundException();
        }
    }


    public static UserItem getUser(int id) throws UserNotFoundException {
        try{
            return tabItems.get(id);
        }catch (Exception e){
            throw new UserNotFoundException();
        }
    }


}
