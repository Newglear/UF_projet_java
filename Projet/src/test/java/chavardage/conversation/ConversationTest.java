package chavardage.conversation;

import chavardage.message.TCPMessage;
import chavardage.message.TCPType;
import chavardage.message.WrongConstructorException;
import chavardage.userList.ListeUser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConversationTest {



    @Test
    public void conversationTest() throws ConversationException, WrongConstructorException {
        // TODO
        ListeUser.getInstance().setMyId(3); // j'ai l'id 3
        Conversation conversation = new Conversation(5); // je crée une conversation avec 5
        conversation.sendMessage("coucou");
        assertEquals(5, conversation.getDestinataireId());
        conversation.traiterMessageEntrant(new TCPMessage(3, "hola"));
        try { // on teste que les exceptions se lancent bien quand il faut
            conversation.traiterMessageEntrant(new TCPMessage(3, TCPType.OuvertureSession));
        } catch (ConversationException e) {
            System.out.println("exception levée : " + e);
        }
        try {
            conversation.traiterMessageEntrant(new TCPMessage(4, "ahah"));
        } catch (ConversationException e) {
            System.out.println("exception levée : " + e);
        }
        conversation.traiterMessageEntrant(new TCPMessage(3, TCPType.FermetureSession));
    }



// TODO supprimer

    /* @Test
    public void constructorIntTest() throws Exception {
        TCPServeur tcpServeur = new TCPServeur(); // je mets le serveur en localhost aussi
        ListeUser listeUser = ListeUser.getInstance();
        listeUser.addUser(1, "romain", InetAddress.getLocalHost());
        Conversation conversation1 = new Conversation(1);
        conversation1.fermerConversation();
        tcpServeur.interrupt();
    }

    @Test
    public void constructorSocketTest() throws Exception {
        EnvoyerMessage envoyerMessage = new EnvoyerMessage(); // envoi d'un message d'ouverture de conversation
        ServerSocket ecoute = new ServerSocket(TCPServeur.PORT_TCP); // mini serveur en localhost
        Socket socket = ecoute.accept();
        Conversation conversation = new Conversation(socket);
        conversation.fermerConversation();
        ecoute.close();
    }


    @Test
    public void sendMessageTest() throws IOException {
        TCPServeur tcpServeur = new TCPServeur(); // je mets le serveur en localhost aussi
        ListeUser listeUser = ListeUser.getInstance();
        listeUser.addUser(1, "romain", InetAddress.getLocalHost());
        Conversation conversation1 = new Conversation(1);
        conversation1.sendMessage("coucou");
        conversation1.fermerConversation();
        // c'est un peu chelou parce que c'est la même conversation qui envoie et qui reçoit, mais c'est ok
        tcpServeur.interrupt();
    }*/





}
