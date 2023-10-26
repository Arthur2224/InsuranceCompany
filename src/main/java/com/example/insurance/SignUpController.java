package com.example.insurance;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;

public class SignUpController {
    @FXML
    private Button SignUp;
    @FXML
    public Button TrySignUp;
    @FXML
    private Label thinkaboutInsurance;
    @FXML
    private TextField emailTxtField;
    @FXML
    private TextField passwordTxtField;
    @FXML
    protected void SwitchSceneSignUp(){
        // Load the new scene
        try {
            // Load the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            // Create the stage and set the new scene
            Stage stage = (Stage) SignUp.getScene().getWindow();
            Scene scene = new Scene(root,1280,720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void UpTip(){
        thinkaboutInsurance.setOpacity(1);
    }
    @FXML
    protected void DownTip(){
        thinkaboutInsurance.setOpacity(0);
    }


    @FXML
    protected void signUpUser() {
        DataBaseConnectionVerification dataBaseConnectionVerification = new DataBaseConnectionVerification();

        if (!emailTxtField.getText().isEmpty() && !passwordTxtField.getText().isEmpty())
            dataBaseConnectionVerification.signupUser((Stage) SignUp.getScene().getWindow(), emailTxtField.getText(), passwordTxtField.getText());
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Введите данные во все поля!");
            alert.setTitle("Данные не введены во все поля");
            alert.show();
        }

    }}
