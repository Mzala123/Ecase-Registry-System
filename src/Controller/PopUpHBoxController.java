/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class PopUpHBoxController implements Initializable {

    @FXML
    private HBox hbox;
    @FXML
    private JFXButton removeHboxFromTable;
    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton selectAllValues;
    
    public static HBox tempHbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hbox.getChildren().setAll(removeHboxFromTable, delete, selectAllValues);
        hbox = tempHbox;
    }    

    @FXML
    private void removeHboxFromTable(ActionEvent event) {
    }

    @FXML
    private void DeleteSelectedItems(ActionEvent event) {
    }

    @FXML
    private void SelectionAllData(ActionEvent event) {
    }
    
}
