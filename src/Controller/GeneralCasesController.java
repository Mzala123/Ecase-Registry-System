/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CaseFile;
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
public class GeneralCasesController implements Initializable {

    @FXML
    private StackPane mainStackPane;
    @FXML
    private TextField searchCaseField;
    @FXML
    private VBox listHbox;
    @FXML
    private BorderPane borderpane;
    @FXML
    private VBox contentVbox;
    @FXML
    private VBox rightVbox;
    @FXML
    private VBox attachmentVBox;
    
     public static VBox tempCaseDetails = new VBox();

    public static BorderPane tempBorderPane = new BorderPane();
    
    public static VBox tempVbox = new VBox();
    
    public static VBox tempRightVbox = new VBox();

    public static VBox tempAttachmentVbox = new VBox();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tempVbox = listHbox;
        tempCaseDetails = contentVbox;
        tempBorderPane = borderpane;
        tempRightVbox = rightVbox;
        tempAttachmentVbox = attachmentVBox;
          Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    loadDataInList();
                });
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }    

    @FXML
    private void CasePanelArea(ActionEvent event) {
          Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    SwitchWindow window = new SwitchWindow();
                    window.loadNewWindow("/View/GeneralPanel.fxml", "Officer Panel", true, true);
                    mainStackPane.getScene().getWindow().hide();
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
        file.GeneralcaseDetails();
        //complaint.displayComplaintDetails();

    }
    

    @FXML
    private void findComplaint(KeyEvent event) {
    }
    
}
