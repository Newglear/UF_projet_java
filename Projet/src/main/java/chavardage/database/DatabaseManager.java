package chavardage.database;

import java.sql.*;

public class DatabaseManager {

    private String urlDatabase ="jdbc:mysql://localhost:3306/chavardage";
    private String userName = "root";
    private String password = "root";

    private Connection database;

    private Statement statement;

    private DatabaseManager(){
        try {
            database = DriverManager.getConnection(urlDatabase,userName,password);
            statement = database.createStatement();
        }catch (SQLException e){e.printStackTrace();}
    }

    private static final DatabaseManager instance = new DatabaseManager();

    public static DatabaseManager getInstance() {return instance;}

    public synchronized ResultSet getMessages(int idUserLocal, int idUserDistant) throws SQLException{
        int minId = Math.min(idUserLocal,idUserDistant);
        int maxId = Math.max(idUserLocal,idUserDistant);
        ResultSet result = statement.executeQuery("SELECT date,sentBy,message FROM conversation AS c JOIN message as m ON c.userId1=" + minId  + "AND c.userId2=" + maxId + " AND c.idConversation=m.convId;");
        return result;
    }

    //TODO appeller quand nouvel utilisateur se connecte (insère dans la database s'il n'existe pas déjà)
    public synchronized  void insertNewUser(int idUser) throws  SQLException{
        statement.executeQuery("INSERT INTO user SELECT " + idUser + " WHERE NOT EXISTS(SELECT * FROM user where idUser=" + idUser +");");
    }

    //TODO Insérer message dans la table
    public synchronized  void insertMessage(int idUserLocal, int idUserDistant, String message) throws SQLException{
        int minId= Math.min(idUserLocal,idUserDistant);
        int maxId= Math.max(idUserLocal,idUserDistant);

        ResultSet result = statement.executeQuery("SELECT * FROM conversation WHERE userId1=" + minId + " AND userId2=" + maxId + ";");

        if(!result.next()){
                statement.executeQuery("INSERT INTO conversation  (userId1,userId2) VALUES (" + minId + "," + maxId +");");
        }


    }
}
