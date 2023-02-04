package com.example.cs307_proj2_final;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Objects;

public class UpdateOrder {
    HelloController father;

    @FXML
    private TextField quantity;

    @FXML
    private TextField address;


    @FXML
    void BUTTON_Input(ActionEvent event) throws SQLException {
        if (!Objects.equals(quantity.getText(), "") &&
                !Objects.equals(num.getText(), "") && !Objects.equals(model.getText(), "") && !Objects.equals(salesman.getText(), "") &&
                lod_date.getText() != null && del_date.getText() != null) {
            father.updateOrder(num.getText(), model.getText(), Integer.parseInt(salesman.getText()), Integer.parseInt(quantity.getText()), del_date.getText(), lod_date.getText());
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        }
    }

    @FXML
    private TextField num;

    @FXML
    private TextField model;

    @FXML
    private TextField salesman;

    @FXML
    private TextField lod_date;

    @FXML
    private TextField del_date;

    @FXML
    void BUTTON_file(ActionEvent event) throws SQLException, FileNotFoundException {
        if (!Objects.equals(address.getText(), "")) {
            //loading function here
            father.updateOrderRun(address.getText());
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        }
    }


}
