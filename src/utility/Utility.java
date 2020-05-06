/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Benjamin Crew
 */
public class Utility {
    public FXMLLoader loadFxml;

    public Stage LoadFXML (String fxmlFilename) {
        Stage stage;

        try {
            loadFxml = new FXMLLoader(getClass().getResource(fxmlFilename));
            stage = loadFxml.load();
            return stage;
        }
        catch (IOException ioError) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setContentText("Failed to load FXML.");
            error.showAndWait();
            javafx.application.Platform.exit();
            return null;
        }
    }

    // returns true if a given string contains a decimal point.
    public Boolean checkDecimalPoint (String number) {
        return number.contains(".");
    }

    public void checkDecimalKey (KeyEvent event) {
        String key = event.getCharacter();
        if (!key.matches("[0-9]") && !key.matches("\\.")) {
            event.consume();
        }
    }

    public void checkIntegerKey (KeyEvent event) {
        String key = event.getCharacter();
        if (!key.matches("[0-9]")) {
            event.consume();
        }
    }
    
    public void displayError(String errorText) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        error.setContentText(errorText);
        error.showAndWait();
    }
    
    public Boolean validateDouble (String number) {
        Double inputValue;
        
        try {
            inputValue = Double.parseDouble(number);            
        }
        catch (Exception error) {
            return false;
        }
        return true;
    }

    public Boolean validateInteger (String number) {
        Integer inputValue;
        
        try {
            inputValue = Integer.parseInt(number);            
        }
        catch (Exception error) {
            return false;
        }
        return true;
    }
}
