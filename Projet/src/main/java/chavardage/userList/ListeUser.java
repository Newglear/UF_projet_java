package chavardage.userList;

import chavardage.AssignationProblemException;
import chavardage.IllegalConstructorException;
import chavardage.chavardageManager.AlreadyUsedPseudoException;
import javafx.application.Platform;
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
    private boolean test = true; // si on est en test on ne notifie pas l'observer


    private Consumer<UserItem> observer = (user) -> LOGGER.trace("default subscriber : " + user);
    private static final Logger LOGGER = LogManager.getLogger(ListeUser.class);


    public void setObserver(Consumer<UserItem> observer){
        LOGGER.trace("je set l'observer");
        this.observer=observer;
        // on passe tous les éléments déjà dans la liste à l'observer au moment où on set l'observer
        for (Map.Entry<Integer,UserItem> entry : tabItems.entrySet()){
            LOGGER.trace("je passe " + entry.getValue() + " à l'observer");
            observer.accept(entry.getValue());
        }
        test=false;
    }

    // The ONLY instance of UserList
    private static final ListeUser instance = new ListeUser();

    /** récupérer l'unique instance de ListeUser*/
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

    // hashmap synchronized
    protected Map<Integer, UserItem> tabItems = Collections.synchronizedMap(new HashMap<>());


    public synchronized void addUser(int id, String pseudo, InetAddress address){
        addUser(new UserItem(id,pseudo,address));
    }

    /** rajoute un user dans la liste et notifie le front, ne fait rien si l'user était déjà dans la liste*/
    public synchronized void addUser(UserItem userItem){
        if (!this.contains(userItem.getId())){
            userItem.setNotifyFront(NotifyFront.AddUser);
            tabItems.put(userItem.getId(),userItem);
            LOGGER.debug("j'ajoute " + userItem);
            if (!test) Platform.runLater(() -> observer.accept(userItem));
        }
    }

    /** change le pseudo d'un utilisateur dans la liste et notifie le front, lève une exception si l'user n'existe pas*/
    public synchronized void modifyUserPseudo(int id, String newPseudo) throws UserNotFoundException {
        if (tabItems.get(id)==null) throw new UserNotFoundException(id);
        tabItems.get(id).setNotifyFront(NotifyFront.ChangePseudo);
        tabItems.get(id).setPseudo(newPseudo);
        if (!test) Platform.runLater(() -> observer.accept(tabItems.get(id)));
    }


    /** enlève un user de la liste et notifie le front*/
    public synchronized void removeUser(int id) throws UserNotFoundException{
        if (!this.tabItems.containsKey(id)) throw new UserNotFoundException(id);
        UserItem deleteUser = tabItems.get(id);
        deleteUser.setNotifyFront(NotifyFront.DeleteUser);
        LOGGER.debug("j'enlève "+deleteUser);
        if (!test) Platform.runLater(() -> observer.accept(deleteUser));
        tabItems.remove(id);
    }

    public synchronized UserItem getUser(int id) throws UserNotFoundException {
        if (tabItems.get(id)==null) throw new UserNotFoundException(id);
        return tabItems.get(id);

    }

    public synchronized int getTailleListe(){
        return tabItems.size();
    }

    public synchronized boolean pseudoDisponible(String pseudo) { // Return true si le pseudo n'est pas dans la HashMap, false sinon
        if (myPseudo.equals(pseudo)) return false;
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

    public synchronized void setMyPseudo(String pseudo) throws AlreadyUsedPseudoException, SamePseudoAsOld {
        if (myPseudo.equals(pseudo)) throw new SamePseudoAsOld();
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

    public synchronized int getMyId() throws AssignationProblemException {
        if (myId==-1) throw new AssignationProblemException("ListeUser","myId");
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
