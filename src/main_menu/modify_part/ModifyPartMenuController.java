/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main_menu.modify_part;

import inventory.InHouse;
import inventory.Inventory;
import inventory.Outsourced;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import inventory.Part;
import utility.Utility;

/**
 * FXML Controller class
 *
 * @author Benjamin Crew
 */
public class ModifyPartMenuController {

    @FXML
    private ToggleGroup InHouse_Outsourced;

    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private ChoiceBox partIdBox;
    @FXML
    private Label companyNameLabel;
    @FXML
    private Label machineIDLabel;
    @FXML
    private RadioButton inHouseButton;
    @FXML
    private RadioButton outsourcedButton;
    @FXML
    private TextField companyNameBox;
    @FXML
    private TextField inventoryBox;
    @FXML
    private TextField machineIDBox;
    @FXML
    private TextField maximumBox;
    @FXML
    private TextField minimumBox;
    @FXML
    private TextField partNameBox;
    @FXML
    private TextField priceBox;

    private Utility utility;

    @FXML
    private void initialize () {
        utility = new Utility();

        ObservableList<String> partIdBoxChoices = FXCollections.observableArrayList();

        partIdBoxChoices.add("Auto-generated");

        partIdBox.setItems(partIdBoxChoices);
        partIdBox.getSelectionModel().selectFirst();

        if (Inventory.selectedPart instanceof InHouse) {
            InHouse_Outsourced.selectToggle(inHouseButton);
            inHouseButtonClicked();
            machineIDBox.setText(Integer.toString(((InHouse) Inventory.selectedPart).getMachineId()));
        }
        else if (Inventory.selectedPart instanceof Outsourced) {
            InHouse_Outsourced.selectToggle(outsourcedButton);
            outsourcedButtonClicked();
            companyNameBox.setText(((Outsourced) Inventory.selectedPart).getCompanyName());
        }

        partNameBox.setText(Inventory.selectedPart.getName());
        inventoryBox.setText(Integer.toString(Inventory.selectedPart.getStock()));
        priceBox.setText(Double.toString(Inventory.selectedPart.getPrice()));
        minimumBox.setText(Integer.toString(Inventory.selectedPart.getMin()));
        maximumBox.setText(Integer.toString(Inventory.selectedPart.getMax()));
    }

    @FXML
    private void inHouseButtonClicked () {
        companyNameLabel.setVisible(false);

        companyNameBox.setVisible(false);
        companyNameBox.setDisable(true);

        machineIDLabel.setVisible(true);

        machineIDBox.setVisible(true);
        machineIDBox.setDisable(false);
    }

    @FXML
    private void outsourcedButtonClicked () {
        machineIDLabel.setVisible(false);

        machineIDBox.setVisible(false);
        machineIDBox.setDisable(true);

        companyNameLabel.setVisible(true);

        companyNameBox.setVisible(true);
        companyNameBox.setDisable(false);
    }

    private Boolean validateFields() {
        String errorText = "";

        if ("".equals(partNameBox.getText()))
            errorText = "Part name can not be blank.";
        else if ("".equals(inventoryBox.getText()))
            errorText = "Inventory level can not be blank.";
        else if ("".equals(priceBox.getText()))
            errorText = "Price can not be blank.";
        else if ("".equals(minimumBox.getText()))
            errorText = "Minimum can not be blank.";
        else if ("".equals(maximumBox.getText()))
            errorText = "Maximum can not be blank.";
        else if ((inHouseButton.selectedProperty().getValue()) && "".equals(machineIDBox.getText()))
            errorText = "Machine ID can not be blank.";
        else if ((outsourcedButton.selectedProperty().getValue()) && "".equals(companyNameBox.getText()))
            errorText = "Company name can not be blank.";

        else if (!utility.validateInteger(inventoryBox.getText()))
            errorText = "The inventory level must be a positive integer.";
        else if (!utility.validateDouble(priceBox.getText()))
            errorText = "The price must be a positive decimal number.";
        else if (!utility.validateInteger(minimumBox.getText()))
            errorText = "The minimum stock must be a positive integer.";
        else if (!utility.validateInteger(maximumBox.getText()))
            errorText = "The maximum stock must be a positive integer.";
        else if (inHouseButton.selectedProperty().getValue() && !utility.validateInteger(machineIDBox.getText()))
            errorText = "The machine ID must be a positive integer.";

        else if ((Integer.parseInt(minimumBox.getText()) > Integer.parseInt(maximumBox.getText())) || (Integer.parseInt(maximumBox.getText()) < Integer.parseInt(minimumBox.getText())))
            errorText = "The minimum value can not exceed the maximum value.";

        if (errorText.equals(""))
            return true;
        else {
            utility.displayError(errorText);
            return false;
        }
    }

    @FXML
    private void validatePriceBox (KeyEvent event) {
        Utility checkNumber = new Utility();

        // Ensure the key is either a number or decimal point, and don't allow multiple decimal points.
        if (checkNumber.checkDecimalPoint(priceBox.getText()) && event.getCharacter().equals(".")) {
            event.consume();
        }
        else {
            checkNumber.checkDecimalKey(event);
        }
    }

    @FXML
    private void validateIntegerBox (KeyEvent event) {
        utility.checkIntegerKey(event);
    }

    @FXML
    private void saveButtonClicked(ActionEvent event) {
        Inventory currentInventory = inventory.Inventory.getInstance();
        ObservableList<Part> partsList = currentInventory.getAllParts();

        String partName = partNameBox.getText();

        int id;
        double price;
        int stock;
        int minimum;
        int maximum;

        if (validateFields()) {
            id = Inventory.selectedPart.getId();
            price = Double.parseDouble(priceBox.getText());
            stock = Integer.parseInt(inventoryBox.getText());
            minimum = Integer.parseInt(minimumBox.getText());
            maximum = Integer.parseInt(maximumBox.getText());

            if (inHouseButton.selectedProperty().getValue()) {
                currentInventory.updatePart(partsList.indexOf(Inventory.selectedPart), new InHouse(id, partName, price, stock,
                        minimum, maximum, Integer.parseInt(machineIDBox.getText())));
            }
            else if (outsourcedButton.selectedProperty().getValue()) {
                currentInventory.updatePart(partsList.indexOf(Inventory.selectedPart), new Outsourced(id, partName, price, stock,
                        minimum, maximum, companyNameBox.getText()));
            }
            else
                utility.displayError("Unknown part category error.");

            saveButton.getScene().getWindow().hide();
        }
    }

    @FXML
    private void cancelButtonClicked(ActionEvent event) {
        Alert cancel;

        cancel = utility.displayConfirm("Cancel", "Are you sure you want to cancel modifying this part?");
        if (cancel.getResult().equals(ButtonType.YES))
            cancelButton.getScene().getWindow().hide();
    }
}
