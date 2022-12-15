package com.example.javafxtest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 680, 400);
        stage.setTitle("ClavarChat");
        stage.setScene(scene);
        stage.show();
    }

    public void changeScene(String fxml) throws IOException{
        Scene newScene = new Scene(FXMLLoader.load(getClass().getResource(fxml)),1600,900);
        stg.setScene(newScene);
        stg.setTitle("Test");
        stg.show();
    }

    public static void main(String[] args) {
        launch();
    }
}