package UserList;

import java.net.InetAddress;


public class UserItem {

    private String pseudo;
    private InetAddress address;


    public UserItem(String pseudo, InetAddress address){
        this.pseudo=pseudo;
        this.address=address;
    }

    public String getPseudo(){
        return this.pseudo;
    }

    public InetAddress getAddress(){
        return this.address;
    }

}
