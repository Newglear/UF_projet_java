package Conversation;

import java.net.Socket;
import java.util.ArrayList;

public class ConversationManager {
    // TODO
    protected static ArrayList<Conversation> listeConversations = new ArrayList<Conversation>();

    public static Conversation createConv(int destinataireId){
        try {
            Conversation conversation = new Conversation(destinataireId);
            listeConversations.add(conversation);
            return conversation;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Conversation createConv(Socket socket){
        try {
            Conversation conversation = new Conversation(socket);
            listeConversations.add(conversation);
            return conversation;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
