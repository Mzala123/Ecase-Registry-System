/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Notification;
import Model.Employee;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class CreateFirstAccountController implements Initializable {

    private Pane pane;
    @FXML
    private JFXTextField empUsername;
    @FXML
    private JFXPasswordField empPassword;
    @FXML
    private StackPane stackpane;
    @FXML
    private JFXTextField empEmail;

    public static Pane pane1;
    public static StackPane stackpane1;
    @FXML
    private JFXTextField empUserId;
    @FXML
    private JFXTextField empFname;
    @FXML
    private JFXTextField empLname;
    @FXML
    private JFXComboBox<String> empUserType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        stackpane1 = stackpane1;
        pane1 = pane;
        setUserComboValues();

    }

    private void setUserComboValues() {
        String[] users = {"Admin"};
        ObservableList<String> items = FXCollections.observableArrayList(users);
        empUserType.getItems().addAll(items);
    }

    @FXML
    private void createAdminUSer(ActionEvent event) {
        Employee employee = new Employee();

        String id = empUserId.getText();
        employee.setEmpId(id);
        String username = empUsername.getText();
        employee.setEmpUsername(username);
        String fname = empFname.getText();
        employee.setEmpFname(fname);
        String lname = empLname.getText();
        employee.setEmpLname(lname);
        String email = empEmail.getText();
        employee.setEmpEmail(email);
        String password = empPassword.getText();
        employee.setEmpPassword(password);
        String usertype = empUserType.getValue();
        employee.setEmpType(usertype);

        if (id.isEmpty() || username.isEmpty() || fname.isEmpty() || lname.isEmpty() || email.isEmpty()
                || password.isEmpty() || usertype.isEmpty()) {
            Notification notification = new Notification(1, "CREATING ADMIN", "Please Fill In The Required fields!");
            notification.start();
        }

        employee.addAdminUser();

    }

    @FXML
    private void cancelAdmin(ActionEvent event) {
         empEmail.clear();
         empFname.clear();
         empLname.clear();
         empPassword.clear();
         empUserId.clear();
         empUsername.clear();
    }

    @FXML
    private void returnToSignUp(ActionEvent event) throws IOException {
        StackPane paneStage = FXMLLoader.load(getClass().getResource("/View/signUpPage.fxml"));
        stackpane.getChildren().setAll(paneStage);
        FadeTransition fadein = new FadeTransition(Duration.seconds(.5), paneStage);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.setCycleCount(1);
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(.5), paneStage);
        scaleIn.setFromX(.7);
        scaleIn.setFromY(.7);
        scaleIn.setFromZ(.7);
        scaleIn.setToX(1);
        scaleIn.setToY(1);
        scaleIn.setToZ(1);
        scaleIn.setCycleCount(1);
        scaleIn.play();
        fadein.play();
    }

}
