package com.example.javafxtest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SentMessage {

    @FXML
    private Label messageSent;

    @FXML
    private Label sentDate;

    public Label getText(){return messageSent;}

    public Label getDate(){return sentDate;}
}
