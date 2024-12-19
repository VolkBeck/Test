package com.volkerbecker.hdifferenz;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GlowButton extends Button {
    private final Timeline timeline;
    private final DropShadow glow;

    public GlowButton(String text) {
        super(text);



        // Basis-Styling des Buttons
        setStyle("-fx-background-color: #111111; " +
                "-fx-text-fill: white; " +
                "-fx-min-width: 100px; " +
                "-fx-min-height: 25px; " +
                "-fx-background-radius: 10px;");

        // Glow-Effekt erstellen
        glow = new DropShadow();
        glow.setRadius(5);
        glow.setSpread(0.5);
        setEffect(glow);

        // Timeline für die Farbanimation
        timeline = new Timeline(
                // Rot
                new KeyFrame(Duration.ZERO,
                        new KeyValue(glow.colorProperty(), Color.RED)),
                // Orange
                new KeyFrame(Duration.seconds(0.2),
                        new KeyValue(glow.colorProperty(), Color.rgb(255, 115, 0))),
                // Gelb
                new KeyFrame(Duration.seconds(0.4),
                        new KeyValue(glow.colorProperty(), Color.rgb(255, 251, 0))),
                // Grün
                new KeyFrame(Duration.seconds(0.6),
                        new KeyValue(glow.colorProperty(), Color.rgb(72, 255, 0))),
                // Cyan
                new KeyFrame(Duration.seconds(0.8),
                        new KeyValue(glow.colorProperty(), Color.rgb(0, 255, 213))),
                // Blau
                new KeyFrame(Duration.seconds(1.0),
                        new KeyValue(glow.colorProperty(), Color.rgb(0, 43, 255))),
                // Lila
                new KeyFrame(Duration.seconds(1.2),
                        new KeyValue(glow.colorProperty(), Color.rgb(122, 0, 255))),
                // Pink
                new KeyFrame(Duration.seconds(1.4),
                        new KeyValue(glow.colorProperty(), Color.rgb(255, 0, 200))),
                // Zurück zu Rot
                new KeyFrame(Duration.seconds(1.6),
                        new KeyValue(glow.colorProperty(), Color.RED))
        );

        timeline.setCycleCount(Timeline.INDEFINITE);

        // Glow-Effekt nur beim Hover aktivieren
        glow.setColor(Color.TRANSPARENT);

        setOnMouseEntered(e -> {
            timeline.play();
            glow.setColor(Color.RED);
        });

        setOnMouseExited(e -> {
            timeline.stop();
            glow.setColor(Color.TRANSPARENT);
        });

        // Klick-Effekt
        setOnMousePressed(e -> setStyle("-fx-background-color: Transparent; " +
                "-fx-text-fill: black; " +
                "-fx-min-width: 100px; " +
                "-fx-min-height: 25px; " +
                "-fx-background-radius: 10px;"));

        setOnMouseReleased(e -> setStyle("-fx-background-color: #111111; " +
                "-fx-text-fill: white; " +
                "-fx-min-width: 100px; " +
                "-fx-min-height: 25px; " +
                "-fx-background-radius: 10px;"));
    }
}
