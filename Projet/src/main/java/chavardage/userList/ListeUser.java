package chavardage.userList;

import chavardage.AssignationProblemException;

import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ListeUser{

    private String myPseudo = "";
    private int myId = -1;

    // The ONLY instance of UserList
    private static final ListeUser instance = new ListeUser();

    public static ListeUser getInstance() {
        return instance;
    }

    /** Private constructor to prevent anybody from invoking it. */
    private ListeUser() {}

    protected Map<Integer, UserItem> tabItems = Collections.synchronizedMap(new HashMap<>());


    public synchronized void addUser(int id, String pseudo, InetAddress address){
        tabItems.putIfAbsent(id, new UserItem(id, pseudo, address));
    }

    public synchronized void addUser(UserItem userItem){
        tabItems.putIfAbsent(userItem.getId(), userItem);
    }


    public synchronized void modifyUserPseudo(int id, String newPseudo) throws UserNotFoundException {
       try{
            tabItems.get(id).setPseudo(newPseudo);
       } catch (Exception e) {
            throw new UserNotFoundException(id);
       }
    }

    public synchronized void removeUser(int id) throws UserNotFoundException {
        try {
            tabItems.remove(id);
        } catch (Exception e){
            throw new UserNotFoundException(id);
        }
    }

    public synchronized UserItem getUser(int id) throws UserNotFoundException {
        try{
            return tabItems.get(id);
        }catch (Exception e){
            throw new UserNotFoundException(id);
        }
    }

    public synchronized int getTailleListe(){
        return tabItems.size();
    }

    public synchronized boolean pseudoDisponible(String pseudo){ // Return true si le pseudo n'est pas dans la HashMap, false sinon
        for (Map.Entry<Integer,UserItem> entry : tabItems.entrySet()){
            if (entry.getValue().getPseudo().equals(pseudo)){
                return false;
            }
        }
        return true;
    }

    public synchronized void setMyId(int id){
        myId=id;
    }

    public synchronized void setMyPseudo(String pseudo){
        myPseudo=pseudo;
    }

    public synchronized int getMyId() throws AssignationProblemException {
        if (myId==-1){
            throw new AssignationProblemException("ListeUser", "myId");
        }
        return myId;

    }

    public synchronized String getMyPseudo() throws AssignationProblemException {
        if (myPseudo.equals("")){
            throw new AssignationProblemException("ListeUser", "myPseudo");
        }
        return myPseudo;
    }

    public void clear(){
        this.myId = -1;
        this.myPseudo = "";
        this.tabItems.clear();
    }

}
