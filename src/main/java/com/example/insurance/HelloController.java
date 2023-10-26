package com.example.insurance;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class HelloController {

    @FXML
    public TextField PhoneTextField;
    @FXML
    public TextField Name;
    @FXML
    public TextField Surname;
    @FXML
    public TextField LastName;
    @FXML
    public DatePicker Calendar;

    @FXML
    public TextField email;
    @FXML
    public PasswordField Password;
    @FXML
    public PasswordField Password1;
    @FXML
    public TextField EmployeeCode;

    @FXML
    public Button SignUp;
    @FXML
    public GridPane GridPanel;


    @FXML
    public Label thinkaboutcode;


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
        };}
        @FXML
        protected  void SwitchToSignUpScene() {
            try {
                // Load the new scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
                Parent root = loader.load();

                // Create the stage and set the new scene
                Stage stage = (Stage) SignUp.getScene().getWindow();
                Scene scene = new Scene(root,1280,720);
                stage.setResizable(false);
                stage.setTitle("UnionInsurance");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        @FXML protected void UpTip(){
        thinkaboutcode.setOpacity(1);
        }
        @FXML
        protected void DownTip(){
        thinkaboutcode.setOpacity(0);
        }
        @FXML
        protected void TryLogIn(){
            java.time.LocalDate selectedDate = Calendar.getValue();
            if(Objects.equals(Password.getText(), Password1.getText())&&!Password.getText().isEmpty()){
                DataBaseConnectionVerification DBConVer=new DataBaseConnectionVerification();
                if(EmployeeCode.getText().isEmpty()) DBConVer.LogInUser(Name.getText(),Surname.getText(),LastName.getText(),PhoneTextField.getText(),selectedDate,email.getText(),Password.getText());
                else DBConVer.LogInUser(Name.getText(),Surname.getText(),LastName.getText(),PhoneTextField.getText(),selectedDate,email.getText(),Password.getText(),EmployeeCode.getText());
            }
            else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Поля для пароля не совпадают!");
                alert.show();
            }
        }




    ;
    }
