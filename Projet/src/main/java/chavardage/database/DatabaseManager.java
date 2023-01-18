package chavardage.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Date;

public class DatabaseManager {

    private String urlDatabase;
    private String userName;
    private String password;

    private Connection database;

    private Statement statement;

    private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);


    public final int messageLengthMax = 100;
    private DatabaseManager(){
        try {
            urlDatabase = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tp_java4ir_003";
            userName = "tp_java4ir_003";
            password = "theiy5Wo";
            database = DriverManager.getConnection(urlDatabase,userName,password);
            statement = database.createStatement();
            LOGGER.trace("initialisation du database manager réussie");
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }


    private static final DatabaseManager instance = new DatabaseManager();


    public static DatabaseManager getInstance() {return instance;}


    /** Permet d'obtenir l'ensemble des messages d'une conversation entre deux utilisateurs*/
    public synchronized ResultSet getMessages(int idUserLocal, int idUserDistant) throws SQLException{
        String sql = "SELECT date,sentBy,message FROM message AS m JOIN conversation AS c ON c.idConversation=m.convId AND c.userId1=? AND c.userId2=?;";
        PreparedStatement retriveMessage = database.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        int minId = Math.min(idUserLocal,idUserDistant);
        int maxId = Math.max(idUserLocal,idUserDistant);
        retriveMessage.setInt(1,minId);
        retriveMessage.setInt(2,maxId);
        return retriveMessage.executeQuery();
    }

    /** appelée quand nouvel utilisateur se connecte (insère dans la database l'userId s'il n'existe pas déjà)*/
    public synchronized  void insertNewUser(int idUser, String pseudo){
        try {
            statement.execute("INSERT INTO user SELECT " + idUser + ",null WHERE NOT EXISTS(SELECT * FROM user where idUser=" + idUser +");");
            String sql = "UPDATE user SET pseudo = ? WHERE (idUser=?);";
            PreparedStatement pst = database.prepareStatement(sql);
            pst.setString(1,pseudo);
            pst.setInt(2,idUser);
            pst.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void modifyPseudo(int idUser, String pseudo){
        try {
            String sql = "UPDATE user SET pseudo = ? WHERE (idUser=?)";
            PreparedStatement pst = database.prepareStatement(sql);
            pst.setString(1,pseudo);
            pst.setInt(2,idUser);
            pst.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
    /** Permet d'insérer un message dans la database, elle crée aussi la conversation si elle n'existe pas déjà*/
    public synchronized  void insertMessage(int idUserLocal, int idUserDistant, String message, int sentBy){
        try{
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
        }catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

    }

    /** Cette fonction permet de retrouver le pseudo et l'id de toutes les personnes (stocké à droite dans la database)
     avec qui l'id fournit a des conversations ouvertes **/
    public synchronized  ResultSet getOldConvRightUser(int userId) throws  SQLException{
        String sql = "SELECT userId2,pseudo FROM conversation as c JOIN user as u ON c.userId2=u.idUser WHERE c.userId1=?;";
        PreparedStatement pst = database.prepareStatement(sql);
        pst.setInt(1,userId);
        return pst.executeQuery();
    }

    /** Cette fonction permet de retrouver le pseudo et l'id de toutes les personnes (stocké à gauche dans la database)
     avec qui l'id fournit a des conversations ouvertes **/
    public synchronized ResultSet getOldConvLeftUser(int userId) throws SQLException{
        String sql = "SELECT userId1,pseudo FROM conversation as c JOIN user as u ON c.userId1=u.idUser WHERE c.userId2=?;";
        PreparedStatement pst = database.prepareStatement(sql);
        pst.setInt(1,userId);
        return pst.executeQuery();
    }

    public synchronized boolean isInDatabase(int userId) {
        try {
            ResultSet resultat = statement.executeQuery("SELECT idUser FROM user WHERE idUser=" + userId + ";");
            if(!resultat.next()){
                resultat.close();
                return false;
            }else {
                resultat.close();
                return true;
            }
        }catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public synchronized boolean convExists(int idLocal, int idDistant) throws SQLException{
        int minId = Math.min(idLocal,idDistant);
        int maxId = Math.max(idLocal,idDistant);
        ResultSet result;
        result = statement.executeQuery("SELECT idConversation FROM conversation WHERE userId1=" + minId + " AND userId2=" + maxId + ";");
        return (result.next());
    }

    public synchronized boolean pseudoAlreadyInDB(String pseudo) throws SQLException{
        String sql = "SELECT pseudo FROM user WHERE pseudo=?;";
        PreparedStatement pst = database.prepareStatement(sql);
        pst.setString(1,pseudo);
        return(pst.executeQuery().next());
    }
    public synchronized void clearDatabase() {
        try{
            statement.execute("DELETE FROM message");
            statement.execute("DELETE FROM conversation");
            statement.execute("DELETE FROM user");
        }catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
