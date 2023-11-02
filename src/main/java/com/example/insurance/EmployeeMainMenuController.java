package com.example.insurance;

import com.example.insurance.Contracts;
import com.example.insurance.DataBaseConnectionVerification;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory; // Correct import
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmployeeMainMenuController implements Initializable {

    @FXML
    private Tab contract;

    @FXML
    private TableView<Contracts> contracts; // Specify the type for TableView

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

// ...

    @FXML
    protected void upDateTable() {
        ObservableList<Contracts> list = FXCollections.observableArrayList();
        DataBaseConnectionVerification DB = new DataBaseConnectionVerification();
        list = DB.getContacts();
        client.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        payout.setCellValueFactory(new PropertyValueFactory<>("payout"));
        start_date.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        validality.setCellValueFactory(new PropertyValueFactory<>("validality"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        type.setCellValueFactory(new PropertyValueFactory<>("type_of_insurance"));

        // Set cell factories for the "Agree" and "Disagree" columns
        agreeButtonColumn.setCellValueFactory(param -> new SimpleBooleanProperty(true).asObject());
        disagreeButtonColumn.setCellValueFactory(param -> new SimpleBooleanProperty(true).asObject());

        agreeButtonColumn.setCellFactory(param -> new TableCell<Contracts, Boolean>() {
            final Button agreeButton = new Button("Agree");
            {
                agreeButton.setOnAction(event -> {
                    Contracts contract = getTableView().getItems().get(getIndex());
                    // Handle the "Agree" button click here for the specific contract.
                    System.out.println("Agreed to contract ID: " + contract.getId());
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

        disagreeButtonColumn.setCellFactory(param -> new TableCell<Contracts, Boolean>() {
            final Button disagreeButton = new Button("Disagree");
            {
                disagreeButton.setOnAction(event -> {
                    Contracts contract = getTableView().getItems().get(getIndex());
                    // Handle the "Disagree" button click here for the specific contract.
                    System.out.println("Disagreed with contract ID: " + contract.getId());
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

// ...

// Create a cell value factory for the boolean columns (Agree and Disagree buttons)




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       upDateTable();

    }
}
