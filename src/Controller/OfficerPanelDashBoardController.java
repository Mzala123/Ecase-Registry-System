/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.CaseAllocatorDashboardController.tempAllCaseLabel;
import static Controller.CaseAllocatorDashboardController.tempCompetitionGauge;
import static Controller.CaseAllocatorDashboardController.tempConsumerGauge;
import Model.CaseFile;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
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

    public static Label tempAllCaseLabel = new Label();
    public static SimpleMetroArcGauge tempConsumerGauge = new SimpleMetroArcGauge();
    public static SimpleMetroArcGauge tempCompetitionGauge = new SimpleMetroArcGauge();

    public static PieChart tempPiechart = new PieChart();
    public static BarChart<?, ?> tempstatusBarChar;
    CaseFile file = new CaseFile();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tempAllCaseLabel = allCasesLabel;
        tempConsumerGauge = consumerGauge;
        tempCompetitionGauge = competitionGauge;
        tempPiechart = officerPieChart;
        tempstatusBarChar = officerBarChart;
        loadDashBoardData();
    }

    private void loadDashBoardData() {
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(1000);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            file.AllRegisteredCases();
                            file.AllConsumerCases();
                            file.AllCompetitionCases();
                            file.pieChartCaseStatus();
                            file.barchartCaseStatus();

                        }
                    });
                    return null;
                }
            };
            new Thread(task).start();
        } catch (Exception ex) {

        }
    }

}
