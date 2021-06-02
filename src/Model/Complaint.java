/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ComplaintListController;
//import static Controller.ComplaintListController.arrayComlainptList;
import static Controller.ComplaintListController.list;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.glyphfont.Glyph;

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

    private String complaint_DetailId;
    private String natureComplaint;
    private JFXButton iconButton;
    private JFXButton iconButton1;

    private ImageView imageView;

    DBHandler handler = new DBHandler();
    PreparedStatement preparedStatement = null;

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
        String query = "SELECT * from complaint "
                + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId "
                + "LEFT JOIN person ON  person.nationalId = complaint_details.complainantId "
                + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId "
                + "where complaint_details.detailId > 0 ORDER BY complaint_details.detailId";
        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);

            while (handler.result.next()) {
                complaint_detailId = handler.result.getInt("detailId");
                complaintName = handler.result.getString("natureComplaint");
                ImageView imageView = setImage();
                HBox box = createHBox(imageView, complaint_detailId, complaintName);
                ComplaintListController.tempVbox.getChildren().addAll(box);
                
                // ComplaintListController.complaintList.getItems().add(box);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private HBox createHBox(ImageView Icon, int caseNo, String caseName) {
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

        Label labelCaseNo = new Label("Case " + caseNo);
        Label labelCaseName = new Label(caseName);
        labelCaseName.setStyle("-fx-padding-left:5px;");
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

        vboxMin.getChildren().addAll(labelCaseNo, labelCaseName);
        hbox.getChildren().addAll(vbox, vboxMin);
        hbox.setOnMouseClicked((event) -> {
                    ComplaintListController.tempCaseDetails.getChildren().clear();
                    displayComplaintDetails(caseNo);
                   
                }); 

        return hbox;
    }

    private JFXButton iconButton() {
        iconButton = new JFXButton();
        iconButton.setPrefSize(55, 55);
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.FOLDER);
        //  button.setStyle("-fx-background-color: linear-gradient(to top right,#088584,#daa520)");
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
        /*String query = "SELECT * from complaint "
                + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId "
                + "LEFT JOIN person ON  person.nationalId = complaint_details.complainantId "
                + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId "
                + "where complaint_details.detailId = 2";*/

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
                + "where complaint_details.detailId = '"+complaint_id+"'";
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
                        complaintHbox2,section3, respondentHbox1, resMainHbox);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Complaint.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
