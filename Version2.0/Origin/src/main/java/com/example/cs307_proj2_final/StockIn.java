package com.example.cs307_proj2_final;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class StockIn {
    HelloController father;

    @FXML
    private TextField MASSAGE;

    @FXML
    private TextField LOCALFILE;

    @FXML
    void BUTTON_Input(ActionEvent event) throws SQLException {
        if (!Objects.equals(MASSAGE.getText(), "")) {
            String[] list = MASSAGE.getText().split(",");
            if (list.length == 7) {
                father.stockIn(parseInt(list[0]), list[1], list[2], parseInt(list[3]), list[4], parseInt(list[5]), parseInt(list[6]));
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            }
        }
    }

    @FXML
    void BUTTON_Local(ActionEvent event) throws SQLException, IOException {
        if (!Objects.equals(LOCALFILE.getText(), "")) {
            father.stockInRun(LOCALFILE.getText());
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        }
    }



    @FXML
    public DatePicker date;

    @FXML
    public TextField quantity;

    @FXML
    public TextField price;

    @FXML
    public TextField center;

    @FXML
    public TextField model;

    @FXML
    public TextField staff;

    @FXML
    public void go(ActionEvent event) throws SQLException{
        father.stockIn(333, String.valueOf(Integer.parseInt(center.getText())),model.getText(),Integer.parseInt(staff.getText()),date.getAccessibleText(),Integer.parseInt(price.getText()),Integer.parseInt(quantity.getText()));
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void cancel(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

}
