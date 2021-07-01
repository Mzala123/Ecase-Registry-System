/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Complaint;
import Model.Person;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class ComplaintSummaryListController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label labelNationalId;
    @FXML
    private Pagination pagination;
    
      Complaint complaint = new Complaint();
      public static TableView<Complaint> tableComplaint = createTableComplaintDetails();
   // public static List<Person> personList = new ArrayList<>();
    public static ObservableList<Complaint> complaintList = FXCollections.observableArrayList();
    
    public static int index = 0;
    private final int rowsPerPage = 10;
    @FXML
    private Label labelComplaintId;

     public static Label tempLabelComplaintId = new Label();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        tempLabelComplaintId = labelComplaintId;
    }
    
      private void initializeData(){
        complaint.complaintDataList();
        pagination.setPageFactory(this::createPage);
    }
    
    
    private Node createPage(int pageIndex) {
        try {
            int fromIndex = pageIndex * rowsPerPage;
            int toIndex = Math.min(fromIndex + rowsPerPage, complaintList.size());
            tableComplaint.setItems(FXCollections.observableArrayList(complaintList.subList(fromIndex, toIndex)));
            //b bn tblInvoices.setFixedCellSize(37);
            return tableComplaint;
        } catch (IllegalArgumentException ex) {
            return new Label("No Data Here");
        }  
    }
    
     public static TableView<Complaint> createTableComplaintDetails(){
        
        TableView<Complaint> table = new TableView<>();
        
        TableColumn<Complaint, JFXButton> colSelect = new TableColumn<>("SELECT");
        colSelect.setCellValueFactory(new PropertyValueFactory<>("button"));
        colSelect.setMaxWidth(10);
        colSelect.setPrefWidth(20);
        
        
        TableColumn<Complaint, Integer> colId = new TableColumn<>("Case No");
        colId.setCellValueFactory(new PropertyValueFactory<>("complaintId"));
        colId.setMinWidth(10);
        colId.setPrefWidth(20);
        
        TableColumn<Complaint, String> colNatureComplaint = new TableColumn<>("Nature Of Complaint");
        colNatureComplaint.setCellValueFactory(new PropertyValueFactory<>("complaintNature"));
        colNatureComplaint.setMinWidth(10);
        colNatureComplaint.setPrefWidth(150);
        
        TableColumn<Complaint, String> colComplaintDesc = new TableColumn<>("Complaint Summary");
        colComplaintDesc.setCellValueFactory(new PropertyValueFactory<>("complaintDesc"));
        colComplaintDesc.setMinWidth(10);
        colComplaintDesc.setPrefWidth(150);
       // Callback<Complaint>;
        
        TableColumn<Complaint, String> colRegMode = new TableColumn<>("Registration Mode");
        colRegMode.setCellValueFactory(new PropertyValueFactory<>("regMode"));
        colRegMode.setMinWidth(10);
        colRegMode.setPrefWidth(100);
        
        TableColumn<Complaint, String> colFinancialYear = new TableColumn<>("Financial Year");
        colFinancialYear.setCellValueFactory(new PropertyValueFactory<>("financialYear"));
        colFinancialYear.setMinWidth(10);
        colFinancialYear.setPrefWidth(100);
       
        TableColumn<Complaint, String> colDepartment = new TableColumn<>("Department");
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colDepartment.setMinWidth(10);
        colDepartment.setPrefWidth(100);
        
        TableColumn<Complaint, String> colRegDate = new TableColumn<>("Registration Date");
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));
        colRegDate.setMinWidth(10);
        colRegDate.setPrefWidth(100);
      
        table.getColumns().addAll(colSelect, colId, colNatureComplaint, colComplaintDesc, colDepartment, 
                colFinancialYear, colRegMode, colRegDate);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        //table.setFixedCellSize(Region.USE_COMPUTED_SIZE);
        
       for (TableColumn column : table.getColumns()) {
            column.setMinWidth(140);
        }
      
        return table;
        
    }
     
       
    //start here
    public static final Callback<TableColumn<Complaint,String>, TableCell<Complaint,String>> WRAPPING_CELL_FACTORY = 
            new Callback<TableColumn<Complaint,String>, TableCell<Complaint,String>>() {
        @Override
        public TableCell<Complaint, String> call(TableColumn<Complaint, String> param) {
            TableCell<Complaint,String> tableCell = new TableCell<Complaint,String>() {
                @Override protected void updateItem(String item, boolean empty) {
                    if (item == getItem()) return;

                    super.updateItem(item, empty);

                    if (item == null) {
                        super.setText(null);
                        super.setGraphic(null);
                    } else {
                        super.setText(null);
                        Label l = new Label(item);
                        l.setWrapText(true);
                        VBox box = new VBox(l);
                        l.heightProperty().addListener((observable,oldValue,newValue)-> {
                        	box.setPrefHeight(newValue.doubleValue()+7);
                        	Platform.runLater(()->this.getTableRow().requestLayout());
                        });
                        super.setGraphic(box);
                    }
                }
                
            };
           
            return tableCell;
             }
                
            };
    
    // end here 

    @FXML
    private void findComplaint(KeyEvent event) {
        
        
        FilteredList <Complaint> filteredList = new FilteredList<>(complaintList,p->true);  
            searchField.textProperty().addListener((ObservableValue,oldValue,newValue)->{
            filteredList.setPredicate((Predicate<? super Complaint>) complaint->{ 
                
               if (newValue == null || newValue.isEmpty()){
                        return true;
                    }  
                 String filterLowerCase = newValue.toLowerCase();
                 
               if (complaint.getComplaintNature().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               if (complaint.getComplaintDesc().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               if (complaint.getDepartment().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               
                if (complaint.getFinancialYear().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
                  if (complaint.getRegDate().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
                 if (complaint.getRegMode().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }                    
                    return false;
                  });
    
              SortedList<Complaint> sortedData = new SortedList<>(filteredList); 
              sortedData.comparatorProperty().bind(tableComplaint.comparatorProperty());
              tableComplaint.setItems(sortedData);
           });
    }

    @FXML
    private void exportToPdf(ActionEvent event) {
        Complaint complaint = new Complaint();
        complaint.exportToPdf(System.getProperty("user.home") +"\\Documents\\Ecase\\Reports\\RegisteredComplaints.pdf");
    }
    
    
}
