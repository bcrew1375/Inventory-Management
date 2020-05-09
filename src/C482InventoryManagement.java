/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package C482InventoryManagement;

import javafx.application.Application;
import javafx.stage.Stage;

import utility.Utility;
/**
 *
 * @author Benjamin Crew
 */
public class C482InventoryManagement extends Application {

    public static Stage mainMenuStage;

    @Override
    public void start(Stage mainStage) {
//        Stage addPartMenuStage;
        String fxmlFilename;
        Utility loadFxml = new Utility();

        fxmlFilename = "/main_menu/MainMenu.fxml";
        mainStage = loadFxml.LoadFXML(fxmlFilename);
        mainMenuStage = mainStage;

/*        fxmlFilename = "main_menu/add_part/AddPartMenu.fxml";
        addPartMenuStage = loadFxml.LoadFXML(fxmlFilename);
        addPartMenuStage.initOwner(mainMenuStage);*/

        mainMenuStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}