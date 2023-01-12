package com.example.javafxtest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReceiveMessage {

    @FXML
    private Label messageReceive;
    @FXML
    private Label receiveDate;
    public Label getText(){return messageReceive;}

    public Label getDate(){return receiveDate;}

}
