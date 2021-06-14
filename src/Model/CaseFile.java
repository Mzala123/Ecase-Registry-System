/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ComplaintListController;
import Controller.OfficerCasesListController;
import Controller.OfficerPanelController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.sun.javafx.scene.control.skin.LabeledImpl;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
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

    private ImageView imageView;

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

    public void caseDetails() {

        int complaint_detailId = 0;
        String complaintName = "";
        String caseOfficer = "";
        String firstname = "";
        String lastname = "";

        String query = "SELECT complaint_details.detailId, complaint.natureComplaint, user.Firstname as Fname,"
                + "user.Lastname as Lname from complaint LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId \n"
                + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId \n"
                + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId \n"
                + "LEFT JOIN user ON user.Id = complaint_details.caseOfficerId "
                + "where complaint_details.detailId > 0 AND user.Firstname='" + OfficerPanelController.checkLabel.getText() + "'";
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
                HBox box = createHBox(imageView, caseOfficer, complaint_detailId, complaintName);
                OfficerCasesListController.tempVbox.getChildren().addAll(box);

                // ComplaintListController.complaintList.getItems().add(box);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private HBox createHBox(ImageView Icon, String caseOfficer, int caseNo, String caseName) {
        HBox hbox = new HBox();
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
            displayCaseFileDetails(caseNo);

        });

        return hbox;
    }

    public void displayCaseFileDetails(int caseFile) {
        String query = "SELECT person.id, person.nationalId, person.firstname, person.lastname, person.gender, person.dob, \n"
                + "person.type, person.nationality, person.address, person.residence, person.phonenumber, person.email, \n"
                + "complaint.complaintId, complaint.natureComplaint, complaint.complaintDescription, complaint.registrationMode,\n"
                + "complaint.financialYear, complaint.department, organization.email as orgEmail, organization.registrationNo,\n"
                + "organization.orgName, organization.postalAddress, organization.businessType, user.Firstname, user.Lastname, \n"
                + "complaint_details.progressMade, complaint_details.status from complaint \n"
                + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId \n"
                + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId \n"
                + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId \n"
                + "LEFT JOIN user On user.Id = complaint_details.caseOfficerId\n"
                + "where complaint_details.detailId = '" + caseFile + "' AND user.Firstname='" + OfficerPanelController.checkLabel.getText() + "'";

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

                Label dob = new Label("Date of birth");
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
                    label.getStyleClass().add("caseLabel");

                    HBox hbox = new HBox(label);
                    hbox.getStyleClass().add("vboxBGColor");
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setMaxHeight(100);

                    initializeStatusList();
                    JFXTextArea progressField = new JFXTextArea();
                    progressField.setPromptText("Input current progress");
                    progressField.setMaxSize(250, 90);

                    statusCombo.setMaxSize(250, 40);
                    statusCombo.setPromptText("Choose Status");
                    VBox vbox = new VBox();

                    HBox middleHbox = new HBox();
                    middleHbox.setMinSize(600, 300);
                    middleHbox.setAlignment(Pos.CENTER);
                    middleHbox.getChildren().addAll(progressField, statusCombo);

                    HBox bottomHbox = new HBox();
                    bottomHbox.setMaxHeight(70);

                    JFXButton btnConfirm = new JFXButton("update");
                    btnConfirm.getStyleClass().add("btnUpdateCase");
                    btnConfirm.setMinSize(100, 40);
                    FontAwesomeIconView icon2 = new FontAwesomeIconView(FontAwesomeIcon.CHECK_CIRCLE);
                    icon2.setGlyphSize(15);
                    icon2.setFill(Paint.valueOf("#daa520"));
                    btnConfirm.setGraphic(icon2);
                    bottomHbox.setAlignment(Pos.TOP_CENTER);
                    bottomHbox.getChildren().add(btnConfirm);

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

                    btnConfirm.setOnAction((e) -> {

                        try {
                            String grabProgress = progressField.getText();
                            String grabStatus = statusCombo.getValue().toString();

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
                                notification.position(Pos.BOTTOM_CENTER);
                                notification.darkStyle();
                                notification.showError();
                            } else {

                                Notifications notification = Notifications.create();
                                notification.title("Updating Case details");
                                notification.text("Case Updated Sucessfully");
                                notification.hideAfter(Duration.seconds(5));
                                notification.position(Pos.BOTTOM_CENTER);
                                notification.darkStyle();
                                notification.showConfirm();
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });
                });

                //end of progress section data.
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

                //end of topic bar/
                TopHbox.getChildren().addAll(firstTopVbox, secondTopVbox);
                progressHbox.getChildren().addAll(progressVbox, statusVbox);
                updateCaseHbox.getChildren().addAll(updateCaseVbox);
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

            }

        } catch (SQLException ex) {
            Logger.getLogger(CaseFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initializeStatusList() {
        String[] status = {"Ongoing", "Preliminary Dismissed", "Transferred", "Closed"};
        ObservableList<String> list = FXCollections.observableArrayList(status);
        this.statusCombo.getItems().addAll(list);

    }

}
