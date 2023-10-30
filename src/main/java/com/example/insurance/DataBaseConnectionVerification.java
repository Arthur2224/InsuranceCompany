package com.example.insurance;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataBaseConnectionVerification {
    Connection connection;
    public static int client_id;

    /*
    CheckUserExist -- checking in selected table email and if user actually has data in DB return false
     */
    private boolean CheckUserExist(String table,String column,String value) {
        PreparedStatement psCheckUserExist = null;
        PreparedStatement psCheckUsersPassword = null;
        ResultSet resultSet = null;
        try {
            connection=DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11657485","sql11657485","fePiZmiwKC");
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
    private boolean CheckPassword(String table,String emailOrID,String value1,String passwordOrID,String value2){

            PreparedStatement psCheckUsersPassword=null;
            PreparedStatement findIDbyEmail=null;
            ResultSet resultSet=null;
            ResultSet resultSet1=null;
            try{
                connection=DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11657485","sql11657485","fePiZmiwKC");
                findIDbyEmail=connection.prepareStatement("SELECT ID FROM "+table+" WHERE "+emailOrID+" = ?");
                findIDbyEmail.setString(1,value1);
                resultSet=findIDbyEmail.executeQuery();
                if(resultSet.next()){
                    client_id=resultSet.getInt("ID");
                    System.out.println("CheckPassword : ClientId: "+client_id);
                    psCheckUsersPassword=connection.prepareStatement("SELECT "+passwordOrID+" FROM "+table+" WHERE ID= "+resultSet.getInt("ID"));
                    resultSet1=psCheckUsersPassword.executeQuery();
                    if(resultSet1.next()){
                        if( value2.equals(resultSet1.getString("password")) ) return  true;
                    }
                }

                return false;
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
                connection=DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11657485","sql11657485","fePiZmiwKC");
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
    public void LogInUser(Stage stage,String name, String surname, String lastname, String number, LocalDate birthday, String email,String password, String idOfEmployee){
        PreparedStatement UpdateNewData=null;

        String newDate=String.valueOf(birthday);
        int resultSet;
        System.out.println(name.getClass());
        if(!CheckUserExist("employee","email",email)){
            try{
                connection=DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11657485","sql11657485","fePiZmiwKC");
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
            }

            try {


                FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 1280, 720);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void signupUser(Stage stage, String email, String password){
        
        PreparedStatement psInset=null;
        PreparedStatement psCheckUserExist=null;
        PreparedStatement psCheckUsersPassword=null;

        ResultSet resultSet=null;


        try{
            connection=DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11657485","sql11657485","fePiZmiwKC");

            boolean client=CheckUserExist("client","email",email);
            boolean employee=CheckUserExist("employee","email",email);
            if(client||employee){

                if(CheckPassword("client","email",email,"password",password)||CheckPassword("employee","email",email,"password",password)){

                try {
                    if(psCheckUserExist!=null) psCheckUserExist.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
                    Parent root = loader.load();

                    Scene scene = new Scene(root, 1280, 720);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }}
                else{
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Введен неправильный пароль!");
                    alert.show();
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


    public void SetNewContract(int validality,int cost,int payout,int type_of_insurance){
        PreparedStatement InsertNewContract=null;
        System.out.println(client_id);

        try {
            connection=DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11657485","sql11657485","fePiZmiwKC");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime end_date=now.plusDays(validality);
             //  2021/03/22 16:37:15
            InsertNewContract=connection.prepareStatement("INSERT INTO contracts ( client_id, start_date, validality, cost, payout,end_date,status) VALUES (?, ?, ?, ?, ?, ?, ?)");

            InsertNewContract.setInt(1,client_id);
            // Assuming now is your LocalDateTime
            String formattedDate = dtf.format(now);
            InsertNewContract.setDate(2, java.sql.Date.valueOf(now.toLocalDate()));
            // Extract the "yyyy-MM-dd" part
            InsertNewContract.setInt(3,validality);
            InsertNewContract.setInt(4, cost);
            InsertNewContract.setInt(5,payout);
            //InsertNewContract.setInt(6,);
            InsertNewContract.setDate(6, java.sql.Date.valueOf(end_date.toLocalDate()));

            InsertNewContract.setString(7,"Приостановлен");
            InsertNewContract.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public  Object[] getDescriptionOfInsuranceType(int insurance_id){
        PreparedStatement getDataOfInsuranceType=null;
        ResultSet resultSet=null;
        Object[] dataOfIT=new Object[7];
        try{
            connection=DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11657485","sql11657485","fePiZmiwKC");
            getDataOfInsuranceType=connection.prepareStatement("SELECT * FROM insurancetypes WHERE ID = "+insurance_id);
            resultSet=getDataOfInsuranceType.executeQuery();
            if(resultSet.next()){
                dataOfIT[0]=resultSet.getString("insurance_name");
                dataOfIT[1]=resultSet.getString("description");
                dataOfIT[2]=resultSet.getInt("min_duration");
                dataOfIT[3]=resultSet.getInt("max_duration");
                dataOfIT[4]=resultSet.getInt("min_coverage");
                dataOfIT[5]=resultSet.getInt("max_coverage");
                dataOfIT[6]=resultSet.getInt("base_price");
            }


        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return dataOfIT;
    }


}
