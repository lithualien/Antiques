package com.github.lithualien.controller;

import com.github.lithualien.model.object.Antique;
import com.github.lithualien.model.Dao;
import com.github.lithualien.model.DaoImpl;
import com.github.lithualien.model.object.Condition;
import com.github.lithualien.model.object.Location;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class Controller {
    private Dao dao = new DaoImpl();

    @FXML
    private TableView<Antique> antiquesTable;

   @FXML
   private TableColumn<Antique, Integer> id;

    @FXML
    private TableColumn<Antique, String> name, condition, date, city;

    @FXML
    private TableColumn<Antique, Double> price;

    @FXML
    private ChoiceBox<Location> locations;

    @FXML
    private ChoiceBox<Condition> conditions;

    @FXML
    private Label output;

    @FXML
    private void getAllAntiques(MouseEvent event) {
        antiquesTable.setItems(dao.getAntiques());
    }

    @FXML
    private void getSelectedItems(MouseEvent event) {
        if(locations.getValue() == null && conditions.getValue() != null) {
            antiquesTable.setItems(dao.getAntiquesByCondition(conditions.getValue()));
        }
        else if(locations.getValue() != null && conditions.getValue() == null) {
            antiquesTable.setItems(dao.getAntiquesByLocation(locations.getValue()));
        } else if (locations.getValue() == null && conditions.getValue() == null) {

        } else {
            antiquesTable.setItems(dao.getAntiquesByConditionAndLocation(conditions.getValue(), locations.getValue()));
        }
        conditions.setValue(null);
        locations.setValue(null);
    }

    @FXML
    private void getPriceSum(MouseEvent event) {
        output.setText("Sum of all antiques: " + dao.getSumOfAllAntiques() + "€");
    }

    @FXML
    private void getBelowAverage(MouseEvent event) {
        antiquesTable.setItems(dao.getBelowAverage());
    }

    @FXML void getAboveAverage(MouseEvent event) {
        antiquesTable.setItems(dao.getAboveAverage());
    }

    private void setDefaultText() {
        antiquesTable.setPlaceholder(new Label("Select operations from below"));
    }

    private void setLocations() {
        locations.setItems(dao.getLocations());
    }

    private void setConditions() {
        conditions.setItems(dao.getConditions());
    }

    private void setTableColumns() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        condition.setCellValueFactory(new PropertyValueFactory<>("condition"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        city.setCellValueFactory(new PropertyValueFactory<>("location"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML
    public void initialize() {
        setDefaultText();
        setTableColumns();
        setLocations();
        setConditions();
    }
}
