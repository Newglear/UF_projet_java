package com.example.javafxtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Loged {

    @FXML
    private Label username;
    @FXML
    private Label userDest;
    @FXML
    private TextField textSend;
    @FXML
    private Button deco;
    @FXML
    private Button pseudoChange;
    @FXML
    private Button send;
    @FXML
    private VBox vboxConnect;
    @FXML
    private VBox vboxSent;

    @FXML
    private VBox vboxReceive;



    public void disconnect(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("login.fxml",680,400);
    }

    public void test(ActionEvent event){
        try{
            String message = textSend.getText();
            textSend.setText("");
            SentMessage controllerMessage = new SentMessage();
           // FXMLLoader loaderReceive = new FXMLLoader(getClass().getResource("receiveMessage.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sentMessage.fxml"));

            loader.setController(controllerMessage);
            //loaderReceive.setController(controllerReceive);

            //Node newN = loaderReceive.load();
            Node n = loader.load();

            controllerMessage.getText().setText(message);
            //controllerReceive.getText().setText(message);

            vboxSent.getChildren().add(n);
            //vboxReceive.getChildren().add(newN);


        }catch (Exception e){e.printStackTrace();}

    }

    public void envoiMessage(ActionEvent event){
        try{
            String message = textSend.getText();
            textSend.setText("");
            SentMessage controllerMessage = new SentMessage();
            FXMLLoader loaderEnvoi = new FXMLLoader(getClass().getResource("sentMessage.fxml"));


            loaderEnvoi.setController(controllerMessage);

            Node messageEnvoye = loaderEnvoi.load();

            controllerMessage.getText().setText(message);
            HBox hbox = new HBox();
            hbox.getChildren().add(messageEnvoye);
            hbox.setAlignment(Pos.BASELINE_RIGHT);

            vboxSent.getChildren().add(hbox);
            
        }catch (Exception e){e.printStackTrace();}

    }

    public void messageRecu(ActionEvent event){
        try{
            FXMLLoader loaderReceive = new FXMLLoader(getClass().getResource("receiveMessage.fxml"));

            Node messageReceive = loaderReceive.load();

            vboxSent.getChildren().add(messageReceive);
        }catch (Exception e){e.printStackTrace();}
    }
}
