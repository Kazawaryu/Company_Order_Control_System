package com.example.cs307_proj2_final;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SearchInfo {
    String row, way, detail;

    HelloController fatherCtrl;

    @FXML
    private MenuButton TEXT_searchrow;

    @FXML
    private MenuButton TEXT_searchway;

    @FXML
    private TextField INPUT_detail;

    @FXML
    void setRowNum(ActionEvent event) {
        TEXT_searchrow.setText("Number");
        row = "number";
    }

    @FXML
    void setRowModel(ActionEvent event) {
        TEXT_searchrow.setText("Model");
        row = "model";
    }

    @FXML
    void setRowName(ActionEvent event) {
        TEXT_searchrow.setText("Name");
        row = "name";
    }

    @FXML
    void setRowPrice(ActionEvent event) {
        TEXT_searchrow.setText("Unit Price");
        row = "unit_pride";
    }

    @FXML
    void setWayAcc(ActionEvent event) {
        TEXT_searchway.setText("Accurate");
        way = "acc";
    }

    @FXML
    void setWayRange(ActionEvent event) {
        TEXT_searchway.setText("Range");
        TEXT_searchrow.setText("Unit Price");
        row = "unit_price";
        way = "ran";
    }

    @FXML
    void setWayBlurred(ActionEvent event) {
        if (row != null && !row.equals("name") && !row.equals("model")) {
            TEXT_searchway.setText("Blurred");
            TEXT_searchrow.setText("Name");
            row = "name";
        } else if (row != null) {
            TEXT_searchway.setText("Blurred");

        }
        way = "blu";

    }

    @FXML
    void Input(ActionEvent event) throws IOException, SQLException {
        if (INPUT_detail.getText() != null) {
            if (INPUT_detail.getText() != null && way != null && row != null) {
                detail = INPUT_detail.getText();
                fatherCtrl.prep_view_model_selest(row, way, detail);
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            }
        }
    }

    @FXML
    void Cancel(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }


    @FXML
    private TextField u1;

    @FXML
    private TextField u2;

    @FXML
    private TextField u3;

    @FXML
    private TextField u4;

    @FXML
    private TextField u5;


    @FXML
    private TextField i1;

    @FXML
    private TextField i2;

    @FXML
    private TextField i3;

    @FXML
    private TextField i4;

    @FXML
    private TextField i5;

    @FXML
    void BUTTON_insert(ActionEvent event) throws SQLException {
        fatherCtrl.Insert_Model(Integer.parseInt(i1.getText()), i2.getText(), i3.getText(), i4.getText()
                , Integer.parseInt(i5.getText()));
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void BUTTON_delete(ActionEvent event) throws SQLException {

        fatherCtrl.prep_view_model_selest(row, way, detail);
        fatherCtrl.Delete_Model();
        System.out.println("nafkjskjbask");
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }


    @FXML
    void BUTTON_update(ActionEvent event) throws SQLException {
        fatherCtrl.Update_Model(Integer.parseInt(u1.getText()), u2.getText(), u3.getText(), u4.getText()
                , Integer.parseInt(u5.getText()));
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }


}
