package com.example.cs307_proj2_final;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class PlaceOrder {
    HelloController father;

    @FXML
    private TextField TEXT_localfile;

    @FXML
    private TextField TEXT_input;

    @FXML
    void BUTTON_input(ActionEvent event) throws SQLException {
        if (!Objects.equals(TEXT_input.getText(), "")) {
            String[] a = TEXT_input.getText().split(",");
            if (a.length == 10) {
                father.placeOrder(a[0], a[1], a[2], Integer.parseInt(a[3]), Integer.parseInt(a[4])
                        , a[5], a[6], a[7], Integer.parseInt(a[8]), a[9]);
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            }
        }
    }

    @FXML
    void BUTTON_file(ActionEvent event) throws SQLException, IOException {
        if (!Objects.equals(TEXT_localfile.getText(), "")) {
            father.placeOrderRun(TEXT_localfile.getText());
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        }
    }
}
