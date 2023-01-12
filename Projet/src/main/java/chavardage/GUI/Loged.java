package chavardage.GUI;

import chavardage.database.DatabaseManager;
import chavardage.userList.ListeUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Loged {

    @FXML
    private Label username;
    @FXML
    private Label userDest;
    @FXML
    private TextArea textSend;
    @FXML
    private Button deco;
    @FXML
    private Button pseudoChange;
    @FXML
    private Button sendButton;
    @FXML
    private VBox vboxConnect;
    @FXML
    private VBox vboxChat;

    private DatabaseManager databaseManager = DatabaseManager.getInstance();

    private ListeUser listeUser = ListeUser.getInstance();

    public void addUserConnected(String Pseudo, int id) {
        try{
            FXMLLoader userLoader = new FXMLLoader(getClass().getResource("user.fxml"));
            User controllerUser = new User();
            userLoader.setController(controllerUser);
            Node userConnected = userLoader.load();
            controllerUser.getUsername().setText(Pseudo);
            controllerUser.getId().setText("#" + Integer.toString(id));
            userConnected.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        vboxChat.getChildren().clear();
                        afficherAncienMessages(databaseManager.getMessages(listeUser.getMyId(),id));
                        textSend.setVisible(true);
                        textSend.setDisable(false);
                        sendButton.setVisible(true);
                        sendButton.setDisable(false);
                        userDest.setText(Pseudo);
                    }catch (Exception e){e.printStackTrace();}
                }
            });
            vboxConnect.getChildren().add(userConnected);
        }catch (Exception e){e.printStackTrace();}
    }

    public void disconnect(ActionEvent event) throws IOException {
        //Main m = new Main();
        //m.changeScene("login.fxml",680,400);
        addUserConnected("Romain",3);
        addUserConnected("Aude",2);
    }

    public void afficherAncienMessages(ResultSet listeMessages) throws Exception {
        FXMLLoader loaderSentMessage;
        FXMLLoader loaderReceivedMessage;
        SentMessage controllerSend;
        ReceiveMessage controllerReceive;
        Node messageSent;
        Node messageReceived;
        Date date;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String message;
        while(listeMessages.next()){
            date = listeMessages.getTimestamp("date");
            message = listeMessages.getString("message");
            if(listeMessages.getInt("sentBy") == listeUser.getMyId()){
                loaderSentMessage = new FXMLLoader(getClass().getResource("sentMessage.fxml"));
                controllerSend = new SentMessage();
                loaderSentMessage.setController(controllerSend);
                messageSent = loaderSentMessage.load();
                controllerSend.getDate().setText(dateFormat.format(date));
                controllerSend.getText().setText(message);
                HBox hbox = new HBox(messageSent);
                hbox.setAlignment(Pos.BASELINE_RIGHT);
                vboxChat.getChildren().add(hbox);
            }else {
                loaderReceivedMessage = new FXMLLoader(getClass().getResource("receiveMessage.fxml"));
                controllerReceive = new ReceiveMessage();
                loaderReceivedMessage.setController(controllerReceive);
                messageReceived = loaderReceivedMessage.load();
                controllerReceive.getText().setText(message);
                controllerReceive.getDate().setText(dateFormat.format(date));
                HBox hbox = new HBox(messageReceived);
                hbox.setAlignment(Pos.BASELINE_LEFT);
                vboxChat.getChildren().add(hbox);
            }
        }
    }

    public void envoiMessage(ActionEvent event){
        try{

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();

            String message = textSend.getText();
            textSend.setText("");
            SentMessage controllerMessage = new SentMessage();
            FXMLLoader loaderEnvoi = new FXMLLoader(getClass().getResource("sentMessage.fxml"));


            loaderEnvoi.setController(controllerMessage);

            Node messageEnvoye = loaderEnvoi.load();

            controllerMessage.getText().setText(message);
            controllerMessage.getDate().setText(dateFormat.format(date));

            HBox hbox = new HBox(messageEnvoye);
            hbox.setAlignment(Pos.BASELINE_RIGHT);
            vboxChat.getChildren().add(hbox);

        }catch (Exception e){e.printStackTrace();}

    }

    public void messageRecu(ActionEvent event){
        try{
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();

            String message = textSend.getText();
            textSend.setText("");

            ReceiveMessage controllerMessage = new ReceiveMessage();
            FXMLLoader loaderReceive = new FXMLLoader(getClass().getResource("receiveMessage.fxml"));
            loaderReceive.setController(controllerMessage);

            Node messageReceive = loaderReceive.load();

            controllerMessage.getText().setText(message);
            controllerMessage.getDate().setText(dateFormat.format(date));

            HBox hbox = new HBox(messageReceive);
            HBox.setHgrow(messageReceive,Priority.NEVER);

            vboxChat.getChildren().add(hbox);

        }catch (Exception e){e.printStackTrace();}
    }
}
