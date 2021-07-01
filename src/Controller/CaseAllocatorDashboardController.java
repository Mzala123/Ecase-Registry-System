/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Complaint;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class CaseAllocatorDashboardController implements Initializable {

    @FXML
    private Label allCasesLabel;
    @FXML
    private SimpleMetroArcGauge consumerGauge;
    @FXML
    private SimpleMetroArcGauge competitionGauge;

    public static Label tempAllCaseLabel = new Label();
    public static SimpleMetroArcGauge tempConsumerGauge = new SimpleMetroArcGauge();
    public static SimpleMetroArcGauge tempCompetitionGauge = new SimpleMetroArcGauge();
    @FXML
    private BarChart<?, ?> natureBarchart;
    @FXML
    private BarChart<?, ?> regModeBarchart;

    public static BarChart<?, ?> tempnatureBarChar;
    public static BarChart<?, ?> tempregModeBarChar;

    Complaint complaint = new Complaint();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tempAllCaseLabel = allCasesLabel;
        tempConsumerGauge = consumerGauge;
        tempCompetitionGauge = competitionGauge;

        tempnatureBarChar = natureBarchart;
        tempregModeBarChar = regModeBarchart;

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

                            complaint.AllRegisteredCases();
                            complaint.AllConsumerCases();
                            complaint.AllCompetitionCases();
                            complaint.natureComplaintGraph();
                            complaint.modeRegistrationGraph();

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
