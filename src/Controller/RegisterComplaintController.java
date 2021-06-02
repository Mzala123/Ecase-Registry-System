/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Complaint;
import Model.SwitchWindow;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.time.Year;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class RegisterComplaintController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private JFXTextArea complaintDesc;
    @FXML
    private ComboBox<String> regMode;
    @FXML
    private ComboBox<String> financialYear;
    @FXML
    private JFXTextArea complaintNature;

    public static String passdate;

    public static ObservableList<String> listOfIDs = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> department;
    @FXML
    private ComboBox<String> natureComplaint;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initializeRegModeList();
        initializeDepartment();
        initializeYear();
        initializeComplaintNature();

    }

    private void initializeRegModeList() {
        String[] regMode = {"Walk-In", "Facebook", "Website", "Phone Call", "Email", "WhatsApp", "Post Office", "Other"};
        ObservableList<String> list = FXCollections.observableArrayList(regMode);
        this.regMode.getItems().addAll(list);

    }

    private void initializeComplaintNature(){
        String[] nature = {"Alleged Excluding liability for defective products", "Alleged Unconscionable conduct",
            "Alleged anti-competitive business practice" ,
            "Alleged supply of products likely to cause harm when consumed"};
        ObservableList<String> list = FXCollections.observableArrayList(nature);
        this.natureComplaint.getItems().addAll(list);
    }
    private void initializeDepartment() {
        String[] department = {"Consumer Affairs", "Competition Affairs"};
        ObservableList<String> list = FXCollections.observableArrayList(department);
        this.department.getItems().addAll(list);
    }

    private void initializeYear() {
        Year currentYear = Year.now();
        int year;
        for (int count = 1; count <= 30; count++) {

            year = currentYear.getValue();
            Year nextYear = currentYear.plusYears(count);
            year = year + count;
            System.out.println(year - 1 + "/" + nextYear);
            String financialYear = ((year - 1) + "/" + nextYear);
            ObservableList<String> list = FXCollections.observableArrayList(financialYear);
            this.financialYear.getItems().addAll(list);
        }
    }

    @FXML
    private void cancelRegistration(ActionEvent event) {
    }

    @FXML
    private void registerComplaint(ActionEvent event) {
        Complaint complaint = new Complaint();
        String complaintDesc = this.complaintDesc.getText();
        complaint.setComplaintDesc(complaintDesc);
        Date currentTime = new Date();
        String regDate;
        regDate = currentTime.toString();
        complaint.setRegDate(regDate);

        if (financialYear == null) {
            Notifications notification = Notifications.create();
            notification.title("REGISTERING COMPLAINT");
            notification.text("PLEASE INCLUDE THE FINANCIAL YEAR");
            notification.hideAfter(Duration.seconds(5));
            notification.position(Pos.CENTER);
            notification.darkStyle();
            notification.showWarning();
        }
        String financialYear = this.financialYear.getValue();
        complaint.setFinancialYear(financialYear);
        
        if(natureComplaint  == null){
            Notifications notification = Notifications.create();
            notification.title("REGISTERING COMPLAINT");
            notification.text("PLEASE INCLUDE THE COMPLAINT NATURE");
            notification.hideAfter(Duration.seconds(5));
            notification.position(Pos.CENTER);
            notification.darkStyle();
            notification.showWarning();
        }
           String complaintNature = this.natureComplaint.getValue();
           complaint.setComplaintNature(complaintNature);
        if (regMode == null) {
            Notifications notification = Notifications.create();
            notification.title("REGISTERING COMPLAINT");
            notification.text("PLEASE INCLUDE THE MODE OF REGISTRATION");
            notification.hideAfter(Duration.seconds(5));
            notification.position(Pos.CENTER);
            notification.darkStyle();
            notification.showWarning();
        }

        String regMode = this.regMode.getValue();
        complaint.setRegMode(regMode);

        if (department == null) {
            Notifications notification = Notifications.create();
            notification.title("REGISTERING COMPLAINT");
            notification.text("PLEASE INCLUDE DEPARTMENT");
            notification.hideAfter(Duration.seconds(5));
            notification.position(Pos.CENTER);
            notification.darkStyle();
            notification.showWarning();
        }
        String department = this.department.getValue();
        complaint.setDepartment(department);

        if (complaintDesc.isEmpty() || complaintNature.isEmpty()) {
            Notifications notification = Notifications.create();
            notification.title("REGISTERING COMPLAINT");
            notification.text("PLEASE FILL IN THE FIELDS");
            notification.hideAfter(Duration.seconds(5));
            notification.position(Pos.CENTER);
            notification.darkStyle();
            notification.showWarning();
        }
        else {
            complaint.registerComplaint();
            SwitchWindow window = new SwitchWindow();
            window.loadNewWindow("/View/AddComplainantToComplaint.fxml", "Complainant details", true, true);
            CasePanelController.tempStackPane.getScene().getWindow().hide();
            passdate = complaint.fetchCurrentComplaintID(regDate);
            System.out.print("Date is " + passdate);
            listOfIDs.addAll(passdate);

        }
        
        
    }
    
    

}
