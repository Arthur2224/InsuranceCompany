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
import javafx.scene.control.cell.TreeItemPropertyValueFactory; // Correct import

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeMainMenuController implements Initializable {

    @FXML
    private Tab contract;

    @FXML
    private TableView<Contracts> contracts; // Specify the type for TableView

    @FXML
    private TreeTableColumn<Contracts, Integer> client;

    @FXML
    private TreeTableColumn<Contracts, Integer> cost;

    @FXML
    private TreeTableColumn<Contracts, Integer> id;

    @FXML
    private TreeTableColumn<Contracts, Integer> payout;

    @FXML
    private TreeTableColumn<Contracts, String> start_date;

    @FXML
    private TreeTableColumn<Contracts, String> status;

    @FXML
    private TreeTableColumn<Contracts, String> type;

    @FXML
    private TableColumn<Contracts, Integer> validality;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Contracts> list = FXCollections.observableArrayList();
        DataBaseConnectionVerification DB = new DataBaseConnectionVerification();
        list = DB.getContacts();
        client.setCellValueFactory(new TreeItemPropertyValueFactory<>("id_client"));
        cost.setCellValueFactory(new TreeItemPropertyValueFactory<>("cost"));
        id.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        payout.setCellValueFactory(new TreeItemPropertyValueFactory<>("payout"));
        start_date.setCellValueFactory(new TreeItemPropertyValueFactory<>("start_date"));
        validality.setCellValueFactory(new TreeItemPropertyValueFactory<>("validality"));
        status.setCellValueFactory(new TreeItemPropertyValueFactory<>("status"));
        type.setCellValueFactory(new TreeItemPropertyValueFactory<>("type_of_insurance"));

        contracts.setItems(list);
    }
}
