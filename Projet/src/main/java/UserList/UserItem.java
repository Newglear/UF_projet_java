package UserList;

import java.net.InetAddress;


public class UserItem {

    private String pseudo;
    private InetAddress address;
    private int id;


    public UserItem(String pseudo, InetAddress address, int id){
        this.pseudo=pseudo;
        this.address=address;
        this.id=id;
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
