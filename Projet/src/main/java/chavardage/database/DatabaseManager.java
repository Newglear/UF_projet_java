package chavardage.database;

import java.sql.*;
import java.util.Date;

public class DatabaseManager {

    //TODO se connecter à la databese centralisé
    private String urlDatabase;
    private String userName;
    private String password;

    private Connection database;

    private Statement statement;

    public final int messageLengthMax = 100;
    private DatabaseManager(){
        try {
            urlDatabase = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tp_java4ir_003";
            userName = "tp_java4ir_003";
            password = "ptheiy5Wo";
            database = DriverManager.getConnection(urlDatabase,userName,password);
            statement = database.createStatement();
        }catch (SQLException e){e.printStackTrace();}
    }
    /*
    private DatabaseManager(){
        try {
            urlDatabase = "jdbc:mysql://localhost:3306/chavardage";
            userName = "root";
            password = "root";
            database = DriverManager.getConnection(urlDatabase,userName,password);
            statement = database.createStatement();
        }catch (SQLException e){e.printStackTrace();}
    }
    */

    private static final DatabaseManager instance = new DatabaseManager();

    //private  static final DatabaseManager instanceTest = new DatabaseManager(true);

    public static DatabaseManager getInstance() {return instance;}

    //public static DatabaseManager getInstanceTest() {return instanceTest;}

    //Permet d'obtenir l'ensemble des messages d'une conversation entre deux utilisateurs
    public synchronized ResultSet getMessages(int idUserLocal, int idUserDistant) throws SQLException{
        String sql = "SELECT date,sentBy,message FROM message AS m JOIN conversation AS c ON c.idConversation=m.convId AND c.userId1=? AND c.userId2=?;";
        PreparedStatement retriveMessage = database.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        int minId = Math.min(idUserLocal,idUserDistant);
        int maxId = Math.max(idUserLocal,idUserDistant);
        retriveMessage.setInt(1,minId);
        retriveMessage.setInt(2,maxId);
        ResultSet result = retriveMessage.executeQuery();
        return result;
    }

    //TODO appeller quand nouvel utilisateur se connecte (insère dans la database l'userId s'il n'existe pas déjà)
    public synchronized  void insertNewUser(int idUser) throws  SQLException{
        statement.execute("INSERT INTO user SELECT " + idUser + " WHERE NOT EXISTS(SELECT * FROM user where idUser=" + idUser +");");
    }

    //Permet d'insérer un message dans la database, elle crée aussi la conversation si elle n'existe pas déjà
    public synchronized  void insertMessage(int idUserLocal, int idUserDistant, String message, int sentBy) throws SQLException{

        int minId= Math.min(idUserLocal,idUserDistant);
        int maxId= Math.max(idUserLocal,idUserDistant);
        int convId;

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        ResultSet convAlreadyExist = statement.executeQuery("SELECT * FROM conversation WHERE userId1=" + minId + " AND userId2=" + maxId + ";");
        ResultSet  convIdQuerry;


        if(!convAlreadyExist.next()){
                statement.execute("INSERT INTO conversation  (userId1,userId2) VALUES (" + minId + "," + maxId +");");
        }

        convAlreadyExist.close();

        convIdQuerry = statement.executeQuery("SELECT idConversation FROM conversation as c WHERE c.userId1=" + minId + " AND userId2=" + maxId + ";");
        convIdQuerry.next();
        convId = convIdQuerry.getInt("idconversation");
        convIdQuerry.close();

        String sql = "INSERT INTO message (convId,date,sentBy,Message) VALUES (?,?,?,?);";
        PreparedStatement pst = database.prepareStatement(sql);
        pst.setInt(1,convId);
        pst.setTimestamp(2,timestamp);
        pst.setInt(3,sentBy);
        pst.setString(4,message);
        pst.executeUpdate();
        pst.close();
    }

    public synchronized  void insertMessage(int idUserLocal, int idUserDistant, String message,Timestamp timestamp, int sentBy) throws SQLException{

        int minId= Math.min(idUserLocal,idUserDistant);
        int maxId= Math.max(idUserLocal,idUserDistant);
        int convId;

        ResultSet convAlreadyExist = statement.executeQuery("SELECT * FROM conversation WHERE userId1=" + minId + " AND userId2=" + maxId + ";");
        ResultSet  convIdQuerry;


        if(!convAlreadyExist.next()){
            statement.execute("INSERT INTO conversation  (userId1,userId2) VALUES (" + minId + "," + maxId +");");
        }

        convAlreadyExist.close();

        convIdQuerry = statement.executeQuery("SELECT idConversation FROM conversation as c WHERE c.userId1=" + minId + " AND userId2=" + maxId + ";");
        convIdQuerry.next();
        convId = convIdQuerry.getInt("idconversation");
        convIdQuerry.close();

        String sql = "INSERT INTO message (convId,date,sentBy,Message) VALUES (?,?,?,?);";
        PreparedStatement pst = database.prepareStatement(sql);
        pst.setInt(1,convId);
        pst.setTimestamp(2,timestamp);
        pst.setInt(3,sentBy);
        pst.setString(4,message);
        pst.executeUpdate();
        pst.close();
    }
    public synchronized boolean isInDatabase(int userId) throws  SQLException{
        ResultSet resultat = statement.executeQuery("SELECT idUser FROM user WHERE idUser=" + userId + ";");
        if(!resultat.next()){
            resultat.close();
            return false;
        }else {
            resultat.close();
            return true;
        }
    }

    public synchronized void clearDatabase() throws  SQLException{
        statement.execute("DELETE FROM message");
        statement.execute("DELETE FROM conversation");
        statement.execute("DELETE FROM user");
    }
}
