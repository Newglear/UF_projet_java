package chavardage.GUI;

import chavardage.chavardageManager.AlreadyUsedPseudoException;
import chavardage.chavardageManager.ChavardageManager;
import chavardage.chavardageManager.UsurpateurException;
import chavardage.database.DatabaseManager;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LogIn {


    @FXML
    private Label error;

    @FXML
    private Label attente;

    @FXML
    private Button  boutton;
    @FXML
    private TextField username;
    @FXML
    private TextField id;

    private DatabaseManager databaseManager = DatabaseManager.getInstance();

    private LogIn(){}

    private static LogIn instance = new LogIn();

    public static LogIn getInstance(){return instance;}
    public void userLogin(ActionEvent event) throws Exception{
        checkLogin();
    }

    protected void checkLogin() throws Exception {
        // TODO message "en attente de connexion"
        Main m = new Main();
        error.setText("");
        if(username.getText().isEmpty() || id.getText().isEmpty()){
            error.setText("Veuillez saisir un Username et votre ID");
        }
        else {
            try {
                if(username.getText().length()>15){
                    error.setText("Username trop long (Max 15 caractères)");
                }else {
                    int idUser = Integer.parseInt(id.getText());
                    String pseudo = username.getText();
                    ChavardageManager chavardageManager = ChavardageManager.getInstance();
                    ListeUser listeUser = ListeUser.getInstance();
                    listeUser.setMyself(idUser,pseudo);
                    try{
                        chavardageManager.connectToApp(new UserItem(idUser,pseudo));
                        databaseManager.insertNewUser(idUser); // insère dans DB si existe pas déjà
                        m.loggedScene();
                    } catch (InterruptedException e) {
                    } catch (UsurpateurException e) {
                        error.setText("Cet ID est déjà utilisé par un autre User connecté");
                    } catch (AlreadyUsedPseudoException e) {
                        error.setText("Ce pseudo est déjà utilisé par un autre User connecté");
                    }

                }
            }catch (NumberFormatException e){
                error.setText("Veuillez saisir un nombre valide pour l'ID");

            }
        }
    }

    public Label getAttente() {
        return attente;
    }
}
