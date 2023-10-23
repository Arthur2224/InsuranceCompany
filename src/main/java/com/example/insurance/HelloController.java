package com.example.insurance;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {
    @FXML
    private TextField PhoneTextField;

    // The method to handle the InputMethodTextChanged event
    public void CheckLenghtOfText() {
        // Implement your logic here
        String inputText = PhoneTextField.getText();
        int textLength = inputText.length();
        System.out.println("Text length: " + textLength);

        // You can add your own validation or processing logic here
    }
}
