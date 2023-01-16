package chavardage.GUI;

import chavardage.chavardageManager.GestionUDPMessage;
import chavardage.conversation.ConversationManager;
import chavardage.networkManager.TCPServeur;
import chavardage.networkManager.UDPServeur;
import chavardage.userList.ListeUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main extends Application {

    private static Stage stg;
    private static final Logger LOGGER = LogManager.getLogger(Main.class);




    @Override
    public void start(Stage stage) throws Exception {
        stg = stage;
        stage.setResizable(false);
        loginScene();
    }

    //public void setHandler()
    public void loggedScene() throws Exception{
        FXMLLoader loggedLoader = new FXMLLoader(getClass().getResource("loged.fxml"));
        Loged logedController = Loged.getInstance();
        loggedLoader.setController(logedController);
        Scene newScene = new Scene(loggedLoader.load(),1300,700);
        ListeUser.getInstance().setObserver(logedController);
        logedController.getUsername().setText(ListeUser.getInstance().getMyPseudo());
        newScene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER & Loged.textSendActive){
                logedController.envoiMessage();
            }
        });
        logedController.unFocusTextArea();
        stg.setScene(newScene);
        stg.setTitle("Chavardage");
        stg.show();
    }

    public void loginScene() throws Exception{
        ListeUser.getInstance().clear();
        ConversationManager.getInstance().clear();
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        LogIn loginController = LogIn.getInstance();
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
       //  Configurator.setRootLevel(Level.INFO);


        UDPServeur udpServeur = new UDPServeur(GestionUDPMessage.getInstance());
        TCPServeur tcpServeur = new TCPServeur(ConversationManager.getInstance());

        launch();



    }
}