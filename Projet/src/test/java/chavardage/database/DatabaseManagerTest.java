package chavardage.database;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class DatabaseManagerTest {

    @Before
    public void clearDatabase() throws SQLException {
        DatabaseManager.getInstance().clearDatabase();
        DatabaseManager.getInstance().insertNewUser(1);
        DatabaseManager.getInstance().insertNewUser(2);
        DatabaseManager.getInstance().insertNewUser(3);
    }

    @Test
    public void insertNewUserTest() throws SQLException{
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        assertEquals(true,databaseManager.isInDatabase(1));
        assertEquals(true,databaseManager.isInDatabase(2));
        assertEquals(true,databaseManager.isInDatabase(3));
        assertEquals(false,databaseManager.isInDatabase(4));
        assertEquals(false,databaseManager.isInDatabase(5));
    }

    @Test
    public void insertMessageTest() throws  SQLException{
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        databaseManager.insertMessage(1,2,"Test#1",timestamp,1);
        databaseManager.insertMessage(1,2,"Test#2",timestamp,2);
        ResultSet result = databaseManager.getMessages(1,2);

        result.next();

        assertEquals("Test#1",result.getString("message"));
        assertEquals(timestamp, result.getTimestamp("date"));
        assertEquals(1,result.getInt("sentBy"));

        result.next();

        assertEquals("Test#2",result.getString("message"));
        assertEquals(timestamp, result.getTimestamp("date"));
        assertEquals(2,result.getInt("sentBy"));
        result.close();
    }


}
