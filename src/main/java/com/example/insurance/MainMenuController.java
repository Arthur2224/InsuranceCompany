package com.example.insurance;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    public ChoiceBox<String> typeOfInsurance;
   @FXML
    public ChoiceBox<String> termOfInsurance;
    private String[] types={"casco","simple"};
    @Override
    public void initialize(URL arg0, ResourceBundle arf1){
            typeOfInsurance.getItems().addAll(types);
    }

}
