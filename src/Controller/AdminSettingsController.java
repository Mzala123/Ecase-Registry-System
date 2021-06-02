/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.CreateUsersController.file;
import static Controller.CreateUsersController.inputstream;
import Model.DBHandler;
import Model.Employee;
import Model.Notification;
import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class AdminSettingsController implements Initializable {

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
    private TextField empCurrPassword;
    @FXML
    private PasswordField empConfirmPasscode;
    @FXML
    private PasswordField empNewPasscode;
    @FXML
    private Circle imageCircle;
    @FXML
    private JFXTextArea imagePathField;


    public static File file;
    public static FileInputStream inputstream; 
    String imagepath = "";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            getAdminValues();
        } catch (SQLException ex) {
            Logger.getLogger(AdminSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void UpdateAdminDetails(ActionEvent event) {
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
        String newPassword = empNewPasscode.getText();
        String confirmPassword = empConfirmPasscode.getText();
        employee.setEmpPassword(newPassword);
        imagepath = imagePathField.getText();
         try {
             if(file == null){
                  Notification notification = new Notification(3, "UPDATING USER PROFILE", "No Profile Image Added!");
                  notification.start();
             }
             else
             {
             inputstream = new FileInputStream(file);
             employee.setImage(file);
             }
             
        } catch (FileNotFoundException ex) {
           Notification notification = new Notification(3, "UPDATING USER PROFILE", "No Profile Image Added!");
           notification.start();
        }
        if(newPassword.isEmpty() ||confirmPassword.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Edit Admin Account details");
            alert.setContentText("Please set the new password details!");
            alert.showAndWait();
        }
        else if(newPassword.equals(confirmPassword) == false){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("PASSWORD MISMATCH");
            alert.setContentText("The set password do not match, Try Again!");
            alert.showAndWait();
        }
        else{
            employee.editAccountUserDetails();
        }
         
       

    }

    @FXML
    private void cancelUpdate(ActionEvent event) {
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
    

    private  void  getAdminValues() throws SQLException{
       Employee employee = new Employee();
       employee.setAdminValues();
       empEmail.setText(employee.getEmpEmail());
       empFname.setText(employee.getEmpFname());
       empId.setText(employee.getEmpId());
       empLname.setText(employee.getEmpLname());
       empCurrPassword.setText(employee.getEmpPassword());
       empUsername.setText(employee.getEmpUsername());
       
       Image image = null;
            try {
                image = new Image(employee.getImage().toURI().toURL().toString());
            } catch (MalformedURLException ex) {
                Logger.getLogger(CreateUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }
            imageCircle.setFill(new ImagePattern(image));
        
   }
}
