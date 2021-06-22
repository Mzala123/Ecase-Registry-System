/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Client;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class UpdateOrganizationDataController implements Initializable {

    @FXML
    private TextField businessRegNo;
    @FXML
    private TextField businessName;
    @FXML
    private JFXTextArea postalAddress;
    @FXML
    private TextField businessEmail;
    @FXML
    private JFXDatePicker registrationDate;
    @FXML
    private JFXComboBox<String> businessType;
    
    public static TextField tempBusinessRegNo = new TextField();
    public static TextField tempBusinessEmail = new TextField();
    public static JFXTextArea tempPostalAddress = new JFXTextArea();
    public static JFXDatePicker tempRegDate = new JFXDatePicker();
    @FXML
    private Label labelRegNo;
    
    public static Label tempLabelRegNo = new Label();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       tempBusinessRegNo = businessRegNo;
       tempBusinessEmail = businessEmail;
       tempPostalAddress = postalAddress;
       tempRegDate = registrationDate;
       initializebusinessType();
       setOrganizationDetails();
       
    }    
    
     private void initializebusinessType(){
         ObservableList<String> typeList = FXCollections.observableArrayList();
         typeList.addAll("Manufacturing","Kaunjika","Energy","Retail","Transportation","Banking",
                 "Telecommunications","Insurance");
         businessType.getItems().addAll(typeList);
          
    }

    @FXML
    private void updateOrganizationDetails(ActionEvent event) {
        Organization organization = new Organization();
        String email = businessEmail.getText();
        organization.setEmail(email);
        String businessName = this.businessName.getText();
        organization.setOrgName(businessName);
        String address = this.postalAddress.getText();
        organization.setPostalAddress(address);
        String regNo = this.businessRegNo.getText();
        organization.setId(regNo);
        String businessType = this.businessType.getValue();
        organization.setBusinessType(businessType);
        String regDate = this.registrationDate.getValue().toString();
        organization.setRedDate(regDate);
        if(registrationDate.getValue().toString().isEmpty()){
            Notifications notification = Notifications.create();
                notification.title("Updating Client Organization Details");
                notification.text("Insert Registration Date");
                notification.hideAfter(Duration.seconds(3));
                notification.position(Pos.CENTER);
                notification.darkStyle();
                notification.showWarning();
        }
        organization.updateClientOrgdetails();
        
    }
    
    public void setOrganizationDetails(){
        Organization organization = new Organization();
        organization.setOrganizationData(ClientOrganizationListController.tempLabelOrgNo.getText());
        labelRegNo.setText(ClientOrganizationListController.tempLabelOrgNo.getText());
        tempLabelRegNo =labelRegNo;
        System.out.println("Well well shall we " +ClientOrganizationListController.tempLabelOrgNo.getText());
        businessName.setText(organization.getOrgName());
        businessType.setValue(organization.getBusinessType());
    }

    @FXML
    private void CloseOrgUpdateWindow(ActionEvent event) {
        
    }
    
}
