package com.example.insurance;

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
    private Button signUp;
    @FXML
    private Button trySignUp;
    @FXML
    private Label tipOnSignUpPage;
    @FXML
    private TextField emailTxtField;
    @FXML
    private TextField passwordTxtField;
    @FXML
    protected void switchSceneSignUp(){
        // Load the new scene
        try {
            // Load the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            // Create the stage and set the new scene
            Stage stage = (Stage) signUp.getScene().getWindow();
            Scene scene = new Scene(root,1280,720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void upTip(){
        tipOnSignUpPage.setOpacity(1);
    }
    @FXML
    protected void downTip(){
        tipOnSignUpPage.setOpacity(0);
    }
    @FXML
    protected void signUpColorUp(){
        trySignUp.setStyle("-fx-border-color: linear-gradient(to bottom left,#b0c4de 30% ,  #301c94 70% );-fx-border-width:6px;-fx-background-color:null;-fx-border-radius:5px;-fx-font: 18 Arial;");
    }
    @FXML
    protected void signUpColorDown(){
        trySignUp.setStyle("-fx-border-color:  linear-gradient(to bottom left,#b0c4de 30% ,  #301c94 30% );-fx-border-width:4px;-fx-background-color:null;-fx-border-radius:0px");
    }

    @FXML
    protected void signUpUser() {
        DataBaseConnectionVerification dataBaseConnectionVerification = new DataBaseConnectionVerification();

        if (!emailTxtField.getText().isEmpty() && !passwordTxtField.getText().isEmpty())
            dataBaseConnectionVerification.signupUser((Stage) signUp.getScene().getWindow(), emailTxtField.getText(), passwordTxtField.getText());
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Введите данные во все поля!");
            alert.setTitle("Данные не введены во все поля");
            alert.show();
        }

    }}
