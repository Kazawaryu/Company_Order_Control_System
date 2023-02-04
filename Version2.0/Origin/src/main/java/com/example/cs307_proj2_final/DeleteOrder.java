package com.example.cs307_proj2_final;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Objects;

public class DeleteOrder {
    HelloController father;

    @FXML
    private TextField TEXT_file;

    @FXML
    private TextField TEXT_input;

    @FXML
    void BUTTON_input(ActionEvent event) throws SQLException {
        if (!Objects.equals(TEXT_input.getText(), "")) {
            String[] a = TEXT_input.getText().split(",");
            if (a.length == 3) {
                father.deleteOrder(a[0],Integer.parseInt(a[1]),Integer.parseInt(a[2]));
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            }
        }
    }

    @FXML
    void BUTTON_file(ActionEvent event) throws SQLException, FileNotFoundException {
        if (!Objects.equals(TEXT_file.getText(), "")) {
            father.deleteOrderRun(TEXT_file.getText());
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        }
    }
}
