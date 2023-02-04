package com.example.cs307_proj2_final;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login_new.fxml"));
        Parent root = fxmlLoader.load();
        //HelloController controller = fxmlLoader.getController();
        //controller.inti(12210722);

        Scene scene = new Scene(root);
        stage.setTitle("sustc");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}