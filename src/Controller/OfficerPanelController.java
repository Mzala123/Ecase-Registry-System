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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class OfficerPanelController implements Initializable {

    @FXML
    private Circle imageViewCircle;
    @FXML
    private Label labelUsername;
    @FXML
    private StackPane mainStackPane;

    public static Label checkLabel = new Label();
    @FXML
    private StackPane stackpaneMiddle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setAdminProfile();
        checkLabel = labelUsername;
        MenusSwitch change = new MenusSwitch();
        SwitchCenterPane(change.officerDashBoard);
    }

    @FXML
    private void logOut(ActionEvent event) {
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(1000);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            SwitchWindow window = new SwitchWindow();
                            window.loadNewWindow("/View/signUpPage.fxml", "Login Section", true, true);
                            mainStackPane.getScene().getWindow().hide();
                        }
                    });
                    return null;
                }
            };
            new Thread(task).start();
        } catch (Exception ex) {

        }
    }

    @FXML
    private void switchToDashboard(ActionEvent event) {
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(1000);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            MenusSwitch change = new MenusSwitch();
                            SwitchCenterPane(change.officerDashBoard);
                        }
                    });
                    return null;
                }
            };
            new Thread(task).start();
        } catch (Exception ex) {

        }

    }

    @FXML
    private void switchToOfficerCases(ActionEvent event) {

        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(1000);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            SwitchWindow window = new SwitchWindow();
                            window.loadNewWindow("/View/OfficerCasesList.fxml", "List of complaints", true, true);
                            mainStackPane.getScene().getWindow().hide();
                        }
                    });
                    return null;
                }
            };
            new Thread(task).start();
        } catch (Exception ex) {

        }
    }

    @FXML
    private void switchToSettings(ActionEvent event) {

        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(1000);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            MenusSwitch change = new MenusSwitch();
                            SwitchCenterPane(change.adminSettings);
                        }
                    });
                    return null;
                }
            };
            new Thread(task).start();
        } catch (Exception ex) {

        }
    }

    @FXML
    private void existSystem(ActionEvent event) {

    }

    public void setAdminProfile() {
        Employee employee = new Employee();
        employee.setAdminValues();
        labelUsername.setText(employee.getEmpFname());
        Image image = null;
        try {
            image = new Image(employee.getImage().toURI().toURL().toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CasePanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        imageViewCircle.setFill(new ImagePattern(image));
    }

    @FXML
    private void switchToNotification(ActionEvent event) {

    }

    private void SwitchCenterPane(String pane) {

        stackpaneMiddle.getChildren().clear();
        try {
            StackPane pane1 = FXMLLoader.load(getClass().getResource(pane));
            ObservableList<Node> paneElements = pane1.getChildren();
            stackpaneMiddle.getChildren().setAll(paneElements);
            FadeTransition fadein = new FadeTransition(Duration.seconds(.5), stackpaneMiddle);
            fadein.setFromValue(0);
            fadein.setToValue(1);
            fadein.setCycleCount(1);
            PathTransition path = new PathTransition();
            path.setPath(stackpaneMiddle.getShape());
            path.setNode(stackpaneMiddle);
            path.setDuration(Duration.seconds(.5));
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
