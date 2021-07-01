/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ComplaintListController;
import Controller.CreateUsersController;
import static Controller.CreateUsersController.file;
import Controller.DashBoardController;
import Controller.GeneralCasesController;
import Controller.OfficerCasesListController;
import Controller.GeneralDashboardController;
import static Controller.OfficerCasesListController.tempAttachmentVbox;
import static Controller.OfficerCasesListController.tempRightVbox;
import Controller.OfficerPanelController;
import Controller.OfficerPanelDashBoardController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.sun.javafx.scene.control.skin.LabeledImpl;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author bounce
 */
public class CaseFile {

    DBHandler handler = new DBHandler();
    PreparedStatement preparedStatement = null;
    JFXComboBox<String> statusCombo = new JFXComboBox<>();
    ObservableList<String> list;
    private ImageView imageView;

    private Circle circle;

    private int totalCases;
    private int allConsumerCases;
    private int allCompetitionCases;

    private int totalOngoingCase;
    private int allOngoingConsumerCases;
    private int allOngoingCompetitionCases;

    public CaseFile() {
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    private ImageView setImage() {
        ImageView view = new ImageView();
        Image image = new Image("/Images/Folders.png");
        view.setImage(image);
        view.setFitHeight(50);
        view.setFitWidth(50);

        return view;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getAllConsumerCases() {
        return allConsumerCases;
    }

    public void setAllConsumerCases(int allConsumerCases) {
        this.allConsumerCases = allConsumerCases;
    }

    public int getAllCompetitionCases() {
        return allCompetitionCases;
    }

    public void setAllCompetitionCases(int allCompetitionCases) {
        this.allCompetitionCases = allCompetitionCases;
    }

    public Circle getCircle() {
        return circle;
    }

    public Circle setCircle() {
        Circle circle = new Circle();
        //circle.setFill(Paint.valueOf("#088584"));
        circle.setStroke(Color.BLACK);
        circle.setStyle("-fx-background-color:#088584;");
        circle.setTranslateX(2);
        circle.setTranslateY(2);
        this.circle = circle;
        return circle;
    }

    public int getTotalOngoingCase() {
        return totalOngoingCase;
    }

    public void setTotalOngoingCase(int totalOngoingCase) {
        this.totalOngoingCase = totalOngoingCase;
    }

    public int getAllOngoingConsumerCases() {
        return allOngoingConsumerCases;
    }

    public void setAllOngoingConsumerCases(int allOngoingConsumerCases) {
        this.allOngoingConsumerCases = allOngoingConsumerCases;
    }

    public int getAllOngoingCompetitionCases() {
        return allOngoingCompetitionCases;
    }

    public void setAllOngoingCompetitionCases(int allOngoingCompetitionCases) {
        this.allOngoingCompetitionCases = allOngoingCompetitionCases;
    }

    public void caseDetails() {

        int complaint_detailId = 0;
        String complaintName = "";
        String caseOfficer = "";
        String firstname = "";
        String lastname = "";

        String query = "SELECT complaint_details.detailId, complaint.natureComplaint, userofficer.Firstname as Fname,"
                + "userofficer.Lastname as Lname from complaint LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId \n"
                + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId \n"
                + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId \n"
                + "LEFT JOIN userofficer ON userofficer.uniqueId = complaint_details.caseOfficerId "
                + "where complaint_details.detailId > 0 AND userofficer.Firstname='" + OfficerPanelController.checkLabel.getText() + "'";
        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);

            while (handler.result.next()) {
                complaint_detailId = handler.result.getInt("detailId");
                complaintName = handler.result.getString("natureComplaint");
                firstname = handler.result.getString("Fname");
                lastname = handler.result.getString("Lname");
                caseOfficer = firstname + " " + lastname;
                ImageView imageView = setImage();
                Circle circle = setCircle();
                HBox box = createHBox(circle, caseOfficer, complaint_detailId, complaintName);
                OfficerCasesListController.tempVbox.getChildren().addAll(box);

                // ComplaintListController.complaintList.getItems().add(box);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void GeneralcaseDetails() {

        int complaint_detailId = 0;
        String complaintName = "";
        String caseOfficer = "";
        String firstname = "";
        String lastname = "";

        String query = "SELECT complaint_details.detailId, complaint.natureComplaint, userofficer.Firstname as Fname,\n"
                + " userofficer.Lastname as Lname from complaint \n"
                + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId \n"
                + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId\n"
                + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId \n"
                + "LEFT JOIN userofficer ON userofficer.uniqueId = complaint_details.caseOfficerId \n"
                + "where complaint_details.detailId >0 AND (status='Preliminary Dismissed' OR status='Transferred' OR status='Closed')";

        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);

            while (handler.result.next()) {
                complaint_detailId = handler.result.getInt("detailId");
                complaintName = handler.result.getString("natureComplaint");
                firstname = handler.result.getString("Fname");
                lastname = handler.result.getString("Lname");
                caseOfficer = firstname + " " + lastname;
                ImageView imageView = setImage();
                Circle circle = setCircle();
                HBox box = createHBox1(circle, caseOfficer, complaint_detailId, complaintName);

                GeneralCasesController.tempVbox.getChildren().addAll(box);

                // ComplaintListController.complaintList.getItems().add(box);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private HBox createHBox(Circle Icon, String caseOfficer, int caseNo, String caseName) {
        HBox hbox = new HBox();
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(500);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //HBox hbox = new HBox();
                            hbox.setPrefSize(360, 60);
                            //  hbox.setStyle("-fx-background-color: white;");
                            hbox.getStyleClass().add("mainHbox");
                            VBox vbox = new VBox();
                            vbox.setPrefSize(60, 60);
                            vbox.setPadding(new Insets(0, 10, 0, 10));
                            vbox.setAlignment(Pos.CENTER);

                            vbox.getChildren().add(Icon);

                            VBox vboxMin = new VBox();
                            vboxMin.setPrefSize(320, 60);
                            vboxMin.setAlignment(Pos.CENTER_LEFT);
                            //vboxMin.setSpacing(10);
                            vboxMin.getStyleClass().add("miniHbox");

                            Label labelOfficer = new Label("Officer " + caseOfficer);

                            Label labelCaseNo = new Label("Case " + caseNo);
                            Label labelCaseName = new Label(caseName);
                            labelCaseName.setStyle("-fx-padding-left:5px;");
                            labelOfficer.setStyle("-fx-padding-left:5px;");
                            labelCaseNo.setStyle("-fx-padding-left:5px;");

                            Separator separator = new Separator();
                            separator.setOrientation(Orientation.HORIZONTAL);
                            separator.setStyle(""
                                    + "-fx-border-insets: 10px;"
                                    + "-fx-background-color: black;"
                                    + "-fx-margin: 5px;"
                                    + "");

                            separator.setScaleZ(.5);
                            separator.setScaleY(.5);

                            vboxMin.getChildren().addAll(labelOfficer, labelCaseNo, labelCaseName);
                            hbox.getChildren().addAll(vbox, vboxMin);
                            hbox.setOnMouseClicked((event) -> {
                                OfficerCasesListController.tempCaseDetails.getChildren().clear();
                                OfficerCasesListController.tempAttachmentVbox.getChildren().clear();
                                
                                displayCaseFileDetails(caseNo);
                                

                            });
                        }
                    });
                    return null;
                }
            };
            new Thread(task).start();
        } catch (Exception ex) {
        }

        return hbox;
    }

    
    private HBox createHBox1(Circle Icon, String caseOfficer, int caseNo, String caseName) {
        HBox hbox = new HBox();
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(500);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //HBox hbox = new HBox();
                            hbox.setPrefSize(360, 60);
                            //  hbox.setStyle("-fx-background-color: white;");
                            hbox.getStyleClass().add("mainHbox");
                            VBox vbox = new VBox();
                            vbox.setPrefSize(60, 60);
                            vbox.setPadding(new Insets(0, 10, 0, 10));
                            vbox.setAlignment(Pos.CENTER);

                            vbox.getChildren().add(Icon);

                            VBox vboxMin = new VBox();
                            vboxMin.setPrefSize(320, 60);
                            vboxMin.setAlignment(Pos.CENTER_LEFT);
                            //vboxMin.setSpacing(10);
                            vboxMin.getStyleClass().add("miniHbox");

                            Label labelOfficer = new Label("Officer " + caseOfficer);

                            Label labelCaseNo = new Label("Case " + caseNo);
                            Label labelCaseName = new Label(caseName);
                            labelCaseName.setStyle("-fx-padding-left:5px;");
                            labelOfficer.setStyle("-fx-padding-left:5px;");
                            labelCaseNo.setStyle("-fx-padding-left:5px;");

                            Separator separator = new Separator();
                            separator.setOrientation(Orientation.HORIZONTAL);
                            separator.setStyle(""
                                    + "-fx-border-insets: 10px;"
                                    + "-fx-background-color: black;"
                                    + "-fx-margin: 5px;"
                                    + "");

                            separator.setScaleZ(.5);
                            separator.setScaleY(.5);

                            vboxMin.getChildren().addAll(labelOfficer, labelCaseNo, labelCaseName);
                            hbox.getChildren().addAll(vbox, vboxMin);
                            hbox.setOnMouseClicked((event) -> {
                                
                                GeneralCasesController.tempCaseDetails.getChildren().clear();
                                GeneralCasesController.tempAttachmentVbox.getChildren().clear();
                                
                                GeneralCaseFileDetails(caseNo);

                            });
                        }
                    });
                    return null;
                }
            };
            new Thread(task).start();
        } catch (Exception ex) {
        }

        return hbox;
    }

    private void showAttachments1(int caseFile) {

        try {
            //HBox hbox = new HBox();

            String Query = "SELECT * FROM ecase.attachment where detailId ='" + caseFile + "'";
            preparedStatement = handler.connection.prepareStatement(Query);
            handler.result = preparedStatement.executeQuery(Query);
            while (handler.result.next()) {

                String nameOfFile = handler.result.getString("fileName");
                HBox hbox = createAttachmentDesign(nameOfFile, caseFile);
                GeneralCasesController.tempAttachmentVbox.getChildren().addAll(hbox);
                GeneralCasesController.tempAttachmentVbox.setAlignment(Pos.TOP_LEFT);

            }

        } catch (SQLException ex) {
            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private HBox createAttachmentDesign1(String fileName, int caseNo) {
        HBox hbox = new HBox();
        hbox.setPrefSize(240, 50);
        //hbox.setStyle("-fx-background-color: white;");
        hbox.getStyleClass().add("mainHbox");
        Label labelFileName = new Label(fileName);
        labelFileName.setPadding(new Insets(5, 5, 5, 5));
        // labelFileName.setStyle("-fx-padding-right:5;");
        labelFileName.setWrapText(true);
        hbox.getChildren().addAll(labelFileName);

        hbox.setOnMouseClicked((event) -> {
            String Name = "";
            if (event.getButton() == MouseButton.PRIMARY) {
                try {
                    String query = "Select * from ecase.attachment where fileName ='" + labelFileName.getText() + "'";
                    preparedStatement = handler.connection.prepareStatement(query);
                    handler.result = preparedStatement.executeQuery(query);
                    int size = 0;
                    byte[] contents = new byte[1024];
                    if (handler.result.next()) {
                        File file;
                        InputStream inputstream;
                        Name = handler.result.getString("fileName");
                        inputstream = handler.result.getBinaryStream("attachment");
                        file = new File(System.getProperty("user.home") + "\\Documents\\Attachments");
                        if (file.exists()) {

                        } else {
                            file.mkdir();
                        }
                        byte[] n = new byte[1024];
                        File af = new File(System.getProperty("user.home") + "\\Documents\\" + Name + "");
                        OutputStream outputStream = new FileOutputStream(af);
                        while ((size = inputstream.read(contents)) != -1) {
                            outputStream.write(contents, 0, size);
                        }
                        outputStream.close();
                        inputstream.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Desktop desktop = Desktop.getDesktop();
                                try {
                                    desktop.open(af);
                                } catch (IOException ex) {
                                    Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }).start();

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
            

        });

        return hbox;
    }
    
    
    private void showAttachments(int caseFile) {

        try {
            //HBox hbox = new HBox();

            String Query = "SELECT * FROM ecase.attachment where detailId ='" + caseFile + "'";
            preparedStatement = handler.connection.prepareStatement(Query);
            handler.result = preparedStatement.executeQuery(Query);
            while (handler.result.next()) {

                String nameOfFile = handler.result.getString("fileName");
                HBox hbox = createAttachmentDesign(nameOfFile, caseFile);
                tempAttachmentVbox.getChildren().addAll(hbox);
                tempAttachmentVbox.setAlignment(Pos.TOP_LEFT);

            }

        } catch (SQLException ex) {
            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private HBox createAttachmentDesign(String fileName, int caseNo) {
        HBox hbox = new HBox();
        hbox.setPrefSize(240, 50);
        //hbox.setStyle("-fx-background-color: white;");
        hbox.getStyleClass().add("mainHbox");
        Label labelFileName = new Label(fileName);
        labelFileName.setPadding(new Insets(5, 5, 5, 5));
        // labelFileName.setStyle("-fx-padding-right:5;");
        labelFileName.setWrapText(true);
        hbox.getChildren().addAll(labelFileName);

        hbox.setOnMouseClicked((event) -> {
            String Name = "";
            if (event.getButton() == MouseButton.PRIMARY) {
                try {
                    String query = "Select * from ecase.attachment where fileName ='" + labelFileName.getText() + "'";
                    preparedStatement = handler.connection.prepareStatement(query);
                    handler.result = preparedStatement.executeQuery(query);
                    int size = 0;
                    byte[] contents = new byte[1024];
                    if (handler.result.next()) {
                        File file;
                        InputStream inputstream;
                        Name = handler.result.getString("fileName");
                        inputstream = handler.result.getBinaryStream("attachment");
                        file = new File(System.getProperty("user.home") + "\\Documents\\Attachments");
                        if (file.exists()) {

                        } else {
                            file.mkdir();
                        }
                        byte[] n = new byte[1024];
                        File af = new File(System.getProperty("user.home") + "\\Documents\\" + Name + "");
                        OutputStream outputStream = new FileOutputStream(af);
                        while ((size = inputstream.read(contents)) != -1) {
                            outputStream.write(contents, 0, size);
                        }
                        outputStream.close();
                        inputstream.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Desktop desktop = Desktop.getDesktop();
                                try {
                                    desktop.open(af);
                                } catch (IOException ex) {
                                    Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }).start();

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {

                Label text = new Label("Remove attachment");
                Label text1 = new Label(fileName);
                JFXButton btnConfirm = new JFXButton("remove");
                btnConfirm.getStyleClass().add("btnUpdateCase");
                btnConfirm.setMinSize(80, 25);
                btnConfirm.setStyle("-fx-background-color:#c7e0e0; "
                        + "-fx-text-fill:white; -fx-border-radius:20; -fx-background-radius:20;"
                        + "-fx-pref-height:30px; -fx-pref-width: 150; -fx-text-fill:black");
                FontAwesomeIconView icon2 = new FontAwesomeIconView(FontAwesomeIcon.REMOVE);
                icon2.setGlyphSize(20);
                icon2.setFill(Paint.valueOf("#daa520"));
                btnConfirm.setGraphic(icon2);

                VBox vBox = new VBox();
                vBox.setSpacing(4);
                vBox.setAlignment(Pos.CENTER);
                vBox.setPadding(new Insets(5, 4, 5, 0));
                vBox.getChildren().addAll(text, text1, btnConfirm);
                Stage stage = new Stage();
                BorderPane borderpane = new BorderPane();
                Scene scene = new Scene(vBox, 240, 240);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UTILITY);
                stage.setMaximized(false);
                stage.show();

                btnConfirm.setOnAction((e) -> {
                    String query = "Delete from ecase.attachment where detailId='" + caseNo + "' "
                            + "AND fileName='" + fileName + "'";
                    try {
                        preparedStatement = handler.connection.prepareStatement(query);

                        if (preparedStatement.execute() == true) {
                            Notification notification = new Notification(5, "Removing a File", "Failed to remove the file");
                            notification.start();
                        } else {
                            Notification notification = new Notification(5, "Removing a File", "File Removed Successfully");
                            notification.start();
                            OfficerCasesListController.tempCaseDetails.getChildren().clear();
                            displayCaseFileDetails(caseNo);
                            OfficerCasesListController.tempAttachmentVbox.getChildren().clear();
                            showAttachments(caseNo);

                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });

            }

        });

        return hbox;
    }

    public void displayCaseFileDetails(int caseFile) {
        String query = "SELECT person.id, person.nationalId, person.firstname, person.lastname, person.gender, person.dob, \n"
                + "person.type, person.nationality, person.address, person.residence, person.phonenumber, person.email, \n"
                + "complaint.complaintId, complaint.natureComplaint, complaint.complaintDescription, complaint.registrationMode,\n"
                + "complaint.financialYear, complaint.department, organization.email as orgEmail, organization.registrationNo,\n"
                + "organization.orgName, organization.postalAddress, organization.businessType, userofficer.Firstname, userofficer.Lastname, \n"
                + "complaint_details.progressMade, complaint_details.status,attachment.fileName, attachment.attachment from complaint \n"
                + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId \n"
                + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId \n"
                + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId \n"
                + "LEFT JOIN userofficer On userofficer.uniqueId = complaint_details.caseOfficerId\n"
                + "LEFT JOIN attachment ON attachment.detailId = complaint_details.detailId\n"
                + "where complaint_details.detailId = '" + caseFile + "' AND userofficer.Firstname='" + OfficerPanelController.checkLabel.getText() + "'";

        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);

            if (handler.result.next()) {
                Label national_id = new Label("National Id");
                national_id.getStyleClass().add("labelBold");
                String nationalId = handler.result.getString("nationalId");
                Label idValue = new Label(nationalId);

                Label fullname = new Label(("Complainant Fullname"));
                fullname.getStyleClass().add("labelBold");
                String firstname = handler.result.getString("firstname");
                String lastname = handler.result.getString("lastname");
                Label fullnameValue = new Label(firstname + " " + lastname);

                Label dob = new Label("Age of Client");
                dob.getStyleClass().add("labelBold");
                String dateBirth = handler.result.getString("dob");
                Label dobValue = new Label(dateBirth);

                Label gender = new Label("Gender");
                gender.getStyleClass().add("labelBold");
                String sex = handler.result.getString("gender");
                Label genderValue = new Label(sex);

                Label Address = new Label("Address");
                Address.getStyleClass().add("labelBold");
                String address = handler.result.getString("address");
                Label addressValue = new Label(address);

                Label nationality = new Label("Nationality");
                nationality.getStyleClass().add("labelBold");
                String nation = handler.result.getString("nationality");
                Label nationalityValue = new Label(nation);

                Label Residence = new Label("Residence");
                Residence.getStyleClass().add("labelBold");
                String residence = handler.result.getString("residence");
                Label residenceValue = new Label(residence);

                Label Phonenumber = new Label("Contact number");
                Phonenumber.getStyleClass().add("labelBold");
                String contact = handler.result.getString("phonenumber");
                Label contactValue = new Label(contact);

                Label email = new Label("Email");
                email.getStyleClass().add("labelBold");
                String emailAddress = handler.result.getString("email");
                Label emailValue = new Label(emailAddress);

                Label complaintDesc = new Label("Complaint Description");
                complaintDesc.getStyleClass().add("labelBold");
                String complaintDetails = handler.result.getString("complaintDescription");
                Label complaintDescValue = new Label(complaintDetails);
                complaintDesc.setMaxWidth(300);
                complaintDesc.setWrapText(true);
                complaintDesc.setTextAlignment(TextAlignment.JUSTIFY);

                Label department = new Label("Department");
                department.getStyleClass().add("labelBold");
                String departMent = handler.result.getString("department");
                Label depapartmentValue = new Label(departMent);

                Label financial = new Label("Financial Year");
                financial.getStyleClass().add("labelBold");
                String year = handler.result.getString("financialYear");
                Label financialValue = new Label(year);

                Label regMode = new Label("Mode of Registration");
                regMode.getStyleClass().add("labelBold");
                String regmode = handler.result.getString("registrationMode");
                Label regmodeValue = new Label(regmode);

                /*Label regDate = new Label("Date Registered");
                regDate.getStyleClass().add("labelBold");
                String regdate = handler.result.getString("regDate");
                Label regdateValue = new Label(regdate);*/
                Label businessNo = new Label("Business registration number");
                businessNo.getStyleClass().add("labelBold");
                String businessno = handler.result.getString("registrationNo");
                Label businessNoValue = new Label(businessno);

                Label orgName = new Label("Business registration name");
                orgName.getStyleClass().add("labelBold");
                String orgname = handler.result.getString("orgName");
                Label orgNameValue = new Label(orgname);

                Label postalAddress = new Label("Business Postal Address");
                postalAddress.getStyleClass().add("labelBold");
                String postal = handler.result.getString("postalAddress");
                Label postalValue = new Label(postal);

                Label businessType = new Label("Business Type");
                businessType.getStyleClass().add("labelBold");
                String businesstype = handler.result.getString("businessType");
                Label businessTypeValue = new Label(orgname);

                Label businessEmail = new Label("Official Business Email");
                businessEmail.getStyleClass().add("labelBold");
                String businessemail = handler.result.getString("orgEmail");
                Label businesemailValue = new Label(businessemail);

                String caseName = handler.result.getString("natureComplaint");

                Label casename = new Label(caseName);
                casename.getStyleClass().add("labelBold");
                casename.setWrapText(true);
                //  casename.setMaxWidth(800);
                casename.setTextAlignment(TextAlignment.JUSTIFY);

                String progressMade = handler.result.getString("progressMade");
                Label progressTitle = new Label("Case Progress");
                Label progress = new Label(progressMade);
                progressTitle.getStyleClass().add("labelBold");
                progress.setWrapText(true);
                progress.setTextAlignment(TextAlignment.JUSTIFY);

                String status = handler.result.getString("status");
                Label statusTitle = new Label("Current Case Status");
                statusTitle.getStyleClass().add("labelBold");
                Label currentStatus = new Label(status);
                currentStatus.setWrapText(true);

                //progress section data
                HBox progressHbox = new HBox();
                progressHbox.setPadding(new Insets(0, 0, 0, 10));
                progressHbox.prefHeight(1000);
                progressHbox.getStyleClass().add("TopHbox");
                progressHbox.setAlignment(Pos.TOP_LEFT);

                VBox progressVbox = new VBox();
                progressVbox.setAlignment(Pos.CENTER_LEFT);
                progressVbox.setMinSize(300, 50);
                progressVbox.getChildren().addAll(progressTitle, progress);

                VBox statusVbox = new VBox();
                statusVbox.setAlignment(Pos.CENTER_LEFT);
                statusVbox.setMinSize(300, 50);
                statusVbox.getChildren().addAll(statusTitle, currentStatus);

                HBox updateCaseHbox = new HBox();
                updateCaseHbox.setPadding(new Insets(0, 0, 0, 10));
                updateCaseHbox.setMinWidth(960);
                updateCaseHbox.getStyleClass().add("TopHbox");
                updateCaseHbox.setAlignment(Pos.TOP_LEFT);

                JFXButton btnUpdate = new JFXButton("update");

                btnUpdate.getStyleClass().add("btnUpdateCase");
                btnUpdate.setMinSize(120, 30);
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
                icon.setGlyphSize(15);
                icon.setFill(Paint.valueOf("#daa520"));
                btnUpdate.setGraphic(icon);

                VBox updateCaseVbox = new VBox();
                updateCaseVbox.setAlignment(Pos.CENTER_LEFT);
                updateCaseVbox.setMinSize(300, 50);
                updateCaseVbox.getChildren().addAll(btnUpdate);

                btnUpdate.setOnAction((event) -> {
                    Stage stage = new Stage();
                    Label label = new Label("Update Case File");
                    label.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR,
                            40));
                    label.setStyle("-fx-text-fill: white");
                    HBox hbox = new HBox(label);
                    hbox.getStyleClass().add("vboxBGColor");
                    hbox.setStyle("-fx-background-color:#088584");
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setMinHeight(100);

                    JFXTextArea progressField = new JFXTextArea();
                    progressField.setPromptText("Input current progress");
                    progressField.setMaxSize(250, 135);
                    progressField.setPadding(new Insets(10, 0, 0, 0));
                    statusCombo.setMaxSize(250, 40);
                    statusCombo.setPromptText("Choose Status");
                    statusCombo.setPadding(new Insets(110, 0, 0, 20));
                    VBox vbox = new VBox();

                    HBox middleHbox = new HBox();
                    middleHbox.setMinSize(600, 100);
                    middleHbox.setAlignment(Pos.CENTER);
                    middleHbox.getChildren().addAll(progressField, statusCombo);

                    HBox bottomHbox = new HBox();
                    bottomHbox.setMaxHeight(70);

                    JFXButton btnConfirm = new JFXButton("update");
                    btnConfirm.getStyleClass().add("btnUpdateCase");
                    btnConfirm.setMinSize(100, 40);
                    btnConfirm.setStyle("-fx-background-color:#c7e0e0; "
                            + "-fx-text-fill:white; -fx-border-radius:20; -fx-background-radius:20;"
                            + "-fx-pref-height:30px; -fx-pref-width: 150; -fx-text-fill:black");
                    FontAwesomeIconView icon2 = new FontAwesomeIconView(FontAwesomeIcon.CHECK_CIRCLE);
                    icon2.setGlyphSize(20);
                    icon2.setFill(Paint.valueOf("#daa520"));
                    btnConfirm.setGraphic(icon2);
                    bottomHbox.setAlignment(Pos.CENTER);
                    bottomHbox.getChildren().add(btnConfirm);
                    bottomHbox.setPadding(new Insets(40, 0, 0, 0));
                    vbox.getChildren().addAll(middleHbox, bottomHbox);

                    BorderPane borderpane = new BorderPane();
                    borderpane.setTop(hbox);
                    borderpane.setCenter(vbox);

                    Scene scene = new Scene(borderpane, 600, 400);
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UTILITY);
                    stage.setMaximized(false);
                    stage.show();

                    initializeStatusList();
                    // list.clear();
                    btnConfirm.setOnAction((e) -> {

                        try {
                            String grabProgress = progressField.getText();

                            if (grabProgress.isEmpty()) {
                                Notification notification = new Notification(3, "Updating Case",
                                        "Please fill in the required field");
                                notification.start();
                            }

                            String grabStatus = statusCombo.getValue().toString();
                            if (grabStatus.isEmpty()) {
                                Notification notification = new Notification(3, "Updating Case",
                                        "Please fill in the required field");
                                notification.start();
                            }
                            String updateCaseQuery = "UPDATE ecase.complaint_details SET progressMade=?,"
                                    + "status=? where detailId='" + caseFile + "'";
                            preparedStatement = handler.connection.prepareStatement(updateCaseQuery);
                            preparedStatement.setString(1, grabProgress);
                            preparedStatement.setString(2, grabStatus);

                            if (preparedStatement.execute() == true) {
                                Notifications notification = Notifications.create();
                                notification.title("Updating Case details");
                                notification.text("Failed to update case");
                                notification.hideAfter(Duration.seconds(5));
                                notification.position(Pos.TOP_RIGHT);
                                notification.darkStyle();
                                notification.showError();
                            } else {

                                Notifications notification = Notifications.create();
                                notification.title("Updating Case details");
                                notification.text("Case Updated Sucessfully");
                                notification.hideAfter(Duration.seconds(5));
                                notification.position(Pos.TOP_RIGHT);
                                notification.darkStyle();
                                notification.showConfirm();
                                OfficerCasesListController.tempCaseDetails.getChildren().clear();
                                displayCaseFileDetails(caseFile);
                                OfficerCasesListController.tempAttachmentVbox.getChildren().clear();
                                showAttachments(caseFile);

                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });
                });

                //end of progress section data.
                //attachments section here
                JFXButton btnAttach = new JFXButton("Attach File");
                btnAttach.getStyleClass().add("btnAttachFile");
                btnAttach.setMinSize(120, 30);
                FontAwesomeIconView iconFile = new FontAwesomeIconView(FontAwesomeIcon.FILE);
                iconFile.setGlyphSize(15);
                iconFile.setFill(Paint.valueOf("#daa520"));
                btnAttach.setContentDisplay(ContentDisplay.LEFT);
                btnAttach.setGraphic(iconFile);
                tempRightVbox.getChildren().addAll(btnAttach);
                //tempRightVbox.setAlignment(Pos.CE);

                VBox attachFileVbox = new VBox();
                attachFileVbox.setAlignment(Pos.CENTER_LEFT);
                attachFileVbox.setMinSize(300, 50);
                attachFileVbox.getChildren().addAll(btnAttach);

                btnAttach.setOnAction((event) -> {
                    Stage stage = new Stage();

                    //hello
                    Label label = new Label("Attach Files related to this Case");
                    label.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR,
                            30));
                    label.setStyle("-fx-text-fill: white");
                    HBox hbox = new HBox(label);
                    hbox.getStyleClass().add("vboxBGColor");
                    hbox.setStyle("-fx-background-color:#088584");
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setMinHeight(100);

                    JFXTextArea filePathField = new JFXTextArea();
                    filePathField.setPromptText("File Paths");
                    filePathField.setMaxSize(350, 135);
                    filePathField.setPadding(new Insets(10, 0, 0, 0));

                    VBox vbox = new VBox();

                    HBox middleHbox = new HBox();
                    middleHbox.setMinSize(600, 100);
                    middleHbox.setAlignment(Pos.CENTER);
                    middleHbox.getChildren().addAll(filePathField);

                    HBox bottomHbox = new HBox();
                    bottomHbox.setMaxHeight(70);

                    JFXButton btnBrowse = new JFXButton("Browse Files");
                    btnBrowse.getStyleClass().add("btnUpdateCase");
                    btnBrowse.setMinSize(150, 30);
                    btnBrowse.setStyle("-fx-background-color:#c7e0e0; "
                            + "-fx-text-fill:white; -fx-border-radius:20; -fx-background-radius:20;"
                            + "-fx-pref-height:30px; -fx-pref-width: 100; -fx-text-fill:black");
                    FontAwesomeIconView icon2 = new FontAwesomeIconView(FontAwesomeIcon.PAPERCLIP);
                    icon2.setGlyphSize(20);
                    icon2.setFill(Paint.valueOf("#daa520"));
                    btnBrowse.setGraphic(icon2);
                    bottomHbox.setAlignment(Pos.CENTER);

                    JFXButton btnSave = new JFXButton("Confirm");
                    btnSave.getStyleClass().add("btnUpdateCase");
                    btnSave.setMinSize(150, 30);
                    btnSave.setStyle("-fx-background-color:#c7e0e0; "
                            + "-fx-text-fill:white; -fx-border-radius:20; -fx-background-radius:20;"
                            + "-fx-pref-height:30px; -fx-pref-width: 100; -fx-text-fill:black");
                    FontAwesomeIconView iconSave = new FontAwesomeIconView(FontAwesomeIcon.SAVE);
                    iconSave.setGlyphSize(20);
                    iconSave.setFill(Paint.valueOf("#daa520"));
                    btnSave.setGraphic(iconSave);
                    // btnSave.setPadding(new Insets(0, 10, 0, 10));
                    btnBrowse.setPadding(new Insets(0, 10, 0, 0));
                    bottomHbox.getChildren().addAll(btnBrowse, btnSave);
                    bottomHbox.setPadding(new Insets(40, 0, 0, 0));
                    vbox.getChildren().addAll(middleHbox, bottomHbox);
                    //end of hello

                    BorderPane borderpane = new BorderPane();
                    borderpane.setTop(hbox);
                    borderpane.setCenter(vbox);

                    Scene scene = new Scene(borderpane, 600, 400);
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UTILITY);
                    stage.setMaximized(false);
                    stage.show();

                    btnBrowse.setOnAction((e) -> {

                        Stage primaryStage = new Stage();

                        ImageView view = new ImageView();
                        FileChooser chooser = new FileChooser();
                        chooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("All Files", "*.png", "*.jpg", "*.gif",
                                        "*.doc", "*.pdf", "*.csv"));
                        file = chooser.showOpenDialog(primaryStage);
                        if (file != null) {
                            filePathField.setText(file.getAbsolutePath().toString());

                        }

                    });

                    btnSave.setOnAction((e) -> {
                        try {
                            File file = new File(filePathField.getText());
                            String fileName = file.getName();

                            FileInputStream inputstream;
                            if (filePathField.getText().isEmpty()) {

                            }
                            if (file.length() > 1048576) {
                                Notifications notification = Notifications.create();
                                notification.title("Attaching file");
                                notification.text("File too large");
                                notification.hideAfter(Duration.seconds(4));
                                notification.position(Pos.CENTER);
                                notification.darkStyle();
                                notification.showError();
                            }

                            String insertFileQuery = "INSERT INTO ecase.attachment(detailId, fileName, attachment)"
                                    + "VALUES(?,?,?)";
                            preparedStatement = handler.connection.prepareStatement(insertFileQuery);
                            preparedStatement.setInt(1, caseFile);
                            preparedStatement.setString(2, fileName);
                            inputstream = new FileInputStream(file);
                            preparedStatement.setBinaryStream(3, inputstream, (int) file.length());

                            if (preparedStatement.execute()) {
                                Notifications notification = Notifications.create();
                                notification.title("Attaching file");
                                notification.text("FAILED TO ATTACH A FILE");
                                notification.hideAfter(Duration.seconds(5));
                                notification.position(Pos.CENTER);
                                notification.darkStyle();
                                notification.showError();
                            } else {

                                Notification notification = new Notification(5, "Attaching a File", "File Attached Successfully");
                                notification.start();
                                OfficerCasesListController.tempCaseDetails.getChildren().clear();
                                displayCaseFileDetails(caseFile);
                                OfficerCasesListController.tempAttachmentVbox.getChildren().clear();
                                showAttachments(caseFile);
                            }

                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });

                });

                //End of attachment file
                //First row data
                HBox TopHbox = new HBox();
                TopHbox.setPadding(new Insets(0, 0, 0, 10));
                TopHbox.prefHeight(1000);

                TopHbox.getStyleClass().add("TopHbox");
                TopHbox.setAlignment(Pos.TOP_LEFT);
                VBox firstTopVbox = new VBox();
                firstTopVbox.setAlignment(Pos.CENTER_LEFT);
                firstTopVbox.setMinSize(300, 50);
                firstTopVbox.getChildren().addAll(national_id, idValue);

                VBox secondTopVbox = new VBox();
                secondTopVbox.setAlignment(Pos.CENTER_LEFT);
                secondTopVbox.setMinSize(300, 50);
                secondTopVbox.getChildren().addAll(fullname, fullnameValue);

                /*End of first row data*/
 /*Second row data achina gender and dob*/
                HBox secondRowHbox = new HBox();
                secondRowHbox.setPadding(new Insets(0, 0, 0, 10));
                secondRowHbox.setMinWidth(960);
                secondRowHbox.getStyleClass().add("TopHbox");
                secondRowHbox.setAlignment(Pos.TOP_LEFT);

                VBox firstSecondRowVbox = new VBox();
                firstSecondRowVbox.setAlignment(Pos.CENTER_LEFT);
                firstSecondRowVbox.setMinSize(300, 50);
                firstSecondRowVbox.getChildren().addAll(dob, dobValue);

                VBox secondRowVbox = new VBox();
                secondRowVbox.setAlignment(Pos.CENTER_LEFT);
                secondRowVbox.setMinSize(300, 50);
                secondRowVbox.getChildren().addAll(gender, genderValue);

                /*end of second row data achina gender and dob*/
 /*Third row data nationality and address*/
                HBox thirdMainHbox = new HBox();
                thirdMainHbox.setPadding(new Insets(0, 0, 0, 10));
                thirdMainHbox.prefHeight(1000);
                thirdMainHbox.getStyleClass().add("TopHbox");
                thirdMainHbox.setAlignment(Pos.TOP_LEFT);

                VBox thirdrowDataOne = new VBox();
                thirdrowDataOne.setAlignment(Pos.CENTER_LEFT);
                thirdrowDataOne.setMinSize(300, 50);
                thirdrowDataOne.getChildren().addAll(Address, addressValue);

                VBox thirdRowDataTwo = new VBox();
                thirdRowDataTwo.setAlignment(Pos.CENTER_LEFT);
                thirdRowDataTwo.setMinSize(300, 50);
                thirdRowDataTwo.getChildren().addAll(nationality, nationalityValue);

                /**
                 * End of the third row
                 */
                /*Fourth row data*/
                HBox fourthMainHbox = new HBox();
                fourthMainHbox.setPadding(new Insets(0, 0, 0, 10));
                fourthMainHbox.prefHeight(1000);
                fourthMainHbox.getStyleClass().add("TopHbox");
                fourthMainHbox.setAlignment(Pos.TOP_LEFT);

                VBox fourthrowDataOne = new VBox();
                fourthrowDataOne.setAlignment(Pos.CENTER_LEFT);
                fourthrowDataOne.setMinSize(300, 50);
                fourthrowDataOne.getChildren().addAll(Residence, residenceValue);

                VBox fourthRowDataTwo = new VBox();
                fourthRowDataTwo.setAlignment(Pos.CENTER_LEFT);
                fourthRowDataTwo.setMinSize(300, 50);
                fourthRowDataTwo.getChildren().addAll(Phonenumber, contactValue);

                VBox fourthRowDataThree = new VBox();
                fourthRowDataThree.setAlignment(Pos.CENTER_LEFT);
                fourthRowDataThree.setMinSize(300, 50);
                fourthRowDataThree.getChildren().addAll(email, emailValue);

                /*End of fourth row data*/
                //complaint description
                VBox fifthRowData = new VBox();
                fifthRowData.setPadding(new Insets(10, 0, 10, 10));
                fifthRowData.prefHeight(1000);
                fifthRowData.getStyleClass().add("TopHbox");
                fifthRowData.setAlignment(Pos.TOP_LEFT);
                //fifthRowData.setAlignment(Pos.CENTER_LEFT);
                fifthRowData.setMinSize(300, 50);
                fifthRowData.getChildren().addAll(complaintDesc, complaintDescValue);
                //end complaint description

                //Complaint details
                HBox complaintHbox1 = new HBox();
                complaintHbox1.setPadding(new Insets(0, 0, 0, 10));
                complaintHbox1.prefHeight(1000);
                complaintHbox1.getStyleClass().add("TopHbox");
                complaintHbox1.setAlignment(Pos.TOP_LEFT);

                VBox departmentHbox = new VBox();
                departmentHbox.setAlignment(Pos.CENTER_LEFT);
                departmentHbox.setMinSize(300, 50);
                departmentHbox.getChildren().addAll(department, depapartmentValue);

                VBox financialYearHbox = new VBox();
                financialYearHbox.setAlignment(Pos.CENTER_LEFT);
                financialYearHbox.setMinSize(300, 50);
                financialYearHbox.getChildren().addAll(financial, financialValue);

                //end complaint details
                // further complaint details reg date etc
                HBox complaintHbox2 = new HBox();
                complaintHbox2.setPadding(new Insets(0, 0, 0, 10));
                complaintHbox2.prefHeight(1000);
                complaintHbox2.getStyleClass().add("TopHbox");
                complaintHbox2.setAlignment(Pos.TOP_LEFT);

                VBox regModeVbox = new VBox();
                regModeVbox.setAlignment(Pos.CENTER_LEFT);
                regModeVbox.setMinSize(300, 50);
                regModeVbox.getChildren().addAll(regMode, regmodeValue);

                VBox regDateVbox = new VBox();
                regDateVbox.setAlignment(Pos.CENTER_LEFT);
                regDateVbox.setMinSize(300, 50);
//                regDateVbox.getChildren().addAll(regDate, regdateValue);

                //Respondent details
                HBox respondentHbox1 = new HBox();
                respondentHbox1.setPadding(new Insets(0, 0, 0, 10));
                respondentHbox1.prefHeight(1000);
                respondentHbox1.getStyleClass().add("TopHbox");
                respondentHbox1.setAlignment(Pos.TOP_LEFT);

                VBox businessRegBoVBox = new VBox();
                businessRegBoVBox.setAlignment(Pos.CENTER_LEFT);
                businessRegBoVBox.setMinSize(300, 50);
                businessRegBoVBox.getChildren().addAll(businessNo, businessNoValue);

                VBox orgNameVbox = new VBox();
                orgNameVbox.setAlignment(Pos.CENTER_LEFT);
                orgNameVbox.setMinSize(300, 50);
                orgNameVbox.getChildren().addAll(orgName, orgNameValue);

                //finish this part
                HBox resMainHbox = new HBox();
                resMainHbox.setPadding(new Insets(0, 0, 10, 10));
                resMainHbox.prefHeight(1000);
                resMainHbox.getStyleClass().add("TopHbox");
                resMainHbox.setAlignment(Pos.TOP_LEFT);

                VBox resRowDataOne = new VBox();
                resRowDataOne.setAlignment(Pos.CENTER_LEFT);
                resRowDataOne.setMinSize(300, 50);
                resRowDataOne.getChildren().addAll(postalAddress, postalValue);

                VBox resRowDataTwo = new VBox();
                resRowDataTwo.setAlignment(Pos.CENTER_LEFT);
                resRowDataTwo.setMinSize(300, 50);
                resRowDataTwo.getChildren().addAll(businessType, businessTypeValue);

                VBox resRowDataThree = new VBox();
                resRowDataThree.setAlignment(Pos.CENTER_LEFT);
                resRowDataThree.setMinSize(300, 50);
                resRowDataThree.getChildren().addAll(businessEmail, businesemailValue);

                //end of respondent details
                //Topic bar
                HBox TopicBarHbox = new HBox();
                TopicBarHbox.setPadding(new Insets(4, 4, 4, 4));
                TopicBarHbox.setPrefSize(980, 50);
                TopicBarHbox.getStyleClass().add("TopicBar");
                TopicBarHbox.setAlignment(Pos.CENTER_LEFT);

                TopicBarHbox.getChildren().add(casename);

                //Row sections here 
                HBox section1 = new HBox();
                section1.setPadding(new Insets(4, 4, 0, 5));
                section1.setPrefSize(960, 40);
                section1.getStyleClass().add("subTitleBar");
                section1.setAlignment(Pos.CENTER_LEFT);
                Label labelSection1 = new Label("Complainant Details");
                section1.getChildren().addAll(labelSection1);

                HBox section2 = new HBox();
                section2.setPadding(new Insets(4, 4, 0, 5));
                section2.setPrefSize(960, 40);
                section2.getStyleClass().add("subTitleBar");
                section2.setAlignment(Pos.CENTER_LEFT);
                Label labelSection2 = new Label("Case Details");
                section2.getChildren().addAll(labelSection2);

                HBox section3 = new HBox();
                section3.setPadding(new Insets(4, 4, 0, 5));
                section3.setPrefSize(960, 40);
                section3.getStyleClass().add("subTitleBar");
                section3.setAlignment(Pos.CENTER_LEFT);
                Label labelSection3 = new Label("Respondent Details");
                section3.getChildren().addAll(labelSection3);

                HBox section4 = new HBox();
                section4.setPadding(new Insets(4, 4, 0, 5));
                section4.setPrefSize(960, 40);
                section4.getStyleClass().add("subTitleBar");
                section4.setAlignment(Pos.CENTER_LEFT);
                Label labelSection4 = new Label("Provide Current Case progress");
                section4.getChildren().addAll(labelSection4);
                //End of row title sections

                //pull file and attachment
                //end of attachment function
                //end of topic bar/
                TopHbox.getChildren().addAll(firstTopVbox, secondTopVbox);
                progressHbox.getChildren().addAll(progressVbox, statusVbox);
                updateCaseHbox.getChildren().addAll(updateCaseVbox, attachFileVbox);
                secondRowHbox.getChildren().addAll(firstSecondRowVbox, secondRowVbox);
                thirdMainHbox.getChildren().addAll(thirdrowDataOne, thirdRowDataTwo);
                fourthMainHbox.getChildren().addAll(fourthrowDataOne, fourthRowDataTwo, fourthRowDataThree);
                complaintHbox1.getChildren().addAll(departmentHbox, financialYearHbox);
                complaintHbox2.getChildren().addAll(regModeVbox, regDateVbox);
                respondentHbox1.getChildren().addAll(businessRegBoVBox, orgNameVbox);
                resMainHbox.getChildren().addAll(resRowDataOne, resRowDataTwo, resRowDataThree);

                OfficerCasesListController.tempBorderPane.setTop(TopicBarHbox);
                OfficerCasesListController.tempCaseDetails.getChildren().addAll(section2, fifthRowData, complaintHbox1,
                        complaintHbox2, section4, progressHbox, updateCaseHbox, section1, TopHbox, secondRowHbox, thirdMainHbox, fourthMainHbox, section3,
                        respondentHbox1, resMainHbox);

                showAttachments(caseFile);

            }

        } catch (SQLException ex) {
            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initializeStatusList() {
        String[] status = {"Ongoing", "Preliminary Dismissed", "Transferred", "Closed"};
        // list.clear();
        list = FXCollections.observableArrayList(status);
        this.statusCombo.getItems().addAll(list);

    }

    public void AllRegisteredCases() {
        try {
            String query = "Select Count(*) as totalCases from ecase.complaint\n"
                    + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId \n"
                    + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId \n"
                    + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId \n"
                    + "LEFT JOIN userofficer On userofficer.uniqueId = complaint_details.caseOfficerId \n"
                    + "where userofficer.Firstname='" + OfficerPanelController.checkLabel.getText() + "'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            while (handler.result.next()) {
                totalCases = handler.result.getInt("totalCases");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }
        OfficerPanelDashBoardController.tempAllCaseLabel.setText(totalCases + "");
    }

    public void AllConsumerCases() {
        OfficerPanelDashBoardController.tempConsumerGauge.setMaxValue(totalCases);
        System.out.println("Total cases" + totalCases);
        try {
            String query = "Select Count(*) as totalCases from ecase.complaint\n"
                    + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId \n"
                    + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId \n"
                    + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId \n"
                    + "LEFT JOIN userofficer On userofficer.uniqueId = complaint_details.caseOfficerId \n"
                    + "where department='Consumer Affairs' AND "
                    + "userofficer.Firstname='" + OfficerPanelController.checkLabel.getText() + "'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            while (handler.result.next()) {
                allConsumerCases = handler.result.getInt("totalCases");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }
        OfficerPanelDashBoardController.tempConsumerGauge.setValue(allConsumerCases);
    }

    public void AllCompetitionCases() {
        OfficerPanelDashBoardController.tempCompetitionGauge.setMaxValue(totalCases);
        try {
            String query = "Select Count(*) as totalCases from ecase.complaint\n"
                    + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId \n"
                    + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId \n"
                    + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId \n"
                    + "LEFT JOIN userofficer On userofficer.uniqueId = complaint_details.caseOfficerId \n"
                    + "where department='Competition Affairs' AND "
                    + "userofficer.Firstname='" + OfficerPanelController.checkLabel.getText() + "'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            while (handler.result.next()) {
                allCompetitionCases = handler.result.getInt("totalCases");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }
        OfficerPanelDashBoardController.tempCompetitionGauge.setValue(allCompetitionCases);
    }

    public void pieChartCaseStatus() {
        int consumerCount = 0;
        int competitionCount = 0;
        int transferredCount = 0;
        int closedCount = 0;
        try {

            String query1 = "Select count(If(department='Consumer Affairs', 1, NULL)) as consumerCount, \n"
                    + "count(if(department='Competition Affairs', 1, NULL)) as competitionCount\n"
                    + "from complaint\n"
                    + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId\n"
                    + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId\n"
                    + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId\n"
                    + "LEFT JOIN userofficer On userofficer.uniqueId = complaint_details.caseOfficerId\n"
                    + "where (status='Preliminary Dismissed' OR status='Transferred' OR status ='Closed') "
                    + "AND userofficer.Firstname='" + OfficerPanelController.checkLabel.getText() + "'";

            preparedStatement = handler.connection.prepareStatement(query1);
            handler.result = preparedStatement.executeQuery(query1);
            if (handler.result.next()) {
                consumerCount = handler.result.getInt("consumerCount");
                competitionCount = handler.result.getInt("competitionCount");
                /* transferredCount = handler.result.getInt("transferCount");
                closedCount = handler.result.getInt("closeCount");*/

            }

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Concluded Consumer Cases", consumerCount),
                    new PieChart.Data("Concluded Competition Cases", competitionCount));
            OfficerPanelDashBoardController.tempPiechart.setData(pieChartData);

        } catch (SQLException ex) {
            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void barchartCaseStatus() {
        int ongoingCount = 0;
        int dismissedCount = 0;
        int transferredCount = 0;
        int closedCount = 0;
        try {

            String query = "Select count(If(status='Ongoing', 1, NULL)) as ongoingCount, \n"
                    + "count(If(status='Preliminary Dismissed', 1, NULL)) as dismissCount,\n"
                    + "count(If(status='Transferred', 1, NULL)) as transferCount,\n"
                    + "count(If(status='Closed', 1, NULL)) as closeCount from complaint\n"
                    + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId\n"
                    + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId\n"
                    + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId\n"
                    + "LEFT JOIN userofficer On userofficer.uniqueId = complaint_details.caseOfficerId \n"
                    + "where userofficer.Firstname='" + OfficerPanelController.checkLabel.getText() + "'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            if (handler.result.next()) {
                ongoingCount = handler.result.getInt("ongoingCount");
                dismissedCount = handler.result.getInt("dismissCount");
                transferredCount = handler.result.getInt("transferCount");
                closedCount = handler.result.getInt("closeCount");

            }

            final String ongoing = "Ongoing";
            final String dismissed = "Dismissed";
            final String transferred = "Transferred";
            final String closed = "Closed";

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Current Case Status");

            final XYChart.Data<String, Number> data = new XYChart.Data(ongoing, ongoingCount);
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data);
                    }
                }
            });

            final XYChart.Data<String, Number> data1 = new XYChart.Data(dismissed, dismissedCount);
            data1.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data1);
                    }
                }
            });

            final XYChart.Data<String, Number> data2 = new XYChart.Data(transferred, transferredCount);
            data2.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data2);
                    }
                }
            });

            final XYChart.Data<String, Number> data3 = new XYChart.Data(closed, closedCount);
            data3.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data3);
                    }
                }
            });

            series1.getData().addAll(data, data1, data2, data3);
            OfficerPanelDashBoardController.tempstatusBarChar.getData().addAll(series1);

        } catch (SQLException ex) {
            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayLabelForData(XYChart.Data<String, Number> data) {
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");
        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
                Group parentGroup = (Group) parent;
                parentGroup.getChildren().add(dataText);
            }

        });
        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
                dataText.setLayoutX(Math.round(bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2));
                dataText.setLayoutY(Math.round(bounds.getMinY() - dataText.prefHeight(-1) * 0.5));
            }
        });
    }

    public void ongoingCases() {
        try {
            String query = "Select Count(*) as totalCases from ecase.complaint\n"
                    + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId\n"
                    + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId\n"
                    + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId\n"
                    + "LEFT JOIN userofficer On userofficer.uniqueId = complaint_details.caseOfficerId\n"
                    + "where status = 'Ongoing'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            while (handler.result.next()) {
                totalOngoingCase = handler.result.getInt("totalCases");
            }
            GeneralDashboardController.tempAllCaseLabel.setText(totalOngoingCase + "");
        } catch (SQLException ex) {
            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ongoingConsumerCases() {
        GeneralDashboardController.tempConsumerGauge.setMaxValue(totalOngoingCase);
        try {
            String query = "Select Count(*) as totalCases from ecase.complaint\n"
                    + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId\n"
                    + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId\n"
                    + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId\n"
                    + "LEFT JOIN userofficer On userofficer.uniqueId = complaint_details.caseOfficerId\n"
                    + "where status = 'Ongoing' AND  department='Consumer Affairs'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            while (handler.result.next()) {
                allOngoingConsumerCases = handler.result.getInt("totalCases");
            }
            GeneralDashboardController.tempConsumerGauge.setValue(allOngoingConsumerCases);
        } catch (SQLException ex) {
            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ongoingCompetitonCases() {
        GeneralDashboardController.tempCompetitionGauge.setMaxValue(totalOngoingCase);
        try {
            String query = "Select Count(*) as totalCases from ecase.complaint\n"
                    + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId\n"
                    + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId\n"
                    + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId\n"
                    + "LEFT JOIN userofficer On userofficer.uniqueId = complaint_details.caseOfficerId\n"
                    + "where status = 'Ongoing' AND  department='Competition Affairs'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            while (handler.result.next()) {
                allOngoingCompetitionCases = handler.result.getInt("totalCases");
            }
            GeneralDashboardController.tempCompetitionGauge.setValue(allOngoingCompetitionCases);
        } catch (SQLException ex) {
            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void barChartConsumerStuff() {
        int dismissCount = 0;
        int transferCount = 0;
        int closeCount = 0;

        try {
            String query = "Select count(If(status='Preliminary Dismissed', 1, NULL)) as dismissCount,\n"
                    + "count(If(status='Transferred', 1, NULL)) as transferCount,\n"
                    + "count(If(status='Closed', 1, NULL)) as closeCount from complaint\n"
                    + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId\n"
                    + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId\n"
                    + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId\n"
                    + "LEFT JOIN userofficer On userofficer.Id = complaint_details.caseOfficerId \n"
                    + "where department='Consumer Affairs'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            while (handler.result.next()) {
                dismissCount = handler.result.getInt("dismissCount");
                transferCount = handler.result.getInt("transferCount");
                closeCount = handler.result.getInt("closeCount");
            }

            final String ongoing = "Ongoing";
            final String dismissed = "Dismissed";
            final String transferred = "Transferred";
            final String closed = "Closed";

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Current Case Status");

            final XYChart.Data<String, Number> data = new XYChart.Data(closed, closeCount);
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data);
                    }
                }
            });

            final XYChart.Data<String, Number> data1 = new XYChart.Data(dismissed, dismissCount);
            data1.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data1);
                    }
                }
            });

            final XYChart.Data<String, Number> data2 = new XYChart.Data(transferred, transferCount);
            data2.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data2);
                    }
                }
            });

            series1.getData().addAll(data, data1, data2);
            GeneralDashboardController.tempstatusBarChar.getData().addAll(series1);

        } catch (SQLException ex) {
            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void pieChartCompetitionStuff() {
        int dismissCount = 0;
        int transferCount = 0;
        int closeCount = 0;

        try {
            String query = "Select count(If(status='Preliminary Dismissed', 1, NULL)) as dismissCount,\n"
                    + "count(If(status='Transferred', 1, NULL)) as transferCount,\n"
                    + "count(If(status='Closed', 1, NULL)) as closeCount from complaint\n"
                    + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId\n"
                    + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId\n"
                    + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId\n"
                    + "LEFT JOIN userofficer On userofficer.Id = complaint_details.caseOfficerId \n"
                    + "where department='Competition Affairs'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            while (handler.result.next()) {
                dismissCount = handler.result.getInt("dismissCount");
                transferCount = handler.result.getInt("transferCount");
                closeCount = handler.result.getInt("closeCount");
            }

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Dismissed Cases", dismissCount),
                    new PieChart.Data("Transfered Cases", transferCount),
                    new PieChart.Data("Concluded Cases", closeCount));
            GeneralDashboardController.tempPiechart.setData(pieChartData);

        } catch (SQLException ex) {
            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ///Lets start here
    public void GeneralCaseFileDetails(int caseFile) {
      

        String query = "SELECT person.id, person.nationalId, person.firstname, person.lastname, person.gender, person.dob, \n"
                + "person.type, person.nationality, person.address, person.residence, person.phonenumber, person.email, \n"
                + "complaint.complaintId, complaint.natureComplaint, complaint.complaintDescription, complaint.registrationMode,\n"
                + "complaint.financialYear, complaint.department, organization.email as orgEmail, organization.registrationNo,\n"
                + "organization.orgName, organization.postalAddress, organization.businessType, userofficer.Firstname, userofficer.Lastname, \n"
                + "complaint_details.progressMade, complaint_details.status,attachment.fileName, attachment.attachment from complaint\n"
                + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId \n"
                + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId\n"
                + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId\n"
                + "LEFT JOIN userofficer On userofficer.uniqueId = complaint_details.caseOfficerId\n"
                + "LEFT JOIN attachment ON attachment.detailId = complaint_details.detailId\n"
                + "where complaint_details.detailId = '" + caseFile + "'";

        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);

            if (handler.result.next()) {
                Label national_id = new Label("National Id");
                national_id.getStyleClass().add("labelBold");
                String nationalId = handler.result.getString("nationalId");
                Label idValue = new Label(nationalId);

                Label fullname = new Label(("Complainant Fullname"));
                fullname.getStyleClass().add("labelBold");
                String firstname = handler.result.getString("firstname");
                String lastname = handler.result.getString("lastname");
                Label fullnameValue = new Label(firstname + " " + lastname);

                Label dob = new Label("Age of Client");
                dob.getStyleClass().add("labelBold");
                String dateBirth = handler.result.getString("dob");
                Label dobValue = new Label(dateBirth);

                Label gender = new Label("Gender");
                gender.getStyleClass().add("labelBold");
                String sex = handler.result.getString("gender");
                Label genderValue = new Label(sex);

                Label Address = new Label("Address");
                Address.getStyleClass().add("labelBold");
                String address = handler.result.getString("address");
                Label addressValue = new Label(address);

                Label nationality = new Label("Nationality");
                nationality.getStyleClass().add("labelBold");
                String nation = handler.result.getString("nationality");
                Label nationalityValue = new Label(nation);

                Label Residence = new Label("Residence");
                Residence.getStyleClass().add("labelBold");
                String residence = handler.result.getString("residence");
                Label residenceValue = new Label(residence);

                Label Phonenumber = new Label("Contact number");
                Phonenumber.getStyleClass().add("labelBold");
                String contact = handler.result.getString("phonenumber");
                Label contactValue = new Label(contact);

                Label email = new Label("Email");
                email.getStyleClass().add("labelBold");
                String emailAddress = handler.result.getString("email");
                Label emailValue = new Label(emailAddress);

                Label complaintDesc = new Label("Complaint Description");
                complaintDesc.getStyleClass().add("labelBold");
                String complaintDetails = handler.result.getString("complaintDescription");
                Label complaintDescValue = new Label(complaintDetails);
                complaintDesc.setMaxWidth(300);
                complaintDesc.setWrapText(true);
                complaintDesc.setTextAlignment(TextAlignment.JUSTIFY);

                Label department = new Label("Department");
                department.getStyleClass().add("labelBold");
                String departMent = handler.result.getString("department");
                Label depapartmentValue = new Label(departMent);

                Label financial = new Label("Financial Year");
                financial.getStyleClass().add("labelBold");
                String year = handler.result.getString("financialYear");
                Label financialValue = new Label(year);

                Label regMode = new Label("Mode of Registration");
                regMode.getStyleClass().add("labelBold");
                String regmode = handler.result.getString("registrationMode");
                Label regmodeValue = new Label(regmode);

                /*Label regDate = new Label("Date Registered");
                regDate.getStyleClass().add("labelBold");
                String regdate = handler.result.getString("regDate");
                Label regdateValue = new Label(regdate);*/
                Label businessNo = new Label("Business registration number");
                businessNo.getStyleClass().add("labelBold");
                String businessno = handler.result.getString("registrationNo");
                Label businessNoValue = new Label(businessno);

                Label orgName = new Label("Business registration name");
                orgName.getStyleClass().add("labelBold");
                String orgname = handler.result.getString("orgName");
                Label orgNameValue = new Label(orgname);

                Label postalAddress = new Label("Business Postal Address");
                postalAddress.getStyleClass().add("labelBold");
                String postal = handler.result.getString("postalAddress");
                Label postalValue = new Label(postal);

                Label businessType = new Label("Business Type");
                businessType.getStyleClass().add("labelBold");
                String businesstype = handler.result.getString("businessType");
                Label businessTypeValue = new Label(orgname);

                Label businessEmail = new Label("Official Business Email");
                businessEmail.getStyleClass().add("labelBold");
                String businessemail = handler.result.getString("orgEmail");
                Label businesemailValue = new Label(businessemail);

                String caseName = handler.result.getString("natureComplaint");

                Label casename = new Label(caseName);
                casename.getStyleClass().add("labelBold");
                casename.setWrapText(true);
                //  casename.setMaxWidth(800);
                casename.setTextAlignment(TextAlignment.JUSTIFY);

                String progressMade = handler.result.getString("progressMade");
                Label progressTitle = new Label("Case Progress");
                Label progress = new Label(progressMade);
                progressTitle.getStyleClass().add("labelBold");
                progress.setWrapText(true);
                progress.setTextAlignment(TextAlignment.JUSTIFY);

                String status = handler.result.getString("status");
                Label statusTitle = new Label("Current Case Status");
                statusTitle.getStyleClass().add("labelBold");
                Label currentStatus = new Label(status);
                currentStatus.setWrapText(true);

                //progress section data
                HBox progressHbox = new HBox();
                progressHbox.setPadding(new Insets(0, 0, 0, 10));
                progressHbox.prefHeight(1000);
                progressHbox.getStyleClass().add("TopHbox");
                progressHbox.setAlignment(Pos.TOP_LEFT);

                VBox progressVbox = new VBox();
                progressVbox.setAlignment(Pos.CENTER_LEFT);
                progressVbox.setMinSize(300, 50);
                progressVbox.getChildren().addAll(progressTitle, progress);

                VBox statusVbox = new VBox();
                statusVbox.setAlignment(Pos.CENTER_LEFT);
                statusVbox.setMinSize(300, 50);
                statusVbox.getChildren().addAll(statusTitle, currentStatus);

                HBox updateCaseHbox = new HBox();
                updateCaseHbox.setPadding(new Insets(0, 0, 0, 10));
                updateCaseHbox.setMinWidth(960);
                updateCaseHbox.getStyleClass().add("TopHbox");
                updateCaseHbox.setAlignment(Pos.TOP_LEFT);

//                JFXButton btnUpdate = new JFXButton("update");
//
//                btnUpdate.getStyleClass().add("btnUpdateCase");
//                btnUpdate.setMinSize(120, 30);
//                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
//                icon.setGlyphSize(15);
//                icon.setFill(Paint.valueOf("#daa520"));
//                btnUpdate.setGraphic(icon);
//
//                VBox updateCaseVbox = new VBox();
//                updateCaseVbox.setAlignment(Pos.CENTER_LEFT);
//                updateCaseVbox.setMinSize(300, 50);
//                updateCaseVbox.getChildren().addAll(btnUpdate);
//
//                btnUpdate.setOnAction((event) -> {
//                    Stage stage = new Stage();
//                    Label label = new Label("Update Case File");
//                    label.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR,
//                            40));
//                    label.setStyle("-fx-text-fill: white");
//                    HBox hbox = new HBox(label);
//                    hbox.getStyleClass().add("vboxBGColor");
//                    hbox.setStyle("-fx-background-color:#088584");
//                    hbox.setAlignment(Pos.CENTER);
//                    hbox.setMinHeight(100);
//
//                    JFXTextArea progressField = new JFXTextArea();
//                    progressField.setPromptText("Input current progress");
//                    progressField.setMaxSize(250, 135);
//                    progressField.setPadding(new Insets(10, 0, 0, 0));
//                    statusCombo.setMaxSize(250, 40);
//                    statusCombo.setPromptText("Choose Status");
//                    statusCombo.setPadding(new Insets(110, 0, 0, 20));
//                    VBox vbox = new VBox();
//
//                    HBox middleHbox = new HBox();
//                    middleHbox.setMinSize(600, 100);
//                    middleHbox.setAlignment(Pos.CENTER);
//                    middleHbox.getChildren().addAll(progressField, statusCombo);
//
//                    HBox bottomHbox = new HBox();
//                    bottomHbox.setMaxHeight(70);
//
//                    JFXButton btnConfirm = new JFXButton("update");
//                    btnConfirm.getStyleClass().add("btnUpdateCase");
//                    btnConfirm.setMinSize(100, 40);
//                    btnConfirm.setStyle("-fx-background-color:#c7e0e0; "
//                            + "-fx-text-fill:white; -fx-border-radius:20; -fx-background-radius:20;"
//                            + "-fx-pref-height:30px; -fx-pref-width: 150; -fx-text-fill:black");
//                    FontAwesomeIconView icon2 = new FontAwesomeIconView(FontAwesomeIcon.CHECK_CIRCLE);
//                    icon2.setGlyphSize(20);
//                    icon2.setFill(Paint.valueOf("#daa520"));
//                    btnConfirm.setGraphic(icon2);
//                    bottomHbox.setAlignment(Pos.CENTER);
//                    bottomHbox.getChildren().add(btnConfirm);
//                    bottomHbox.setPadding(new Insets(40, 0, 0, 0));
//                    vbox.getChildren().addAll(middleHbox, bottomHbox);
//
//                    BorderPane borderpane = new BorderPane();
//                    borderpane.setTop(hbox);
//                    borderpane.setCenter(vbox);
//
//                    Scene scene = new Scene(borderpane, 600, 400);
//                    stage.setScene(scene);
//                    stage.initModality(Modality.APPLICATION_MODAL);
//                    stage.initStyle(StageStyle.UTILITY);
//                    stage.setMaximized(false);
//                    stage.show();
//
//                    initializeStatusList();
//                     list.clear();
//                    btnConfirm.setOnAction((e) -> {
//
//                        try {
//                            String grabProgress = progressField.getText();
//
//                            if (grabProgress.isEmpty()) {
//                                Notification notification = new Notification(3, "Updating Case",
//                                        "Please fill in the required field");
//                                notification.start();
//                            }
//
//                            String grabStatus = statusCombo.getValue().toString();
//                            if (grabStatus.isEmpty()) {
//                                Notification notification = new Notification(3, "Updating Case",
//                                        "Please fill in the required field");
//                                notification.start();
//                            }
//                            String updateCaseQuery = "UPDATE ecase.complaint_details SET progressMade=?,"
//                                    + "status=? where detailId='" + caseFile + "'";
//                            preparedStatement = handler.connection.prepareStatement(updateCaseQuery);
//                            preparedStatement.setString(1, grabProgress);
//                            preparedStatement.setString(2, grabStatus);
//
//                            if (preparedStatement.execute() == true) {
//                                Notifications notification = Notifications.create();
//                                notification.title("Updating Case details");
//                                notification.text("Failed to update case");
//                                notification.hideAfter(Duration.seconds(5));
//                                notification.position(Pos.TOP_RIGHT);
//                                notification.darkStyle();
//                                notification.showError();
//                            } else {
//
//                                Notifications notification = Notifications.create();
//                                notification.title("Updating Case details");
//                                notification.text("Case Updated Sucessfully");
//                                notification.hideAfter(Duration.seconds(5));
//                                notification.position(Pos.TOP_RIGHT);
//                                notification.darkStyle();
//                                notification.showConfirm();
//                                GeneralCasesController.tempCaseDetails.getChildren().clear();
//                                GeneralCaseFileDetails(caseFile);
//                                GeneralCasesController.tempAttachmentVbox.getChildren().clear();
//                                showAttachments1(caseFile);
//
//                            }
//
//                        } catch (SQLException ex) {
//                            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//
//                    });
//                });

                //end of progress section data.
                //attachments section here
//                JFXButton btnAttach = new JFXButton("Attach File");
//                btnAttach.getStyleClass().add("btnAttachFile");
//                btnAttach.setMinSize(120, 30);
//                FontAwesomeIconView iconFile = new FontAwesomeIconView(FontAwesomeIcon.FILE);
//                iconFile.setGlyphSize(15);
//                iconFile.setFill(Paint.valueOf("#daa520"));
//                btnAttach.setContentDisplay(ContentDisplay.LEFT);
//                btnAttach.setGraphic(iconFile);
//                tempRightVbox.getChildren().addAll(btnAttach);
//                //tempRightVbox.setAlignment(Pos.CE);
//
//                VBox attachFileVbox = new VBox();
//                attachFileVbox.setAlignment(Pos.CENTER_LEFT);
//                attachFileVbox.setMinSize(300, 50);
//                attachFileVbox.getChildren().addAll(btnAttach);
//
//                btnAttach.setOnAction((event) -> {
//                    Stage stage = new Stage();
//
//                    //hello
//                    Label label = new Label("Attach Files related to this Case");
//                    label.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR,
//                            30));
//                    label.setStyle("-fx-text-fill: white");
//                    HBox hbox = new HBox(label);
//                    hbox.getStyleClass().add("vboxBGColor");
//                    hbox.setStyle("-fx-background-color:#088584");
//                    hbox.setAlignment(Pos.CENTER);
//                    hbox.setMinHeight(100);
//
//                    JFXTextArea filePathField = new JFXTextArea();
//                    filePathField.setPromptText("File Paths");
//                    filePathField.setMaxSize(350, 135);
//                    filePathField.setPadding(new Insets(10, 0, 0, 0));
//
//                    VBox vbox = new VBox();
//
//                    HBox middleHbox = new HBox();
//                    middleHbox.setMinSize(600, 100);
//                    middleHbox.setAlignment(Pos.CENTER);
//                    middleHbox.getChildren().addAll(filePathField);
//
//                    HBox bottomHbox = new HBox();
//                    bottomHbox.setMaxHeight(70);
//
//                    JFXButton btnBrowse = new JFXButton("Browse Files");
//                    btnBrowse.getStyleClass().add("btnUpdateCase");
//                    btnBrowse.setMinSize(150, 30);
//                    btnBrowse.setStyle("-fx-background-color:#c7e0e0; "
//                            + "-fx-text-fill:white; -fx-border-radius:20; -fx-background-radius:20;"
//                            + "-fx-pref-height:30px; -fx-pref-width: 100; -fx-text-fill:black");
//                    FontAwesomeIconView icon2 = new FontAwesomeIconView(FontAwesomeIcon.PAPERCLIP);
//                    icon2.setGlyphSize(20);
//                    icon2.setFill(Paint.valueOf("#daa520"));
//                    btnBrowse.setGraphic(icon2);
//                    bottomHbox.setAlignment(Pos.CENTER);
//
//                    JFXButton btnSave = new JFXButton("Confirm");
//                    btnSave.getStyleClass().add("btnUpdateCase");
//                    btnSave.setMinSize(150, 30);
//                    btnSave.setStyle("-fx-background-color:#c7e0e0; "
//                            + "-fx-text-fill:white; -fx-border-radius:20; -fx-background-radius:20;"
//                            + "-fx-pref-height:30px; -fx-pref-width: 100; -fx-text-fill:black");
//                    FontAwesomeIconView iconSave = new FontAwesomeIconView(FontAwesomeIcon.SAVE);
//                    iconSave.setGlyphSize(20);
//                    iconSave.setFill(Paint.valueOf("#daa520"));
//                    btnSave.setGraphic(iconSave);
//                    // btnSave.setPadding(new Insets(0, 10, 0, 10));
//                    btnBrowse.setPadding(new Insets(0, 10, 0, 0));
//                    bottomHbox.getChildren().addAll(btnBrowse, btnSave);
//                    bottomHbox.setPadding(new Insets(40, 0, 0, 0));
//                    vbox.getChildren().addAll(middleHbox, bottomHbox);
//                    //end of hello
//
//                    BorderPane borderpane = new BorderPane();
//                    borderpane.setTop(hbox);
//                    borderpane.setCenter(vbox);
//
//                    Scene scene = new Scene(borderpane, 600, 400);
//                    stage.setScene(scene);
//                    stage.initModality(Modality.APPLICATION_MODAL);
//                    stage.initStyle(StageStyle.UTILITY);
//                    stage.setMaximized(false);
//                    stage.show();
//
//                    btnBrowse.setOnAction((e) -> {
//
//                        Stage primaryStage = new Stage();
//
//                        ImageView view = new ImageView();
//                        FileChooser chooser = new FileChooser();
//                        chooser.getExtensionFilters().addAll(
//                                new FileChooser.ExtensionFilter("All Files", "*.png", "*.jpg", "*.gif",
//                                        "*.doc", "*.pdf", "*.csv"));
//                        file = chooser.showOpenDialog(primaryStage);
//                        if (file != null) {
//                            filePathField.setText(file.getAbsolutePath().toString());
//
//                        }
//
//                    });
//
//                    btnSave.setOnAction((e) -> {
//                        try {
//                            File file = new File(filePathField.getText());
//                            String fileName = file.getName();
//
//                            FileInputStream inputstream;
//                            if (filePathField.getText().isEmpty()) {
//
//                            }
//                            if (file.length() > 1048576) {
//                                Notifications notification = Notifications.create();
//                                notification.title("Attaching file");
//                                notification.text("File too large");
//                                notification.hideAfter(Duration.seconds(4));
//                                notification.position(Pos.CENTER);
//                                notification.darkStyle();
//                                notification.showError();
//                            }
//
//                            String insertFileQuery = "INSERT INTO ecase.attachment(detailId, fileName, attachment)"
//                                    + "VALUES(?,?,?)";
//                            preparedStatement = handler.connection.prepareStatement(insertFileQuery);
//                            preparedStatement.setInt(1, caseFile);
//                            preparedStatement.setString(2, fileName);
//                            inputstream = new FileInputStream(file);
//                            preparedStatement.setBinaryStream(3, inputstream, (int) file.length());
//
//                            if (preparedStatement.execute()) {
//                                Notifications notification = Notifications.create();
//                                notification.title("Attaching file");
//                                notification.text("FAILED TO ATTACH A FILE");
//                                notification.hideAfter(Duration.seconds(5));
//                                notification.position(Pos.CENTER);
//                                notification.darkStyle();
//                                notification.showError();
//                            } else {
//
//                                Notification notification = new Notification(5, "Attaching a File", "File Attached Successfully");
//                                notification.start();
//                                GeneralCasesController.tempCaseDetails.getChildren().clear();
//                                GeneralCaseFileDetails(caseFile);
//                                GeneralCasesController.tempAttachmentVbox.getChildren().clear();
//                                showAttachments1(caseFile);
//                            }
//
//                        } catch (FileNotFoundException ex) {
//                            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (SQLException ex) {
//                            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//
//                    });
//
//                });

                //End of attachment file
                //First row data
                HBox TopHbox = new HBox();
                TopHbox.setPadding(new Insets(0, 0, 0, 10));
                TopHbox.prefHeight(1000);

                TopHbox.getStyleClass().add("TopHbox");
                TopHbox.setAlignment(Pos.TOP_LEFT);
                VBox firstTopVbox = new VBox();
                firstTopVbox.setAlignment(Pos.CENTER_LEFT);
                firstTopVbox.setMinSize(300, 50);
                firstTopVbox.getChildren().addAll(national_id, idValue);

                VBox secondTopVbox = new VBox();
                secondTopVbox.setAlignment(Pos.CENTER_LEFT);
                secondTopVbox.setMinSize(300, 50);
                secondTopVbox.getChildren().addAll(fullname, fullnameValue);

                /*End of first row data*/
 /*Second row data achina gender and dob*/
                HBox secondRowHbox = new HBox();
                secondRowHbox.setPadding(new Insets(0, 0, 0, 10));
                secondRowHbox.setMinWidth(960);
                secondRowHbox.getStyleClass().add("TopHbox");
                secondRowHbox.setAlignment(Pos.TOP_LEFT);

                VBox firstSecondRowVbox = new VBox();
                firstSecondRowVbox.setAlignment(Pos.CENTER_LEFT);
                firstSecondRowVbox.setMinSize(300, 50);
                firstSecondRowVbox.getChildren().addAll(dob, dobValue);

                VBox secondRowVbox = new VBox();
                secondRowVbox.setAlignment(Pos.CENTER_LEFT);
                secondRowVbox.setMinSize(300, 50);
                secondRowVbox.getChildren().addAll(gender, genderValue);

                /*end of second row data achina gender and dob*/
 /*Third row data nationality and address*/
                HBox thirdMainHbox = new HBox();
                thirdMainHbox.setPadding(new Insets(0, 0, 0, 10));
                thirdMainHbox.prefHeight(1000);
                thirdMainHbox.getStyleClass().add("TopHbox");
                thirdMainHbox.setAlignment(Pos.TOP_LEFT);

                VBox thirdrowDataOne = new VBox();
                thirdrowDataOne.setAlignment(Pos.CENTER_LEFT);
                thirdrowDataOne.setMinSize(300, 50);
                thirdrowDataOne.getChildren().addAll(Address, addressValue);

                VBox thirdRowDataTwo = new VBox();
                thirdRowDataTwo.setAlignment(Pos.CENTER_LEFT);
                thirdRowDataTwo.setMinSize(300, 50);
                thirdRowDataTwo.getChildren().addAll(nationality, nationalityValue);

                /**
                 * End of the third row
                 */
                /*Fourth row data*/
                HBox fourthMainHbox = new HBox();
                fourthMainHbox.setPadding(new Insets(0, 0, 0, 10));
                fourthMainHbox.prefHeight(1000);
                fourthMainHbox.getStyleClass().add("TopHbox");
                fourthMainHbox.setAlignment(Pos.TOP_LEFT);

                VBox fourthrowDataOne = new VBox();
                fourthrowDataOne.setAlignment(Pos.CENTER_LEFT);
                fourthrowDataOne.setMinSize(300, 50);
                fourthrowDataOne.getChildren().addAll(Residence, residenceValue);

                VBox fourthRowDataTwo = new VBox();
                fourthRowDataTwo.setAlignment(Pos.CENTER_LEFT);
                fourthRowDataTwo.setMinSize(300, 50);
                fourthRowDataTwo.getChildren().addAll(Phonenumber, contactValue);

                VBox fourthRowDataThree = new VBox();
                fourthRowDataThree.setAlignment(Pos.CENTER_LEFT);
                fourthRowDataThree.setMinSize(300, 50);
                fourthRowDataThree.getChildren().addAll(email, emailValue);

                /*End of fourth row data*/
                //complaint description
                VBox fifthRowData = new VBox();
                fifthRowData.setPadding(new Insets(10, 0, 10, 10));
                fifthRowData.prefHeight(1000);
                fifthRowData.getStyleClass().add("TopHbox");
                fifthRowData.setAlignment(Pos.TOP_LEFT);
                //fifthRowData.setAlignment(Pos.CENTER_LEFT);
                fifthRowData.setMinSize(300, 50);
                fifthRowData.getChildren().addAll(complaintDesc, complaintDescValue);
                //end complaint description

                //Complaint details
                HBox complaintHbox1 = new HBox();
                complaintHbox1.setPadding(new Insets(0, 0, 0, 10));
                complaintHbox1.prefHeight(1000);
                complaintHbox1.getStyleClass().add("TopHbox");
                complaintHbox1.setAlignment(Pos.TOP_LEFT);

                VBox departmentHbox = new VBox();
                departmentHbox.setAlignment(Pos.CENTER_LEFT);
                departmentHbox.setMinSize(300, 50);
                departmentHbox.getChildren().addAll(department, depapartmentValue);

                VBox financialYearHbox = new VBox();
                financialYearHbox.setAlignment(Pos.CENTER_LEFT);
                financialYearHbox.setMinSize(300, 50);
                financialYearHbox.getChildren().addAll(financial, financialValue);

                //end complaint details
                // further complaint details reg date etc
                HBox complaintHbox2 = new HBox();
                complaintHbox2.setPadding(new Insets(0, 0, 0, 10));
                complaintHbox2.prefHeight(1000);
                complaintHbox2.getStyleClass().add("TopHbox");
                complaintHbox2.setAlignment(Pos.TOP_LEFT);

                VBox regModeVbox = new VBox();
                regModeVbox.setAlignment(Pos.CENTER_LEFT);
                regModeVbox.setMinSize(300, 50);
                regModeVbox.getChildren().addAll(regMode, regmodeValue);

                VBox regDateVbox = new VBox();
                regDateVbox.setAlignment(Pos.CENTER_LEFT);
                regDateVbox.setMinSize(300, 50);
//                regDateVbox.getChildren().addAll(regDate, regdateValue);

                //Respondent details
                HBox respondentHbox1 = new HBox();
                respondentHbox1.setPadding(new Insets(0, 0, 0, 10));
                respondentHbox1.prefHeight(1000);
                respondentHbox1.getStyleClass().add("TopHbox");
                respondentHbox1.setAlignment(Pos.TOP_LEFT);

                VBox businessRegBoVBox = new VBox();
                businessRegBoVBox.setAlignment(Pos.CENTER_LEFT);
                businessRegBoVBox.setMinSize(300, 50);
                businessRegBoVBox.getChildren().addAll(businessNo, businessNoValue);

                VBox orgNameVbox = new VBox();
                orgNameVbox.setAlignment(Pos.CENTER_LEFT);
                orgNameVbox.setMinSize(300, 50);
                orgNameVbox.getChildren().addAll(orgName, orgNameValue);

                //finish this part
                HBox resMainHbox = new HBox();
                resMainHbox.setPadding(new Insets(0, 0, 10, 10));
                resMainHbox.prefHeight(1000);
                resMainHbox.getStyleClass().add("TopHbox");
                resMainHbox.setAlignment(Pos.TOP_LEFT);

                VBox resRowDataOne = new VBox();
                resRowDataOne.setAlignment(Pos.CENTER_LEFT);
                resRowDataOne.setMinSize(300, 50);
                resRowDataOne.getChildren().addAll(postalAddress, postalValue);

                VBox resRowDataTwo = new VBox();
                resRowDataTwo.setAlignment(Pos.CENTER_LEFT);
                resRowDataTwo.setMinSize(300, 50);
                resRowDataTwo.getChildren().addAll(businessType, businessTypeValue);

                VBox resRowDataThree = new VBox();
                resRowDataThree.setAlignment(Pos.CENTER_LEFT);
                resRowDataThree.setMinSize(300, 50);
                resRowDataThree.getChildren().addAll(businessEmail, businesemailValue);

                //end of respondent details
                //Topic bar
                HBox TopicBarHbox = new HBox();
                TopicBarHbox.setPadding(new Insets(4, 4, 4, 4));
                TopicBarHbox.setPrefSize(980, 50);
                TopicBarHbox.getStyleClass().add("TopicBar");
                TopicBarHbox.setAlignment(Pos.CENTER_LEFT);

                TopicBarHbox.getChildren().add(casename);

                //Row sections here 
                HBox section1 = new HBox();
                section1.setPadding(new Insets(4, 4, 0, 5));
                section1.setPrefSize(960, 40);
                section1.getStyleClass().add("subTitleBar");
                section1.setAlignment(Pos.CENTER_LEFT);
                Label labelSection1 = new Label("Complainant Details");
                section1.getChildren().addAll(labelSection1);

                HBox section2 = new HBox();
                section2.setPadding(new Insets(4, 4, 0, 5));
                section2.setPrefSize(960, 40);
                section2.getStyleClass().add("subTitleBar");
                section2.setAlignment(Pos.CENTER_LEFT);
                Label labelSection2 = new Label("Case Details");
                section2.getChildren().addAll(labelSection2);

                HBox section3 = new HBox();
                section3.setPadding(new Insets(4, 4, 0, 5));
                section3.setPrefSize(960, 40);
                section3.getStyleClass().add("subTitleBar");
                section3.setAlignment(Pos.CENTER_LEFT);
                Label labelSection3 = new Label("Respondent Details");
                section3.getChildren().addAll(labelSection3);

                HBox section4 = new HBox();
                section4.setPadding(new Insets(4, 4, 0, 5));
                section4.setPrefSize(960, 40);
                section4.getStyleClass().add("subTitleBar");
                section4.setAlignment(Pos.CENTER_LEFT);
                Label labelSection4 = new Label("Provide Current Case progress");
                section4.getChildren().addAll(labelSection4);
                //End of row title sections

                //pull file and attachment
                //end of attachment function
                //end of topic bar/
                TopHbox.getChildren().addAll(firstTopVbox, secondTopVbox);
                progressHbox.getChildren().addAll(progressVbox, statusVbox);
                //updateCaseHbox.getChildren().addAll(updateCaseVbox, attachFileVbox);
                secondRowHbox.getChildren().addAll(firstSecondRowVbox, secondRowVbox);
                thirdMainHbox.getChildren().addAll(thirdrowDataOne, thirdRowDataTwo);
                fourthMainHbox.getChildren().addAll(fourthrowDataOne, fourthRowDataTwo, fourthRowDataThree);
                complaintHbox1.getChildren().addAll(departmentHbox, financialYearHbox);
                complaintHbox2.getChildren().addAll(regModeVbox, regDateVbox);
                respondentHbox1.getChildren().addAll(businessRegBoVBox, orgNameVbox);
                resMainHbox.getChildren().addAll(resRowDataOne, resRowDataTwo, resRowDataThree);

                GeneralCasesController.tempBorderPane.setTop(TopicBarHbox);
                GeneralCasesController.tempCaseDetails.getChildren().addAll(section2, fifthRowData, complaintHbox1,
                        complaintHbox2, section4, progressHbox, section1, TopHbox, secondRowHbox, thirdMainHbox, fourthMainHbox, section3,
                        respondentHbox1, resMainHbox);

                showAttachments1(caseFile);

            }

        } catch (SQLException ex) {
            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //end here
}
