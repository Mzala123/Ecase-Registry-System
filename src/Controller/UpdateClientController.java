/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Client;
import Model.MenusSwitch;
import Model.Person;
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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
    private JFXComboBox<String> gender;
    @FXML
    private JFXTextArea address;
    @FXML
    private TextField residence;
    @FXML
    private TextField phonenumber;
    @FXML
    private TextField email;
    @FXML
    private JFXComboBox<String> ageCombo;
    
    public static ObservableList<String> malawiId = FXCollections.observableArrayList(); 
    
    public static TextField tempNationalId = new TextField();
    @FXML
    private Label labelNationalId;
    
    public static Label tempLabel = new Label();
    @FXML
    private StackPane stackpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ClientListController.tempLabel.getText();
        initializeGender();
        initiliazeAge();
        setPersonValues();
        
    }    

     private void initializeGender() {
        String[] gender = {"Male", "Female"};
        ObservableList<String> list = FXCollections.observableArrayList(gender);
        this.gender.getItems().addAll(list);

    }

    private void initiliazeAge(){  
         for(int count=18; count<=100; count++){
          String ages = count + "";
          ageCombo.getItems().addAll(ages);
          //System.out.println(" "+count);
       }
       
    }
    
    @FXML
    private void updateClientDetails(ActionEvent event) {
        Person person = new Person();
        String nationalId = nationaId.getText();
        person.setNationalId(nationalId);
        String fname = this.fname.getText();
        person.setFirstName(fname);
        String lname = this.lname.getText();
        person.setLastName(lname);
        String nationality = this.nationality.getText();
        person.setNationality(nationality);
        String address = this.address.getText();
        person.setPostalAddress(address);
        String  residence = this.residence.getText();
        person.setResidence(residence);
        String phonenumber = this.phonenumber.getText();
        person.setContact(phonenumber);
        String email= this.email.getText();
        person.setEmail(email);
        String dob = this.ageCombo.getValue();
        person.setDob(dob);
        String gender = this.gender.getValue();
        person.setGender(gender);  
        person.updatePersonClientDetails();
     //   person.ClientList();
        
    }
    
    private void setPersonValues(){
        Person person = new Person();
        person.setPersonDataOnFields(ClientListController.tempNationalId.getText());
       // labelNationalId.setText(ClientListController.tempNationalId.getText());
        tempLabel= labelNationalId;
        nationaId.setText(person.getNationalId());
        fname.setText(person.getFirstName());
        lname.setText(person.getLastName());
        nationality.setText(person.getNationality());
        address.setText(person.getPostalAddress());
        residence.setText(person.getResidence());
        phonenumber.setText(person.getContact());
        email.setText(person.getEmail());
        ageCombo.setValue(person.getDob());
        gender.setValue(person.getGender());
    }

    @FXML
    private void closeWindow(ActionEvent event) {
       
    }
    
}
