package com.example.insurance;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeMainMenuController implements Initializable {

    @FXML
    private Label clearProfit;
    @FXML
    private Button exitButton;
    @FXML
    private Label insuranceEventLose;
    @FXML
    private Label profit;
    @FXML
    private Label employee;
    @FXML
    private Label clients;
    @FXML
    private Label clientsPerContract;
    @FXML
    private Label activeContracts;
    @FXML
    private Label pausedContracts;
    @FXML
    private Label passedContracts;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    private String sts="Приостановлен";
    @FXML
    private TableView<Contracts> contracts;

    @FXML
    private TableColumn<Contracts, Integer> client;

    @FXML
    private TableColumn<Contracts, Integer> cost;

    @FXML
    private TableColumn<Contracts, Integer> id;

    @FXML
    private TableColumn<Contracts, Integer> payout;

    @FXML
    private TableColumn<Contracts, String> start_date;

    @FXML
    private TableColumn<Contracts, String> status;

    @FXML
    private TableColumn<Contracts, String> type;

    @FXML
    private TableColumn<Contracts, Integer> validity;
    @FXML
    private TableColumn<Contracts, Boolean> agreeButtonColumn;

    @FXML
    private TableColumn<Contracts, Boolean> disagreeButtonColumn;


    @FXML
    protected void upDateTable() {
        ObservableList<Contracts> listOfClientData;
        DataBaseConnectionVerification DB = new DataBaseConnectionVerification();
        if(sts!="InsuranceEvent"){
        listOfClientData = DB.getContacts(sts);
        client.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        payout.setCellValueFactory(new PropertyValueFactory<>("payout"));
        start_date.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        validity.setCellValueFactory(new PropertyValueFactory<>("validality"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        type.setCellValueFactory(new PropertyValueFactory<>("type_of_insurance"));}
        else{
            listOfClientData=DB.getInsuranceEventContacts();
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            client.setCellValueFactory(new PropertyValueFactory<>("id_clientk"));
            start_date.setCellValueFactory(new PropertyValueFactory<>(String.valueOf( "start_date")));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
        }
        if ("Приостановлен".equals(sts)) {
            if (!contracts.getColumns().contains(agreeButtonColumn)) {
                contracts.getColumns().add(agreeButtonColumn);
            }
            if (!contracts.getColumns().contains(disagreeButtonColumn)) {
                contracts.getColumns().add(disagreeButtonColumn);
            }
        } else {
            contracts.getColumns().remove(agreeButtonColumn);
            contracts.getColumns().remove(disagreeButtonColumn);
        }

                  // Set cell factories for the "Agree" and "Disagree" columns
            agreeButtonColumn.setCellValueFactory(param -> new SimpleBooleanProperty(true).asObject());
            disagreeButtonColumn.setCellValueFactory(param -> new SimpleBooleanProperty(true).asObject());

            agreeButtonColumn.setCellFactory(param -> new TableCell<>() {
                final Button agreeButton = new Button("Одобрить");

                {
                    agreeButton.setOnAction(event -> {
                        Contracts contract = getTableView().getItems().get(getIndex());
                        DB.UpdateContract(contract.getId());

                        upDateTable();
                    });
                }

                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(agreeButton);
                    }
                }
            });

            disagreeButtonColumn.setCellFactory(param -> new TableCell<>() {
                final Button disagreeButton = new Button("Отклонить");

                {
                    disagreeButton.setOnAction(event -> {
                        Contracts contract = getTableView().getItems().get(getIndex());
                        DB.DeleteContract(contract.getId());

                        upDateTable();
                    });
                }

                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(disagreeButton);
                    }
                }
            });


        contracts.setItems(listOfClientData);
    }
    @FXML
    protected void getActiveContracts(){
        sts="Активен";
        upDateTable();
    }
    @FXML
    protected void getPausedContracts(){
       sts="Приостановлен";
        upDateTable();
    }
    @FXML
    protected void getPassedContracts(){
        sts="Завершен";
        upDateTable();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       upDateTable();

    }
    @FXML
    protected void getStatistic(){
        if(startDate.getValue()==null||endDate.getValue()==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Введите данные во все поля!");

            alert.setTitle("Данные не введены во все поля");
            alert.show();
        }
        else
        {
        DataBaseConnectionVerification DB=new DataBaseConnectionVerification();
        int[] stat=DB.getInsuranceStatistics(String.valueOf( startDate.getValue()),String.valueOf(endDate.getValue()));
        clients.setText(String.valueOf( stat[3]));
        employee.setText(String.valueOf( stat[4]));
        clientsPerContract.setText(String.valueOf( stat[2]));
        insuranceEventLose.setText(String.valueOf( stat[9]));
        activeContracts.setText(String.valueOf( stat[6]));
        pausedContracts.setText(String.valueOf( stat[8]));
        passedContracts.setText((String.valueOf( stat[7])));
        profit.setText(String.valueOf(stat[1]));
        clearProfit.setText(String.valueOf(stat[1]-stat[9]));
        }
    }
    @FXML
    protected void getOut(){
        try {
            // Load the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent root = loader.load();

            // Create the stage and set the new scene
            Stage stage = (Stage) exitButton.getScene().getWindow();
            Scene scene = new Scene(root,1280,720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    protected void getInsuranceEventContracts(){

        sts="InsuranceEvent";
        upDateTable();
    }


}
