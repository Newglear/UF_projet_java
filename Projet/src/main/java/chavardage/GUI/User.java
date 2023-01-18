package chavardage.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class User {

    @FXML
    private Label username;
    @FXML
    private Label id;
    @FXML
    private Label lastMessage;
    @FXML
    private AnchorPane background;
    @FXML
    private Label nbNotification;
    @FXML
    private Circle circleNotification;
    @FXML
    private Circle connexionState;

    public Label getUsername(){return username;}

    public Label getId(){return id;}

    public void hover(MouseEvent event){
        background.setStyle("-fx-background-color:#555555");
    }

    public void mouseExited(MouseEvent event){
        background.setStyle("-fx-background-color:#333333");
    }
    public Label getLastMessage(){return lastMessage;}

    public Circle getCircleNotification() {
        return circleNotification;
    }

    public Label getNbNotification() {
        return nbNotification;
    }

    public Circle getConnexionState() {
        return connexionState;
    }
}
