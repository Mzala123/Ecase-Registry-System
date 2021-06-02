/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Client;
import Model.Person;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class UpdateClientController implements Initializable {

    @FXML
    private TextField nationaId;
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField nationality;
    @FXML
    private JFXDatePicker dob;
    @FXML
    private JFXComboBox<?> gender;
    @FXML
    private JFXTextArea address;
    @FXML
    private TextField residence;
    @FXML
    private TextField phonenumber;
    @FXML
    private TextField email;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void updateClientDetails(ActionEvent event) {
        Person person = new Person();
        String nationalId = nationaId.getText();
        String fname = this.fname.getText();
        String lname = this.lname.getText();
        String nationality = this.nationality.getText();
        String address = this.address.getText();
        String  residence = this.residence.getText();
        String phonenumber = this.phonenumber.getText();
        String email= this.email.getText();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
    }
    
}
