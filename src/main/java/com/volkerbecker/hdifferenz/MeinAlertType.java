package com.volkerbecker.hdifferenz;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public record MeinAlertType(Alert.AlertType type, String headerText, ButtonType[] buttons) {
    public static final MeinAlertType WARNUNG = new MeinAlertType(
            Alert.AlertType.WARNING,
            "Willsch des wirklich?",
            new ButtonType[]{ButtonType.OK}
    );

    public static final MeinAlertType INFO = new MeinAlertType(
            Alert.AlertType.INFORMATION,
            "Wollt dich nur informiere!!!",
            new ButtonType[]{ButtonType.OK}
    );

    public static final MeinAlertType FEHLER = new MeinAlertType(
            Alert.AlertType.ERROR,
            "Ein Fehler ist aufgetreten!!!",
            new ButtonType[]{ButtonType.OK}
    );

}
