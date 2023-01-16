package chavardage.userList;

import chavardage.AssignationProblemException;

import java.io.Serializable;
import java.net.InetAddress;


public class UserItem implements Serializable {

    private String pseudo;

    private NotifyFront notifyFront = NotifyFront.AddUser;
    private InetAddress address;
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

    public InetAddress getAddress() throws AssignationProblemException {
        if (this.address==null){
            throw new AssignationProblemException("UserItem", "address");
        }
        return this.address;
    }

    public int getId(){
        return this.id;
    }

    public void setPseudo(String newPseudo){
        this.pseudo=newPseudo;
    }

    public void setAddress(InetAddress address){
        this.address=address;
    }

    @Override
    public String toString() {
        return "UserItem{" +
                "pseudo='" + pseudo + '\'' +
                ", id=" + id +
                ", address=" + address +
                '}';
    }

    public NotifyFront getNotifyFront() {
        return notifyFront;
    }

    public void setNotifyFront(NotifyFront notifyFront) {
        this.notifyFront = notifyFront;
    }
}
