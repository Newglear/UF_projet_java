package UserList;

import java.io.Serializable;
import java.net.InetAddress;


public class UserItem implements Serializable {

    private String pseudo;
    private final InetAddress address;
    private final int id; // id connue des utilisateurs


    public UserItem(int id, String pseudo, InetAddress address){
        this.id=id;
        this.pseudo=pseudo;
        this.address=address;
    }
    
    public UserItem(int id, String pseudo){
        this.id=id; 
        this.pseudo=pseudo;
        address = null;
    }

    public String getPseudo(){
        return this.pseudo;
    }

    public InetAddress getAddress(){
        return this.address;
    }

    public int getId(){
        return this.id;
    }

    public void setPseudo(String newPseudo){
        this.pseudo=newPseudo;
    }
}
