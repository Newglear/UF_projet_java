import java.net.InetAddress;


public class UserItem {

    int id;
    String pseudo;
    InetAddress address;


    public UserItem(int id, String pseudo, InetAddress address){
        this.id=id;
        this.pseudo=pseudo;
        this.address=address;
    }

}
