/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Employee;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class DashBoardController implements Initializable {

    @FXML
    private StackPane stackpane;
    @FXML
    private Pane paneAdmin;
    @FXML
    private Label labelAdmins;
    @FXML
    private Pane paneCaseOfficer;
    @FXML
    private Label labelCaseOfficer;
    @FXML
    private Pane paneCaseAllocator;
    @FXML
    private Label labelCaseAllocator;
    @FXML
    private Pane paneDirector;
    @FXML
    private Label labelDirectors;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<?, ?> barChart;

    public static Label tempLabelAdmin;
    public static Label tempLabelCaseOfficer;
    public static Label tempLabelCaseAllocator;
    public static Label tempLabelDirector;      
    public static PieChart tempPieChart;
    
    public static BarChart<?, ?> tempBarChar;
    
    @FXML
    private NumberAxis numberAxis;
    @FXML
    private CategoryAxis roleCategoryAxis;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        tempLabelAdmin = labelAdmins;
        tempLabelCaseOfficer = labelCaseOfficer;
        tempLabelCaseAllocator = labelCaseAllocator;
        tempLabelDirector = labelDirectors;
        
        tempPieChart = pieChart;
        
        tempBarChar = barChart; 
        setUserNumberBasedOnRole();
    }

    
        
    
        
        
    private void setUserNumberBasedOnRole() {
           Employee employee = new Employee();
           employee.numberOfAdmins();
           employee.numberOfCaseAllocators();
           employee.numberOfCaseOfficer();
           employee.numberOfDirectors();
           
           employee.run();
           
    }
    
    
}
