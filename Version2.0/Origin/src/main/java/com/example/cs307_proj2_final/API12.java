package com.example.cs307_proj2_final;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class API12 {
    HelloController father;

    @FXML
    private TextField text;

    @FXML
    void BUTTON_GO(ActionEvent event) throws SQLException {
        if (!Objects.equals(text.getText(), "")) {
            father.loadAPI12(text.getText());
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        }
    }

}
