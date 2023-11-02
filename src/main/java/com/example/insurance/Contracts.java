package com.example.insurance;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contracts {
    private final IntegerProperty id=new SimpleIntegerProperty();
    private  final IntegerProperty id_client=new SimpleIntegerProperty();
    private final IntegerProperty id_employee=new SimpleIntegerProperty();
    private  final StringProperty start_date=new SimpleStringProperty();
    private final IntegerProperty validality=new SimpleIntegerProperty();
    private final IntegerProperty cost =new SimpleIntegerProperty();
    private  final IntegerProperty payout=new SimpleIntegerProperty();
    private final StringProperty type_of_insurance=new SimpleStringProperty();
    private final StringProperty status=new SimpleStringProperty();


    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getType_of_insurance() {
        return type_of_insurance.get();
    }

    public StringProperty type_of_insuranceProperty() {
        return type_of_insurance;
    }

    public int getPayout() {
        return payout.get();
    }

    public IntegerProperty payoutProperty() {
        return payout;
    }

    public int getCost() {
        return cost.get();
    }

    public IntegerProperty costProperty() {
        return cost;
    }

    public int getValidality() {
        return validality.get();
    }

    public IntegerProperty validalityProperty() {
        return validality;
    }

    public String getStart_date() {
        return start_date.get();
    }

    public StringProperty start_dateProperty() {
        return start_date;
    }

    public int getId_employee() {
        return id_employee.get();
    }

    public IntegerProperty id_employeeProperty() {
        return id_employee;
    }

    public int getId_client() {
        return id_client.get();
    }

    public IntegerProperty id_clientProperty() {
        return id_client;
    }
    public void setId(int value) {id.set(value);}
    public void setId_client(int value){
        id_client.set(value);
    }
    public void setId_employee(int value){
        id_employee.set(value);
    }
    public void setStart_date(String value){
        start_date.set(value);
    }
    public void setValidality(int value){
        validality.set(value);
    }
    public void setCost(int value){
        cost.set(value);
    }
    public void setPayout(int value){
        payout.set(value);
    }
    public void setType_of_insurance(String value){
        type_of_insurance.set(value);
    }
    public void setStatus(String value){
        status.set(value);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }
}
