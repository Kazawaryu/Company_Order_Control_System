package com.example.cs307_proj2_final;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testTXT extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create a pane to hold two players
        GridPane pane = new GridPane();
        pane.setStyle("-fx-border-color: green;");
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);

        // Date format
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        EventHandler<ActionEvent> eventHandler = e -> {
            primaryStage.setTitle(df.format(new Date()));
            System.out.println(df.format(new Date()));
        };

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        // Create a scene
        Scene scene = new Scene(pane, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Starting");
        primaryStage.setResizable(false);
        primaryStage.show();
    }



}
