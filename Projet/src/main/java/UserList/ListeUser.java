package UserList;

import java.util.ArrayList;
import java.net.InetAddress;

public class ListeUser{

    //1er user de la liste est nous même
    protected static ArrayList<UserItem> tabItems = new ArrayList<>();


    public static void addUser(int id, String pseudo, InetAddress address){
        tabItems.add(new UserItem(pseudo, address, id));
    }

    public static void modifyUserPseudo(int id, String newPseudo) throws EmptyUserListException{
       try{
            tabItems.get(getIndex(id)).setPseudo(newPseudo);
       } catch (IndexOutOfBoundsException | UserNotFoundException e) {
            throw new EmptyUserListException();
       }
    }

    public static void removeUser(int id) throws UserNotFoundException {
        try {
            tabItems.remove(getIndex(id));
        } catch (IndexOutOfBoundsException e){
            throw new UserNotFoundException();
        }
    }

    // TODO  : coder ça mieux si possible
    protected static int getIndex(int id) throws UserNotFoundException {
        for (int i = 0; i<tabItems.size(); i++){
            if (tabItems.get(i).getId()==id){
                return i;
            }
        }
        throw new UserNotFoundException();
    }

    public static UserItem getUser(int id) throws UserNotFoundException {
        for (UserItem user : tabItems) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }


    public static String getMyPseudo(){
        return tabItems.get(0).getPseudo();
    }

}
