package main_menu;

import inventory.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TableView<Part> partsTableView;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TextField partSearchBox;
    @FXML
    private TextField productSearchBox;

    private Stage addPartMenuStage;
    private Stage addProductMenuStage;
    private Stage modifyPartMenuStage;
    private Stage modifyProductMenuStage;

    private Inventory currentInventory;

    private ObservableList<Part> allPartsList;
    private ObservableList<Part> searchedPartsList;
    private ObservableList<Product> allProductsList;
    private ObservableList<Product> searchedProductsList;

    private Utility utility;

    @FXML
    private void initialize() {
        utility = new Utility();

        currentInventory = Inventory.getInstance();
        allPartsList = currentInventory.getAllParts();
        allProductsList = currentInventory.getAllProducts();
        searchedPartsList = FXCollections.observableArrayList();
        searchedProductsList = FXCollections.observableArrayList();

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Test items setup
        //--------------------//
        Part testPart = new InHouse(0, "Hard Drive", 80.00, 5, 2, 10, 7);
        Part testPart2 = new InHouse(1, "Motherboard", 120.00, 5, 2, 10, 7);
        Part testPart3 = new InHouse(2, "Chassis", 100.00, 5, 2, 10, 7);
        Part testPart4 = new Outsourced(3, "Processor", 350.00, 6, 3, 10, "Company A");
        Part testPart5 = new Outsourced (4, "GPU", 300.00, 7, 4, 10, "Company B");

        Product testProduct1 = new Product(0, "Chassis with HD", 250, 5, 2, 10);
        Product testProduct2 = new Product(1, "Computer", 1100, 5, 2, 10);

        testProduct1.addAssociatedPart(testPart3);
        testProduct1.addAssociatedPart(testPart);

        testProduct2.addAssociatedPart(testPart3);
        testProduct2.addAssociatedPart(testPart2);
        testProduct2.addAssociatedPart(testPart4);
        testProduct2.addAssociatedPart(testPart5);
        testProduct2.addAssociatedPart(testPart);

        currentInventory.addPart(testPart);
        currentInventory.addPart(testPart2);
        currentInventory.addPart(testPart3);
        currentInventory.addPart(testPart4);
        currentInventory.addPart(testPart5);

        currentInventory.addProduct(testProduct1);
        currentInventory.addProduct(testProduct2);
        //--------------------//

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
        Alert delete;

        Inventory.selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (Inventory.selectedPart != null) {
            delete = utility.displayConfirm("Delete", "Are you sure you want to delete this part?");

            if (delete.getResult().equals(ButtonType.YES)) {
                currentInventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());

                searchedPartsList.remove(partsTableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    private void partModifyButton(ActionEvent event) {
        Inventory.selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (Inventory.selectedPart != null) {
            modifyPartMenuStage = utility.LoadFXML("/main_menu/modify_part/ModifyPartMenu.fxml");
            modifyPartMenuStage.initModality(Modality.APPLICATION_MODAL);
            modifyPartMenuStage.show();
        }
    }

    @FXML
    private void partSearch(ActionEvent event) {
        if (!partSearchBox.getText().equals("")) {
            searchedPartsList.removeAll(searchedPartsList);
            searchedPartsList.addAll(currentInventory.lookupPart(partSearchBox.getText()));

            try {
                searchedPartsList.addAll(currentInventory.lookupPart(Integer.parseInt(partSearchBox.getText())));
            } catch (Exception error) {
            }

            partsTableView.setItems(searchedPartsList);
        }
        else
            partsTableView.setItems(allPartsList);
    }

    @FXML
    private void productAddButton(ActionEvent event) {
        addProductMenuStage = utility.LoadFXML("/main_menu/add_product/AddProductMenu.fxml");
        addProductMenuStage.initModality(Modality.APPLICATION_MODAL);
        addProductMenuStage.show();
    }

    @FXML
    private void productDeleteButton(ActionEvent event) {
        Alert delete;

        Inventory.selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if (Inventory.selectedProduct != null) {
            delete = utility.displayConfirm("Delete", "Are you sure you want to delete this product?");

            if (delete.getResult().equals(ButtonType.YES)) {
                currentInventory.deleteProduct(productsTableView.getSelectionModel().getSelectedItem());

                searchedProductsList.remove(productsTableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    private void productModifyButton(ActionEvent event) {
        Inventory.selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if (Inventory.selectedProduct != null) {
            modifyProductMenuStage = utility.LoadFXML("/main_menu/modify_product/ModifyProductMenu.fxml");
            modifyProductMenuStage.initModality(Modality.APPLICATION_MODAL);
            modifyProductMenuStage.show();
        }
    }

    @FXML
    private void productSearch(ActionEvent event) {
        if (!productSearchBox.getText().equals("")) {
            searchedProductsList.removeAll(searchedProductsList);
            searchedProductsList.addAll(currentInventory.lookupProduct(productSearchBox.getText()));

            try {
                searchedProductsList.addAll(currentInventory.lookupProduct(Integer.parseInt(productSearchBox.getText())));
            } catch (Exception error) {
            }

            productsTableView.setItems(searchedProductsList);
        }
        else
            productsTableView.setItems(allProductsList);
    }
}