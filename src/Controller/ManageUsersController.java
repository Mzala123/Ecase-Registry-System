   /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Employee;
import static Model.SwitchWindow.scene;
import static Model.SwitchWindow.stage;
import com.itextpdf.text.pdf.BidiOrder;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class ManageUsersController implements Initializable {

    @FXML
    private BorderPane borderpane;
    
    @FXML
    private TableView<Employee> userTableView;
    @FXML
    private TableColumn<Employee, String> colEmpId;
    @FXML
    private TableColumn<Employee, String> colEmpUsername;
    @FXML
    private TableColumn<Employee, String> colEmpFname;
    @FXML
    private TableColumn<Employee, String> colEmpLname;
    @FXML
    private TableColumn<Employee, String> colEmpEmail;
    @FXML
    private TableColumn<Employee, String> colEmpRole;
    
    public static ObservableList<Employee> userAccountList = FXCollections.observableArrayList();

    Employee employee = new Employee();
    @FXML
    private StackPane stackpane;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeUserAccountColumns();
        loadUserAccountData();
        selectAccountToDelete();
    }    
    
    private void initializeUserAccountColumns(){    
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpUsername.setCellValueFactory(new PropertyValueFactory<>("empUsername"));
        colEmpFname.setCellValueFactory(new PropertyValueFactory<>("empFname"));
        colEmpLname.setCellValueFactory(new PropertyValueFactory<>("empLname"));
        colEmpEmail.setCellValueFactory(new PropertyValueFactory<>("empEmail"));
        colEmpRole.setCellValueFactory(new PropertyValueFactory<>("empType"));
    }
    
    private void loadUserAccountData(){
        employee.LoadUserAccountDetails();
        userTableView.getItems().setAll(userAccountList);
    }
    
     public void selectAccountToDelete(){
        userTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        userTableView.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            for(int i : userTableView.getSelectionModel().getSelectedIndices()){
               HBox hbox = new HBox();
               hbox.setAlignment(Pos.CENTER_RIGHT);
               hbox.setMinHeight(65);
               hbox.setSpacing(10);
               hbox.setStyle("-fx-background-color:#c7e0e0"); 
              // hbox.setPadding(new Insets(i, i, i, i));
               
               JFXButton btnCancel = new JFXButton("Cancel");
               FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLONE);
               icon.setScaleY(2);
               icon.setScaleX(2);
               icon.setScaleZ(2);
               btnCancel.setMinSize(60, 55);
               btnCancel.setContentDisplay(ContentDisplay.TOP);
               btnCancel.setAlignment(Pos.CENTER);
               btnCancel.setGraphic(icon);
               btnCancel.setPadding(new Insets(0, 0, 0, 10));
               
               Separator separator = new Separator(Orientation.VERTICAL);
               separator.setPadding(new Insets(4, 0, 4, 10));
               
               JFXButton btnDelete = new JFXButton("Delete");
               FontAwesomeIconView icon1 = new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT);
               icon1.setScaleY(2);
               icon1.setScaleX(2);
               icon1.setScaleZ(2);
               btnDelete.setMinSize(60, 55);
               btnDelete.setContentDisplay(ContentDisplay.TOP);
               btnDelete.setAlignment(Pos.CENTER);
               btnDelete.setGraphic(icon1);
               btnDelete.setPadding(new Insets(0, 0, 0, 10));
               
               JFXButton btnSelectAll = new JFXButton("Select all");
               FontAwesomeIconView icon2 = new FontAwesomeIconView(FontAwesomeIcon.LIST_ALT);
               icon2.setScaleY(2);
               icon2.setScaleX(2);
               icon2.setScaleZ(2);
               btnSelectAll.setMinSize(60, 55);
               btnSelectAll.setContentDisplay(ContentDisplay.TOP);
               btnSelectAll.setAlignment(Pos.CENTER);
               btnSelectAll.setGraphic(icon2);
               btnSelectAll.setPadding(new Insets(0, 10, 0, 10));
              
               hbox.getChildren().addAll(btnCancel, separator, btnDelete, btnSelectAll);
                 
            TranslateTransition translate = new TranslateTransition(Duration.seconds(.5), hbox);
            translate.setFromX(100);
            translate.setToX(0);     
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(.5), hbox);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1); 
            borderpane.setBottom(hbox);
            //borderpane.getChildren().remove(hbox);
            translate.play();
            fadeIn.play();           
             }
        });
        
    }



}
