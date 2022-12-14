package userList;

import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class ListeUserTest{


    @Before
    public void remplirListe() throws UnknownHostException{
        ListeUser.addUser(2345,"romain", InetAddress.getLocalHost());
        ListeUser.addUser(4567,"aude", InetAddress.getLocalHost());
        ListeUser.addUser(5678, "evan", InetAddress.getLocalHost());
        ListeUser.addUser(9876, "gwen", InetAddress.getLocalHost());

    }

    @Test
    public void getUserTest() throws UserNotFoundException {
        assertEquals("romain",ListeUser.getUser(2345).getPseudo());
        assertEquals("aude",ListeUser.getUser(4567).getPseudo());
        assertEquals("gwen",ListeUser.getUser(9876).getPseudo());
        assertEquals("evan",ListeUser.getUser(5678).getPseudo());
    }


    @Test
    public void addUserTest() throws UnknownHostException {
        assertEquals("romain", ListeUser.tabItems.get(2345).getPseudo());
        assertEquals(InetAddress.getLocalHost(), ListeUser.tabItems.get(2345).getAddress());
        assertEquals(2345, ListeUser.tabItems.get(2345).getId());
    }


    @Test
    public void modifyUserTest() throws UnknownHostException, UserNotFoundException {
        ListeUser.modifyUserPseudo(2345,"romainMaisMieux");
        assertEquals("romainMaisMieux", ListeUser.tabItems.get(2345).getPseudo());
        assertEquals(InetAddress.getLocalHost(), ListeUser.tabItems.get(2345).getAddress());
        assertEquals(2345, ListeUser.tabItems.get(2345).getId());
        ListeUser.modifyUserPseudo(2345, "romain");
    }

    @Test (expected = UserNotFoundException.class)
    public void emptyExceptionTest() throws UserNotFoundException {
        ListeUser.modifyUserPseudo(0, "romainMaisMieux");
    }




    @Test (expected = UserNotFoundException.class)
    public void removeUserTest() throws UserNotFoundException {
        ListeUser.removeUser(2345);
        ListeUser.modifyUserPseudo(2345, "hola");
    }

    @Test
    public void UserItemTest() throws UnknownHostException {
        UserItem user = new UserItem(5432, "romain", InetAddress.getLocalHost());
        user.setPseudo("romain2");
        assertEquals("romain2", user.getPseudo());
    }

    @Test
    public void getTailleListeTest(){
        assertEquals(4, ListeUser.getTailleListe());
    }

    @Test
    public void addUserWithSameKey() throws UnknownHostException{
        ListeUser.addUser(2345, "Aude", InetAddress.getLocalHost());
        assertEquals(4, ListeUser.getTailleListe());
    }

    @Test (expected = AssignationProblemException.class)
    public void AssignationIdExceptionTest() throws AssignationProblemException {
        int id = ListeUser.getMyId();
        System.out.println("id: " + id);
    }

    @Test (expected = AssignationProblemException.class)
    public void AssignationPseudoExceptionTest() throws AssignationProblemException{
        String pseudo=ListeUser.getMyPseudo();
        System.out.println("pseudo : "+ pseudo);
    }

    @Test
    public void getIdTest() throws AssignationProblemException {
        ListeUser.setMyId(3);
        int id = ListeUser.getMyId();
        assertEquals(3, id);
    }

    @Test
    public void getPseudoTest() throws AssignationProblemException {
        ListeUser.setMyPseudo("moi");
        String id = ListeUser.getMyPseudo();
        assertEquals("moi", id);
    }

    @Test
    public void pseudoDisponibleTest(){
        assertTrue(ListeUser.pseudoDisponible("blabla"));
        assertFalse(ListeUser.pseudoDisponible("romain"));
    }



}