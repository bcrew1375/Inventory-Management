/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main_menu;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

//import c482_inventory_management.main_menu.add_part.AddPartMenu;
import javafx.application.Platform;

/**
 *
 * @author Benjamin Crew
 */
public class MainMenu {
    private String fxmlFilename;
    
    //private final AddPartMenu addPartMenu;

    //private final Stage addPartMenuStage;
    //private final Stage addProductMenuStage;
    //private final Stage modifyPartMenuStage;
    //private final Stage modifyProductMenuStage;
    //private final Stage mainMenuStage;

    protected void callAddPartMenu () {
        //addPartMenuStage.show();
        //addPartMenu.main();
    }

    private void displayMenu () {
        //mainMenuStage.show();
    }

    public MainMenu () {
        // Load each menu's stage.
        //this.mainMenuStage = mainStage;

        //this.fxmlFilename = "AddPart/AddPartMenu.fxml";
        //this.addPartMenuStage = this.LoadFXML(fxmlFilename);

        //this.fxmlFilename = "../AddProduct/AddProductMenu.fxml";
        //this.addProductMenuStage = this.LoadFXML(fxmlFilename);

        //this.fxmlFilename = "../ModifyPart/ModifyPartMenu.fxml";
        //this.modifyPartMenuStage = this.LoadFXML(fxmlFilename);

        //this.fxmlFilename = "../ModifyProduct/ModifyProductMenu.fxml";
        //this.modifyProductMenuStage = this.LoadFXML(fxmlFilename);
        
        // Create each menu's class instance.
        //addPartMenu = new AddPartMenu();
    }
    
    public void main () {
        System.out.println("Main menu.");
        displayMenu();
    }
}