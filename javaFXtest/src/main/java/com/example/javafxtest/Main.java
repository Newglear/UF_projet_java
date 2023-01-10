package com.example.javafxtest;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.Key;

public class Main extends Application {

    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 680, 400);
        stage.setTitle("Chavardage");
        stage.setScene(scene);
        stage.show();
    }

    //public void setHandler()
    public void changeScene(String fxml,int lon, int lar ) throws IOException{
        Scene newScene = new Scene(FXMLLoader.load(getClass().getResource(fxml)),lon,lar);
        stg.setScene(newScene);
        stg.setTitle("Test");
        stg.show();
    }

    public static void main(String[] args) {
        launch();
    }
}