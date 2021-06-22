/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.AddComplainantToComplaintController;
import static Controller.CasePanelController.tempStackPane;
import Controller.ClientListController;
import static Controller.ClientListController.tempComplainantPane;
import Controller.RegisterComplaintController;
import static Controller.RegisterComplaintController.listOfIDs;
import Controller.UpdateCaseController;
import Controller.UpdateClientController;
import static Controller.UpdateClientController.malawiId;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
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
public class Person {

    private String nationalId;
    private String firstName;
    private String id;
    private String lastName;
    private String postalAddress;
    private String email;
    private String dob;
    private String contact;
    private String nationality;
    private String gender;
    private String residence;
    protected String clientType = "Person";
    private JFXCheckBox checkBox;
    private JFXButton button;

    public static List<String> complaintsDetails = new ArrayList<>();

    DBHandler handler = new DBHandler();
    PreparedStatement preparedStatement = null;

    public Person() {
    }

    public Person(String id, String postalAddress, String email, String contact) {
        this.id = id;
        this.postalAddress = postalAddress;
        this.email = email;
        this.contact = contact;
    }

    public Person(String nationalId, String firstName, String id, String lastName, String postalAddress,
            String email, String dob, String contact, String nationality, String gender, String residence) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.postalAddress = postalAddress;
        this.email = email;
        this.dob = dob;
        this.contact = contact;
        this.nationality = nationality;
        this.gender = gender;
        this.residence = residence;
    }

    public Person(String nationalId, String firstName, String lastName, String postalAddress, String contact, String gender) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalAddress = postalAddress;
        this.contact = contact;
        this.gender = gender;
    }

    public Person(String nationalId, String firstName, String lastName, String postalAddress, String contact, String gender, JFXCheckBox checkBox) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalAddress = postalAddress;
        this.contact = contact;
        this.gender = gender;
        this.checkBox = checkBox;
    }

    public Person(String nationalId, String firstName, String lastName, String postalAddress, String contact, String gender, JFXButton button) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalAddress = postalAddress;
        this.contact = contact;
        this.gender = gender;
        this.button = button;
    }

    public Person(String id, String postalAddress, String email) {
        this.id = id;
        this.postalAddress = postalAddress;
        this.email = email;

    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public void setCheckBox(JFXCheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public void setButton(JFXButton button) {
        this.button = button;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getContact() {
        return contact;
    }

    public String getNationality() {
        return nationality;
    }

    public String getGender() {
        return gender;
    }

    public String getResidence() {
        return residence;
    }

    public JFXCheckBox getCheckBox() {
        return checkBox;
    }

    public JFXButton getButton() {
        return button;
    }

    public void createPersonClient() {

        try {
            String checkRowExists = "select count(*) as no_of_rows FROM ecase.person";
            preparedStatement = handler.connection.prepareStatement(checkRowExists);
            handler.result = preparedStatement.executeQuery(checkRowExists);

            while (handler.result.next()) {
                int found = handler.result.getInt("no_of_rows");
                System.out.println("Existing id is " + found);
                if (found == 0) {

                    String query = "INSERT INTO ecase.person (nationalId, firstname, lastname,gender,"
                            + "dob,type,nationality,address,residence,phonenumber,email) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                    preparedStatement = handler.connection.prepareStatement(query);
                    preparedStatement.setString(1, getNationalId());
                    preparedStatement.setString(2, getFirstName());
                    preparedStatement.setString(3, getLastName());
                    preparedStatement.setString(4, getGender());
                    preparedStatement.setString(5, getDob());
                    preparedStatement.setString(6, clientType);
                    preparedStatement.setString(7, getNationality());
                    preparedStatement.setString(8, getPostalAddress());
                    preparedStatement.setString(9, getResidence());
                    preparedStatement.setString(10, getContact());
                    preparedStatement.setString(11, getEmail());

                    if (preparedStatement.execute()) {
                        Notifications notification = Notifications.create();
                        notification.title("CREATING CLIENT PERSON");
                        notification.text("FAILED TO ADD CLIENT PERSON");
                        notification.hideAfter(Duration.seconds(5));
                        notification.position(Pos.CENTER);
                        notification.darkStyle();
                        notification.showError();
                    } else {
                        Notifications notification = Notifications.create();
                        notification.title("CREATING CLIENT PERSON");
                        notification.text("CLIENT PERSON ADDED SUCCESSFULLY");
                        notification.hideAfter(Duration.seconds(5));
                        notification.position(Pos.TOP_RIGHT);
                        notification.darkStyle();
                        notification.showConfirm();
                        registerClientPerson();
                    }

                } else if (found > 0) {

                    String checkPersonExists = "select * from ecase.person where nationalId"
                            + " = '" + getNationalId() + "'";
                    String personId = "";
                    preparedStatement = handler.connection.prepareStatement(checkPersonExists);
                    handler.result = preparedStatement.executeQuery(checkPersonExists);
                    if (handler.result.next()) {
                        personId = handler.result.getString("nationalId");
                        System.out.println(("The availble id is" + personId));
                        if (getNationalId().equalsIgnoreCase(personId)) {
                            Notifications notification = Notifications.create();
                            notification.title("CREATING CLIENT PERSON");
                            notification.text("THIS CLIENT PERSON IS ALREADY REGISTERED");
                            notification.hideAfter(Duration.seconds(5));
                            notification.position(Pos.TOP_RIGHT);
                            notification.darkStyle();
                            notification.showWarning();
                        }
                    } else {
                        String query = "INSERT INTO ecase.person (nationalId, firstname, lastname,gender,"
                                + "dob,type,nationality,address,residence,phonenumber,email) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                        preparedStatement = handler.connection.prepareStatement(query);
                        preparedStatement.setString(1, getNationalId());
                        preparedStatement.setString(2, getFirstName());
                        preparedStatement.setString(3, getLastName());
                        preparedStatement.setString(4, getGender());
                        preparedStatement.setString(5, getDob());
                        preparedStatement.setString(6, clientType);
                        preparedStatement.setString(7, getNationality());
                        preparedStatement.setString(8, getPostalAddress());
                        preparedStatement.setString(9, getResidence());
                        preparedStatement.setString(10, getContact());
                        preparedStatement.setString(11, getEmail());

                        if (preparedStatement.execute()) {
                            Notifications notification = Notifications.create();
                            notification.title("CREATING CLIENT PERSON");
                            notification.text("FAILED TO ADD CLIENT PERSON");
                            notification.hideAfter(Duration.seconds(5));
                            notification.position(Pos.CENTER);
                            notification.darkStyle();
                            notification.showError();
                        } else {
                            Notifications notification = Notifications.create();
                            notification.title("CREATING CLIENT PERSON");
                            notification.text("CLIENT PERSON ADDED SUCCESSFULLY");
                            notification.hideAfter(Duration.seconds(5));
                            notification.position(Pos.TOP_RIGHT);
                            notification.darkStyle();
                            notification.showConfirm();
                            registerClientPerson();
                        }

                    }

                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void registerClientPerson() {

        String query = "INSERT INTO ecase.clients (typeId, clientType) VALUES(?,?)";
        try {
            preparedStatement = handler.connection.prepareStatement(query);
            preparedStatement.setString(1, getNationalId());
            preparedStatement.setString(2, clientType);
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void showClientPersonDetails() {
        String query = "select * from ecase.person";
        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            AddComplainantToComplaintController.personList.clear();
            while (handler.result.next()) {
                nationalId = handler.result.getString("nationalId");
                firstName = handler.result.getString("firstname");
                lastName = handler.result.getString("lastname");
                postalAddress = handler.result.getString("address");
                email = handler.result.getString("email");
                dob = handler.result.getString("dob");
                contact = handler.result.getString("phonenumber");
                nationality = handler.result.getString("nationality");
                gender = handler.result.getString("gender");
                residence = handler.result.getString("residence");
                clientType = handler.result.getString("type");

                AddComplainantToComplaintController.personList.addAll(new Person(nationalId, firstName,
                        lastName, postalAddress, contact, gender, insertButton(nationalId)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(Person.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ClientList() {
        String query = "select * from ecase.person";
        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            ClientListController.personList.clear();
            while (handler.result.next()) {
                nationalId = handler.result.getString("nationalId");
                firstName = handler.result.getString("firstname");
                lastName = handler.result.getString("lastname");
                postalAddress = handler.result.getString("address");
                email = handler.result.getString("email");
                dob = handler.result.getString("dob");
                contact = handler.result.getString("phonenumber");
                nationality = handler.result.getString("nationality");
                gender = handler.result.getString("gender");
                residence = handler.result.getString("residence");
                clientType = handler.result.getString("type");

                ClientListController.personList.addAll(new Person(nationalId, firstName,
                        lastName, postalAddress, contact, gender, selectActionButton(nationalId)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(Person.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    private JFXButton insertButton(String id) {

        button = new JFXButton();
        button.setPrefWidth(35);
        button.setPrefHeight(35);
        button.setContentDisplay(ContentDisplay.CENTER);
        button.setId(id);
        button.getId();
        FontAwesomeIconView icon2 = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
        button.setStyle("-fx-background-color: #c7e0e0;");
        button.getStyleClass().add("circleButton");
        icon2.setGlyphSize(20);
        // icon2.setStyle("-fx-fill: #b78;");
        icon2.setFill(Paint.valueOf("whitesmoke"));
        button.setGraphic(icon2);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Complaint complaint = new Complaint();
                String complaintIdentifier = "";
                ;

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("COMPLAINANT DETAILS");
                alert.setContentText("are these the complainant's details on the lodged Complaint?");
                ButtonType okButton = new ButtonType("YES", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("NO", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(okButton, noButton);

                alert.showAndWait().ifPresent(type -> {
                    if (type == okButton) {
                        SwitchWindow window = new SwitchWindow();
                        listOfIDs.addAll(id);
                        System.out.println("The time is " + RegisterComplaintController.passdate);
                        System.out.println("The details are " + listOfIDs);

                        window.loadNewWindow("/View/AddRespondentToComplaint.fxml",
                                "Respondent Details", true, true);
                        AddComplainantToComplaintController.tempComplainantPane.getScene().getWindow().hide();
                    } else if (type == noButton) {
                        alert.close();
                    }

                });

            }

        });

        return button;
    }

    private JFXButton selectActionButton(String id) {

        button = new JFXButton();
        button.setPrefWidth(35);
        button.setPrefHeight(35);
        button.setContentDisplay(ContentDisplay.CENTER);
        button.setId(id);
        button.getId();
        FontAwesomeIconView icon2 = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
        button.setStyle("-fx-background-color: #c7e0e0;");
        button.getStyleClass().add("circleButton");
        icon2.setGlyphSize(20);
        // icon2.setStyle("-fx-fill: #b78;");
        icon2.setFill(Paint.valueOf("#daa520"));
        button.setGraphic(icon2);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(id);
                try {
                    ClientListController.tempNationalId.setText(id);

                    System.out.println("is it setting though " + ClientListController.tempNationalId.getText());

                    Parent root = FXMLLoader.load(getClass().getResource("/View/UpdateClient.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Update Client Person");
                    stage.setMaximized(false);
                    stage.initStyle(StageStyle.UTILITY);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                    //(id);

                    System.out.println("is it is it " + id);

                } catch (IOException ex) {
                    Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });

        return button;
    }

    public String setPersonDataOnFields(String id) {
        try {

            String query = "select * from ecase.person where nationalId='" + id + "'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            if (handler.result.next()) {
                
                nationalId = handler.result.getString("nationalId");
                firstName = handler.result.getString("firstname");
                lastName = handler.result.getString("lastname");
                gender = handler.result.getString("gender");
                dob = handler.result.getString("dob");
                nationality = handler.result.getString("nationality");
                postalAddress = handler.result.getString("address");
                residence = handler.result.getString("residence");
                contact = handler.result.getString("phonenumber");
                email = handler.result.getString("email");

            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public void updatePersonClientDetails() {
        try {
           // System.out.println("Lets check this pout " +UpdateClientController.tempLabel.getText());
            String query = "select id from ecase.person where nationalId ='"+UpdateClientController.tempLabel.getText()+"'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            int id = 0;
            
            while(handler.result.next()){
                
             id = handler.result.getInt("id");
            
            }
            
            String updateQuery = "UPDATE ecase.person set nationalId=?, firstname=?, lastname=?, gender=?,"
                    + "dob=?, nationality=?, address=?, residence=?, phonenumber=?, email=? where id ='"+id+"'";
            preparedStatement = handler.connection.prepareStatement(updateQuery);      
            preparedStatement.setString(1, getNationalId());
            preparedStatement.setString(2, getFirstName());
            preparedStatement.setString(3, getLastName());
            preparedStatement.setString(4, getGender());
            preparedStatement.setString(5, getDob());
            preparedStatement.setString(6, getNationality());
            preparedStatement.setString(7, getPostalAddress());
            preparedStatement.setString(8, getResidence());
            preparedStatement.setString(9, getContact());
            preparedStatement.setString(10, getEmail());
            
             if (preparedStatement.execute() == true) {
                Notifications notification = Notifications.create();
                notification.title("Updating Client Person Details");
                notification.text("Failed to update");
                notification.hideAfter(Duration.seconds(3));
                notification.position(Pos.CENTER);
                notification.darkStyle();
                notification.showError();
            } else {

                Notifications notification = Notifications.create();
                notification.title("Updating Client Person details");
                notification.text("Update Done Sucessfully");
                notification.hideAfter(Duration.seconds(3));
                notification.position(Pos.CENTER);
                notification.darkStyle();
                notification.showConfirm();
               
            }
            ClientListController.personList.clear();
            ClientList();
             
            
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
