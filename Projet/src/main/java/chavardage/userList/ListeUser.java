package chavardage.userList;

import chavardage.AssignationProblemException;
import chavardage.GUI.Loged;
import chavardage.IllegalConstructorException;
import chavardage.chavardageManager.AlreadyUsedPseudoException;
import chavardage.chavardageManager.GestionUDPMessage;
import chavardage.chavardageManager.UsurpateurException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ListeUser{

    private String myPseudo = "";
    private int myId = -1;


    private Consumer<UserItem> observer = (user) -> LOGGER.trace("default subscriber : " + user);
    private static final Logger LOGGER = LogManager.getLogger(ListeUser.class);


    public void setObserver(Consumer<UserItem> observer){
        LOGGER.trace("je set l'observer");
        this.observer=observer;
        for (Map.Entry<Integer,UserItem> entry : tabItems.entrySet()){
            LOGGER.trace("je passe " + entry.getValue() + " à l'observer");
            observer.accept(entry.getValue());
        }
    }

    // The ONLY instance of UserList
    private static final ListeUser instance = new ListeUser();

    public static ListeUser getInstance() {
        return instance;
    }

    /** Private constructor to prevent anybody from invoking it. */
    private ListeUser() {}

    /** constructeur public pour des tests*/
    public ListeUser(boolean test) throws IllegalConstructorException {
        if (!test){
            throw new IllegalConstructorException();
        }
    }

    protected Map<Integer, UserItem> tabItems = Collections.synchronizedMap(new HashMap<>());


    public synchronized void addUser(int id, String pseudo, InetAddress address){
        addUser(new UserItem(id,pseudo,address));
    }

    public synchronized void addUser(UserItem userItem){
        if (!this.contains(userItem.getId())){
            userItem.setNotifyFront(NotifyFront.AddUser);
            tabItems.put(userItem.getId(),userItem);
            observer.accept(userItem);
        }
    }


    public synchronized void modifyUserPseudo(int id, String newPseudo) throws UserNotFoundException {
        if (tabItems.get(id)==null) throw new UserNotFoundException(id);
        tabItems.get(id).setNotifyFront(NotifyFront.ChangePseudo);
        tabItems.get(id).setPseudo(newPseudo);
        observer.accept(tabItems.get(id));
    }

    public synchronized void removeUser(int id) {
        tabItems.get(id).setNotifyFront(NotifyFront.DeleteUser);
        tabItems.remove(id);
        observer.accept(tabItems.get(id));
    }

    public synchronized UserItem getUser(int id) throws UserNotFoundException {
        if (tabItems.get(id)==null) throw new UserNotFoundException(id);
        return tabItems.get(id);

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
        LOGGER.debug("id set à " + id);
    }

    public synchronized void setMyPseudo(String pseudo) throws AlreadyUsedPseudoException {
        if (!pseudoDisponible(pseudo)){
            throw new AlreadyUsedPseudoException(pseudo);
        }
        myPseudo=pseudo;
    }

    public synchronized void setMyself(UserItem user){
        this.myId = user.getId();
        LOGGER.debug("id set à " + user.getId());
        this.myPseudo=user.getPseudo();
    }

    public synchronized void setMyself(int id, String pseudo){
        this.myId = id;
        LOGGER.debug("id set à " + id);
        this.myPseudo=pseudo;
    }

    public synchronized int getMyId()  {

        return myId;

    }

    public synchronized String getMyPseudo() throws AssignationProblemException {
        if (myPseudo.equals("")){
            throw new AssignationProblemException("ListeUser", "myPseudo");
        }
        return myPseudo;
    }

    public synchronized void clear(){
        this.myId = -1;
        LOGGER.debug("id reset");
        this.myPseudo = "";
        this.tabItems.clear();
    }

    public synchronized UserItem getMySelf() {
        return new UserItem(myId,myPseudo);
    }

    public synchronized void affiche(){
        for (Map.Entry<Integer,UserItem> entry : tabItems.entrySet()){
            System.out.println(entry.getValue());
        }
    }



    public synchronized boolean contains(int userId){
        return tabItems.containsKey(userId);
    }
}
