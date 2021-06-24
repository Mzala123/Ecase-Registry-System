/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class OfficerPanelDashBoardController implements Initializable {

    @FXML
    private StackPane stackpane;
    @FXML
    private Label allCasesLabel;
    @FXML
    private SimpleMetroArcGauge consumerGauge;
    @FXML
    private SimpleMetroArcGauge competitionGauge;
    @FXML
    private PieChart officerPieChart;
    @FXML
    private BarChart<?, ?> officerBarChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
