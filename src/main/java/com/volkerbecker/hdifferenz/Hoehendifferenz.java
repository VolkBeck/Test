package com.volkerbecker.hdifferenz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Hoehendifferenz extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        HoehendifferenzController hoehendifferenzController = new HoehendifferenzController();
        hoehendifferenzController.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(Hoehendifferenz.class.getResource("datenansicht.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1065 , 738);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("Das ist ja die Höhe...");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Bilder/logo.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}