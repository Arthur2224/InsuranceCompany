package com.example.insurance;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class MainMenuController implements Initializable {
    @FXML
    public ChoiceBox<String> typeOfInsurance;
   @FXML
    public ChoiceBox<String> termOfInsurance;
   private String[] term={"30","100"};
    private String[] types={"casco","simple"};
    @Override
    public void initialize(URL arg0, ResourceBundle arf1){
            typeOfInsurance.getItems().addAll(types);
            termOfInsurance.getItems().addAll(term);
    }


    @FXML
    protected void setNewContract(){
        DataBaseConnectionVerification DB= new DataBaseConnectionVerification();
        int cost=0;
        String value = termOfInsurance.getValue();

        switch (typeOfInsurance.getValue()){
            case "casco":
                cost= (int) (Integer.parseInt(value)*0.5);
                break;
            case  "simple":
                cost=(int) (Integer.parseInt(value)*0.8);
                break;
            default: cost=0;
        }
        System.out.println(cost);
        int payout= (int) (cost*1.6);
        DB.SetNewContract( parseInt(termOfInsurance.getValue()), cost,payout,1);

    }

}
