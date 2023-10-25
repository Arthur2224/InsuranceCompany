package com.example.insurance;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class DataBaseConnectionVerification {

    @FXML
    public void signupUser(Stage stage, String email, String password){




        Connection connection=null;
        PreparedStatement psInset=null;
        PreparedStatement psCheckUserExist=null;
        PreparedStatement psCheckUsersPassword=null;
        ResultSet resultSet2=null;
        ResultSet resultSet=null;

        try{
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/insurancecompany","root","123456789ABC!");
            psCheckUserExist=connection.prepareStatement("SELECT * FROM client WHERE email =? AND password =? ");
            psCheckUserExist.setString(1,email);
            psCheckUserExist.setString(2,password);
            resultSet= psCheckUserExist.executeQuery();

            if(resultSet.isBeforeFirst()){
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
                    Parent root = loader.load();

                    Scene scene = new Scene(root, 1280, 720);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Пользователя с данной электронной почтой не существует либо введен неправильный пароль!");
                alert.show();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
