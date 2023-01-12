package chavardage.GUI;

import chavardage.userList.ListeUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stg;

    private ListeUser listeUser = ListeUser.getInstance();
    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 680, 400);
        stage.setTitle("Chavardage");
        stage.setScene(scene);
        stage.show();
    }

    //public void setHandler()
    public void loggedScene() throws Exception{
        FXMLLoader loggedLoader = new FXMLLoader(getClass().getResource("loged.fxml"));
        Loged logedController = new Loged();
        loggedLoader.setController(logedController);
        Scene newScene = new Scene(loggedLoader.load(),1300,700);
        logedController.getUsername().setText(listeUser.getMyPseudo());
        stg.setScene(newScene);
        stg.setTitle("Test");
        stg.show();
    }

    public static void main(String[] args) {
        launch();
    }
}