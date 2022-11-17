import java.util.ArrayList;

public class ListeUser{

    ArrayList<UserItem> tabItems = new ArrayList<>();


    public void addUser(int id, String pseudo){
        tabItems.add(new UserItem(id, pseudo));
        // TODO
    }

    public void modifyUser(int id, String oldPseudo, String newPseudo){
        tabItems.set(tabItems.indexOf(new UserItem(id, oldPseudo)), new UserItem(id, newPseudo));
        // TODO
    }


}
