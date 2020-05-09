/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main_menu.modify_product;

import java.net.URL;
import java.util.ResourceBundle;

import inventory.Inventory;
import inventory.Part;
import inventory.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import utility.Utility;

/**
 * FXML Controller class
 *
 * @author Benjamin Crew
 */
public class ModifyProductMenuController {
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private ChoiceBox productIdBox;
    @FXML
    private TableColumn<Part, Integer> allPartsIdColumn;
    @FXML
    private TableColumn<Part, String> allPartsNameColumn;
    @FXML
    private TableColumn<Part, Integer> allPartsInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> allPartsPriceColumn;
    @FXML
    private TableColumn<Part, Integer> productPartsIdColumn;
    @FXML
    private TableColumn<Part, String> productPartsNameColumn;
    @FXML
    private TableColumn<Part, Integer> productPartsInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> productPartsPriceColumn;
    @FXML
    private TableView<Part> allPartsTableView;
    @FXML
    private TableView<Part> productPartsTableView;
    @FXML
    private TextField inventoryBox;
    @FXML
    private TextField maximumBox;
    @FXML
    private TextField minimumBox;
    @FXML
    private TextField searchBox;
    @FXML
    private TextField productNameBox;
    @FXML
    private TextField priceBox;

    private Inventory currentInventory;

    private ObservableList<Part> allPartsList;
    private ObservableList<Part> productPartsList;
    private ObservableList<Part> searchedPartsList;

    private Utility utility;

    public void initialize() {
        ObservableList<String> partIdBoxChoices = FXCollections.observableArrayList();

        partIdBoxChoices.add("Auto-generated");

        utility = new Utility();

        currentInventory = Inventory.getInstance();
        allPartsList = currentInventory.getAllParts();
        productPartsList = FXCollections.observableArrayList();
        searchedPartsList = FXCollections.observableArrayList();

        productIdBox.setItems(partIdBoxChoices);
        productIdBox.getSelectionModel().selectFirst();

        allPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPartsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        allPartsTableView.setItems(allPartsList);
        allPartsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        productNameBox.setText(Inventory.selectedProduct.getName());
        inventoryBox.setText(Integer.toString(Inventory.selectedProduct.getStock()));
        priceBox.setText(Double.toString(Inventory.selectedProduct.getPrice()));
        minimumBox.setText(Integer.toString(Inventory.selectedProduct.getMin()));
        maximumBox.setText(Integer.toString(Inventory.selectedProduct.getMax()));

        productPartsList = Inventory.selectedProduct.getAllAssociatedParts();

        productPartsTableView.setItems(productPartsList);
        productPartsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void addAssociatedPart () {
        Inventory.selectedPart = allPartsTableView.getSelectionModel().getSelectedItem();

        int partId;
        int currentStock;

        if (Inventory.selectedPart != null) {
            partId = Inventory.selectedPart.getId();
            currentStock = currentInventory.lookupPart(partId).getStock();

            if (currentStock > 0) {
                //currentInventory.lookupPart(partId).setStock(currentStock - 1);
                productPartsList.add(currentInventory.lookupPart(partId));

                allPartsTableView.refresh();
            }
        }
    }

    @FXML
    private void deleteAssociatedPart () {
        Inventory.selectedPart = productPartsTableView.getSelectionModel().getSelectedItem();

        int partId;
        //int currentStock;

        Alert delete;

        if (Inventory.selectedPart != null) {
            delete = utility.displayConfirm("Delete", "Are you sure you want to delete this part from the product?");

            if (delete.getResult().equals(ButtonType.YES)) {
                partId = Inventory.selectedPart.getId();
                //currentStock = currentInventory.lookupPart(partId).getStock();

                //currentInventory.lookupPart(partId).setStock(currentStock + 1);
                productPartsList.remove(currentInventory.lookupPart(partId));

                allPartsTableView.refresh();
            }
        }
    }

    @FXML
    private void partSearch(ActionEvent event) {
        if (!searchBox.getText().equals("")) {
            searchedPartsList.removeAll(searchedPartsList);
            searchedPartsList.addAll(currentInventory.lookupPart(searchBox.getText()));

            try {
                searchedPartsList.addAll(currentInventory.lookupPart(Integer.parseInt(searchBox.getText())));
            } catch (Exception error) {
            }

            allPartsTableView.setItems(searchedPartsList);
        }
        else
            allPartsTableView.setItems(allPartsList);
    }

    @FXML
    private void saveButtonClicked(ActionEvent event) {
        ObservableList<Product> productsList = currentInventory.getAllProducts();

        Product modifiedProduct;

        String productName;

        int id;
        double price;
        int stock;
        int minimum;
        int maximum;

        if (validateFields()) {
            id = Inventory.selectedProduct.getId();
            productName = productNameBox.getText();
            price = Double.parseDouble(priceBox.getText());
            stock = Integer.parseInt(inventoryBox.getText());
            minimum = Integer.parseInt(minimumBox.getText());
            maximum = Integer.parseInt(maximumBox.getText());

            if (!productPartsList.isEmpty()) {
                modifiedProduct = new Product(id, productName, price, stock, minimum, maximum);
                currentInventory.updateProduct(productsList.indexOf(Inventory.selectedProduct), modifiedProduct);

                for (int i = 0; i < productPartsList.size(); i++) {
                    modifiedProduct.addAssociatedPart(productPartsList.get(i));
                }

                saveButton.getScene().getWindow().hide();
            }
            else
                utility.displayError("A product must have at least one associated part.");
        }
    }

    private Boolean validateFields() {
        Double partsPriceTotal = 0.0;
        String errorText = "";

        if ("".equals(productNameBox.getText()))
            errorText = "Product name can not be blank.";
        else if ("".equals(inventoryBox.getText()))
            errorText = "Inventory level can not be blank.";
        else if ("".equals(priceBox.getText()))
            errorText = "Price can not be blank.";
        else if ("".equals(minimumBox.getText()))
            errorText = "Minimum can not be blank.";
        else if ("".equals(maximumBox.getText()))
            errorText = "Maximum can not be blank.";

        else if (!utility.validateInteger(inventoryBox.getText()))
            errorText = "The inventory level must be a positive integer.";
        else if (!utility.validateDouble(priceBox.getText()))
            errorText = "The price must be a positive decimal number.";
        else if (!utility.validateInteger(minimumBox.getText()))
            errorText = "The minimum stock must be a positive integer.";
        else if (!utility.validateInteger(maximumBox.getText()))
            errorText = "The maximum stock must be a positive integer.";

        else if ((Integer.parseInt(minimumBox.getText()) > Integer.parseInt(maximumBox.getText())) || (Integer.parseInt(maximumBox.getText()) < Integer.parseInt(minimumBox.getText())))
            errorText = "The minimum value can not exceed the maximum value.";
        else if ((Integer.parseInt(inventoryBox.getText()) < Integer.parseInt(minimumBox.getText())) || (Integer.parseInt(inventoryBox.getText()) > Integer.parseInt(maximumBox.getText())))
            errorText = "The inventory level must fall between the minimum and maximum values.";

        // Ensure the price of the product is not less than the combined price of the associated parts.
        for (int i = 0; i < productPartsList.size(); i++) {
            partsPriceTotal += productPartsList.get(i).getPrice();
        }

        if (Double.parseDouble(priceBox.getText()) < partsPriceTotal)
            errorText = "The product price shouldn't be less than the total price of the associated parts.";

        if (errorText.equals(""))
            return true;
        else {
            utility.displayError(errorText);
            return false;
        }
    }

    @FXML
    private void cancelButtonClicked(ActionEvent event) {
        Alert cancel;

        cancel = utility.displayConfirm("Cancel", "Are you sure you want to cancel modifying this product?");
        if (cancel.getResult().equals(ButtonType.YES))
            cancelButton.getScene().getWindow().hide();
    }
}
