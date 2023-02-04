package com.example.cs307_proj2_final;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AllInfo {


    @FXML
    public Text text11;

    @FXML
    public Text text22;

    @FXML
    public Text text21;

    @FXML
    public Text text13;

    @FXML
    public Text text24;

    @FXML
    public Text text12;

    @FXML
    public Text text23;

    @FXML
    public Text text14;

    @FXML
    void BUTTON_flash(ActionEvent event) {

    }

    @FXML
    void BUTTON_cancel(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

}
