package com.example.insurance;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloController {

    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private DatePicker calendarPicker;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField employeeCodeField;

    @FXML
    private Button signUpButton;
    @FXML
    private GridPane gridPanel;

    @FXML
    private Label tipOnMainPage;

    @FXML
    protected void clearTextFields() {
        for (Node node : gridPanel.getChildren()) {
            if (node instanceof TextInputControl || node instanceof DatePicker) {
                if (node instanceof TextInputControl) {
                    ((TextInputControl) node).clear();
                } else if (node instanceof DatePicker) {
                    ((DatePicker) node).setValue(null);
                }
            }
        }
    }

    @FXML
    protected void switchToSignUpScene() {
        try {
            // Load the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent root = loader.load();

            // Create the stage and set the new scene
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            Scene scene = new Scene(root, 1280, 720);
            stage.setResizable(false);
            stage.setTitle("UnionInsurance");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void upTip() {
        tipOnMainPage.setOpacity(1);
    }

    @FXML
    protected void downTip() {
        tipOnMainPage.setOpacity(0);
    }

    @FXML
    protected void tryLogIn() {
        java.time.LocalDate selectedDate = calendarPicker.getValue();
        DataBaseConnectionVerification dbConVerifier = new DataBaseConnectionVerification();
        if (Objects.equals(passwordField.getText(), confirmPasswordField.getText()) && !passwordField.getText().isEmpty()) {
            if (employeeCodeField.getText().isEmpty()) {
                dbConVerifier.LogInUser(nameField.getText(), surnameField.getText(), lastNameField.getText(),
                        phoneTextField.getText(), selectedDate, emailField.getText(), passwordField.getText());
            } else {
                dbConVerifier.LogInUser(new Stage(), nameField.getText(), surnameField.getText(), lastNameField.getText(),
                        phoneTextField.getText(), selectedDate, emailField.getText(), passwordField.getText(), employeeCodeField.getText());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Поля для пароля не совпадают!");
            alert.show();
        }
    }
}
