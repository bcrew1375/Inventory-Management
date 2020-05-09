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
public class Product {
    private ObservableList<Part> associatedParts;

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        associatedParts = FXCollections.observableArrayList();

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public void addAssociatedPart (Part part) {
        associatedParts.add(part);
    }

    public boolean deleteAssociatedPart (Part selectedAspart) {
        return associatedParts.remove(selectedAspart);
    }

    public ObservableList<Part> getAllAssociatedParts () {
        return associatedParts;
    }

    public int getId () {
        return this.id;
    }

    public String getName () {
        return this.name;
    }

    public double getPrice () {
        return this.price;
    }

    public int getStock () {
        return this.stock;
    }

    public int getMin () {
        return this.min;
    }

    public int getMax () {
        return this.max;
    }

    public void setId (int id) {
        this.id = id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setPrice (double price) {
        this.price = price;
    }

    public void setStock (int stock) {
        this.stock = stock;
    }

    public void setMin (int min) {
        this.min = min;
    }

    public void setMax (int max) {
        this.max = max;
    }
}