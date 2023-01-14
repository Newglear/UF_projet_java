package chavardage.GUI;

import chavardage.AssignationProblemException;
import chavardage.chavardageManager.AlreadyUsedPseudoException;
import chavardage.chavardageManager.ChavardageManager;
import chavardage.conversation.Conversation;
import chavardage.conversation.ConversationAlreadyExists;
import chavardage.conversation.ConversationManager;
import chavardage.database.DatabaseManager;
import chavardage.message.TCPMessage;
import chavardage.userList.ListeUser;
import chavardage.userList.UserItem;
import chavardage.userList.UserNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

public class Loged implements Consumer<UserItem> {
    private static final Logger LOGGER = LogManager.getLogger(Loged.class);

    @FXML
    private Button validButton;
    @FXML
    private TextField textChangementPsd;
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
    @FXML
    private Label errorMessage;
    @FXML
    private Label errorChangePseudo;

    private Map<Integer,User> userControllerMap = Collections.synchronizedMap(new HashMap<>());
    private DatabaseManager databaseManager = DatabaseManager.getInstance();

    private Loged(){
    }

    private static Loged instance = new Loged();
    public static Loged getInstance(){
        return instance;
    }
    public static boolean textSendActive = false;
    private ListeUser listeUser = ListeUser.getInstance();

    private int destinataireId;


    public Label getUsername(){return username;}
    public void addUserConnected(String Pseudo, int id) {
        try{

            FXMLLoader userLoader = new FXMLLoader(getClass().getResource("user.fxml"));
            User controllerUser = new User();
            userLoader.setController(controllerUser);
            Node userConnected = userLoader.load();
            controllerUser.getUsername().setText(Pseudo);
            controllerUser.getId().setText("#" + Integer.toString(id));
            controllerUser.getLastMessage().setText(getLastMessage(id));
            synchronized (this){
                userControllerMap.put(id,controllerUser);
            }
            userConnected.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        ConversationManager.getInstance().openConversation(id);
                    } catch (UserNotFoundException | AssignationProblemException | ConversationAlreadyExists ignored) {
                        ignored.printStackTrace();
                    }
                    try {
                        vboxChat.getChildren().clear();
                        afficherAncienMessages(databaseManager.getMessages(listeUser.getMyId(),id));
                        textSend.setVisible(true);
                        textSend.setDisable(false);
                        sendButton.setVisible(true);
                        sendButton.setDisable(false);
                        textSendActive = true;
                        userDest.setText(Pseudo);
                        destinataireId = id;
                    }catch (Exception e){e.printStackTrace();}
                }
            });
            vboxConnect.getChildren().add(userConnected);
        }catch (Exception e){e.printStackTrace();}
    }

    public void unFocusTextArea(){
        textSend.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                vboxChat.requestFocus();
            }
        });
    }
    public void deleteUserConnected(int id){

        for (Node child : vboxConnect.getChildren()) {
            Label label = (Label)child.lookup("#id");
            if(label.getText().equals("#"+ id)) {
                vboxConnect.getChildren().remove(child);
                break;
            }
        }
        userControllerMap.remove(id);
    }

    public void changePseudoUser(int idUser, String newPseudo){
        for (Node child : vboxConnect.getChildren()) {
            Label pseudoUser = (Label)child.lookup("#id");
            if (pseudoUser.getText().equals("#"+idUser )){
                pseudoUser.setText(newPseudo);
                break;
            }
        }
    }

    public void disconnect(ActionEvent event) throws Exception {
        ConversationManager.getInstance().closeAll();
        ChavardageManager.getInstance().disconnect(listeUser.getMySelf());
        Main m = new Main();
        m.loginScene();

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
        listeMessages.close();
    }

    public String getLastMessage(int idUserConnected) throws Exception{
        ResultSet listeMessage = databaseManager.getMessages(listeUser.getMyId(),idUserConnected);
        if(listeMessage.last()){
            if(listeMessage.getInt("sentBy")==listeUser.getMyId()) {
                return ("You : " + listeMessage.getString("message"));
            }else {
                return(listeMessage.getString("message"));
            }
        }else{
            return "";
        }
    }

    public void envoyerMessage(ActionEvent buttonPressed){
        envoiMessage();
    }
    public void envoiMessage(){
        try{

            String message = textSend.getText();

            if(message.length() == 0){
                errorMessage.setText("Veuillez saisir un message non vide.");
                return;
            }
            if(message.length() > databaseManager.messageLengthMax){
                errorMessage.setText("Le message est trop long (maximum " + databaseManager.messageLengthMax + ").");
                return;
            }

            errorMessage.setText("");

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();


            textSend.setText("");
            SentMessage controllerMessage = new SentMessage();
            FXMLLoader loaderEnvoi = new FXMLLoader(getClass().getResource("sentMessage.fxml"));


            loaderEnvoi.setController(controllerMessage);

            Node messageEnvoye = loaderEnvoi.load();

            controllerMessage.getText().setText(message);
            controllerMessage.getDate().setText(dateFormat.format(date));

            HBox hbox = new HBox(messageEnvoye);
            hbox.setAlignment(Pos.BASELINE_RIGHT);

            ConversationManager.getInstance().getSendData(destinataireId).envoyer(new TCPMessage(destinataireId,ListeUser.getInstance().getMyId(), message));

            databaseManager.insertMessage(listeUser.getMyId(),destinataireId,message,listeUser.getMyId());
            User userController = userControllerMap.get(destinataireId);
            userController.getLastMessage().setText("You : " + message);
            vboxChat.getChildren().add(hbox);

        }catch (Exception e){e.printStackTrace();}

    }

    public void messageRecu(TCPMessage tcpMessage){
        try{
            if (tcpMessage.getEnvoyeurId()!=destinataireId) return;
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();
            String message=tcpMessage.getTexte();

            ReceiveMessage controllerMessage = new ReceiveMessage();
            FXMLLoader loaderReceive = new FXMLLoader(getClass().getResource("receiveMessage.fxml"));
            loaderReceive.setController(controllerMessage);

            Node messageReceive = loaderReceive.load();

            controllerMessage.getText().setText(message);
            controllerMessage.getDate().setText(dateFormat.format(date));

            User userController = userControllerMap.get(destinataireId);
            userController.getLastMessage().setText(message);

            HBox hbox = new HBox(messageReceive);
            HBox.setHgrow(messageReceive,Priority.NEVER);

            vboxChat.getChildren().add(hbox);

        }catch (Exception e){e.printStackTrace();}
    }

    public void changePseudo(){
        pseudoChange.setDisable(true);
        deco.setDisable(true);
        pseudoChange.setVisible(false);
        deco.setVisible(false);
        textChangementPsd.setVisible(true);
        textChangementPsd.setDisable(false);
        validButton.setVisible(true);
        validButton.setDisable(false);

    }

    public void validerChangement(ActionEvent event){
        String newPseudo = textChangementPsd.getText();
        if(newPseudo.length() == 0){
            errorChangePseudo.setText("Veuillez saisir un pseudo non vide");
            return;
        }
        if(newPseudo.length() >15){
            errorChangePseudo.setText("Ce pseudo est trop long (Max 15)");
            return;
        }
        try{
            listeUser.setMyPseudo(newPseudo);
            username.setText(newPseudo);
            ChavardageManager.getInstance().notifyChangePseudo(listeUser.getMySelf());
            errorChangePseudo.setText("");
            deco.setDisable(false);
            pseudoChange.setDisable(false);
            pseudoChange.setVisible(true);
            deco.setVisible(true);
            textChangementPsd.setVisible(false);
            textChangementPsd.setDisable(true);
            validButton.setVisible(false);
            validButton.setDisable(true);
        } catch (AlreadyUsedPseudoException e) {
            errorChangePseudo.setText("Ce pseudo est déjà utilisé");
        }
    }

    @Override
    public void accept(UserItem userItem) {
        LOGGER.trace("j'accepte " + userItem);
        switch (userItem.getNotifyFront()){
            case AddUser:
                addUserConnected(userItem.getPseudo(),userItem.getId());
                break;
            case DeleteUser:
                deleteUserConnected(userItem.getId());
                break;
            case ChangePseudo:
                changePseudoUser(userItem.getId(),userItem.getPseudo());
                break;
        }
    }


}
