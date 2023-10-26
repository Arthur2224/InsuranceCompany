package com.example.insurance;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class DataBaseConnectionVerification {
    Connection connection;

    /*
    CheckUserExist -- checking in selected table email and if user actually has data in DB return false
     */
    private boolean CheckUserExist(String table,String column,String value) {
        PreparedStatement psCheckUserExist = null;
        PreparedStatement psCheckUsersPassword = null;
        ResultSet resultSet = null;
        try {
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/insurancecompany","root","123456789ABC!");
            psCheckUserExist = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + column + " = ?");

            psCheckUserExist.setString(1, value);

            resultSet= psCheckUserExist.executeQuery();
            return resultSet.isBeforeFirst();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    Actually same method but it's get x2 more values to find user by email and password(id etc)
    */
    private boolean CheckPassword(String table,String column1,String value1,String column2,String value2){
        PreparedStatement psCheckUserExist=null;
            PreparedStatement psCheckUsersPassword=null;
            ResultSet resultSet=null;
            try{
                connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/insurancecompany","root","123456789ABC!");
                psCheckUserExist=connection.prepareStatement("SELECT * FROM+"+table+"+ WHERE"+column1+ "="+value1+" AND "+column2+"+=?");

                psCheckUserExist.setString(1,value2);

                resultSet= psCheckUserExist.executeQuery();
                if(resultSet.isBeforeFirst()) return true;
                else return false;

            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }
    /*
    Method to LogIn user. First of all method check does user exist in DB and if not than take data's into DB
    */
    public void LogInUser(String name, String surname, String lastname, String number, LocalDate birthday, String email,String password){
        PreparedStatement psInset=null;
        PreparedStatement psCheckUserExist=null;
        PreparedStatement InsertDataToDB=null;
        int resultSet;
        if(!CheckUserExist("client","email",email)){

            try{
                connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/insurancecompany","root","123456789ABC!");
                InsertDataToDB=connection.prepareStatement("INSERT INTO client ( first_name, second_name, last_name, date_of_birth, email, password,phone_number) VALUES (?, ?, ?, ?, ?, ?, ?)");

                InsertDataToDB.setString(1,name);
                InsertDataToDB.setString(2,surname);
                InsertDataToDB.setString(3,lastname);
                InsertDataToDB.setString(4, String.valueOf(birthday));
                InsertDataToDB.setString(5,email);
                InsertDataToDB.setString(6,password);
                InsertDataToDB.setString(7,number);
                resultSet=InsertDataToDB.executeUpdate();
                InsertDataToDB.close();

            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //For employees that's has special code
    public void LogInUser(String name, String surname, String lastname, String number, LocalDate birthday, String email,String password, String idOfEmployee){
        PreparedStatement UpdateNewData=null;

        String newDate=String.valueOf(birthday);
        int resultSet;
        System.out.println(name.getClass());
        if(!CheckUserExist("employee","email",email)){
            try{
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/insurancecompany", "root", "123456789ABC!");
                UpdateNewData = connection.prepareStatement("UPDATE employee SET " +
                        "first_name = '" + name + "', " +
                        "second_name = '" + surname + "', " +
                        "last_name = '" + lastname + "', " +
                        "date_of_birth = '" + java.sql.Date.valueOf(newDate) + "', " +
                        "email = '" + email + "', " +
                        "phone_number = '" + number + "', " +
                        "password = '" + password + "' " +
                        "WHERE ID = " + Integer.parseInt(idOfEmployee));
                UpdateNewData.executeUpdate();
                UpdateNewData.close();

            }
            catch (SQLException e) {
                e.printStackTrace();
            }}
    }


    public void signupUser(Stage stage, String email, String password){
        
        PreparedStatement psInset=null;
        PreparedStatement psCheckUserExist=null;
        PreparedStatement psCheckUsersPassword=null;

        ResultSet resultSet=null;


        try{
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/insurancecompany","root","123456789ABC!");
            psCheckUserExist=connection.prepareStatement("SELECT * FROM client WHERE email =? AND password =? ");
            psCheckUserExist.setString(1,email);
            psCheckUserExist.setString(2,password);
            resultSet= psCheckUserExist.executeQuery();

            if(CheckUserExist("client","email",email)){
                try {
                    psCheckUserExist.close();

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
