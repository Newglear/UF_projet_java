package com.example.javafxtest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class User {

    @FXML
    private Label username;
    @FXML
    private Label id;
    @FXML
    private Label lastMessage;

    public Label getUsername(){return username;}

    public Label getId(){return id;}

    public Label getLastMessage(){return lastMessage;}
}
