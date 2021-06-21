/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Employee;
import Model.MenusSwitch;
import Model.SwitchWindow;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class AdminPanelController implements Initializable {

    @FXML
    private Circle imageViewCircle;
    @FXML
    private Label labelUsername;
    @FXML
    private StackPane stackpaneMiddle;
    @FXML
    private BorderPane borderpane;
    @FXML
    private StackPane mainStackPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        MenusSwitch change = new MenusSwitch();
        SwitchCenterPane(change.Dashboard);
        setAdminProfile();
          
    }

    @FXML
    private void switchToDashboard(ActionEvent event) {
        MenusSwitch change = new MenusSwitch();
        SwitchCenterPane(change.Dashboard);
    }

    @FXML
    private void switchToAddUser(ActionEvent event) {
        MenusSwitch change = new MenusSwitch();
        SwitchCenterPane(change.addUserSection);
    }

    @FXML
    private void switchToManageUser(ActionEvent event) {
        MenusSwitch change = new MenusSwitch();
        SwitchCenterPane(change.ManageUsers);
    }

    @FXML
    private void switchToUserLogs(ActionEvent event) {
    }

    @FXML
    private void switchToSettings(ActionEvent event) {
         MenusSwitch change = new MenusSwitch();
         SwitchCenterPane(change.adminSettings);
   
    }

    @FXML
    private void existSystem(ActionEvent event) {
    }

    private void SwitchCenterPane(String pane) {

        stackpaneMiddle.getChildren().clear();
        try {
            StackPane pane1 = FXMLLoader.load(getClass().getResource(pane));
            ObservableList<Node> paneElements = pane1.getChildren();
            stackpaneMiddle.getChildren().setAll(paneElements);
            FadeTransition fadein = new FadeTransition(Duration.seconds(1), stackpaneMiddle);
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
            path.setPath(stackpaneMiddle.getShape());
            path.setNode(stackpaneMiddle);
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

    public void setAdminProfile() {
        Employee employee = new Employee();
        employee.setAdminValues();
        labelUsername.setText(employee.getEmpFname()+" "+employee.getEmpLname());
        Image image = null;
            try {
                image = new Image(employee.getImage().toURI().toURL().toString());
            } catch (MalformedURLException ex) {
                Logger.getLogger(CreateUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }
            imageViewCircle.setFill(new ImagePattern(image));
    }
    
    @FXML
    private void logOut(ActionEvent event) {
        SwitchWindow window = new SwitchWindow();
        window.loadNewWindow("/View/signUpPage.fxml", "Login Section", false, false);
        mainStackPane.getScene().getWindow().hide();
    }

}
