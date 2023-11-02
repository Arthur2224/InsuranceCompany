package com.example.insurance;

import com.example.insurance.Contracts;
import com.example.insurance.DataBaseConnectionVerification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory; // Correct import

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        contracts.setItems(list);
    }
}
