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

public class HelloController {
    //Creating a graphic (image)




    @FXML
    public TextField PhoneTextField;
    @FXML
    Text coloredText = new Text();
    @FXML
    public Button SignUp;
    @FXML
    public GridPane GridPanel;
    @FXML
    public DatePicker Calendar;



    // The method to handle the InputMethodTextChanged event
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






    ;
    }
