package com.example.insurance;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class addInsuranceEventController {
    @FXML
    private Button Back;

    @FXML
    private TextArea description;
    @FXML
    private Button sendInsuranceIvent;
    @FXML
    protected void setNewInsuranceEvent(){
        MainMenuController MMC=new MainMenuController();


        if(!description.getText().isEmpty()){
            DataBaseConnectionVerification DBC=new DataBaseConnectionVerification();
            System.out.println(MMC.getSelectedId()+" yes Sir");
            DBC.NewInsuranceEvent(MMC.getSelectedId(),description.getText());
            DBC.PauseContract(MMC.getSelectedId());
            Stage stage = (Stage) description.getScene().getWindow();
            stage.close();
            MMC.upDateTable();
        }

    }

}
