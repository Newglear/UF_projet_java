package UserList;

import java.net.InetAddress;
import java.util.HashMap;

public class ListeUser{
    /*TODO Rajouter une fonction qui retourne si un pseudo est dans la HashMap : public static boolean pseudoDisponible(String Pseudo)
    Ne pas vérifier pour soi même (id 0)*/
    protected static HashMap<Integer, UserItem> tabItems = new HashMap<>();

    public static void addUser(int id, String pseudo, InetAddress address){
        tabItems.putIfAbsent(id, new UserItem(id, pseudo, address));
    }

    public static void modifyUserPseudo(int id, String newPseudo) throws UserNotFoundException {
       try{
            tabItems.get(id).setPseudo(newPseudo);
       } catch (Exception e) {
            throw new UserNotFoundException();
       }
    }

    public static void removeUser(int id) throws UserNotFoundException {
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

    public static int getTailleListe(){
        return tabItems.size();
    }

}
