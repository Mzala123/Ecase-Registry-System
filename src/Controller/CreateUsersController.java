/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Employee;
import Model.Notification;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class CreateUsersController implements Initializable {

    @FXML
    private StackPane stackPaneUsers;
    @FXML
    private TextField empId;
    @FXML
    private TextField empUsername;
    @FXML
    private TextField empFname;
    @FXML
    private TextField empLname;
    @FXML
    private TextField empEmail;

    @FXML
    private JFXComboBox<String> empUserType;
    @FXML
    private Circle imageCircle;
    @FXML
    private JFXTextArea imagePathField;
    @FXML
    private PasswordField empPasscode;

    public static File file;
    public static FileInputStream inputstream; 
    String imagepath = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setComboSystemUsers();
    }

    private void setComboSystemUsers() {
        String[] usertype = {"Admin", "Case Officer", "Case Allocator", "Director"};
        ObservableList<String> items = FXCollections.observableArrayList(usertype);
        empUserType.getItems().addAll(items);
    }

    @FXML
    private void addSystemUser(ActionEvent event) {
        Employee employee = new Employee();
        String id = empId.getText();
        employee.setEmpId(id);
        String username = empUsername.getText();
        employee.setEmpUsername(username);
        String fname = empFname.getText();
        employee.setEmpFname(fname);
        String lname = empLname.getText();
        employee.setEmpLname(lname);
        String email = empEmail.getText();
        employee.setEmpEmail(email);
        String password = empPasscode.getText();
        employee.setEmpPassword(password);
        String usertype = empUserType.getValue();
        employee.setEmpType(usertype);
        imagepath = imagePathField.getText();
        
         if (id.isEmpty() || username.isEmpty() || fname.isEmpty()
                || lname.isEmpty() || email.isEmpty() || password.isEmpty() || usertype.isEmpty() || imagepath.isEmpty() ){
            Notification notification = new Notification(3, "CREATING USER", "Please fill in the required fields");
            notification.start();
        }
         else{
         
        try {
            inputstream = new FileInputStream(file);
            employee.setImage(file);
        } catch (FileNotFoundException ex) {
           Notification notification = new Notification(3, "CREATING USER", "No Profile Image Added!");
           notification.start();
        }
       
        try {
            employee.addSystemUsers();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
         }  

    }

    @FXML
    private void cancelSystemUser(ActionEvent event) {
        empEmail.clear();
        empFname.clear();
        empId.clear();
        empLname.clear();
        empPasscode.clear();
        empUsername.clear();
    }

    @FXML
    private void insertUserProfile(ActionEvent event) {
        Stage stage = new Stage();
        Employee employee = new Employee();
        ImageView view = new ImageView();
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        file = chooser.showOpenDialog(stage);
        if (file != null) {
           imagepath = file.getAbsolutePath();
           imagePathField.setText(file.getAbsolutePath().toString());
            Image image = null;
            try {
                image = new Image(file.toURI().toURL().toString());
            } catch (MalformedURLException ex) {
                Logger.getLogger(CreateUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }
            imageCircle.setFill(new ImagePattern(image));

        }
    }

}
