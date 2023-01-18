package chavardage.GUI;

import chavardage.AssignationProblemException;
import chavardage.chavardageManager.AlreadyUsedPseudoException;
import chavardage.chavardageManager.ChavardageManager;
import chavardage.conversation.ConversationAlreadyExists;
import chavardage.conversation.ConversationManager;
import chavardage.database.DatabaseManager;
import chavardage.message.TCPMessage;
import chavardage.userList.ListeUser;
import chavardage.userList.SamePseudoAsOld;
import chavardage.userList.UserItem;
import chavardage.userList.UserNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;
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
    @FXML
    private ScrollPane scrollPaneMessage;
    private Map<Integer,User> userControllerMap = Collections.synchronizedMap(new HashMap<>());
    private Map<Integer,EventHandler> eventHandlerMap = Collections.synchronizedMap(new HashMap<>());

    private DatabaseManager databaseManager = DatabaseManager.getInstance();

    private Loged(){
    }

    private static Loged instance = new Loged();
    public static Loged  getInstance(){
        return instance;
    }
    public static boolean textSendActive = false;
    private ListeUser listeUser = ListeUser.getInstance();

    private int destinataireId;

    public Label getUsername(){return username;}
    public void addUserConnected(String Pseudo, int id) {
        if (userControllerMap.containsKey(id)) return; // si l'user est déjà affiché, on fait rien
        try{
            FXMLLoader userLoader = new FXMLLoader(getClass().getResource("user.fxml"));
            User controllerUser = new User();
            userLoader.setController(controllerUser);
            Node userConnected = userLoader.load();
            controllerUser.getUsername().setText(Pseudo);
            controllerUser.getId().setText("#" + id);
            controllerUser.getLastMessage().setText(getLastMessage(id));
            synchronized (this){
                userControllerMap.put(id,controllerUser);
            }
            EventHandler handlerConnected = event -> {
                if(destinataireId != id){
                    try {
                        ConversationManager.getInstance().openConversation(id);
                    } catch (UserNotFoundException | AssignationProblemException e) {
                        e.printStackTrace();
                    } catch (ConversationAlreadyExists ignored) { // si l'autre en face avait ouvert la conversation déjà
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
                        controllerUser.getCircleNotification().setVisible(false);
                        controllerUser.getNbNotification().setText("0");
                        controllerUser.getNbNotification().setVisible(false);
                    }catch (Exception e){e.printStackTrace();}
                }
            };
            userConnected.addEventFilter(MouseEvent.MOUSE_CLICKED,handlerConnected);
            eventHandlerMap.put(id,handlerConnected);
            vboxConnect.getChildren().add(userConnected);
        }catch (Exception e){e.printStackTrace();}
    }
    public void addUserDisconnected(String pseudo, int id){
        try {
            FXMLLoader userLoader = new FXMLLoader(getClass().getResource("user.fxml"));
            User controllerUser = new User();
            userLoader.setController(controllerUser);
            Node userDisconnected = userLoader.load();
            controllerUser.getUsername().setText(pseudo);
            controllerUser.getId().setText("#" + id);
            controllerUser.getConnexionState().setFill(Color.rgb(89,89,89));
            controllerUser.getLastMessage().setText(getLastMessage(id));
            userControllerMap.put(id,controllerUser);
            EventHandler handlerDisconnected = event -> {
                try {
                    vboxChat.getChildren().clear();
                    afficherAncienMessages(databaseManager.getMessages(listeUser.getMyId(),id));
                    userDest.setText(pseudo);
                    destinataireId = id;
                    controllerUser.getCircleNotification().setVisible(false);
                    controllerUser.getNbNotification().setText("0");
                    controllerUser.getNbNotification().setVisible(false);
                }catch (Exception e){e.printStackTrace();}
            };
            userDisconnected.addEventFilter(MouseEvent.MOUSE_CLICKED,handlerDisconnected);
            eventHandlerMap.put(id,handlerDisconnected);
            vboxConnect.getChildren().add(userDisconnected);
        }catch (Exception e){e.printStackTrace();}
    }

    public void newUserConnected(int id,String pseudo){
        for (Node child : vboxConnect.getChildren()) {
            Label label = (Label)child.lookup("#id");
            if(label.getText().equals("#" + id)){
                switchToConnected(id,pseudo,child);
                return;
            }
        }
        addUserConnected(pseudo,id);
    }

    public void afficherDisconnectedUser(){
        try{
            LOGGER.debug("Je suis dans l'affichage du disconnect");
            Boolean found = false;
            ResultSet leftUsers = databaseManager.getOldConvLeftUser(listeUser.getMyId());
            ResultSet rightUsers = databaseManager.getOldConvRightUser(listeUser.getMyId());

            while(leftUsers.next()){
                LOGGER.debug("Je suis dans l'affichage du leftUser");
                int idUser = leftUsers.getInt("userId1");
                String pseudoUser = leftUsers.getString("pseudo");
                for (Node child : vboxConnect.getChildren()) {
                    Label label = (Label)child.lookup("#id");
                    if(label.getText().equals("#" + idUser)){
                        found = true;
                        break;
                    }
                }
                if(!found){
                    addUserDisconnected(pseudoUser,idUser);
                }
            }

            found = false;

            while(rightUsers.next()){
                LOGGER.debug("Je suis dans l'affichage du RightUser");
                int idUser = rightUsers.getInt("userId2");
                String pseudoUser = rightUsers.getString("pseudo");
                addUserDisconnected(pseudoUser,idUser);
                for (Node child : vboxConnect.getChildren()) {
                    Label label = (Label)child.lookup("#id");
                    if(label.getText().equals("#" + idUser)){
                        found = true;
                        break;
                    }
                }
                if(!found){
                    addUserDisconnected(pseudoUser,idUser);
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void switchToConnected(int id, String pseudo, Node child){
        if(destinataireId==id){
            try{
                ConversationManager.getInstance().openConversation(id);
                textSend.setVisible(true);
                textSend.setDisable(false);
                sendButton.setVisible(true);
                sendButton.setDisable(false);
            }catch (Exception e){e.printStackTrace();}
        }
        User controllerUser = userControllerMap.get(id);
        controllerUser.getConnexionState().setFill(Color.rgb(46,166,22));
        controllerUser.getUsername().setText(pseudo);
        textSendActive = true;
        child.removeEventFilter(MouseEvent.MOUSE_CLICKED,eventHandlerMap.get(id));
        EventHandler handlerConnected = new EventHandler() {
            @Override
            public void handle(Event event) {
                if (destinataireId != id) {
                    try {
                        ConversationManager.getInstance().openConversation(id);
                    } catch (UserNotFoundException | AssignationProblemException e) {
                        e.printStackTrace();
                    } catch (
                            ConversationAlreadyExists ignored) { // si l'autre en face avait ouvert la conversation déjà
                    }
                    try {
                        vboxChat.getChildren().clear();
                        afficherAncienMessages(databaseManager.getMessages(listeUser.getMyId(), id));
                        textSend.setVisible(true);
                        textSend.setDisable(false);
                        sendButton.setVisible(true);
                        sendButton.setDisable(false);
                        textSendActive = true;
                        userDest.setText(pseudo);
                        destinataireId = id;
                        controllerUser.getCircleNotification().setVisible(false);
                        controllerUser.getNbNotification().setText("0");
                        controllerUser.getNbNotification().setVisible(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        child.addEventFilter(MouseEvent.MOUSE_CLICKED, handlerConnected);
        eventHandlerMap.replace(id,handlerConnected);
    }

    public void switchToDisconnected(int id, String pseudo){
        if (destinataireId == id){
            textSend.setVisible(false);
            textSend.setDisable(true);
            sendButton.setVisible(false);
            sendButton.setDisable(true);
        }
        User controllerUser = userControllerMap.get(id);
        controllerUser.getConnexionState().setFill(Color.rgb(89,89,89));
        textSendActive = false;

        for (Node child : vboxConnect.getChildren()) {
            Label label = (Label)child.lookup("#id");
            if(label.getText().equals("#"+ id)) {
                EventHandler handlerDisconnected = new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        if(destinataireId != id){
                            try {
                                vboxChat.getChildren().clear();
                                afficherAncienMessages(databaseManager.getMessages(listeUser.getMyId(),id));
                                userDest.setText(pseudo);
                                destinataireId = id;
                                controllerUser.getCircleNotification().setVisible(false);
                                controllerUser.getNbNotification().setText("0");
                                controllerUser.getNbNotification().setVisible(false);
                            }catch (Exception e){e.printStackTrace();}
                        }
                    }
                };
                child.removeEventFilter(MouseEvent.MOUSE_CLICKED,eventHandlerMap.get(id));
                child.addEventFilter(MouseEvent.MOUSE_CLICKED, handlerDisconnected);
                eventHandlerMap.replace(id,handlerDisconnected);
                break;
            }
        }
    }

    public void unFocusTextArea(){
        textSend.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                vboxChat.requestFocus();
            }
        });
    }
    public void deleteUserConnected(int id, String pseudo){
        try{
            if(databaseManager.convExists(listeUser.getMyId(),id)){
                switchToDisconnected(id,pseudo);
                return;
            }
        }catch (Exception e){e.printStackTrace();}

        for (Node child : vboxConnect.getChildren()) {
            Label label = (Label)child.lookup("#id");
            if(label.getText().equals("#"+ id)) {
                vboxConnect.getChildren().remove(child);
                break;
            }
        }
        vboxChat.getChildren().clear();
        textSend.setDisable(true);
        textSend.setVisible(false);
        sendButton.setDisable(true);
        sendButton.setVisible(false);
        userDest.setText("");
        userControllerMap.remove(id);

    }

    public void changePseudoUser(int idUser, String newPseudo){
        if(idUser == destinataireId){
            userDest.setText(newPseudo);
        }

        for (Node child : vboxConnect.getChildren()) {
            Label idUserConnected = (Label)child.lookup("#id");
            if (idUserConnected.getText().equals("#"+idUser )){
                Label pseudoUser = (Label) child.lookup("#username");
                pseudoUser.setText(newPseudo);
                break;
            }
        }
    }

    public void disconnect(ActionEvent event) throws Exception {
        ConversationManager.getInstance().closeAll();
        ChavardageManager.getInstance().disconnect(listeUser.getMySelf());
        userControllerMap.clear();
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
            scrollPaneMessage.applyCss();
            scrollPaneMessage.layout();
            scrollPaneMessage.setVvalue(scrollPaneMessage.getVmax());
        }catch (Exception e){e.printStackTrace();}

    }

    public void messageRecu(TCPMessage tcpMessage){
        try{
            if (tcpMessage.getEnvoyeurId()!=destinataireId) {
                for (Node child : vboxConnect.getChildren()) {
                    Label idUser = (Label)child.lookup("#id");
                    if (idUser.getText().equals("#"+tcpMessage.getEnvoyeurId())){
                        Label lastMessage = (Label)child.lookup("#lastMessage");
                        Label nbNotification = (Label) child.lookup("#nbNotification");
                        Circle circleNotif = (Circle) child.lookup("#circleNotification");
                        lastMessage.setText(tcpMessage.getTexte());
                        int nombreNotif = Integer.parseInt(nbNotification.getText());
                        if(nombreNotif == 9){
                            nbNotification.setText("9+");
                        }else {
                            nbNotification.setText(Integer.toString(nombreNotif+1));
                        }
                        nbNotification.setVisible(true);
                        circleNotif.setVisible(true);
                        break;
                    }
                }
            }
            else {
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
                scrollPaneMessage.applyCss();
                scrollPaneMessage.layout();
                scrollPaneMessage.setVvalue(scrollPaneMessage.getVmax());
            }
        }catch (AssignationProblemException | IOException e){e.printStackTrace();}
        catch (NumberFormatException e){
            //ne rien faire car notif > 9
        }
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
            if(databaseManager.pseudoAlreadyInDB(newPseudo)){
                errorChangePseudo.setText("Ce pseudo est le dernier pseudo d'un autre user");
                return;
            }
            listeUser.setMyPseudo(newPseudo);
            databaseManager.modifyPseudo(listeUser.getMyId(),listeUser.getMyPseudo());
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
        } catch (SamePseudoAsOld samePseudoAsOld) {
            errorChangePseudo.setText("veuillez entrer un autre pseudo que votre ancien");
        } catch (Exception e) { e.printStackTrace();}
    }

    @Override
    public void accept(UserItem userItem) {
        LOGGER.trace("j'accepte " + userItem);
        switch (userItem.getNotifyFront()){
            case AddUser:
                newUserConnected(userItem.getId(),userItem.getPseudo());
                break;
            case DeleteUser:
                deleteUserConnected(userItem.getId(),userItem.getPseudo());
                break;
            case ChangePseudo:
                changePseudoUser(userItem.getId(),userItem.getPseudo());
                break;
        }
    }
}
