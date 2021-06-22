/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.AddRespondentToComplaintController;
import static Controller.AddRespondentToComplaintController.tempRespondentStackPane;
import Controller.CasePanelController;
import Controller.ClientOrganizationListController;
import static Controller.RegisterComplaintController.listOfIDs;
import Controller.UpdateOrganizationDataController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author bounce
 */
public class Organization extends Client {

    private String OrgName;
    private String businessType;
    private final String clientType = "Organization";

    private JFXCheckBox checkBox;

    private JFXButton button;

    PreparedStatement preparedStatement = null;
    DBHandler handler = new DBHandler();

    public Organization() {
    }

    public Organization(String OrgName, String businessType, String id, String postalAddress,
            String email, JFXCheckBox checkBox) {
        super(id, postalAddress, email);
        this.OrgName = OrgName;
        this.businessType = businessType;
        this.checkBox = checkBox;
    }

    public Organization(String OrgName, String businessType, String id, String postalAddress,
            String email, JFXButton button) {
        super(id, postalAddress, email);
        this.OrgName = OrgName;
        this.businessType = businessType;
        this.button = button;
    }

    public Organization(String id, String postalAddress, String email,
            String contact, String type, String redDate, String OrgName, String businessType) {
        super(id, postalAddress, email, contact, type, redDate);
        this.OrgName = OrgName;
        this.businessType = businessType;
    }

    public void setCheckBox(JFXCheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public void setOrgName(String OrgName) {
        this.OrgName = OrgName;
    }

    public JFXCheckBox getCheckBox() {
        return checkBox;
    }

    public String getOrgName() {
        return OrgName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setButton(JFXButton button) {
        this.button = button;
    }

    public JFXButton getButton() {
        return button;
    }

    public void createClientOrganization() {

        try {
            String query = "SELECT count(*) AS no_rows from ecase.organization";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            int row_found = 0;
            if (handler.result.next()) {
                row_found = handler.result.getInt("no_rows");
                if (row_found == 0) {

                    String insertQuery = "Insert into ecase.organization(registrationNo, orgName,"
                            + "orgType, postalAddress,businessType,email,regDate)"
                            + "VALUES(?,?,?,?,?,?,?)";
                    preparedStatement = handler.connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, getId());
                    preparedStatement.setString(2, getOrgName());
                    preparedStatement.setString(3, clientType);
                    preparedStatement.setString(4, getPostalAddress());
                    preparedStatement.setString(5, getBusinessType());
                    preparedStatement.setString(6, getEmail());
                    preparedStatement.setString(7, getRedDate());

                    if (preparedStatement.execute()) {
                        Notifications notification = Notifications.create();
                        notification.title("CREATING CLIENT ORGANIZATION");
                        notification.text("FAILED TO ADD CLIENT ORGANIZATION");
                        notification.hideAfter(Duration.seconds(5));
                        notification.position(Pos.CENTER);
                        notification.darkStyle();
                        notification.showError();
                    } else {
                        Notifications notification = Notifications.create();
                        notification.title("CREATING CLIENT ORGANIZATION");
                        notification.text("CLIENT ORGANIZATION ADDED SUCCESSFULLY");
                        notification.hideAfter(Duration.seconds(5));
                        notification.position(Pos.TOP_RIGHT);
                        notification.darkStyle();
                        notification.showConfirm();
                        registerClientOrganization();
                    }
                } else if (row_found > 0) {
                    String checkClientAvail = "select * from ecase.organization where registrationNo "
                            + "='" + getId() + "'";
                    preparedStatement = handler.connection.prepareStatement(checkClientAvail);
                    handler.result = preparedStatement.executeQuery(checkClientAvail);
                    if (handler.result.next()) {
                        Notifications notification = Notifications.create();
                        notification.title("CREATING CLIENT ORGANIZATION");
                        notification.text("CLIENT ORGANIZATION WITH A SIMILAR REGISTRATION ALREADY EXISTS");
                        notification.hideAfter(Duration.seconds(5));
                        notification.position(Pos.CENTER);
                        notification.darkStyle();
                        notification.showWarning();
                    } else {
                        String insertQuery = "Insert into ecase.organization(registrationNo, orgName,"
                                + "orgType, postalAddress,businessType,email,regDate)"
                                + "VALUES(?,?,?,?,?,?,?)";
                        preparedStatement = handler.connection.prepareStatement(insertQuery);
                        preparedStatement.setString(1, getId());
                        preparedStatement.setString(2, getOrgName());
                        preparedStatement.setString(3, clientType);
                        preparedStatement.setString(4, getPostalAddress());
                        preparedStatement.setString(5, getBusinessType());
                        preparedStatement.setString(6, getEmail());
                        preparedStatement.setString(7, getRedDate());

                        if (preparedStatement.execute()) {
                            Notifications notification = Notifications.create();
                            notification.title("CREATING CLIENT ORGANIZATION");
                            notification.text("FAILED TO ADD CLIENT ORGANIZATION");
                            notification.hideAfter(Duration.seconds(5));
                            notification.position(Pos.CENTER);
                            notification.darkStyle();
                            notification.showError();
                        } else {
                            Notifications notification = Notifications.create();
                            notification.title("CREATING CLIENT ORGANIZATION");
                            notification.text("CLIENT PERSON ADDED SUCCESSFULLY");
                            notification.hideAfter(Duration.seconds(5));
                            notification.position(Pos.TOP_RIGHT);
                            notification.darkStyle();
                            notification.showConfirm();

                            registerClientOrganization();
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registerClientOrganization() {

        String query = "INSERT INTO ecase.clients (typeId, clientType) VALUES(?,?)";
        try {
            preparedStatement = handler.connection.prepareStatement(query);
            preparedStatement.setString(1, getId());
            preparedStatement.setString(2, clientType);
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void listOrganizationData() {
        String query = "select * from ecase.organization ORDER BY orgName ASC";
        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            AddRespondentToComplaintController.orgList.clear();
            while (handler.result.next()) {
                String regNo = handler.result.getString("registrationNo");
                OrgName = handler.result.getString("orgName");
                businessType = handler.result.getString("businessType");
                String address = handler.result.getString("postalAddress");
                String email = handler.result.getString("email");

                AddRespondentToComplaintController.orgList.add(new Organization(OrgName, businessType,
                        regNo, address, email, chooseButton(regNo)));

            }
        } catch (SQLException ex) {
            Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JFXCheckBox getColumnItemsUsingCheckBox(String id) {
        checkBox = new JFXCheckBox();
        checkBox.setPrefWidth(30);
        checkBox.setContentDisplay(ContentDisplay.CENTER);
        checkBox.setAlignment(Pos.CENTER);
        checkBox.setId(id);
        //checkBox.setSelected(false);
        //boolean select = checkBox.isSelected() ;  
        checkBox.setOnAction((event) -> {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("id is " + id);
            alert.showAndWait();
        });

        return checkBox;
    }

    private JFXButton chooseButton(String id) {
        button = new JFXButton();
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
        button.setStyle("-fx-background-color: #c7e0e0;");
        button.getStyleClass().add("circleButton");
        icon.setGlyphSize(20);
        button.setId(id);
        button.setPrefWidth(35);
        button.setPrefHeight(35);
        button.setGraphic(icon);
        icon.setFill(Paint.valueOf("black"));
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        button.setOnAction((event) -> {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            //alert.set
            alert.setHeaderText("RESPONDENT DETAILS");
            alert.setContentText("are these the RESPONDENT's details on the lodged Complaint?");
            ButtonType okButton = new ButtonType("YES", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("NO", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    listOfIDs.addAll(id);
                    System.out.println("The fetched data are " + listOfIDs);
                    int complaintId = fetchID(listOfIDs.get(0));
                    String complainantId = listOfIDs.get(1);
                    String respondentId = listOfIDs.get(2);
                    createComplaint(complaintId, complainantId, respondentId);
                    assignCaseAutomatically();
                    listOfIDs.clear();
                    SwitchWindow window = new SwitchWindow();
                    window.loadNewWindow("/View/ComplaintList.fxml", "List of complaints", true, true);
                    tempRespondentStackPane.getScene().getWindow().hide();
                    CasePanelController.tempStackPane.getScene().getWindow().hide();
                } else if (type == noButton) {
                    alert.close();
                }

            });

            /* if (alert.getResult().getText().equals("OK")) {
                listOfIDs.addAll(id);
                System.out.println("The fetched data are " + listOfIDs);
                int complaintId = fetchID(listOfIDs.get(0));
                String complainantId = listOfIDs.get(1);
                String respondentId = listOfIDs.get(2);
                createComplaint(complaintId, complainantId, respondentId);
                assignCaseAutomatically();
                SwitchWindow window = new SwitchWindow();
                window.loadNewWindow("/View/ComplaintList.fxml", "List of complaints", true, true);
                tempRespondentStackPane.getScene().getWindow().hide();
                CasePanelController.tempStackPane.getScene().getWindow().hide();

            } else {
                alert.close();
            }*/
        });

        return button;
    }

    private int fetchID(String dateLodged) {
        int id = 0;
        String query = "select * from ecase.complaint where regDate = '" + dateLodged + "'";
        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            if (handler.result.next()) {
                id = handler.result.getInt("complaintId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    private void createComplaint(int complaintId, String complainantId, String respondentId) {
        String insert = "INSERT INTO ecase.complaint_details (complaintId, complainantId, respondentId)"
                + "VALUES(?,?,?)";
        try {
            preparedStatement = handler.connection.prepareStatement(insert);
            preparedStatement.setInt(1, complaintId);
            preparedStatement.setString(2, complainantId);
            preparedStatement.setString(3, respondentId);

            System.out.println("The first set item is " + complaintId);
            System.out.println("The second set item is " + complainantId);
            System.out.println("The third set item is " + respondentId);

            if (preparedStatement.execute()) {
                Notifications notification = Notifications.create();
                notification.title("COMPLAINT REGISTRATION");
                notification.text("FAILED TO REGISTER COMPLAINT");
                notification.hideAfter(Duration.seconds(5));
                notification.position(Pos.CENTER);
                notification.darkStyle();
                notification.showError();
            } else {
                Notifications notification = Notifications.create();
                notification.title("COMPLAINT REGISTRATION");
                notification.text("COMPLAINT REGISTERED SUCCESSFULLY");
                notification.hideAfter(Duration.seconds(5));
                notification.position(Pos.TOP_RIGHT);
                notification.darkStyle();
                notification.showConfirm();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void assignCaseAutomatically() {
        Random randomNumber = new Random();
        int face;
        //int caseno = 0;
        int detailId = selectCase();
        System.out.println(detailId);
        String query = "Select Id as totalOfficer from ecase.user where Usertype ='Case Officer'";
        int seedingValue;

        // String caseDetailQuery = "Select detailId from ecase.complaint_details where isAssigned = 0";
        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            while (handler.result.next()) {
                seedingValue = handler.result.getInt("totalOfficer");
                face = 1 + randomNumber.nextInt(seedingValue);
                String updateQuery = "UPDATE complaint_details SET isAssigned = '1', "
                        + "caseOfficerId = '" + face + "' WHERE detailId ='" + detailId + "'";
                preparedStatement = handler.connection.prepareStatement(updateQuery);

                if (preparedStatement.execute() == true) {
                    Notifications notification = Notifications.create();
                    notification.title("Automatic assignment of a case to a case Officer");
                    notification.text("Failed to assign case");
                    notification.hideAfter(Duration.seconds(5));
                    notification.position(Pos.BOTTOM_CENTER);
                    notification.darkStyle();
                    notification.showError();
                } else {

                    Notifications notification = Notifications.create();
                    notification.title("Automatic assignment of a case to a case Officer");
                    notification.text("Done Sucessfully");
                    notification.hideAfter(Duration.seconds(5));
                    notification.position(Pos.BOTTOM_CENTER);
                    notification.darkStyle();
                    notification.showConfirm();
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int selectCase() {
        int caseDetailId = 0;
        String query = "Select * from complaint_details where isAssigned = '0'";
        try {
            preparedStatement = handler.connection.prepareStatement(query);
            System.out.println("Lets pull the id");
            handler.result = preparedStatement.executeQuery(query);
            if (handler.result.next()) {
                caseDetailId = handler.result.getInt("detailId");
                System.out.println("The case is" + caseDetailId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
        }

        return caseDetailId;
    }

    public void OrganizationDataList() {
        String query = "select * from ecase.organization ORDER BY orgName ASC";
        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            ClientOrganizationListController.orgList.clear();
            while (handler.result.next()) {
                String regNo = handler.result.getString("registrationNo");
                OrgName = handler.result.getString("orgName");
                businessType = handler.result.getString("businessType");
                String address = handler.result.getString("postalAddress");
                String email = handler.result.getString("email");

                ClientOrganizationListController.orgList.add(new Organization(OrgName, businessType,
                        regNo, address, email, selectRowToEdit(regNo)));

            }
        } catch (SQLException ex) {
            Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JFXButton selectRowToEdit(String id) {
        button = new JFXButton();
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
        button.setStyle("-fx-background-color: #c7e0e0;");
        button.getStyleClass().add("circleButton");
        icon.setGlyphSize(20);
        button.setId(id);
        button.setPrefWidth(35);
        button.setPrefHeight(35);
        button.setGraphic(icon);
        icon.setFill(Paint.valueOf("#daa520"));
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(id);
                try {
                    ClientOrganizationListController.tempLabelOrgNo.setText(id);

                    //  System.out.println("is it setting though " + ClientListController.tempNationalId.getText());
                    Parent root = FXMLLoader.load(getClass().getResource("/View/UpdateOrganizationData.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Update Client Organization");
                    stage.setMaximized(false);
                    stage.initStyle(StageStyle.UTILITY);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();

                    System.out.println("is it is it " + id);

                } catch (IOException ex) {
                    Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });

        return button;
    }

    public String setOrganizationData(String businessNo) {
        Organization organization = new Organization();
        try {
            String query = "select * from ecase.organization where registrationNo ='" + businessNo + "'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            if (handler.result.next()) {
                String businessID = handler.result.getString("registrationNo");
                UpdateOrganizationDataController.tempBusinessRegNo.setText(businessID);
                OrgName = handler.result.getString("orgName");
                businessType = handler.result.getString("businessType");
                String address = handler.result.getString("postalAddress");
                UpdateOrganizationDataController.tempPostalAddress.setText(address);
                String email = handler.result.getString("email");
                UpdateOrganizationDataController.tempBusinessEmail.setText(email);
                String regDate = handler.result.getString("regDate");
                try {
                    UpdateOrganizationDataController.tempRegDate.setValue(LOCAL_DATE(regDate));
                } catch (NullPointerException e) {
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
        }

        return businessNo;
    }

    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.s");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    public void updateClientOrgdetails() {
        try {
            String query = "select id from ecase.organization "
                    + "where registrationNo='" + UpdateOrganizationDataController.tempLabelRegNo.getText()+ "'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            int id = 0;
            while (handler.result.next()) {
                
                id = handler.result.getInt("id");
            }
            String updateQuery = "Update ecase.organization set registrationNo=?, orgName=?,"
                    + "postalAddress=?, businessType=?, email=?, regDate=? where id='"+id+"'";
            preparedStatement = handler.connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, getId());
            preparedStatement.setString(2, getOrgName());
            preparedStatement.setString(3, getPostalAddress());
            preparedStatement.setString(4, getBusinessType());
            preparedStatement.setString(5, getEmail());
            preparedStatement.setString(6, getRedDate());
            
            if (preparedStatement.execute() == true) {
                Notifications notification = Notifications.create();
                notification.title("Updating Client Organization Details");
                notification.text("Failed to update");
                notification.hideAfter(Duration.seconds(3));
                notification.position(Pos.CENTER);
                notification.darkStyle();
                notification.showError();
            } else {

                Notifications notification = Notifications.create();
                notification.title("Updating Client Organization details");
                notification.text("Update Done Sucessfully");
                notification.hideAfter(Duration.seconds(2));
                notification.position(Pos.TOP_RIGHT);
                notification.darkStyle();
                notification.showConfirm();
               
                String updatequery1 = "UPDATE ecase.complaint_details set respondentId=?"
                        + "where respondentId='"+UpdateOrganizationDataController.tempLabelRegNo.getText()+"'";
                preparedStatement = handler.connection.prepareStatement(updatequery1);
                preparedStatement.setString(1, getId());
                
                if (preparedStatement.execute() == true) {
                notification = Notifications.create();
                notification.title("Updating Client Organization Details");
                notification.text("Failed to update");
                notification.hideAfter(Duration.seconds(3));
                notification.position(Pos.CENTER);
                notification.darkStyle();
                notification.showError();
                }
                else {

                notification = Notifications.create();
                notification.title("Updating Client Organization details");
                notification.text("Table Complaint Details Also Updated Sucessfully");
                notification.hideAfter(Duration.seconds(3));
                notification.position(Pos.TOP_RIGHT);
                notification.darkStyle();
                notification.showConfirm();
                }
                
            }
                
            } catch (SQLException ex) {
            Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        /* catch (SQLException ex) {
            Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}
