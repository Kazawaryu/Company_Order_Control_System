package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Objects;

class account {
    String name;
    String password;
    int rank;

    public account(String name, String password, int rank) {
        super();
        this.name = name;
        this.password = password;
        this.rank = rank;
    }
}

public class Controller {

    HashMap<String, account> account_list = new HashMap<>();
    static int login_push_time = 0;

    @FXML
    private PasswordField password;

    @FXML
    private TextField database;

    @FXML
    private TextField name;

    @FXML
    private TextField host;

    @FXML
    void login(ActionEvent event) throws IOException {
        String name = this.name.getText();
        String password = this.password.getText();
        String host = this.host.getText();
        String database = this.database.getText();

        if (login_push_time == 0)
            updateAccount();
        login_push_time++;

        if (name.equals("") | password.equals("") | host.equals("") | database.equals("")) {
            remind(new Stage());
        } else {
            if (account_list.containsKey(name)) {
                if (Objects.equals(account_list.get(name).password, password)) {
                    if (account_list.get(name).rank == 0) {
                        System.out.println("Normal login");
                        cancel(event);

                        File file = new File("rank.txt");
                        file.createNewFile();
                        Writer writer = new FileWriter(file);
                        writer.write("Normal login");
                        writer.close();
                        start(new Stage());
                    } else {
                        System.out.println("Super login");
                        cancel(event);

                        File file = new File("rank.txt");
                        file.createNewFile();
                        Writer writer = new FileWriter(file);
                        writer.write("Super login");
                        writer.close();
                        start(new Stage());
                    }
                }else remind(new Stage());
            }else remind(new Stage());
        }
    }

    private void updateAccount() {
        account user1 = new account("Ghosn", "123456", 1);
        account_list.put("Ghosn", user1);
        account user2 = new account("Yoko", "011022", 0);
        account_list.put("Yoko", user2);
    }

    private void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Home-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("SUSTC Company System");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void remind(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("remind-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 150);
        stage.setTitle("Remind");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cancel(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

}
