package com.example.javafxtest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SentMessage {

    @FXML
    private Label messageSent;

    public Label getText(){return messageSent;}

}
