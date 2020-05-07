package main_menu;

import inventory.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import main_menu.modify_part.ModifyPartMenuController;
import utility.Utility;

/**
 * FXML Controller class
 *
 * @author Benjamin Crew
 */
public class MainMenuController {
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TextField partSearchBox;

    private Stage addPartMenuStage;
    private Stage addProductMenuStage;
    private Stage modifyPartMenuStage;
    private Stage modifyProductMenuStage;

    private Inventory currentInventory;

    private Utility utility = new Utility();

    private ObservableList<Part> allPartsList;
    private ObservableList<Part> searchedPartsList;
    private ObservableList<Product> allProductsList;
    private ObservableList<Product> searchedProductsList;

    @FXML
    private void initialize() {
        currentInventory = Inventory.getInstance();
        allPartsList = currentInventory.getAllParts();
        searchedPartsList = FXCollections.observableArrayList();
        
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        Part testPart = new InHouse(0, "Test Part", 10.00, 5, 2, 10, 7);
        Part testPart2 = new Outsourced(1, "Test Part 2", 50.00, 6, 3, 10, "Company A");
        Part testPart3 = new Outsourced (2, "Test Part 3", 50.00, 7, 4, 10, "Company B");

        currentInventory.addPart(testPart);
        currentInventory.addPart(testPart2);
        currentInventory.addPart(testPart3);
        
        partsTableView.setItems(allPartsList);
        partsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        productsTableView.setItems(allProductsList);
        productsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void exitButton(ActionEvent event) {
        javafx.application.Platform.exit();
    }

    @FXML
    private void partAddButton(ActionEvent event) {
        addPartMenuStage = utility.LoadFXML("/main_menu/add_part/AddPartMenu.fxml");
        addPartMenuStage.initModality(Modality.APPLICATION_MODAL);
        addPartMenuStage.show();
    }

    @FXML
    private void partDeleteButton(ActionEvent event) {
        currentInventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void partModifyButton(ActionEvent event) {
        Inventory.selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        ModifyPartMenuController controller;

        if (Inventory.selectedPart != null) {
            modifyPartMenuStage = utility.LoadFXML("/main_menu/modify_part/ModifyPartMenu.fxml");

            modifyPartMenuStage.initModality(Modality.APPLICATION_MODAL);
            modifyPartMenuStage.show();
        }
    }

    @FXML
    private void partSearch(ActionEvent event) {
        searchedPartsList.removeAll(searchedPartsList);
        searchedPartsList.addAll(currentInventory.lookupPart(partSearchBox.getText()));

        try {
            searchedPartsList.addAll(currentInventory.lookupPart(Integer.parseInt(partSearchBox.getText())));
        }
        catch (Exception error){
        }

        partsTableView.setItems(searchedPartsList);
    }

    @FXML
    private void productAddButton(ActionEvent event) {
        addProductMenuStage = utility.LoadFXML("/main_menu/add_product/AddProductMenu.fxml");
        addProductMenuStage.initModality(Modality.APPLICATION_MODAL);
        addProductMenuStage.show();
    }

    @FXML
    private void productDeleteButton(ActionEvent event) {
    }

    @FXML
    private void productModifyButton(ActionEvent event) {
        modifyProductMenuStage = utility.LoadFXML("/main_menu/modify_product/ModifyProductMenu.fxml");
        modifyProductMenuStage.initModality(Modality.APPLICATION_MODAL);
        modifyProductMenuStage.show();
    }

    @FXML
    private void productSearchBox(ActionEvent event) {
    }

    @FXML
    private void productSearchButton(ActionEvent event) {
    }
}