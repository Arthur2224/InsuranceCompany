package com.example.insurance;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static java.lang.Integer.*;

public class MainMenuController implements Initializable{
    DataBaseConnectionVerification DB= new DataBaseConnectionVerification();
    @FXML
    public Label TipForterm;
    @FXML
    private Label Description;
    @FXML
    private Label TipForCoverage;
    private Object[] TypeOfInsuranceDescrip;
   @FXML
    public TextField term;

    @FXML
    public TextField coverage;

    public static int base_price;
    public static int min_duration;
    public static int max_duration;
    public static int min_coverage;
    public static int max_coverage;
    public Label totalPrice;
    public static int  coverageInt;
    public static int termInt;

    @FXML
    protected void setNewContract(){
        DataBaseConnectionVerification DB= new DataBaseConnectionVerification();
        int cost=base_price;
        String value = term.getText();


        System.out.println(cost);
        int payout= (int) (cost*1.6);
        DB.SetNewContract( parseInt(term.getText()), cost,payout,1);

    }
    @FXML
    protected void getDescriptonOfInsuranceType(int id){

        TypeOfInsuranceDescrip =DB.getDescriptionOfInsuranceType(id);
        Description.setText(String.valueOf( TypeOfInsuranceDescrip[1]));
        min_duration=(int)TypeOfInsuranceDescrip[2];
        max_duration=(int)TypeOfInsuranceDescrip[3];
        min_coverage=(int)TypeOfInsuranceDescrip[4];
        max_coverage=(int)TypeOfInsuranceDescrip[5];
        base_price=(int)TypeOfInsuranceDescrip[6];

    }
    @FXML
    protected void getDescriptonOfInsuranceTypeForAuto(){

       getDescriptonOfInsuranceType(1);
    }
    @FXML
    protected void getDescriptonOfInsuranceTypeForLife(){

        getDescriptonOfInsuranceType(2);
    }
    @FXML
    protected void getDescriptonOfInsuranceTypeForHouse(){

        getDescriptonOfInsuranceType(3);
    }

    @FXML
    protected void checkDataForTerm(){

        try {
            termInt = Integer.parseInt(term.getText());

            if (termInt < min_duration || termInt > max_duration) {
                term.clear();
                TipForterm.setVisible(true);
                TipForterm.setText("Минимальное кол-во дней: " + min_duration + "\nМаксимальное: " + max_duration);
            } else {
                TipForterm.setVisible(false);
                totalPrice.setText(String.valueOf(( base_price*termInt+coverageInt)/1000.0 ));
            }
        } catch (NumberFormatException e) {

            term.clear();
            TipForterm.setVisible(true);
            TipForterm.setText("Введите корректное число дней.");
        }
    }
    @FXML
    protected void checkDataForCoverage(){
        try {
             coverageInt = Integer.parseInt(coverage.getText());

            if (coverageInt < min_coverage || coverageInt > max_coverage) {
                TipForCoverage.setVisible(true);
                TipForCoverage.setText("Минимальное покрытие: " + min_coverage + "\nМаксимальное: " + max_coverage);
                coverage.clear();
            } else {
                TipForCoverage.setVisible(false);
                totalPrice.setText(String.valueOf(( base_price*termInt+coverageInt)/100.0));
            }
        } catch (NumberFormatException e) {
            coverage.clear();
            TipForCoverage.setVisible(true);
            TipForCoverage.setText("Введите корректную сумму\nстрахового покрытия.");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
