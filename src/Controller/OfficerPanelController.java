/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Employee;
import Model.SwitchWindow;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
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
    @FXML
    private StackPane mainStackPane;
    
    public static Label checkLabel = new Label();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAdminProfile();
        
        checkLabel = labelUsername;
    }    

    @FXML
    private void logOut(ActionEvent event) {
        
    }

    @FXML
    private void switchToDashboard(ActionEvent event) {
        
    }

    @FXML
    private void switchToOfficerCases(ActionEvent event) {
       SwitchWindow window = new SwitchWindow();
       window.loadNewWindow("/View/OfficerCasesList.fxml", "List of complaints", true, true);
       mainStackPane.getScene().getWindow().hide();
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
    
       public void setAdminProfile() {
        Employee employee = new Employee();
        employee.setAdminValues();
        labelUsername.setText(employee.getEmpFname());
        Image image = null;
            try {
                image = new Image(employee.getImage().toURI().toURL().toString());
            } catch (MalformedURLException ex) {
                Logger.getLogger(CasePanelController.class.getName()).log(Level.SEVERE, null, ex);
            }
            imageViewCircle.setFill(new ImagePattern(image));
    }

    @FXML
    private void switchToNotification(ActionEvent event) {
        
        
    }
}

