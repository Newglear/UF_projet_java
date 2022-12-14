package com.example.javafxtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LogIn {

    public LogIn(){}

    @FXML
    private Label Error;
    @FXML
    private Button  boutton;
    @FXML
    private TextField username;
    @FXML
    private TextField id;

    public void userLogin(ActionEvent event) throws IOException{
        checkLogin();
    }

    private void checkLogin() throws IOException {
        Main m = new Main();
        if(username.getText().isEmpty() || id.getText().isEmpty()){
            Error.setText("Veuillez saisir un Username et votre ID");
        }
        else {
            try {
                int idUser = Integer.parseInt(id.getText());
                m.changeScene("loged.fxml");
            }catch (NumberFormatException e){
                Error.setText("Veuillez saisir un nombre valide pour l'ID");

            }
        }



    }
}
