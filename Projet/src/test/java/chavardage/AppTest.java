package chavardage;

import chavardage.conversation.ConversationAlreadyExists;
import chavardage.conversation.ConversationDoesNotExist;
import chavardage.message.TCPMessage;
import chavardage.userList.UserItem;
import chavardage.userList.UserNotFoundException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Test;

public class AppTest {

    int udp1 = 6584;
    int udp2 = 8974;
    int tcp1 = 8478;
    int tcp2 = 7493;


    @Test
    public void appTest() throws UserNotFoundException, AssignationProblemException, ConversationAlreadyExists, ConversationDoesNotExist {
        //Configurator.setRootLevel(Level.INFO);
        AppOnPort app1 = new AppOnPort(udp1,tcp1, udp2, tcp2, new UserItem(1,"Aude"));
        AppOnPort app2 = new AppOnPort(udp2,tcp2,udp1,tcp1, new UserItem(2,"Romain"));
        app1.start();
        app2.start();
        app1.conversationManager.openConversation(2);
        app1.conversationManager.getSendData(2).envoyer(new TCPMessage(2,1,"yo"));
        app2.conversationManager.getSendData(1).envoyer(new TCPMessage(1,2,"coucouuuuuuuuu"));
        app1.closeApp();
    }


}
