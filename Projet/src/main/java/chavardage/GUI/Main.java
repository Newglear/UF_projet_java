package chavardage.GUI;

import chavardage.userList.ListeUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stg;

    private ListeUser listeUser = ListeUser.getInstance();
    @Override
    public void start(Stage stage) throws Exception {
        stg = stage;
        stage.setResizable(false);
        loginScene();
    }

    //public void setHandler()
    public void loggedScene() throws Exception{
        FXMLLoader loggedLoader = new FXMLLoader(getClass().getResource("loged.fxml"));
        Loged logedController = new Loged();
        loggedLoader.setController(logedController);
        Scene newScene = new Scene(loggedLoader.load(),1300,700);
        logedController.getUsername().setText(listeUser.getMyPseudo());
        newScene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER & logedController.textSendActive){
                logedController.envoiMessage();
            }
        });
        logedController.unFocusTextArea();
        stg.setScene(newScene);
        stg.setTitle("Chavardage");
        stg.show();
    }

    public void loginScene() throws Exception{
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        LogIn loginController = new LogIn();
        loginLoader.setController(loginController);
        Scene newScene = new Scene(loginLoader.load(),680,400);
        newScene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                try {
                    loginController.checkLogin();
                }catch (Exception e){e.printStackTrace();}
            }
        });
        stg.setScene(newScene);
        stg.setTitle("Chavardage");
        stg.show();
    }
    public static void main(String[] args) {
        launch();
    }
}