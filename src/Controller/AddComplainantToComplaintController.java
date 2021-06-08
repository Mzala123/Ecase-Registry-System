/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.ClientListController.personList;
import Model.Employee;
import Model.Person;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author bounce
 */
public class AddComplainantToComplaintController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Person person = new Person();
    @FXML
    private StackPane stackpane;
    @FXML
    private Pagination pagination;
    
    private TableView<Person> tablePerson = createTablePerson();
   // public static List<Person> personList = new ArrayList<>();
    public static ObservableList<Person> personList = FXCollections.observableArrayList();
    
    public static int index = 0;
    private final int rowsPerPage = 8;
    
    public static StackPane tempComplainantPane;
    @FXML
    private JFXTextField searchField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            initializeData();
            tempComplainantPane = stackpane;
    }

    private void initializeData(){
        person.showClientPersonDetails();
        pagination.setPageFactory(this::createPage);
    }
    
     private Node createPage(int pageIndex) {
        try {
            int fromIndex = pageIndex * rowsPerPage;
            int toIndex = Math.min(fromIndex + rowsPerPage, personList.size());
            tablePerson.setItems(FXCollections.observableArrayList(personList.subList(fromIndex, toIndex)));
            //tblInvoices.setFixedCellSize(37);
            return tablePerson;
        } catch (IllegalArgumentException ex) {
            return new Label("No such complainant Data\nNote: add complainant details");
        }
    }
     
    private TableView<Person> createTablePerson() {

        TableView<Person> table = new TableView<>(); 
        TableColumn<Person, JFXButton> selectCol = new TableColumn<>("SELECT");
        selectCol.setCellValueFactory(new PropertyValueFactory<>("button"));
        selectCol.setMinWidth(10);
        selectCol.setPrefWidth(30);
      

        TableColumn<Person, String> colId = new TableColumn<>("National ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("nationalId"));
        colId.setMinWidth(10);
        colId.setPrefWidth(70);
        
        TableColumn<Person, String> colFname = new TableColumn<>("Firstname");
        colFname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colFname.setMinWidth(10);
        colFname.setPrefWidth(100);
        
        TableColumn<Person, String> colLname = new TableColumn<>("Lastname");
        colLname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colLname.setMinWidth(10);
        colLname.setPrefWidth(100);
        
        TableColumn<Person, String> colGender = new TableColumn<>("Gender");
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colGender.setMinWidth(10);
        colGender.setPrefWidth(70);
      //  colGender.setMaxWidth(100);
        
        TableColumn<Person, String> colContact = new TableColumn<>("Phone number");
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colContact.setMinWidth(10);
        colContact.setPrefWidth(100);
        
        TableColumn<Person, String> colAddress = new TableColumn<>("Address");
        colAddress.setCellValueFactory(new PropertyValueFactory<>("postalAddress"));
        colAddress.setMinWidth(10);
        colAddress.setPrefWidth(100);
        colAddress.setCellFactory(WRAPPING_CELL_FACTORY);
        
        table.getColumns().addAll(selectCol, colId, colFname, colLname, colAddress, colContact, colGender);
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
    private void findComplainant(KeyEvent event) {
        
        FilteredList <Person> filteredList = new FilteredList<>(personList,p->true);  
            searchField.textProperty().addListener((ObservableValue,oldValue,newValue)->{
            filteredList.setPredicate((Predicate<? super Person>) person->{ 
                
               if (newValue == null || newValue.isEmpty()){
                        return true;
                    }  
                 String filterLowerCase = newValue.toLowerCase();
                 
               if (person.getNationalId().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               if (person.getFirstName().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               if (person.getLastName().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
               
                if (person.getGender().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }
                 if (person.getNationalId().toLowerCase().contains(filterLowerCase)){
                        return true;
                    }                    
                    return false;
                  });
    
              SortedList<Person> sortedData = new SortedList<>(filteredList); 
              sortedData.comparatorProperty().bind(tablePerson.comparatorProperty());
              tablePerson.setItems(sortedData);
           });
             
    }
    
}
