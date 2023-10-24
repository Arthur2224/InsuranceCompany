package com.example.insurance;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;

public class SignUpController {
    @FXML
    public Button SignUp;
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
}
