/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Organization;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class OrganizationFormController implements Initializable {

    @FXML
    private TextField businessRegNo;
    @FXML
    private TextField businessName;
    @FXML
    private JFXTextArea postalAddress;
    @FXML
    private TextField businessEmail;
    @FXML
    private TextField businessContact;
    @FXML
    private JFXDatePicker registrationDate;
    @FXML
    private JFXComboBox<String> businessType;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            initializebusinessType();
    }

    private void initializebusinessType(){
         ObservableList<String> typeList = FXCollections.observableArrayList();
         typeList.addAll("Manufacturing","Kaunjika","Energy","Retail","Transportation","Banking",
                 "Telecommunications","Insurance");
         businessType.getItems().addAll(typeList);
          
    }
    
    @FXML
    private void registerOrganization(ActionEvent event) {
        Organization organization = new Organization();
        
        String businessRegNo = this.businessRegNo.getText();
        organization.setId(businessRegNo);
        String businessName = this.businessName.getText();
        organization.setOrgName(businessName);
        String postalAddress = this.postalAddress.getText();
        organization.setPostalAddress(postalAddress);
        String businessEmail = this.businessEmail.getText();
        organization.setEmail(businessEmail);
        String businessContact = this.businessContact.getText();
        organization.setContact(businessContact);
        
           if(businessType.getValue() == null){
            Notifications notification = Notifications.create();
            notification.title("Creating Client Organization");
            notification.text("Please Include Business Type Option");
            notification.hideAfter(Duration.seconds(5));
            notification.position(Pos.CENTER);
            notification.darkStyle();
            notification.showWarning();
        }
          
       
        String businessType = this.businessType.getValue();
        organization.setBusinessType(businessType);
        
       
        if(registrationDate.getValue() == null){
            Notifications notification = Notifications.create();
            notification.title("Creating Client Organization");
            notification.text("Please Include Company's Registration Date");
            notification.hideAfter(Duration.seconds(5));    
            notification.position(Pos.CENTER);
            notification.darkStyle();
            notification.showWarning();
        }
        
        String registrationDate = this.registrationDate.getValue().toString();
        organization.setRedDate(registrationDate);
        
       
          
        if(businessRegNo.isEmpty() || businessEmail.isEmpty() || businessContact.isEmpty() ||
                businessName.isEmpty() || postalAddress.isEmpty()){
            Notifications notification = Notifications.create();
            notification.title("Creating Client Organization");
            notification.text("Please Fill in the required fields");
            notification.hideAfter(Duration.seconds(5));
            notification.position(Pos.CENTER);
            notification.darkStyle();
            notification.showWarning();
            
        }
        else{
            organization.createClientOrganization();
           
        }
        
    }

    @FXML
    private void canceOrganizationRegistration(ActionEvent event) {
    businessRegNo.clear();
    businessName.clear();
    postalAddress.clear();
    businessEmail.clear();
    businessContact.clear();
    
        
    }

}
   