package com.example.insurance;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeMainMenuController implements Initializable {
    @FXML
    private LineChart lineChart;
    @FXML
    private Label clearProfit;
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
    private Label ActiveContracts;
    @FXML
    private Label PausedContracts;
    @FXML
    private Label PassedContracts;
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
    private TableColumn<Contracts, Integer> validality;
    @FXML
    private TableColumn<Contracts, Boolean> agreeButtonColumn;

    @FXML
    private TableColumn<Contracts, Boolean> disagreeButtonColumn;


    @FXML
    protected void upDateTable() {
        ObservableList<Contracts> list;
        DataBaseConnectionVerification DB = new DataBaseConnectionVerification();

        list = DB.getContacts(sts);
        client.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        payout.setCellValueFactory(new PropertyValueFactory<>("payout"));
        start_date.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        validality.setCellValueFactory(new PropertyValueFactory<>("validality"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        type.setCellValueFactory(new PropertyValueFactory<>("type_of_insurance"));
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
                final Button agreeButton = new Button("Agree");

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
                final Button disagreeButton = new Button("Disagree");

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


        contracts.setItems(list);
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
        insuranceEventLose.setText(String.valueOf( stat[1]));
        ActiveContracts.setText(String.valueOf( stat[6]));
        PausedContracts.setText(String.valueOf( stat[8]));
        PassedContracts.setText((String.valueOf( stat[7])));
        }
    }


}
