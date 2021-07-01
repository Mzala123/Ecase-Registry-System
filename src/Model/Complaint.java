/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.CaseAllocatorDashboardController;
import Controller.ComplaintListController;
//import static Controller.ComplaintListController.arrayComlainptList;
import Controller.ComplaintSummaryListController;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
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
public class Complaint {

    private String complaintNature;
    private String regMode;
    private String regDate;
    private String complaintDesc;
    private String financialYear;
    private String department;
    private int complaintId;

    private String complaint_DetailId;
    private String natureComplaint;
    private JFXButton iconButton;
    private JFXButton iconButton1;

    private JFXButton button;

    private ImageView imageView;

    DBHandler handler = new DBHandler();
    PreparedStatement preparedStatement = null;

    private int totalCases;
    private int allConsumerCases;
    private int allCompetitionCases;
    
     private Circle circle;

    public Complaint() {
    }

    public Complaint(String complaintNature, String regMode, String regDate, String complaintDesc, String financialYear, String department) {
        this.complaintNature = complaintNature;
        this.regMode = regMode;
        this.regDate = regDate;
        this.complaintDesc = complaintDesc;
        this.financialYear = financialYear;
        this.department = department;
    }

    public Complaint(String complaintNature, String regMode, String regDate, String complaintDesc, String financialYear, String department, int complaintId, JFXButton button) {
        this.complaintNature = complaintNature;
        this.regMode = regMode;
        this.regDate = regDate;
        this.complaintDesc = complaintDesc;
        this.financialYear = financialYear;
        this.department = department;
        this.complaintId = complaintId;
        this.button = button;
    }

    public Complaint(String complaint_DetailId, String natureComplaint, JFXButton iconButton, JFXButton iconButton1) {
        this.complaint_DetailId = complaint_DetailId;
        this.natureComplaint = natureComplaint;
        this.iconButton = iconButton;
        this.iconButton1 = iconButton1;
    }

    public Complaint(String complaint_DetailId, String natureComplaint, JFXButton iconButton) {
        this.complaint_DetailId = complaint_DetailId;
        this.natureComplaint = natureComplaint;
        this.iconButton = iconButton;
    }

    public Complaint(String complaint_DetailId, String natureComplaint, ImageView imageView) {
        this.complaint_DetailId = complaint_DetailId;
        this.natureComplaint = natureComplaint;
        this.imageView = imageView;
    }

    public void setImage(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setComplaint_DetailId(String complaint_DetailId) {
        this.complaint_DetailId = complaint_DetailId;
    }

    public void setNatureComplaint(String natureComplaint) {
        this.natureComplaint = natureComplaint;
    }

    public void setIconButton1(JFXButton iconButton1) {
        this.iconButton1 = iconButton1;
    }

    public void setIconButton(JFXButton iconButton) {
        this.iconButton = iconButton;
    }

    public void setComplaintNature(String complaintNature) {
        this.complaintNature = complaintNature;
    }

    public void setRegMode(String regMode) {
        this.regMode = regMode;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public void setComplaintDesc(String complaintDesc) {
        this.complaintDesc = complaintDesc;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getComplaintNature() {
        return complaintNature;
    }

    public String getRegMode() {
        return regMode;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getComplaintDesc() {
        return complaintDesc;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public String getComplaint_DetailId() {
        return complaint_DetailId;
    }

    public String getNatureComplaint() {
        return natureComplaint;
    }

    public String getDepartment() {
        return department;
    }

    public JFXButton getIconButton() {
        return iconButton;
    }

    public JFXButton getIconButton1() {
        return iconButton1;
    }

    public ImageView getImage() {
        return imageView;
    }

    public void setButton(JFXButton button) {
        this.button = button;
    }

    public JFXButton getButton() {
        return button;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public int getComplaintId() {
        return complaintId;
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
    
    public void registerComplaint() {
        try {

            String insert = "Insert into ecase.complaint (natureComplaint, complaintDescription, "
                    + "registrationMode,financialYear,department, regDate) values(?,?,?,?,?,?)";
            preparedStatement = handler.connection.prepareStatement(insert);
            preparedStatement.setString(1, getComplaintNature());
            preparedStatement.setString(2, getComplaintDesc());
            preparedStatement.setString(3, getRegMode());
            preparedStatement.setString(4, getFinancialYear());
            preparedStatement.setString(5, getDepartment());
            preparedStatement.setString(6, getRegDate());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String fetchCurrentComplaintID(String registryTime) {
        String query = "select complaintId from ecase.complaint where regDate = '" + registryTime + "'";
        int complaintId;

        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            if (handler.result.next()) {
                complaintId = handler.result.getInt("complaintId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }

        return registryTime;
    }

    public void complaintDetails() {

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
                + "where complaint_details.detailId > 0 ORDER BY complaint_details.detailId DESC";

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
                ComplaintListController.tempVbox.getChildren().addAll(box);
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
                    Thread.sleep(300);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
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

                            Label labelCaseNo = new Label("Case " + caseNo);
                            Label labelCaseName = new Label(caseName);
                            labelCaseName.setStyle("-fx-padding-left:5px;");
                            labelCaseNo.setStyle("-fx-padding-left:5px;");

                            Label labelOfficer = new Label("Officer " + caseOfficer);
                            labelOfficer.setStyle("-fx-padding-left:5px;");

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
                                ComplaintListController.tempCaseDetails.getChildren().clear();

                                displayComplaintDetails(caseNo);

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

    private JFXButton iconButton() {
        iconButton = new JFXButton();
        iconButton.setPrefSize(55, 55);
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.FOLDER);
        icon.setGlyphSize(30);
        icon.setStyle("-fx-fill: #ffffff");
        iconButton.setGraphic(icon);
        iconButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        return iconButton;
    }

    private ImageView setImage() {
        ImageView view = new ImageView();
        Image image = new Image("/Images/Folders.png");
        view.setImage(image);
        view.setFitHeight(50);
        view.setFitWidth(50);

        return view;
    }

    public void displayComplaintDetails(int complaint_id) {

        String query = "SELECT person.id, person.nationalId, person.firstname, person.lastname, "
                + "person.gender, person.dob, person.type, person.nationality, person.address,"
                + " person.residence, person.phonenumber, person.email, complaint.complaintId, "
                + "complaint.natureComplaint, complaint.complaintDescription, complaint.registrationMode,"
                + " complaint.financialYear, complaint.department, complaint.regDate,"
                + " organization.email as orgEmail,"
                + " organization.registrationNo, organization.orgName, organization.postalAddress,"
                + " organization.businessType from complaint "
                + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId "
                + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId "
                + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId "
                + "where complaint_details.detailId = '" + complaint_id +"'";
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

                Label regDate = new Label("Date Registered");
                regDate.getStyleClass().add("labelBold");
                String regdate = handler.result.getString("regDate");
                Label regdateValue = new Label(regdate);

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
                regDateVbox.getChildren().addAll(regDate, regdateValue);

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
                Label labelSection2 = new Label("Complaint Details");
                section2.getChildren().addAll(labelSection2);

                HBox section3 = new HBox();
                section3.setPadding(new Insets(4, 4, 0, 5));
                section3.setPrefSize(960, 40);
                section3.getStyleClass().add("subTitleBar");
                section3.setAlignment(Pos.CENTER_LEFT);
                Label labelSection3 = new Label("Respondent Details");
                section3.getChildren().addAll(labelSection3);

                //end of topic bar/
                TopHbox.getChildren().addAll(firstTopVbox, secondTopVbox);
                secondRowHbox.getChildren().addAll(firstSecondRowVbox, secondRowVbox);
                thirdMainHbox.getChildren().addAll(thirdrowDataOne, thirdRowDataTwo);
                fourthMainHbox.getChildren().addAll(fourthrowDataOne, fourthRowDataTwo, fourthRowDataThree);
                complaintHbox1.getChildren().addAll(departmentHbox, financialYearHbox);
                complaintHbox2.getChildren().addAll(regModeVbox, regDateVbox);
                respondentHbox1.getChildren().addAll(businessRegBoVBox, orgNameVbox);
                resMainHbox.getChildren().addAll(resRowDataOne, resRowDataTwo, resRowDataThree);

                ComplaintListController.tempBorderPane.setTop(TopicBarHbox);
                ComplaintListController.tempCaseDetails.getChildren().addAll(section1, TopHbox,
                        secondRowHbox, thirdMainHbox, fourthMainHbox, section2, fifthRowData, complaintHbox1,
                        complaintHbox2, section3, respondentHbox1, resMainHbox);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void complaintDataList() {
        try {
            String query = "Select * from ecase.complaint";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            ComplaintSummaryListController.complaintList.clear();
            while (handler.result.next()) {
                complaintId = handler.result.getInt("complaintId");
                complaintNature = handler.result.getString("natureComplaint");
                complaintDesc = handler.result.getString("complaintDescription");
                regMode = handler.result.getString("registrationMode");
                financialYear = handler.result.getString("financialYear");
                department = handler.result.getString("department");
                regDate = handler.result.getString("regDate");
                ComplaintSummaryListController.complaintList.add(new Complaint(complaintNature, regMode,
                        regDate, complaintDesc, financialYear, department, complaintId, selectRowToEdit(complaintId)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JFXButton selectRowToEdit(int id) {
        button = new JFXButton();
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
        button.setStyle("-fx-background-color: #c7e0e0;");
        button.getStyleClass().add("circleButton");
        icon.setGlyphSize(20);
        button.setId(id + "");
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
                    ComplaintSummaryListController.tempLabelComplaintId.setText(id + "");
                    Parent root = FXMLLoader.load(getClass().getResource("/View/UpdateComplaint.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Update Complaint");
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

    public String setComplaintValues(String id) {
        try {
            String query = "SELECT * FROM ecase.complaint where complaintId='" + id + "'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            if (handler.result.next()) {
                complaintNature = handler.result.getString("natureComplaint");
                complaintDesc = handler.result.getString("complaintDescription");
                financialYear = handler.result.getString("financialYear");
                department = handler.result.getString("department");
                regMode = handler.result.getString("registrationMode");

            }

        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public void updateCoplaint() {
        try {
            String query = "UPDATE ecase.complaint SET natureComplaint=?, complaintDescription=?,"
                    + "registrationMode=?, financialYear=?, department=? "
                    + "where complaintId='" + ComplaintSummaryListController.tempLabelComplaintId.getText() + "'";
            preparedStatement = handler.connection.prepareStatement(query);
            preparedStatement.setString(1, getComplaintNature());
            preparedStatement.setString(2, getComplaintDesc());
            preparedStatement.setString(3, getRegMode());
            preparedStatement.setString(4, getFinancialYear());
            preparedStatement.setString(5, getDepartment());
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
                notification.position(Pos.TOP_RIGHT);
                notification.darkStyle();
                notification.showConfirm();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void AllRegisteredCases() {
        try {
            String query = "Select Count(*) as totalCases from ecase.complaint";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            while (handler.result.next()) {
                totalCases = handler.result.getInt("totalCases");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }
        CaseAllocatorDashboardController.tempAllCaseLabel.setText(totalCases + "");
    }

    public void AllConsumerCases() {
        CaseAllocatorDashboardController.tempConsumerGauge.setMaxValue(totalCases);
        System.out.println("Total cases" + totalCases);
        try {
            String query = "Select Count(*) as totalCases from ecase.complaint"
                    + " where department='Consumer Affairs'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            while (handler.result.next()) {
                allConsumerCases = handler.result.getInt("totalCases");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }
        CaseAllocatorDashboardController.tempConsumerGauge.setValue(allConsumerCases);
    }

    public void AllCompetitionCases() {
        CaseAllocatorDashboardController.tempCompetitionGauge.setMaxValue(totalCases);
        try {
            String query = "Select Count(*) as totalCases from ecase.complaint"
                    + " where department='Competition Affairs'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            while (handler.result.next()) {
                allCompetitionCases = handler.result.getInt("totalCases");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }
        CaseAllocatorDashboardController.tempCompetitionGauge.setValue(allCompetitionCases);
    }

    public void natureComplaintGraph() {
        int predatoryCount = 0;
        int hoardingCount = 0;
        int unconscionableCount = 0;
        int harmfulCount = 0;
        int liabilityCount = 0;
        int discrimanatoryCount = 0;
        try {
            String query = "Select count(If(natureComplaint='Alleged Predatory Pricing', 1, NULL)) as predatoryCount, \n"
                    + "count(If(natureComplaint='Alleged Hoarding()',1,NULL)) as hoardingCount,\n"
                    + "count(If(natureComplaint='Alleged Unconscionable conduct',1,NULL)) as unconscionableCount,\n"
                    + "count(If(natureComplaint='Alleged supply of products likely to cause harm when consumed',1,NULL)) as harmfulCount,\n"
                    + "count(If(natureComplaint='Alleged Excluding liability for defective products',1,NULL)) as liabilityCount,\n"
                    + "count(If(natureComplaint='Alleged Discriminatory pricing',1,NULL)) as discrimatoryCount\n"
                    + "from complaint";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            if (handler.result.next()) {
                predatoryCount = handler.result.getInt("predatoryCount");
                hoardingCount = handler.result.getInt("hoardingCount");
                unconscionableCount = handler.result.getInt("unconscionableCount");
                harmfulCount = handler.result.getInt("harmfulCount");
                liabilityCount = handler.result.getInt("liabilityCount");
                discrimanatoryCount = handler.result.getInt("discrimatoryCount");
            }

            final String predatory = "Predatory Pricing";
            final String hoarding = "Hoarding";
            final String unconscionable = "Unconscionable conduct";
            final String harmful = "Harmful Product Supply";
            final String liability = "Excluding liability";
            final String discrimanatory = "Discrimanatory Pricing";

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Alleged Nature of Complaint");

            final XYChart.Data<String, Number> data = new XYChart.Data(predatory, predatoryCount);
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data);
                    }
                }
            });

            final XYChart.Data<String, Number> data1 = new XYChart.Data(hoarding, hoardingCount);
            data1.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data1);
                    }
                }
            });

            final XYChart.Data<String, Number> data2 = new XYChart.Data(unconscionable, unconscionableCount);
            data2.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data2);
                    }
                }
            });

            final XYChart.Data<String, Number> data3 = new XYChart.Data(harmful, harmfulCount);
            data3.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data3);
                    }
                }
            });

            final XYChart.Data<String, Number> data4 = new XYChart.Data(liability, liabilityCount);
            data4.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data4);
                    }
                }
            });

            final XYChart.Data<String, Number> data5 = new XYChart.Data(discrimanatory, discrimanatoryCount);
            data5.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data5);
                    }
                }
            });

            series1.getData().addAll(data, data1, data2, data3, data4, data5);

            // CaseAllocatorDashboardController.tempnatureBarChar.getStyleClass().add("chart-bar");
            CaseAllocatorDashboardController.tempnatureBarChar.getData().addAll(series1);

        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modeRegistrationGraph() {
        int walkCount = 0;
        int faceBookCount = 0;
        int websiteCount = 0;
        int phoneCount = 0;
        int emailCount = 0;
        int whatsappCount = 0;
        int postCount = 0;
        int otherCount = 0;
        try {
            String query = "Select count(If(registrationMode='Walk-In', 1, NULL)) as walkCount, \n"
                    + "count(If(registrationMode='Facebook', 1, NULL)) as faceBookCount, \n"
                    + "count(If(registrationMode='Website', 1, NULL)) as websiteCount, \n"
                    + "count(If(registrationMode='Phone Call', 1, NULL)) as phoneCount, \n"
                    + "count(If(registrationMode='Email', 1, NULL)) as emailCount, \n"
                    + "count(If(registrationMode='WhatsApp', 1, NULL)) as whatsappCount, \n"
                    + "count(If(registrationMode='Post Office', 1, NULL)) as postCount, \n"
                    + "count(If(registrationMode='Other', 1, NULL)) as otherCount\n"
                    + "from complaint";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            if (handler.result.next()) {
                walkCount = handler.result.getInt("walkCount");
                faceBookCount = handler.result.getInt("faceBookCount");
                websiteCount = handler.result.getInt("websiteCount");
                phoneCount = handler.result.getInt("phoneCount");
                emailCount = handler.result.getInt("emailCount");
                whatsappCount = handler.result.getInt("whatsappCount");
                postCount = handler.result.getInt("postCount");
                otherCount = handler.result.getInt("otherCount");
            }
            final String walkIn = "Walk-In";
            final String facebook = "Facebook";
            final String website = "Website";
            final String phone = "Phone Call";
            final String email = "Email";
            final String whatsapp = "WhatsApp";
            final String post = "Post Office";
            final String other = "Others";

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Mode of registration");

            final XYChart.Data<String, Number> data = new XYChart.Data(walkIn, walkCount);
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data);
                    }
                }
            });

            final XYChart.Data<String, Number> data1 = new XYChart.Data(facebook, faceBookCount);
            data1.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data1);
                    }
                }
            });

            final XYChart.Data<String, Number> data2 = new XYChart.Data(website, websiteCount);
            data2.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data2);
                    }
                }
            });

            final XYChart.Data<String, Number> data3 = new XYChart.Data(phone, phoneCount);
            data3.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data3);
                    }
                }
            });

            final XYChart.Data<String, Number> data4 = new XYChart.Data(email, emailCount);
            data4.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data4);
                    }
                }
            });

            final XYChart.Data<String, Number> data5 = new XYChart.Data(whatsapp, whatsappCount);
            data5.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data5);
                    }
                }
            });

            final XYChart.Data<String, Number> data6 = new XYChart.Data(post, postCount);
            data6.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data6);
                    }
                }
            });

            final XYChart.Data<String, Number> data7 = new XYChart.Data(other, otherCount);
            data7.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-background-color:linear-gradient(to top right,#088584,#daa520)");
                        displayLabelForData(data7);
                    }
                }
            });

            series1.getData().addAll(data, data1, data2, data3, data4, data5, data6, data7);
            CaseAllocatorDashboardController.tempregModeBarChar.getData().addAll(series1);

        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
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

    public void exportToPdf(String filename) {
        try {

            Document document = new Document(PageSize.A4, 36, 36, 90, 90);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));

            document.open();
            LocalDate date = LocalDate.now();
            String formatedDate = date.getDayOfMonth() + " "
                    + date.getMonth().name() + ", " + date.getYear();
            Font font = FontFactory.getFont("Times-Roman", 16, Font.BOLD);
            document.add(new Paragraph("ALL REGISTERED COMPLAINTS REPORT | " + formatedDate, font));

            PdfPTable table = new PdfPTable(4);
            float[] widths = {80f, 90f, 90f, 170f};
            table.setTotalWidth(widths);
            table.setLockedWidth(true);

            Font font1 = FontFactory.getFont("Times-Roman", 12, Font.UNDERLINE);
            Font font2 = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);
            Font font3 = FontFactory.getFont("Times-Roman", 13, Font.BOLD);
            BaseFont bf = font1.getCalculatedBaseFont(false);

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell Id = new PdfPCell(new Paragraph("CASE NO", font1));
            table.addCell(Id).setBorder(0);
            PdfPCell nature = new PdfPCell(new Paragraph("NATURE", font1));
            table.addCell(nature).setBorder(0);
            PdfPCell departments = new PdfPCell(new Paragraph("DEPARTMENT", font1));
            table.addCell(departments).setBorder(0);
            PdfPCell summary = new PdfPCell(new Paragraph("SUMMARY", font1));
            table.addCell(summary).setBorder(0);
            /*PdfPCell age = new PdfPCell(new Paragraph("AGE", font1));
            table.addCell(age).setBorder(0);
            PdfPCell address = new PdfPCell(new Paragraph("ADDRESS", font1));
            table.addCell(address).setBorder(0);
            PdfPCell emails = new PdfPCell(new Paragraph("EMAIL", font1));
            table.addCell(emails).setBorder(0);
            PdfPCell phonenumber = new PdfPCell(new Paragraph("PHONE NUMBER", font1));
            table.addCell(phonenumber).setBorder(0);
            PdfPCell nation = new PdfPCell(new Paragraph("NATIONALITY", font1));
            table.addCell(nation).setBorder(0);
            PdfPCell place = new PdfPCell(new Paragraph("RESIDENCE", font1));
            table.addCell(place).setBorder(0);*/

            PdfPCell spaceCell1 = new PdfPCell(new Paragraph(" "));
            spaceCell1.setColspan(6);
            table.addCell(spaceCell1).setBorder(0);

            table.setSpacingBefore(15f);

            table.setSpacingAfter(50f);

            table.setHeaderRows(1);


            /* PdfPCell[] cells = table.getRow(0).getCells();
            for (int j = 0; j < cells.length; j++) {
                cells[j].setBackgroundColor(BaseColor.LIGHT_GRAY);
            }*/
            String query = "Select * from ecase.complaint";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            ComplaintSummaryListController.complaintList.clear();
            while (handler.result.next()) {
                PdfPCell complaintId = new PdfPCell(new Paragraph(handler.result.getString("complaintId"), font2));
                table.addCell(complaintId).setBorder(0);
                PdfPCell complaintNature = new PdfPCell(new Paragraph(handler.result.getString("natureComplaint"), font2));
                table.addCell(complaintNature).setBorder(0);
                PdfPCell department = new PdfPCell(new Paragraph(handler.result.getString("department"), font2));
                table.addCell(department).setBorder(0);
                PdfPCell description = new PdfPCell(new Paragraph(handler.result.getString("complaintDescription"), font2));
                table.addCell(description).setBorder(0);

                /* complaintDesc = handler.result.getString("complaintDescription");
                regMode = handler.result.getString("registrationMode");
                financialYear = handler.result.getString("financialYear");
               
                regDate = handler.result.getString("regDate");*/
            }

            PdfPCell spaceCell2 = new PdfPCell(new Paragraph(" "));
            spaceCell2.setColspan(3);
            table.addCell(spaceCell2).setBorder(0);

            document.add(table);
            document.close();

            Notifications notification = Notifications.create();
            notification.title("Creating Complaint List Report");
            notification.text("Report executed successfully");
            notification.hideAfter(Duration.seconds(3));
            notification.position(Pos.TOP_RIGHT);
            notification.darkStyle();
            notification.showInformation();

        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
