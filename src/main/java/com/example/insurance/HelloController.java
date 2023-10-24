package com.example.insurance;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HelloController {
    @FXML
    public TextField PhoneTextField;
    @FXML
    Text coloredText = new Text();
    @FXML
    public VBox vboxWithBorder;
    @FXML
    public GridPane GridPanel;
    @FXML
    public DatePicker Calendar;

    // The method to handle the InputMethodTextChanged event
    @FXML
    protected void ClearTextFields() {
        for (Node node : GridPanel.getChildren()) {
            if (node instanceof TextInputControl || node instanceof DatePicker) {
                if (node instanceof TextInputControl) {
                    ((TextInputControl) node).clear();
                } else if (node instanceof DatePicker) {
                    ((DatePicker) node).setValue(null);
                }
            }
        };



    }


    ;}
