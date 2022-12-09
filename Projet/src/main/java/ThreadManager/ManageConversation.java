/*package ThreadManager;

import Conversation.Conversation;
import Conversation.ThreadConversationReceive;
import Conversation.ThreadConversationSend;

import java.util.HashMap;

public class ManageConversation {

    public static HashMap<Conversation, ConversationThreadRecord> ListeThreadsConvs = new HashMap<Conversation, ConversationThreadRecord>();
    public void lancerConversation(Conversation conv){
        try {
            ThreadConversationReceive threadReceive = new ThreadConversationReceive(conv);
            ThreadConversationSend threadSend = new ThreadConversationSend(conv);
            ConversationThreadRecord recordThread = new ConversationThreadRecord(threadSend, threadReceive);
            ListeThreadsConvs.put(conv,recordThread);
        }catch (Exception e){e.printStackTrace();}
    }

    public void fermerConversation(Conversation conv){
        ConversationThreadRecord liste = ListeThreadsConvs.get(conv);
        liste.threadConvReceive.interrupt();
        liste.threadConvSend.interrupt();
        ListeThreadsConvs.remove(conv);
    }
}
*/