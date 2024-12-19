package com.volkerbecker.hdifferenz;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Info extends Alert {

    private static final double IMAGE_SIZE = 100.0;
    private static final String COFFEE_GIF_PATH = "/Bilder/Kaffee.gif";
    private static final String LOGO_PATH = "/Bilder/logo.png";


    public Info(MeinAlertType alertType, String titel, String infotext) {
        super(alertType.type());
        try {

            setHeaderText(alertType.headerText());
            getButtonTypes().setAll(alertType.buttons());

            Image image = new Image(String.valueOf(getClass().getResource(COFFEE_GIF_PATH)));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(IMAGE_SIZE);
            imageView.setFitHeight(IMAGE_SIZE);
            setTitle(titel);
            setContentText(infotext);
            setGraphic(imageView);


            seticon();

        } catch (NullPointerException e) {
            System.err.println("Error loading images: " + e.getMessage());
        }
    }

    private void seticon() {
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        try {
            Image icon = new Image(String.valueOf(getClass().getResource(LOGO_PATH)));
            stage.getIcons().add(icon);
        } catch (NullPointerException e) {
            System.err.println("Error loading window icon: " + e.getMessage());
        }
    }
}
