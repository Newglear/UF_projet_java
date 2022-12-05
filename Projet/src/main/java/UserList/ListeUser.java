package UserList;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class ListeUser{

    private static String myPseudo = "";
    private static int myId = -1;

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
        for (Map.Entry<Integer,UserItem> entry : tabItems.entrySet()){
            if (entry.getValue().getPseudo().equals(pseudo)){
                return false;
            }
        }
        return true;
    }

    public static void setMyId(int id){
        myId=id;
    }

    public static void setMyPseudo(String pseudo){
        myPseudo=pseudo;
    }

    public static int getMyId() throws AssignationProblemException {
        if (myId==-1){
            throw new AssignationProblemException();
        }
        return myId;

    }

    public static String getMyPseudo() throws AssignationProblemException {
        if (myPseudo.equals("")){
            throw new AssignationProblemException();
        }
        return myPseudo;
    }

}
