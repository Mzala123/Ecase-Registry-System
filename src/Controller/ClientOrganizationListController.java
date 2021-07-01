/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.AddRespondentToComplaintController.createTableOrganization;
import static Controller.AddRespondentToComplaintController.orgList;
import static Controller.AddRespondentToComplaintController.tableOrg;
import Model.Organization;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class ClientOrganizationListController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Pagination pagination;
    
    
     Organization organization = new Organization();
    
    public static TableView<Organization> tableOrg = createTableOrganization();
   // public static List<Person> personList = new ArrayList<>();
    public static ObservableList<Organization> orgList = FXCollections.observableArrayList();
    
    public static int index = 0;
    private final int rowsPerPage = 10;
    
     public static StackPane tempRespondentStackPane;
    @FXML
    private StackPane stackPaneMain;
    @FXML
    private Label labelBusinessOrgNo;
    
    public static Label tempLabelOrgNo = new Label();

   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tempRespondentStackPane = stackPaneMain;
        tempLabelOrgNo = labelBusinessOrgNo;
        initializeData();
        
        // TODO
    }    

    private void findRespondent(KeyEvent event) {
        FilteredList <Organization> filteredList = new FilteredList<>(orgList,p->true);  
            searchField.textProperty().addListener((ObservableValue,oldValue,newValue)->{
            filteredList.setPredicate((Predicate<? super Organization>) organization->{ 
                
               if (newValue == null || newValue.isEmpty()){
                        return true;
                    }  
                 String filterLowerCase = newValue.toLowerCase();
                 
               if (organization.getOrgName().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               if (organization.getBusinessType().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               if (organization.getId().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               
                if (organization.getPostalAddress().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
                 if (organization.getEmail().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }                    
                    return false;
                  });
    
              SortedList<Organization> sortedData = new SortedList<>(filteredList); 
              sortedData.comparatorProperty().bind(tableOrg.comparatorProperty());
              tableOrg.setItems(sortedData);
           });
             
    }

    
    private void initializeData(){
        organization.OrganizationDataList();
        pagination.setPageFactory(this::createPage);
    }
    
     private Node createPage(int pageIndex) {
        try {
            int fromIndex = pageIndex * rowsPerPage;
            int toIndex = Math.min(fromIndex + rowsPerPage, orgList.size());
            tableOrg.setItems(FXCollections.observableArrayList(orgList.subList(fromIndex, toIndex)));
            //b bn tblInvoices.setFixedCellSize(37);
            return tableOrg;
        } catch (IllegalArgumentException ex) {
            return new Label("No Data Here");
        }  
    }
    
    public static TableView<Organization> createTableOrganization(){
        
        TableView<Organization> table = new TableView<>();
        
        TableColumn<Organization, JFXButton> colSelect = new TableColumn<>("SELECT");
        colSelect.setCellValueFactory(new PropertyValueFactory<>("button"));
        colSelect.setMinWidth(10);
        colSelect.setPrefWidth(30);
        
        
        TableColumn<Organization, String> colId = new TableColumn<>("Registation Number");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setMinWidth(10);
        colId.setPrefWidth(70);
        
        TableColumn<Organization, String> colName = new TableColumn<>("Organization Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("orgName"));
        colName.setMinWidth(10);
        colName.setPrefWidth(100);
        
        TableColumn<Organization, String> colBusinessType = new TableColumn<>("Business Type");
        colBusinessType.setCellValueFactory(new PropertyValueFactory<>("businessType"));
        colBusinessType.setMinWidth(10);
        colBusinessType.setPrefWidth(100);
        
        TableColumn<Organization, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setMinWidth(10);
        colEmail.setPrefWidth(100);
        
        TableColumn<Organization, String> colAddress = new TableColumn<>("Organization Address");
        colAddress.setCellValueFactory(new PropertyValueFactory<>("postalAddress"));
        colAddress.setMinWidth(10);
        colAddress.setPrefWidth(100);
       
      
        table.getColumns().addAll(colSelect, colId, colName, colBusinessType, colEmail, colAddress);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        
       for (TableColumn column : table.getColumns()) {
            column.setMinWidth(100);
        }
      
        return table;
        
    }
    
    //start here
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
        
        FilteredList <Organization> filteredList = new FilteredList<>(orgList,p->true);  
            searchField.textProperty().addListener((ObservableValue,oldValue,newValue)->{
            filteredList.setPredicate((Predicate<? super Organization>) organization->{ 
                
               if (newValue == null || newValue.isEmpty()){
                        return true;
                    }  
                 String filterLowerCase = newValue.toLowerCase();
                 
               if (organization.getOrgName().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               if (organization.getBusinessType().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               if (organization.getId().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               
                if (organization.getPostalAddress().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
                 if (organization.getEmail().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }                    
                    return false;
                  });
    
              SortedList<Organization> sortedData = new SortedList<>(filteredList); 
              sortedData.comparatorProperty().bind(tableOrg.comparatorProperty());
              tableOrg.setItems(sortedData);
           });
             
    }

    @FXML
    private void exportToPdf(ActionEvent event) {
        Organization org = new Organization();
        org.exportToPdf(System.getProperty("user.home") +"\\Documents\\Ecase\\Reports\\Organization.pdf");
    }
    
}
