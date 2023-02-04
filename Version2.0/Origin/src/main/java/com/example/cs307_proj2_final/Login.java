package com.example.cs307_proj2_final;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class Login {
    static Connection con;
    static PreparedStatement stmt = null;
    static boolean verbose = false;

    @FXML
    private PasswordField password;

    @FXML
    private TextField sid;

    @FXML
    void Login(ActionEvent event) {
        try {
            if (!Objects.equals(password.getText(), "") && !Objects.equals(sid.getText(), "")) {

                Properties login = new Properties();
                login.put("host", "localhost");
                login.put("user", "checker");
                login.put("password", "123456");
                login.put("database", "Proj_02");
                Properties prop = new Properties(login);

                openDB(prop.getProperty("host"), prop.getProperty("database"),
                        prop.getProperty("user"), prop.getProperty("password"));
                System.out.println("connect successfully");
                PreparedStatement connect = con.prepareStatement("select count(*) from staff where number = '" + sid.getText() + "'");
                ResultSet cons;
                cons = connect.executeQuery();
                cons.next();
                if (cons.getInt(1) == 1 && Objects.equals(password.getText(), sid.getText())) {

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
                    Parent root = fxmlLoader.load();
                    HelloController controller = fxmlLoader.getController();
                    controller.prepInti(Integer.parseInt(sid.getText()));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Hello!");
                    stage.setScene(scene);
                    stage.show();
                    ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

                }
                closeDB();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Cancle(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }


    private static void openDB(String host, String dbname, String user, String pwd) {
        try {
            //
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }

        String url = "jdbc:postgresql://" + host + "/" + dbname;
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {
            con = DriverManager.getConnection(url, props);
            if (verbose) {
                System.out.println("Successfully connected to the database "
                        + dbname + " as " + user);
            }
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception ignored) {
            }
        }
    }

}
