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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class OfficerCasesListController implements Initializable {

    @FXML
    private TextField searchCaseField;
    @FXML
    private VBox listHbox;
    @FXML
    private VBox contentVbox;
    @FXML
    private VBox listHbox1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void CasePanelArea(ActionEvent event) {
    }

    @FXML
    private void findComplaint(KeyEvent event) {
    }
    
}
