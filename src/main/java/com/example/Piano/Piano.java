package com.example.Piano;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Slider;
import javafx.scene.control.ScrollPane;

import javafx.scene.layout.VBox;

import java.util.Objects;


public class Piano extends Application {

    private static final String[] NOTE_NAMES = {"C4","C4#","D4","D4#","E4","F4","F4#","G4","G4#","A4","A4#","B4","C5","C5#","D5","D5#","E5","F5","F5#","G5","G5#","A5","A5#","B5"};
    private static final String[] NOTE_SOUNDS = {"C4.wav","C4#.wav","D4.wav","D4#.wav","E4.wav","F4.wav","F#4.wav","G4 .wav","G#4.wav","A4 .wav","A4#.wav","B4.wav","C5.wav","C5#.wav","D5.wav","D5#.wav","E5.wav","F5.wav","F5#.wav","G5.wav","G5#.wav","A5.wav","A5#.wav","B5.wav"};

    @Override
    public void start(Stage primaryStage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setStyle("-fx-background-color: dark grey;");

        Slider volumeSlider = new Slider(0, 1, 0.5); // Min=0, Max=1, Default=0.5
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(0.1);
        volumeSlider.setBlockIncrement(0.1);
        volumeSlider.setStyle("-fx-padding: 10;");

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true); // Optional: allows dragging to scroll
        scrollPane.setStyle("-fx-background: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        VBox layout = new VBox(10, scrollPane, volumeSlider);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));


        // Define white and black keys separately
        String[] WHITE_KEYS = {"C4", "D4", "E4", "F4", "G4", "A4", "B4",
                "C5", "D5", "E5", "F5", "G5", "A5", "B5"};

        String[] BLACK_KEYS = {"C4#", "D4#", "", "F4#", "G4#", "A4#",
                "", "C5#", "D5#", "", "F5#", "G5#", "A5#"};

        String[] WHITE_SOUNDS = {"C4.wav", "D4.wav", "E4.wav", "F4.wav", "G4.wav", "A4.wav", "B4.wav",
                "C5.wav", "D5.wav", "E5.wav", "F5.wav", "G5.wav", "A5.wav", "B5.wav"};

        String[] BLACK_SOUNDS = {"C4#.wav", "D4#.wav", "", "F4#.wav", "G4#.wav", "A4#.wav",
                "", "C5#.wav", "D5#.wav", "", "F5#.wav", "G5#.wav", "A5#.wav"};
        // Define key bindings for white and black keys
        String[] WHITE_KEY_BINDINGS = {"A", "S", "D", "F", "G", "H", "J",
                "K", "L", ";", "'", "]", "[", "="};

        String[] BLACK_KEY_BINDINGS = {"W", "E", "", "T", "Y", "U",
                "", "O", "P", "", "{", "}", "|"};

        // Add white keys (larger buttons)
        for (int i = 0; i < WHITE_KEYS.length; i++) {
            Button button = new Button(WHITE_KEYS[i]);
            button.setMinSize(80, 200); // Larger size for white keys
            button.setStyle("-fx-background-color: white; -fx-border-color: black;");
            int finalI = i;
            button.setOnAction(event -> {playSound(WHITE_SOUNDS[finalI], volumeSlider.getValue());
                ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
                st.setToX(0.9); // Shrink slightly
                st.setToY(0.9);
                st.setAutoReverse(true);
                st.setCycleCount(2); // Restore after shrinking
                st.play();
            });
            grid.add(button, i, 1); // Place white keys in row 1
        }

        for (int i = 0; i < BLACK_KEYS.length; i++) {
            if (!BLACK_KEYS[i].isEmpty()) {
                Button button = new Button(BLACK_KEYS[i]);
                button.setMinSize(60, 120); // Smaller size for black keys
                button.setStyle("-fx-background-color: black; -fx-text-fill: white;");
                int finalI = i;
                button.setOnAction(event -> {playSound(BLACK_SOUNDS[finalI], volumeSlider.getValue());
                    ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
                    st.setToX(0.9); // Shrink slightly
                    st.setToY(0.9);
                    st.setAutoReverse(true);
                    st.setCycleCount(2); // Restore after shrinking
                    st.play();
                });
                grid.add(button, i, 0); // Place black keys in row 0 (above white keys)
                GridPane.setColumnSpan(button, 2); // Make black keys span two colum
            }
        }



        Scene scene = new Scene(layout, 1000, 650);

        // Add key event listener to the scene
        scene.setOnKeyPressed(event -> {
            String key = event.getText().toUpperCase();

            // Check white keys
            for (int i = 0; i < WHITE_KEY_BINDINGS.length; i++) {
                if (WHITE_KEY_BINDINGS[i].equals(key)) {
                    playSound(WHITE_SOUNDS[i], volumeSlider.getValue());
                }
            }

            // Check black keys
            for (int i = 0; i < BLACK_KEY_BINDINGS.length; i++) {
                if (BLACK_KEY_BINDINGS[i].equals(key) && !BLACK_KEYS[i].isEmpty()) {
                    playSound(BLACK_SOUNDS[i], volumeSlider.getValue());
                }
            }
        });
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        primaryStage.setTitle("Piano");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void playSound(String soundFile, double volume){
        AudioClip sound = new AudioClip(Objects.requireNonNull(getClass().getResource(soundFile)).toExternalForm());
        sound.setVolume(volume); // Apply volume from slider
        sound.play();
    }

    public static  void  main(String[] args ){
        launch(args);
    }
}