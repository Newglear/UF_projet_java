package chavardage.GUI;

import chavardage.chavardageManager.AlreadyUsedPseudoException;
import chavardage.chavardageManager.ChavardageManager;
import chavardage.chavardageManager.UsurpateurException;
import chavardage.database.DatabaseManager;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.pattern.PlainTextRenderer;

public class LogIn {

    private static final Logger LOGGER = LogManager.getLogger(Loged.class);

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
        LOGGER.trace("J'essaye de me connecter");
        checkLogin();
    }

    public void refreshAffichage(){
        error.setText("");
        attente.setText("Connexion en cours veuillez patienter");
    }
    protected void checkLogin() throws Exception {
        // TODO message "en attente de connexion"

        if(username.getText().isEmpty() || id.getText().isEmpty()){
            LOGGER.error("Le pseudo ou l'id rentrée est vide");
            error.setText("Veuillez saisir un Username et votre ID");
            return;
        }
        if(username.getText().length()>15){
            LOGGER.error("Le pseudo rentré est trop long");
            error.setText("Username trop long (Max 15 caractères)");
            return;
        }
        try {

            int idUser = Integer.parseInt(id.getText());
            if(idUser<1){
                LOGGER.error("L'id rentrée est négatif");
                error.setText("Veuillez saisir un id positif");
                return;
            }
            if(databaseManager.pseudoAlreadyInDB(username.getText(),idUser)){
                LOGGER.error("Le pseudo appartient à un auter user");
                error.setText("Le pseudo appartient à un auter user");
                return;
            }
            LOGGER.debug("Le format des infos rentrées sont valide");
            String pseudo = username.getText();
            ChavardageManager chavardageManager = ChavardageManager.getInstance();
            ListeUser listeUser = ListeUser.getInstance();
            listeUser.setMyself(idUser,pseudo);
            //refreshAffichage();//TODO gérer le problème
            LOGGER.trace("Je me connecte sur le réseau");
            chavardageManager.connectToApp(new UserItem(idUser,pseudo));
            databaseManager.insertNewUser(idUser,pseudo); // insère dans DB si existe pas déjà
            LOGGER.trace("Je suis connecté");
            Main m = new Main();
            m.loggedScene();

        } catch (NumberFormatException e){
            LOGGER.error("L'id n'est pas un nombre");
            error.setText("Veuillez saisir un nombre valide pour l'ID");
        }
        catch (InterruptedException e) {}
        catch (UsurpateurException e) {
            LOGGER.error("L'id est déjà utilisé sur le réseau");
            error.setText("Cet ID est déjà utilisé par un autre User connecté");
            ListeUser.getInstance().clear();
        }
        catch (AlreadyUsedPseudoException e) {
            LOGGER.error("Le pseudo est déjà utilisé sur le réseau");
            error.setText("Ce pseudo est déjà utilisé par un autre User connecté");
            ListeUser.getInstance().clear();
        }
    }

    public Label getAttente() {
        return attente;
    }
}
