package SuperTest;

import Conversation.ConversationManager;
import NetworkManager.ThreadTcpReceiveConnection;
import UserList.ListeUser;
import UserList.UserAlreadyInListException;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UserRecoitRequete{

    @Test
    public static void main(String[] args) throws UnknownHostException, UserAlreadyInListException {
        InetAddress addressUserLanceRequete = InetAddress.getByName(""); // TODO
        ListeUser.addUser(3456, "aude", addressUserLanceRequete);
        ThreadTcpReceiveConnection threadRcv = new ThreadTcpReceiveConnection();
        try {
            ConversationManager.getConv(3456).sendMessage("coucou user1");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
