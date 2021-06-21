/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.MenusSwitch;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class ClientelleListsController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private StackPane personStackPane;
    @FXML
    private StackPane organizationStackPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void switchToClientPersonList(Event event) {
       
    }

    @FXML
    private void switchToOrganiizationClientList(Event event) {
       // MenusSwitch change = new MenusSwitch();
       // SwitchCenterPane1(change.clientList);
    }
    
    
  
   
 
     
}
