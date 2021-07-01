/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Model.Case;
import Model.Person;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class GeneralTableController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label labelNationalId;
    @FXML
    private Pagination pagination;
            
    public static int index = 0;
    private final int rowsPerPage = 10;
    
    public static StackPane tempComplainantPane;
    
    private TableView<Case> tableCase = createTableCaseSummary();
   // public static List<Person> personList = new ArrayList<>();
     
    public static ObservableList<Case> caseList = FXCollections.observableArrayList();
    FilteredList<Case> filteredList = new FilteredList<>(caseList,e->true);
    
    public static BorderPane tempBorderpane;
    @FXML
    private StackPane mainStackPane;

    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      tempComplainantPane = mainStackPane;
      tempBorderpane = borderpane;
      initializeData();
    }   
    
    
     private void initializeData(){
        Case caseSummary = new Case();
        caseSummary.selectAllConcludedCasesDetails();
        
        pagination.setPageFactory(this::createPage);
    }
    private TableView<Case> createTableCaseSummary() {

        TableView<Case> table = new TableView<>();

          /* private int caseNo;
    private String caseOfficer;
    private String caseNatuture;
    private String caseDesc;
    private String caseProgress;
    private String caseStatus;*/
        TableColumn<Case, String> colCaseNo = new TableColumn<>("Case No");
        colCaseNo.setCellValueFactory(new PropertyValueFactory<>("caseNo"));
        colCaseNo.setMinWidth(10);
        colCaseNo.setPrefWidth(70);
        
        TableColumn<Case, String> colOfficerName = new TableColumn<>("Case Officer");
        colOfficerName.setCellValueFactory(new PropertyValueFactory<>("caseOfficer"));
        colOfficerName.setMinWidth(10);
        colOfficerName.setPrefWidth(100);
        
        TableColumn<Case, String> colCaseNature = new TableColumn<>("Case Nature");
        colCaseNature.setCellValueFactory(new PropertyValueFactory<>("caseNatuture"));
        colCaseNature.setMinWidth(10);
        colCaseNature.setPrefWidth(100);
        
        TableColumn<Case, String> colProgressMade = new TableColumn<>("Closure Description");
        colProgressMade.setCellValueFactory(new PropertyValueFactory<>("caseProgress"));
        colProgressMade.setMinWidth(10);
        colProgressMade.setPrefWidth(70);
      //  colGender.setMaxWidth(100);
        
        TableColumn<Case, String> colStatus = new TableColumn<>("Case Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("caseStatus"));
        colStatus.setMinWidth(10);
        colStatus.setPrefWidth(100);
        
        TableColumn<Case, String> colCaseDesc = new TableColumn<>("Case Summary");
        colCaseDesc.setCellValueFactory(new PropertyValueFactory<>("caseDesc"));
        colCaseDesc.setMinWidth(10);
        colCaseDesc.setPrefWidth(100);
       // colCaseDesc.setCellFactory(WRAPPING_CELL_FACTORY);
        
        table.getColumns().addAll(colCaseNo, colOfficerName, colCaseNature, colProgressMade, colStatus, colCaseDesc);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
       
        for (TableColumn column : table.getColumns()) {
            column.setMinWidth(100);
        }
      
        return table;
    }
    
    
    private Node createPage(int pageIndex) {
        try {
            int fromIndex = pageIndex * rowsPerPage;
            int toIndex = Math.min(fromIndex + rowsPerPage, caseList.size());
            tableCase.setItems(FXCollections.observableArrayList(caseList.subList(fromIndex, toIndex)));
            //tblInvoices.setFixedCellSize(37);
            return tableCase;
        } catch (IllegalArgumentException ex) {
            return new Label("No such complainant Data\nNote: add complainant details");
        }
    }
    
    
     public static final Callback<TableColumn<Person,String>, TableCell<Person,String>> WRAPPING_CELL_FACTORY = 
            new Callback<TableColumn<Person,String>, TableCell<Person,String>>() {
        @Override
        public TableCell<Person, String> call(TableColumn<Person, String> param) {
            TableCell<Person,String> tableCell = new TableCell<Person,String>() {
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
    private void findClient(KeyEvent event) {
       
        
        FilteredList <Case> filteredList = new FilteredList<>(caseList,p->true);  
            searchField.textProperty().addListener((ObservableValue,oldValue,newValue)->{
            filteredList.setPredicate((Predicate<? super Case>) cases->{ 
                
               if (newValue == null || newValue.isEmpty()){
                        return true;
                    }  
                 String filterLowerCase = newValue.toLowerCase();
                 
               if (cases.getCaseDesc().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               if (cases.getCaseNatuture().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               if (cases.getCaseOfficer().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               
                if (cases.getCaseStatus().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
                 if (cases.getCaseProgress().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }                    
                    return false;
                  });
    
              SortedList<Case> sortedData = new SortedList<>(filteredList); 
              sortedData.comparatorProperty().bind(tableCase.comparatorProperty());
              tableCase.setItems(sortedData);
           });
             
        
        
       
    }

    @FXML
    private void exportToPdf(ActionEvent event) {
        Case cases = new Case();
        cases.exportToPdf(System.getProperty("user.home") +"\\Documents\\Ecase\\Reports\\Concluded_Cases.pdf");
    }
    
    
    
    
    
    
}
