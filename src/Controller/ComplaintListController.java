/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Complaint;
import Model.SwitchWindow;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class ComplaintListController implements Initializable {

    private VBox vboxList;
    @FXML
    private BorderPane scrollPaneList;
    
    
    private ListView<HBox> listView;
   
    public static ObservableList list = FXCollections.observableArrayList();
    
    public static ListView<HBox> complaintList = new ListView<>();
    @FXML
    private VBox listHbox;
    
    public static VBox tempVbox= new VBox();
    
    public static ObservableList<VBox>  complaintDetails = FXCollections.observableArrayList();
    
    public static VBox setDetails = new VBox();
    
    private Pagination pagination;
    
    
    public static int index = 0;
    private final int rowsPerPage = 5;
    @FXML
    private VBox contentVbox;
    
    
    public static VBox tempCaseDetails = new VBox();
    @FXML
    private BorderPane borderpane;
    
    public static BorderPane tempBorderPane = new BorderPane();
    @FXML
    private StackPane mainStackPane;
    @FXML
    private TextField searchComplaintField;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         complaintList = listView;
         tempVbox = listHbox;
         tempCaseDetails = contentVbox;
         tempBorderPane = borderpane;
         loadDataInList();
    }  
    
    private void loadDataInList(){
        Complaint complaint = new Complaint();
        complaint.complaintDetails();
        //complaint.displayComplaintDetails();
 
    }
    
     private Node createPage(int pageIndex) {
        try {
            int fromIndex = pageIndex * rowsPerPage;
            int toIndex = Math.min(fromIndex + rowsPerPage, complaintDetails.size());
            Label label = new Label("Mtende");
            setDetails.getChildren().add(label);
            setDetails.getChildren().addAll(complaintDetails);
            return setDetails;
        } catch (IllegalArgumentException ex) {
            return new Label("No Data Here");
        }  
    }
    
    @FXML
    private void findComplaint(KeyEvent event) {
        
        
    }

    private void backToCasePanel(ActionEvent event) {
       SwitchWindow window = new SwitchWindow();
       window.loadNewWindow("/View/CasePanel.fxml", "List of complaints", true, true);
       mainStackPane.getScene().getWindow().hide();
    }

    @FXML
    private void CasePanelArea(ActionEvent event) {
       SwitchWindow window = new SwitchWindow();
       window.loadNewWindow("/View/CasePanel.fxml", "List of complaints", true, true);
       mainStackPane.getScene().getWindow().hide();
    }
    
}
