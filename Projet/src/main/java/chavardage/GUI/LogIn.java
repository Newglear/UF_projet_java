package chavardage.GUI;

import chavardage.database.DatabaseManager;
import chavardage.userList.ListeUser;
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

    private void checkLogin() throws Exception {
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
                    ListeUser listeUser = ListeUser.getInstance();
                    listeUser.setMyId(idUser);
                    listeUser.setMyPseudo(username.getText());
                    databaseManager.insertNewUser(idUser);
                    m.loggedScene();
                }
            }catch (NumberFormatException e){
                Error.setText("Veuillez saisir un nombre valide pour l'ID");

            }
        }



    }
}
