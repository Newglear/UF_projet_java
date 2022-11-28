package Conversation;

import java.net.Socket;
import java.util.HashMap;

public class ConversationManager {
    protected static HashMap<Integer, Conversation> mapConversations = new HashMap<>();

    public static Conversation createConv(int destinataireId){
        try {
            Conversation conversation = new Conversation(destinataireId);
            mapConversations.put(conversation.getDestinataireId(),conversation);
            return conversation;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Conversation createConv(Socket socket){
        try {
            Conversation conversation = new Conversation(socket);
            mapConversations.put(conversation.getDestinataireId(),conversation);
            return conversation;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Conversation getConv(int destinataireId){
        return mapConversations.get(destinataireId);
    }

}
