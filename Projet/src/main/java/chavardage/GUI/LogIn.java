package chavardage.GUI;

import chavardage.chavardageManager.AlreadyUsedPseudoException;
import chavardage.chavardageManager.ChavardageManager;
import chavardage.chavardageManager.UsurpateurException;
import chavardage.conversation.ConversationManager;
import chavardage.database.DatabaseManager;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LogIn {


    @FXML
    private Label Error;
    @FXML
    private Button  boutton;
    @FXML
    private TextField username;
    @FXML
    private TextField id;

    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    public void userLogin(ActionEvent event) throws Exception{
        checkLogin();
    }

    protected void checkLogin() throws Exception {
        // TODO message "en attente de connexion"
        Main m = new Main();
        if(username.getText().isEmpty() || id.getText().isEmpty()){
            Error.setText("Veuillez saisir un Username et votre ID");
        }
        else {
            try {
                if(username.getText().length()>15){
                    Error.setText("Username trop long (Max 15 caractères)");
                }else {
                    int idUser = Integer.parseInt(id.getText());
                    String pseudo = username.getText();
                    ChavardageManager chavardageManager = ChavardageManager.getInstance();
                    ListeUser listeUser = ListeUser.getInstance();
                    listeUser.setMyself(idUser,pseudo);
                    try{
                        chavardageManager.connectToApp(new UserItem(idUser,pseudo));
                    } catch (InterruptedException e) {
                    } catch (UsurpateurException e) {
                        Error.setText("Cet ID est déjà utilisé par un autre User connecté");
                    } catch (AlreadyUsedPseudoException e) {
                        Error.setText("Ce pseudo est déjà utilisé par un autre User connecté");
                    }
                    databaseManager.insertNewUser(idUser); // insère dans DB si existe pas déjà
                    m.loggedScene();
                }
            }catch (NumberFormatException e){
                Error.setText("Veuillez saisir un nombre valide pour l'ID");

            }
        }



    }
}
