/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author Benjamin Crew
 */
public class Inventory {
    
    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;
    
    private int nextPartId;
    private int nextProductId;
    
    private Inventory() {
        allParts = FXCollections.observableArrayList();
        
        nextPartId = 0;
        nextProductId = 0;
    }
    
    public static Inventory getInstance() {
        return InventoryHolder.INSTANCE;
    }
    
    private static class InventoryHolder {
        private static final Inventory INSTANCE = new Inventory();
    }
    
    public void addPart(Part newPart) {
        allParts.add(newPart);
        nextPartId++;
    }

    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
        nextProductId++;
    }

    /*public boolean deletePart (Part selectedPart) {
        
    }*/

    /*public boolean deleteProduct (Product selectedProduct) {
        
    }*/
    
    public int getNextPartId () {
        return nextPartId;
    }
    
    public ObservableList<Part> getAllParts () {
        return this.allParts;
    }

    public ObservableList<Product> getAllProducts () {
        return this.allProducts;
    }    
    
    /*public Part lookupPart(int partId) {
        
    }

    public Product lookupProduct(int productId) {
        
    }

    public ObservableList<Part> lookupPart(String partName) {
        
    }

    public ObservableList<Product> lookupProduct(String productName) {
        
    }

    public void updatePart (int index, Part selectedPart) {
        
    }
    
    public void updateProduct (int index, Product newProduct) {
        
    }*/
}
