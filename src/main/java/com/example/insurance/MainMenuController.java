package com.example.insurance;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class MainMenuController implements Initializable{
    DataBaseConnectionVerification DB= new DataBaseConnectionVerification();
    private boolean employee;
    @FXML
    public Label TipForterm_auto;
    @FXML
    public Label TipForterm_life;
    @FXML
    public Label TipForterm_house;
    @FXML
    private Label Description_auto;
    @FXML
    private Label Description_life;
    @FXML
    private Label Description_house;
    @FXML
    private Label TipForCoverage_auto;
    @FXML
    private Label TipForCoverage_life;
    @FXML
    private Label TipForCoverage_house;

    private Object[] TypeOfInsuranceDescrip;
   @FXML
    public TextField term_auto;
    @FXML
    public TextField term_life;
    @FXML
    public TextField term_house;
    @FXML
    public TextField coverage_auto;
    @FXML
    public TextField coverage_life;
    @FXML
    public TextField coverage_house;
    @FXML
    public Tab autoTab;
    @FXML
    public  Tab lifeTab;
    @FXML   Tab houseTab;

    public  int base_price;
    public  int min_duration;
    public  int max_duration;
    public  int min_coverage;
    public  int max_coverage;
    public Label totalPrice_auto;
    public Label totalPrice_life;
    public Label totalPrice_house;
    public  int  coverageInt;
    public  int termInt;
    private Tab currentTab;

    @FXML
    protected void setNewContract(){
        DataBaseConnectionVerification DB= new DataBaseConnectionVerification();
        int cost=base_price;
        int id=1;
        String specific_name="";
        if (currentTab.getId().equals("autoTab")) {
            specific_name="auto";
            id=1;
        } else if (currentTab.getId().equals("lifeTab")) {
            specific_name="life";
            id=2;
        } else if (currentTab.getId().equals("houseTab")) {
            specific_name="house";
            id=3;
        }
        Label label = (Label) findElementByName("totalPrice_" + specific_name,Label.class);
        TextField textField1=(TextField) findElementByName("coverage_" + specific_name,TextField.class);
        TextField textField=(TextField) findElementByName("term_" + specific_name,TextField.class);
        int term=Integer.parseInt(textField.getText());
        int coverage=Integer.parseInt(label.getText());
        DB.SetNewContract(term,coverage,term*base_price+coverage,id);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Заявка успешно подана");
        alert.setTitle("Ожидайте подтверждение Вашей заявки сотрудником компании!\nСпасибо за то, что выбрали UnionInsurance");
        alert.show();

    }

    @FXML
    protected void onTabSelectionChanged(Event event) {
        Tab selectedTab = (Tab) event.getSource();

        if (currentTab != selectedTab) {
            if (selectedTab.getId().equals("autoTab")) {
                getDescriptonOfInsuranceType(1);
            } else if (selectedTab.getId().equals("lifeTab")) {
                getDescriptonOfInsuranceType(2);
            } else if (selectedTab.getId().equals("houseTab")) {
                getDescriptonOfInsuranceType(3);
            }
            currentTab = selectedTab;
        }
    }


    @FXML
    protected void getDescriptonOfInsuranceType(int id){
        TypeOfInsuranceDescrip =DB.getDescriptionOfInsuranceType(id);
        min_duration=(int)TypeOfInsuranceDescrip[2];
        max_duration=(int)TypeOfInsuranceDescrip[3];
        min_coverage=(int)TypeOfInsuranceDescrip[4];
        max_coverage=(int)TypeOfInsuranceDescrip[5];
        base_price=(int)TypeOfInsuranceDescrip[6];
    }
    private Node findElementByName(String name, Class<?> elementType) {
        try {
            Field field = getClass().getDeclaredField(name);
            if (field.getType() == elementType) {
                return (Node) field.get(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    protected void checkDataForTerm(){
        String specific_name="";
        if (currentTab.getId().equals("autoTab")) {
           specific_name="auto";
        } else if (currentTab.getId().equals("lifeTab")) {
            specific_name="life";
        } else if (currentTab.getId().equals("houseTab")) {
            specific_name="house";
        }
        Label label = (Label) findElementByName("TipForterm_" + specific_name,Label.class);
        Label label1= (Label)findElementByName("totalPrice_" + specific_name, Label.class);
        TextField textField=(TextField) findElementByName("term_" + specific_name,TextField.class);
        try {

            termInt = Integer.parseInt(textField.getText());

            if (termInt < min_duration || termInt > max_duration) {
                textField.clear();
                label.setVisible(true);
                label.setText("Минимальное кол-во дней: " + min_duration + "\nМаксимальное: " + max_duration);
            } else {
                label.setVisible(false);
                label1.setText(String.valueOf(( base_price*termInt+coverageInt)));
            }
        } catch (NumberFormatException e) {

            textField.clear();
            label.setVisible(true);
            label.setText("Введите корректное число дней.");
        }
    }
    @FXML
    protected void checkDataForCoverage(){
        String specific_name="";
        if (currentTab.getId().equals("autoTab")) {

            specific_name="auto";
        } else if (currentTab.getId().equals("lifeTab")) {
            specific_name="life";
        } else if (currentTab.getId().equals("houseTab")) {
            specific_name="house";
        }
        Label label = (Label) findElementByName("TipForCoverage_"+specific_name,Label.class);
        Label label1= (Label)findElementByName("totalPrice_"+ specific_name, Label.class);
        TextField textField=(TextField) findElementByName("coverage_" + specific_name,TextField.class);
        try {
             coverageInt = Integer.parseInt(textField.getText());

            if (coverageInt < min_coverage || coverageInt > max_coverage) {
                label.setVisible(true);
                label.setText("Минимальное покрытие: " + min_coverage + "\nМаксимальное: " + max_coverage);
                textField.clear();
            } else {
                label.setVisible(false);
                label1.setText(String.valueOf(( base_price*termInt+coverageInt)));
            }
        } catch (NumberFormatException e) {
            textField.clear();
            label.setVisible(true);
            label.setText("Введите корректную сумму\nстрахового покрытия.");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employee=DB.employee;
        if(employee){
            autoTab.setDisable(true);
            lifeTab.setDisable(true);
            houseTab.setDisable(true);

        }
        Label label =  (Label) findElementByName("Description_" + "auto",Label.class);

        TypeOfInsuranceDescrip =DB.getDescriptionOfInsuranceType(1);
        if (label!=null) {
            label.setText(String.valueOf( TypeOfInsuranceDescrip[1]));

        }
        label =  (Label) findElementByName("Description_" + "life",Label.class);;
        TypeOfInsuranceDescrip =DB.getDescriptionOfInsuranceType(2);
        if (label!=null) {
            label.setText(String.valueOf( TypeOfInsuranceDescrip[1]));

        }
         label =  (Label) findElementByName("Description_" + "house",Label.class);;
        TypeOfInsuranceDescrip =DB.getDescriptionOfInsuranceType(3);
        if (label!=null) {
            label.setText(String.valueOf( TypeOfInsuranceDescrip[1]));

        }

        currentTab = null;

    }
}
