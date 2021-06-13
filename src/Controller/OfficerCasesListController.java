/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.ComplaintListController.tempBorderPane;
import static Controller.ComplaintListController.tempCaseDetails;
import static Controller.ComplaintListController.tempVbox;
import Model.CaseFile;
import Model.Complaint;
import Model.SwitchWindow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class OfficerCasesListController implements Initializable {

    @FXML
    private TextField searchCaseField;
    @FXML
    private VBox listHbox;
    @FXML
    private VBox contentVbox;
    @FXML
    private VBox listHbox1;
    @FXML
    private StackPane mainStackPane;

    public static VBox tempCaseDetails = new VBox();

    public static BorderPane tempBorderPane = new BorderPane();
    @FXML
    private BorderPane borderpane;
    public static VBox tempVbox = new VBox();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tempVbox = listHbox;
        tempCaseDetails = contentVbox;
        tempBorderPane = borderpane;
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(()->{
                    loadDataInList();
                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }

    private void loadDataInList() {
        CaseFile file = new CaseFile();
        file.caseDetails();
        //complaint.displayComplaintDetails();

    }

    @FXML
    private void CasePanelArea(ActionEvent event) {
        SwitchWindow window = new SwitchWindow();
        window.loadNewWindow("/View/OfficerPanel.fxml", "Officer Panel", true, true);
        mainStackPane.getScene().getWindow().hide();
    }

    @FXML
    private void findComplaint(KeyEvent event) {
    }

}
