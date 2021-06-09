/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class OfficerPanelController implements Initializable {

    @FXML
    private Circle imageViewCircle;
    @FXML
    private Label labelUsername;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logOut(ActionEvent event) {
    }

    @FXML
    private void switchToDashboard(ActionEvent event) {
    }

    @FXML
    private void switchToOfficerCases(ActionEvent event) {
    }

    @FXML
    private void switchToUpdateCase(ActionEvent event) {
    }

    @FXML
    private void switchToSettings(ActionEvent event) {
    }

    @FXML
    private void existSystem(ActionEvent event) {
    }
    
}
