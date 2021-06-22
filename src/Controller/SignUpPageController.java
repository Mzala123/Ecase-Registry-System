/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Employee;
import Model.Notification;
import Model.PopWindow;
import Model.SwitchWindow;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import e.pkgcase.registry.system.ECaseRegistrySystem;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class SignUpPageController implements Initializable {

    @FXML
    private StackPane stackpane;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private Label labelCredetialnvalid;
    @FXML
    private BorderPane borderpane;
    @FXML
    private Pane paneLogin;

    public static StackPane tempStackPane;
    public static String Username;
    public static String passcode;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!ECaseRegistrySystem.isLoaded) {
            LoadSplashScreen();
        }
        tempStackPane = stackpane;
    }

    private void LoadSplashScreen() {
        try {
            ECaseRegistrySystem.isLoaded = true;
            StackPane pane = FXMLLoader.load(getClass().getResource("/View/SplashScreen.fxml"));
            stackpane.getChildren().setAll(pane);
            FadeTransition fadein = new FadeTransition(Duration.seconds(3), pane);
            fadein.setFromValue(0);
            fadein.setToValue(1);
            fadein.setCycleCount(1);
            fadein.play();

            FadeTransition fadeout = new FadeTransition(Duration.seconds(2), pane);
            fadeout.setFromValue(1);
            fadeout.setToValue(0);
            fadeout.setCycleCount(1);

            fadein.setOnFinished((event) -> {
                fadeout.play();
            });

            fadeout.setOnFinished((event) -> {
                try {
                    StackPane paneStage = FXMLLoader.load(getClass().getResource("/View/signUpPage.fxml"));
                    stackpane.getChildren().setAll(paneStage);
                } catch (IOException ex) {
                    Logger.getLogger(SignUpPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            

        } catch (IOException ex) {

        }
    }

    @FXML
    private void OnEnterLogin(ActionEvent event) {
        Employee employee = new Employee();
        Username = this.username.getText();
        employee.setEmpUsername(Username);
        passcode = this.password.getText();
        employee.setEmpPassword(passcode);
        if(Username.isEmpty() && passcode.isEmpty()){
            Notification notify = new Notification(5, "Login Form", "Please fill in the fields");
            notify.start();
        }
        else
          employee.logIn();
    }

    @FXML
    private void LoginFunction(ActionEvent event) {
        Employee employee = new Employee();
        Username = this.username.getText();
        employee.setEmpUsername(Username);
        passcode = this.password.getText();
        employee.setEmpPassword(passcode);
        if(Username.isEmpty() && passcode.isEmpty()){
            Notification notify = new Notification(5, "Login Form", "Please fill in the fields");
            notify.start();
        }
        else
          employee.logIn();

    }

    @FXML
    private void createAdminAccount(ActionEvent event) throws IOException {

        StackPane paneStage = FXMLLoader.load(getClass().getResource("/View/CreateFirstAccount.fxml"));
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

    @FXML
    private void forgotPasswordMethod(ActionEvent event) {
        
    }
    

}
