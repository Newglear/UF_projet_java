package UserList;

import java.net.InetAddress;
import java.util.HashMap;

public class ListeUser{

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

    public static boolean pseudoDisponible(String pseudo){ //TODO Return true si le pseudo n'est pas dans la HashMap, false sinon
        return true;
    }
    public static int getMyId(){ // TODO
        return 0;
    }

    public static String getMyPseudo(){ // TODO
        return "moi";
    }

    public static InetAddress getMyAddress() throws Exception{ //TODO
        return InetAddress.getLocalHost();
    }
}
