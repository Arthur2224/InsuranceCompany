package com.example.insurance;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class addInsuranceEventController {


    @FXML
    private TextArea description;

    @FXML
    protected void setNewInsuranceEvent(){
        MainMenuController MMC=new MainMenuController();


        if(!description.getText().isEmpty()){
            int selectedId=MMC.getSelectedId();
            DataBaseConnectionVerification DBC=new DataBaseConnectionVerification();
            DBC.NewInsuranceEvent(selectedId,description.getText());
            DBC.PauseContract(selectedId);
            Stage stage = (Stage) description.getScene().getWindow();
            stage.close();
            MMC.upDateTable();
        }

    }

}
