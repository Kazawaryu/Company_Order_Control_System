package com.example.cs307_proj2_final;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class API13 {
    HelloController father;

    @FXML
    public Text text3;

    @FXML
    public Text text4;

    @FXML
    public Text text1;

    @FXML
    public Text text2;

    @FXML
    private TextField text;

    @FXML
    void GO(ActionEvent event) throws SQLException {
        if (!Objects.equals(text.getText(), "")) {
            father.getContractInfo(text.getText(), this);
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }
}
