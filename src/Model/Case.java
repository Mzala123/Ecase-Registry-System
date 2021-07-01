/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.GeneralTableController;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author bounce
 */
public class Case {

    DBHandler handler = new DBHandler();
    PreparedStatement preparedStatement = null;

    private int caseNo;
    private String caseOfficer;
    private String caseNatuture;
    private String caseDesc;
    private String caseProgress;
    private String caseStatus;

    public Case() {
    }

    public Case(int caseNo, String caseOfficer, String caseNatuture, String caseDesc, String caseProgress, String caseStatus) {
        this.caseNo = caseNo;
        this.caseOfficer = caseOfficer;
        this.caseNatuture = caseNatuture;
        this.caseDesc = caseDesc;
        this.caseProgress = caseProgress;
        this.caseStatus = caseStatus;
    }

    public int getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(int caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseOfficer() {
        return caseOfficer;
    }

    public void setCaseOfficer(String caseOfficer) {
        this.caseOfficer = caseOfficer;
    }

    public String getCaseNatuture() {
        return caseNatuture;
    }

    public void setCaseNatuture(String caseNatuture) {
        this.caseNatuture = caseNatuture;
    }

    public String getCaseDesc() {
        return caseDesc;
    }

    public void setCaseDesc(String caseDesc) {
        this.caseDesc = caseDesc;
    }

    public String getCaseProgress() {
        return caseProgress;
    }

    public void setCaseProgress(String caseProgress) {
        this.caseProgress = caseProgress;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public void selectAllConcludedCasesDetails() {
        String firstname = "";
        String lastname = "";
        try {
            String query = "SELECT complaint_details.detailId, complaint.natureComplaint,complaint.complaintDescription,\n"
                    + "complaint_details.status, complaint_details.progressMade,\n"
                    + "userofficer.Firstname as Fname, userofficer.Lastname as Lname from complaint\n"
                    + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId\n"
                    + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId\n"
                    + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId\n"
                    + "LEFT JOIN userofficer ON userofficer.uniqueId = complaint_details.caseOfficerId\n"
                    + "where complaint_details.detailId >0 AND "
                    + "(status='Preliminary Dismissed' OR status='Transferred' OR status='Closed')"
                    + "ORDER BY complaint_details.detailId ASC";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery();
            GeneralTableController.caseList.clear();
            while (handler.result.next()) {
                caseNo = handler.result.getInt("detailId");
                caseNatuture = handler.result.getString("natureComplaint");
                firstname = handler.result.getString("Fname");
                lastname = handler.result.getString("Lname");
                caseOfficer = firstname + " " + lastname;
                caseDesc = handler.result.getString("complaintDescription");
                caseProgress = handler.result.getString("progressMade");
                caseStatus = handler.result.getString("status");

                GeneralTableController.caseList.addAll(new Case(caseNo, caseOfficer,
                        caseNatuture, caseDesc, caseProgress, caseStatus));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Case.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exportToPdf(String filename) {
        String firstname = "";
        String lastname = "";
        try {
            Document document = new Document(PageSize.A4, 36, 36, 90, 90);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));

            // add header and footer
            /*HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);*/
            document.open();
            LocalDate date = LocalDate.now();
            String formatedDate = date.getDayOfMonth() + " "
                    + date.getMonth().name() + ", " + date.getYear();
            Font font = FontFactory.getFont("Times-Roman", 16, Font.BOLD);
            document.add(new Paragraph("CONCLUDED CASE SUMMARIES | " + formatedDate, font));

            PdfPTable table = new PdfPTable(6);
            float[] widths = {60f, 90f, 90f, 90f, 90f, 120f};
            table.setTotalWidth(widths);
            table.setLockedWidth(true);

            Font font1 = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
            Font font2 = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);
            Font font3 = FontFactory.getFont("Times-Roman", 13, Font.BOLD);
            BaseFont bf = font1.getCalculatedBaseFont(false);

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell Id = new PdfPCell(new Paragraph("Case No", font1));
            table.addCell(Id).setBorder(0);
            PdfPCell officer = new PdfPCell(new Paragraph("Case Officer", font1));
            table.addCell(officer).setBorder(0);
            PdfPCell nature = new PdfPCell(new Paragraph("Case Nature", font1));
            table.addCell(nature).setBorder(0);
            PdfPCell progress = new PdfPCell(new Paragraph("Progress", font1));
            table.addCell(progress).setBorder(0);
            PdfPCell status = new PdfPCell(new Paragraph("Status", font1));
            table.addCell(status).setBorder(0);
            PdfPCell summary = new PdfPCell(new Paragraph("Case Summary", font1));
            table.addCell(summary).setBorder(0);

            PdfPCell spaceCell1 = new PdfPCell(new Paragraph(" "));
            spaceCell1.setColspan(6);
            table.addCell(spaceCell1).setBorder(0);

            table.setSpacingBefore(15f);

            table.setSpacingAfter(50f);

            table.setHeaderRows(1);

            String query = "SELECT complaint_details.detailId, complaint.natureComplaint,complaint.complaintDescription,\n"
                    + "complaint_details.status, complaint_details.progressMade,\n"
                    + "userofficer.Firstname as Fname, userofficer.Lastname as Lname from complaint\n"
                    + "LEFT JOIN complaint_details ON complaint.complaintId = complaint_details.complaintId\n"
                    + "LEFT JOIN person ON person.nationalId = complaint_details.complainantId\n"
                    + "LEFT JOIN organization ON organization.registrationNo = complaint_details.respondentId\n"
                    + "LEFT JOIN userofficer ON userofficer.uniqueId = complaint_details.caseOfficerId\n"
                    + "where complaint_details.detailId >0 AND "
                    + "(status='Preliminary Dismissed' OR status='Transferred' OR status='Closed')"
                    + "ORDER BY complaint_details.detailId ASC";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery();
            GeneralTableController.caseList.clear();
            while (handler.result.next()) {
                
                caseNo = handler.result.getInt("detailId");
                table.addCell(caseNo+"");
                firstname = handler.result.getString("Fname");
                lastname = handler.result.getString("Lname");
                String caseOfficer1 = firstname + " " + lastname;
                table.addCell(caseOfficer1);
                caseNatuture = handler.result.getString("natureComplaint"); 
                table.addCell(caseNatuture); 
                caseProgress = handler.result.getString("progressMade");
                table.addCell(caseProgress);
                caseStatus = handler.result.getString("status");
                table.addCell(caseStatus);
                caseDesc = handler.result.getString("complaintDescription");
                table.addCell(caseDesc);
           
            }

            PdfPCell spaceCell2 = new PdfPCell(new Paragraph(" "));
            spaceCell2.setColspan(3);
            table.addCell(spaceCell2).setBorder(0);

            document.add(table);
            document.close();

            Notifications notification = Notifications.create();
            notification.title("Creating Case Summary List Report");
            notification.text("Report executed successfully");
            notification.hideAfter(Duration.seconds(3));
            notification.position(Pos.TOP_RIGHT);
            notification.darkStyle();
            notification.showInformation();

        } catch (FileNotFoundException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Report Generation Failed");
            alert.setContentText("Document not created or Already Opened with another Application!");
            alert.show();

        } catch (DocumentException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Report Generation Failed");
            alert.setContentText("Document not created or Already Opened with another Application!");
            alert.show();
        } catch (SQLException ex) {
            Logger.getLogger(Case.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
