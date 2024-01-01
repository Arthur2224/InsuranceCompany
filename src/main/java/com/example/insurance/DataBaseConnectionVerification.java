package com.example.insurance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
    private Connection connection;
    private int client_id;



    private  boolean employee;
    private final String url="jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11657485";
    private final String userName="sql11657485";
    private final String password="fePiZmiwKC";
    /*
    CheckUserExist -- checking in selected table email and if user actually has data in DB return false
     */
    public boolean isEmployee() {
        return employee;
    }
    private boolean CheckUserExist(String table, String value) {
        PreparedStatement psCheckUserExist;
        ResultSet resultSet;
        try {
            connection=DriverManager.getConnection(url,userName,password);
            psCheckUserExist = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + "email" + " = ?");

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
    private boolean CheckPassword(String table, String value1, String value2){

            PreparedStatement psCheckUsersPassword;
            PreparedStatement findIDbyEmail;
            ResultSet resultSet;
            ResultSet resultSet1;
            try{
                connection=DriverManager.getConnection(url,userName,password);
                findIDbyEmail=connection.prepareStatement("SELECT ID FROM "+table+" WHERE "+ "email" +" = ?");
                findIDbyEmail.setString(1,value1);
                resultSet=findIDbyEmail.executeQuery();
                if(resultSet.next()){
                    client_id=resultSet.getInt("ID");
                    psCheckUsersPassword=connection.prepareStatement("SELECT "+ "password" +" FROM "+table+" WHERE ID= "+resultSet.getInt("ID"));
                    resultSet1=psCheckUsersPassword.executeQuery();
                    if(resultSet1.next()){
                        return value2.equals(resultSet1.getString("password"));
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
        PreparedStatement InsertDataToDB;

        if(!CheckUserExist("client", email)){

            try{
                connection=DriverManager.getConnection(url,userName,password);
                InsertDataToDB=connection.prepareStatement("INSERT INTO client ( first_name, second_name, last_name, date_of_birth, email, password,phone_number) VALUES (?, ?, ?, ?, ?, ?, ?)");

                InsertDataToDB.setString(1,name);
                InsertDataToDB.setString(2,surname);
                InsertDataToDB.setString(3,lastname);
                InsertDataToDB.setString(4, String.valueOf(birthday));
                InsertDataToDB.setString(5,email);
                InsertDataToDB.setString(6,password);
                InsertDataToDB.setString(7,number);
                InsertDataToDB.executeUpdate();
                InsertDataToDB.close();

            }

            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            SwitchScene(new Stage(),"SignUp.fxml");
        }
    }
    //For employees that's has special code
    public void LogInUser(Stage stage,String name, String surname, String lastname, String number, LocalDate birthday, String email,String password, String idOfEmployee){
        PreparedStatement UpdateNewData=null;

        String newDate=String.valueOf(birthday);
        if(!CheckUserExist("employee", email)){
            try{
                connection=DriverManager.getConnection(url,userName,password);
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

            SwitchScene(stage,"SignUp.fxml");
        }
    }


    public void signupUser(Stage stage, String email, String password){

        PreparedStatement psInset=null;
        PreparedStatement psCheckUserExist=null;
        PreparedStatement psCheckUsersPassword=null;


        try{
            connection=DriverManager.getConnection(url,userName,password);

            boolean client=CheckUserExist("client", email);
            employee=CheckUserExist("employee", email);
            if(client||employee){

                if(CheckPassword("client", email, password)||CheckPassword("employee", email, password)){

                    if(psCheckUserExist!=null) psCheckUserExist.close();

                    if(employee) SwitchScene(stage,"EmployeeMainMenu.fxml");
                    else  SwitchScene(stage,"MainMenu.fxml");

                }
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
        PreparedStatement InsertNewContract;
        System.out.println(client_id);

        try {
            connection=DriverManager.getConnection(url,userName,password);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime end_date=now.plusDays(validality);
            //  2021/03/22 16:37:15
            InsertNewContract=connection.prepareStatement("INSERT INTO contracts ( client_id, start_date, validality, cost, payout,end_date,status,type_of_insurance) VALUES (?, ?, ?, ?, ?, ?, ?,?)");

            InsertNewContract.setInt(1,client_id);
            // Assuming now is your LocalDateTime
            InsertNewContract.setDate(2, java.sql.Date.valueOf(now.toLocalDate()));
            // Extract the "yyyy-MM-dd" part
            InsertNewContract.setInt(3,validality);
            InsertNewContract.setInt(4, cost);
            InsertNewContract.setInt(5,payout);
            //InsertNewContract.setInt(6,);
            InsertNewContract.setDate(6, java.sql.Date.valueOf(end_date.toLocalDate()));

            InsertNewContract.setString(7,"Приостановлен");
            InsertNewContract.setInt(8,type_of_insurance);
            InsertNewContract.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void SwitchScene(Stage stage, String name_scene) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(name_scene));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    public  Object[] getDescriptionOfInsuranceType(int insurance_id){
        PreparedStatement getDataOfInsuranceType=null;
        ResultSet resultSet=null;
        Object[] dataOfIT=new Object[7];
        try{
            connection=DriverManager.getConnection(url,userName,password);
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
    public ObservableList<Contracts> getContacts(String status) {
        PreparedStatement getContracts = null;
        ResultSet resultSet = null;
        ObservableList<Contracts> contracts = FXCollections.observableArrayList();
        if(status == null) status ="Приостановлен";

        try {
            connection = DriverManager.getConnection(url, userName, password);
            getContracts = connection.prepareStatement("SELECT contracts.ID, contracts.employee_id, contracts.client_id, contracts.start_date, contracts.validality,\n" +
                    "    contracts.cost, contracts.payout, contracts.type_of_insurance, contracts.status,\n" +
                    "    insurancetypes.insurance_name AS name, insuranceevent.contract_ID\n" +
                    "FROM contracts\n" +
                    "JOIN insurancetypes ON contracts.type_of_insurance = insurancetypes.ID\n" +
                    "LEFT JOIN insuranceevent ON contracts.ID = insuranceevent.contract_ID\n" +
                    "WHERE contracts.status = ? AND insuranceevent.contract_ID IS NULL;\n");
            getContracts.setString(1,status);
            resultSet = getContracts.executeQuery();

            while (resultSet.next()) {
                Contracts contracts1 = new Contracts();
                contracts1.setId(resultSet.getInt("contracts.ID"));
                contracts1.setId_client(resultSet.getInt("contracts.client_id"));
                contracts1.setId_employee(resultSet.getInt("contracts.employee_id"));
                contracts1.setStart_date(resultSet.getString("contracts.start_date"));
                contracts1.setValidality(resultSet.getInt("contracts.validality"));
                contracts1.setCost(resultSet.getInt("contracts.cost"));
                contracts1.setPayout(resultSet.getInt("contracts.payout"));
                contracts1.setStatus(resultSet.getString("contracts.status"));
                contracts1.setType_of_insurance(resultSet.getString("name"));
                contracts.add(contracts1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a final block
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (getContracts != null) {
                try {
                    getContracts.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return contracts; // Return the populated list
    }

    @FXML
    protected void UpdateContract(int id){
        PreparedStatement UpdateData = null;
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(url, userName, password)){


            UpdateData= connection.prepareStatement("UPDATE contracts " +
                     "SET " +
                     "status = 'Активен', " +
                     "employee_id = ? "+
                     "WHERE ID = ?");
            // Set the values for each placeholder in the PreparedStatement
            UpdateData.setInt(1, client_id);
            UpdateData.setInt(2, id);

            int rowsUpdated = UpdateData.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful.");
            } else {
                System.out.println("No rows were updated.");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a final block
            if (UpdateData != null) {
                try {
                    UpdateData.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }
    @FXML
    protected void PauseContract(int id){
        PreparedStatement UpdateData = null;
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(url, userName, password)){


            UpdateData= connection.prepareStatement("UPDATE contracts " +
                    "SET " +
                    "status = 'Приостановлен' " +
                    "WHERE ID = ?");
            // Set the values for each placeholder in the PreparedStatement
            UpdateData.setInt(1, id);


            int rowsUpdated = UpdateData.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful.");
            } else {
                System.out.println("No rows were updated.");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a final block
            if (UpdateData != null) {
                try {
                    UpdateData.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }
    @FXML
    protected void NewInsuranceEvent(int id,String description){
        PreparedStatement UpdateData = null;
        ResultSet resultSet = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        try (Connection connection = DriverManager.getConnection(url, userName, password)){


            UpdateData = connection.prepareStatement("INSERT INTO insuranceevent " +
                    "(contract_id, description, date_of_event) " +
                    "VALUES (?, ?, ?)");

            UpdateData.setInt(1, id);
            UpdateData.setString(2, description);
            UpdateData.setDate(3,java.sql.Date.valueOf(now.toLocalDate()));


            int rowsUpdated = UpdateData.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful.");
            } else {
                System.out.println("No rows were updated.");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a final block
            if (UpdateData != null) {
                try {
                    UpdateData.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }
    @FXML
    protected void DeleteContract(int id) {
        PreparedStatement DeleteData = null;

        try (Connection connection = DriverManager.getConnection(url, userName, password)) {


               DeleteData = connection.prepareStatement("DELETE FROM contracts WHERE ID= ?");
            DeleteData.setInt(1,id);
                DeleteData.executeUpdate();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a final block
            if (DeleteData != null) {
                try {
                    DeleteData.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
        public int[] getInsuranceStatistics( String startDate, String endDate) {
        int[] statistics = new int[10]; // An array to store the statistics

        try {

            Connection connection = DriverManager.getConnection(url, userName, password);
            // Calculate the number of active contracts
            int activeContracts = calculateActiveContracts(connection);
            int profitLoosses= calculateLoosses(connection,startDate,endDate);
            // Calculate the number of completed contracts
            int completedContracts = calculateCompletedContracts(connection);

            // Calculate the number of suspended contracts
            int suspendedContracts = calculateSuspendedContracts(connection);

            // Calculate profit or loss
            int profit = calculateProfit(connection, startDate, endDate);

            // Calculate the number of customers
            int numCustomers = calculateNumberOfCustomers(connection);

            // Calculate the number of employees
            int numEmployees = calculateNumberOfEmployees(connection);

            // Calculate the ratio of customers to contracts
            int customerToContractRatio = numCustomers / (activeContracts + completedContracts + suspendedContracts);

            // Populate the statistics array
            statistics[0] = activeContracts + completedContracts + suspendedContracts;
            statistics[1] = profit;
            statistics[2] = customerToContractRatio;

            statistics[3] = numCustomers;
            statistics[4] = numEmployees;

            statistics[5] = numCustomers / (activeContracts + completedContracts);
            statistics[6]=activeContracts;
            statistics[7]=completedContracts;
            statistics[8]=suspendedContracts;
            statistics[9]=profitLoosses;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statistics;
    }

    private int calculateActiveContracts(Connection connection) {
        int activeContracts = 0;
        try {
            String query = "SELECT COUNT(*) FROM contracts WHERE status = 'Активен' ";
            PreparedStatement statement = connection.prepareStatement(query);


            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                activeContracts = resultSet.getInt(1);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeContracts;
    }

    private int calculateCompletedContracts(Connection connection) {
        int completedContracts = 0;
        try {
            String query = "SELECT COUNT(*) FROM contracts WHERE status = 'Завершен' ";
            PreparedStatement statement = connection.prepareStatement(query);


            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                completedContracts = resultSet.getInt(1);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return completedContracts;
    }

    private int calculateSuspendedContracts(Connection connection) {
        int suspendedContracts = 0;
        try {
            String query = "SELECT COUNT(*) FROM contracts WHERE status = 'Приостановлен' ";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                suspendedContracts = resultSet.getInt(1);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suspendedContracts;
    }

    private int calculateProfit(Connection connection, String startDate, String endDate) {
        int profitLoss = 0;
        try {
            String query = "SELECT SUM(cost) FROM contracts WHERE start_date BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, startDate);
            statement.setString(2, endDate);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                profitLoss = resultSet.getInt(1);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profitLoss;
    }

    private int calculateNumberOfCustomers(Connection connection) {
        int numCustomers = 0;
        try {
            String query = "SELECT COUNT(*) FROM client";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                numCustomers = resultSet.getInt(1);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numCustomers;
    }

    private int calculateNumberOfEmployees(Connection connection) {
        int numEmployees = 0;
        try {
            String query = "SELECT COUNT(*) FROM employee";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                numEmployees = resultSet.getInt(1);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numEmployees;
    }

    private int calculateLoosses(Connection connection, String startDate, String endDate) {
        int profitLoss = 0;
        try {
            String query = "SELECT SUM(contracts.payout),contracts.ID,insuranceevent.ID FROM contracts JOIN insuranceevent "+
                    "ON contracts.ID=insuranceevent.contract_ID WHERE insuranceevent.date_of_event BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, startDate);
            statement.setString(2, endDate);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                profitLoss = resultSet.getInt(1);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profitLoss;
    }


    public ObservableList<Contracts> getContactsForClient() {
        PreparedStatement getContracts = null;
        ResultSet resultSet = null;
        ObservableList<Contracts> contracts = FXCollections.observableArrayList();


        try {
            connection = DriverManager.getConnection(url, userName, password);
            getContracts = connection.prepareStatement("SELECT contracts.ID, contracts.client_id, contracts.start_date, contracts.validality, \n" +
                    "   contracts.type_of_insurance, contracts.status \n" +
                    "FROM contracts \n " +
                    "WHERE contracts.client_id = ? AND contracts.status = 'Активен' ");
            getContracts.setInt(1,client_id);
            resultSet = getContracts.executeQuery();

            while (resultSet.next()) {
                Contracts contracts1 = new Contracts();
                contracts1.setId(resultSet.getInt("contracts.ID"));

                contracts1.setStart_date(resultSet.getString("contracts.start_date"));
                contracts1.setValidality(resultSet.getInt("contracts.validality"));
                contracts1.setStatus(resultSet.getString("contracts.status"));

                contracts.add(contracts1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a final block
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (getContracts != null) {
                try {
                    getContracts.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return contracts; // Return the populated list
    }



    public ObservableList<Contracts> getInsuranceEventContacts() {
        PreparedStatement getContracts = null;
        ResultSet resultSet = null;
        ObservableList<Contracts> contracts = FXCollections.observableArrayList();


        try {
            connection = DriverManager.getConnection(url, userName, password);
            getContracts = connection.prepareStatement("SELECT * FROM `insuranceevent`");

            resultSet = getContracts.executeQuery();

            while (resultSet.next()) {
                Contracts contracts1 = new Contracts();
                contracts1.setId(resultSet.getInt("ID"));
                contracts1.setId_client(resultSet.getInt("contract_ID"));
                contracts1.setStart_date(resultSet.getString("date_of_event"));
                contracts1.setStatus(resultSet.getString(String.valueOf("description")));
                contracts.add(contracts1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (getContracts != null) {
                try {
                    getContracts.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return contracts;
    }

}


















