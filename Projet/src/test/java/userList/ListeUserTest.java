package userList;

import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class ListeUserTest{


    @Before
    public void remplirListe() throws UnknownHostException{
        ListeUser.getInstance().clear();
        ListeUser listeUser = ListeUser.getInstance();
        listeUser.addUser(2345,"romain", InetAddress.getLocalHost());
        listeUser.addUser(4567,"aude", InetAddress.getLocalHost());
        listeUser.addUser(5678, "evan", InetAddress.getLocalHost());
        listeUser.addUser(9876, "gwen", InetAddress.getLocalHost());

    }

    @Test
    public void getUserTest() throws UserNotFoundException {
        ListeUser listeUser = ListeUser.getInstance();
        assertEquals("romain",listeUser.getUser(2345).getPseudo());
        assertEquals("aude",listeUser.getUser(4567).getPseudo());
        assertEquals("gwen",listeUser.getUser(9876).getPseudo());
        assertEquals("evan",listeUser.getUser(5678).getPseudo());
    }


    @Test
    public void addUserTest() throws UnknownHostException, AssignationProblemException {
        ListeUser listeUser = ListeUser.getInstance();
        assertEquals("romain", listeUser.tabItems.get(2345).getPseudo());
        assertEquals(InetAddress.getLocalHost(), listeUser.tabItems.get(2345).getAddress());
        assertEquals(2345, listeUser.tabItems.get(2345).getId());
    }


    @Test
    public void modifyUserTest() throws UnknownHostException, UserNotFoundException, AssignationProblemException {
        ListeUser listeUser = ListeUser.getInstance();
        listeUser.modifyUserPseudo(2345,"romainMaisMieux");
        assertEquals("romainMaisMieux", listeUser.tabItems.get(2345).getPseudo());
        assertEquals(InetAddress.getLocalHost(), listeUser.tabItems.get(2345).getAddress());
        assertEquals(2345, listeUser.tabItems.get(2345).getId());
        listeUser.modifyUserPseudo(2345, "romain");
    }

    @Test (expected = UserNotFoundException.class)
    public void emptyExceptionTest() throws UserNotFoundException {
        ListeUser listeUser = ListeUser.getInstance();
        listeUser.modifyUserPseudo(0, "romainMaisMieux");
    }




    @Test (expected = UserNotFoundException.class)
    public void removeUserTest() throws UserNotFoundException {
        ListeUser listeUser = ListeUser.getInstance();
        listeUser.removeUser(2345);
        listeUser.modifyUserPseudo(2345, "hola");
    }

    @Test
    public void UserItemTest() throws UnknownHostException {
        UserItem user = new UserItem(5432, "romain", InetAddress.getLocalHost());
        user.setPseudo("romain2");
        assertEquals("romain2", user.getPseudo());
    }

    @Test
    public void getTailleListeTest(){
        ListeUser listeUser = ListeUser.getInstance();
        assertEquals(4, listeUser.getTailleListe());
    }

    @Test
    public void addUserWithSameKey() throws UnknownHostException{
        ListeUser listeUser = ListeUser.getInstance();
        listeUser.addUser(2345, "Aude", InetAddress.getLocalHost());
        assertEquals(4, listeUser.getTailleListe());
    }

    @Test (expected = AssignationProblemException.class)
    public void AssignationIdExceptionTest() throws AssignationProblemException {
        ListeUser listeUser = ListeUser.getInstance();
        int id = listeUser.getMyId();
        System.out.println("id: " + id);
    }

    @Test (expected = AssignationProblemException.class)
    public void AssignationPseudoExceptionTest() throws AssignationProblemException{
        ListeUser listeUser = ListeUser.getInstance();
        String pseudo=listeUser.getMyPseudo();
        System.out.println("pseudo : "+ pseudo);
    }

    @Test
    public void getIdTest() throws AssignationProblemException {
        ListeUser listeUser = ListeUser.getInstance();
        listeUser.setMyId(3);
        int id = listeUser.getMyId();
        assertEquals(3, id);
    }

    @Test
    public void getPseudoTest() throws AssignationProblemException {
        ListeUser listeUser = ListeUser.getInstance();
        listeUser.setMyPseudo("moi");
        String id = listeUser.getMyPseudo();
        assertEquals("moi", id);
    }

    @Test
    public void pseudoDisponibleTest(){
        ListeUser listeUser = ListeUser.getInstance();
        assertTrue(listeUser.pseudoDisponible("blabla"));
        assertFalse(listeUser.pseudoDisponible("romain"));
    }



}