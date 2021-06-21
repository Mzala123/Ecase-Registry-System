/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Client;
import Model.MenusSwitch;
import Model.Notification;
import Model.Person;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class ClientFormController implements Initializable {

    @FXML
    private StackPane stackpane;
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
    private JFXComboBox<String> gender;
    @FXML
    private JFXTextArea address;
    @FXML
    private TextField residence;
    @FXML
    private TextField phonenumber;
    @FXML
    private TextField email;

    Person person = new Person();
    Client client = new Client();
    @FXML
    private StackPane organizationStackPane;
    @FXML
    private JFXTabPane jfxTabPane;
    @FXML
    private JFXComboBox<String> ageCombo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initializeGender();
        initiliazeAge();

    }

    private void initializeGender() {
        String[] gender = {"Male", "Female"};
        ObservableList<String> list = FXCollections.observableArrayList(gender);
        this.gender.getItems().addAll(list);

    }

    private void initiliazeAge(){
                
        String date = LocalDateTime.now().toString();
        System.out.println(date);
        System.out.println(date.substring(0, 3));
        
     for(int count=Integer.parseInt(date.substring(0, 3)); count>17; count--)
        {
            String ages = count + "";
            ageCombo.getItems().addAll(ages);
  
        }
       
    }
    
    
    @FXML
    private void addClientPerson(ActionEvent event) {
        String natinalID = nationaId.getText();
        person.setNationalId(natinalID);
        String fname = this.fname.getText();
        person.setFirstName(fname);
        String lname = this.lname.getText();
        person.setLastName(lname);
        String gender = this.gender.getValue().toString();
        person.setGender(gender);
        String dob = this.ageCombo.getValue().toString();
        person.setDob(dob);
        /*String dob = this.dob.getValue().toString();
        person.setDob(dob);*/
        String nationality = this.nationality.getText();
        person.setNationality(nationality);
        String address = this.address.getText();
        person.setPostalAddress(address);
        String residence = this.residence.getText();
        person.setResidence(residence);
        String phonenumber = this.phonenumber.getText();
        person.setContact(phonenumber);
        String email = this.email.getText();
        person.setEmail(email);

        if (dob == null) {
            Notifications notification = Notifications.create();
            notification.title("Creating Client Person");
            notification.text("Please select Clients date of birth");
            notification.hideAfter(Duration.seconds(5));
            notification.position(Pos.CENTER);
            notification.darkStyle();
            notification.showWarning();
        }

        if (gender == null) {
            Notifications notification = Notifications.create();
            notification.title("Creating Client Person");
            notification.text("Please Select Clients Gender");
            notification.hideAfter(Duration.seconds(5));
            notification.position(Pos.CENTER);
            notification.darkStyle();
            notification.showWarning();
        }

        if (natinalID.isEmpty() || fname.isEmpty() || lname.isEmpty() || nationality.isEmpty()
                || phonenumber.isEmpty()) {
            Notifications notification = Notifications.create();
            notification.title("Creating Client Person");
            notification.text("Please fill in the required fields");
            notification.hideAfter(Duration.seconds(5));
            notification.position(Pos.CENTER);
            notification.darkStyle();
            notification.showWarning();
        } else {
            person.createPersonClient();
          //  person.registerClientPerson();

        }
    }

    @FXML
    private void cancelClientPersonRegistration(ActionEvent event) {
        nationaId.clear();
        fname.clear();
        lname.clear();
        nationality.clear();
        address.clear();
        residence.clear();
        phonenumber.clear();
        email.clear();
    }

    @FXML
    private void switchToClientForm(Event event) {

    }

    @FXML
    private void switchToOrganizationForm(Event event) {
        MenusSwitch change = new MenusSwitch();
        SwitchCenterPane(change.createOrganization);

    }

    private void SwitchCenterPane(String pane) {

        organizationStackPane.getChildren().clear();
        try {
            StackPane pane1 = FXMLLoader.load(getClass().getResource(pane));
            ObservableList<Node> paneElements = pane1.getChildren();
            organizationStackPane.getChildren().setAll(paneElements);
            FadeTransition fadein = new FadeTransition(Duration.seconds(1), organizationStackPane);
            fadein.setFromValue(0);
            fadein.setToValue(1);
            fadein.setCycleCount(1);
//            ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(1), stackpaneMiddle);
//            scaleIn.setFromX(.5);
//            scaleIn.setFromY(.5);
//            scaleIn.setFromZ(.5);
//            scaleIn.setToX(1);
//            scaleIn.setToY(1);
//            scaleIn.setToZ(1);
//            scaleIn.setCycleCount(1);

            PathTransition path = new PathTransition();
            path.setPath(organizationStackPane.getShape());
            path.setNode(organizationStackPane);
            path.setDuration(Duration.seconds(1));
            path.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            path.setCycleCount(1);
            path.setAutoReverse(false);
            path.play();
            //  scaleIn.play();
            fadein.play();

        } catch (IOException ex) {
            Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        {

        }
    }

}
