/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.AdminSettingsController;
import Controller.DashBoardController;
import Controller.ManageUsersController;
import Controller.SignUpPageController;
import Controller.SplashScreenController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.apache.poi.poifs.crypt.CipherAlgorithm;
import org.controlsfx.control.Notifications;

/**
 *
 * @author bounce
 */
public class Employee {

    private String empId;
    private String empUsername;
    private String empFname;
    private String empLname;
    private String empEmail;
    private String empPassword;
    private String empType;
    private File Image;
    FileInputStream inputStream;

    PreparedStatement preparedStatement = null;
    DBHandler handler = new DBHandler();

    int AdminCount;
    int directorCount;
    int officerCount;
    int allocatorCount;

    public Employee() {

    }

    public Employee(String empId, String empUsername, String empFname, String empLname,
            String empEmail, String empPassword, String empType, File Image) {
        this.empId = empId;
        this.empUsername = empUsername;
        this.empFname = empFname;
        this.empLname = empLname;
        this.empEmail = empEmail;
        this.empPassword = empPassword;
        this.empType = empType;
        this.Image = Image;

    }

    public Employee(String empId, String empUsername, String empFname, String empLname, String empEmail, String empType) {
        this.empId = empId;
        this.empUsername = empUsername;
        this.empFname = empFname;
        this.empLname = empLname;
        this.empEmail = empEmail;
        this.empType = empType;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setEmpUsername(String empUsername) {
        this.empUsername = empUsername;
    }

    public void setEmpFname(String empFname) {
        this.empFname = empFname;
    }

    public void setEmpLname(String empLname) {
        this.empLname = empLname;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public void setImage(File Image) {
        this.Image = Image;
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpUsername() {
        return empUsername;
    }

    public String getEmpFname() {
        return empFname;
    }

    public String getEmpLname() {
        return empLname;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public String getEmpType() {
        return empType;
    }

    public File getImage() {
        return Image;
    }

    public void addAdminUser() {

        DBHandler handler = new DBHandler();

        try {
            String querySelect = "select count(*) as no_of_rows FROM User";
            preparedStatement = handler.connection.prepareStatement(querySelect);
            handler.result = preparedStatement.executeQuery(querySelect);

            if (handler.result.next()) {
                int found = handler.result.getInt("no_of_rows");
                System.out.println("Rows found ahahaha" + found);

                if (found == 0) {
                    String query = "INSERT INTO ecase.user (UserId, Username, Firstname, Lastname, Email, Password, Usertype)"
                            + " VALUES(?,?,?,?,?,?,?)";
                    preparedStatement = handler.connection.prepareStatement(query);
                    preparedStatement.setString(1, getEmpId());
                    preparedStatement.setString(2, getEmpUsername());
                    preparedStatement.setString(3, getEmpFname());
                    preparedStatement.setString(4, getEmpLname());
                    preparedStatement.setString(5, getEmpEmail());
                    preparedStatement.setString(6, getEmpPassword());
                    preparedStatement.setString(7, getEmpType());

                    if (preparedStatement.execute()) {

                        Notifications notification = Notifications.create();
                        notification.title("CREATING USER");
                        notification.text("FAILED TO ADD USER");
                        notification.hideAfter(Duration.seconds(3));
                        notification.position(Pos.BOTTOM_RIGHT);
                        notification.darkStyle();
                        notification.showError();

                    } else {
                        Notification notification = new Notification(3, "CREATING USER", "USER ADDED SUCCESSFULLY");
                        notification.start();
                    }

                } else {
                    Notifications notification = Notifications.create();
                    notification.title("CREATING USER");
                    notification.text("ADMIN ALREADY AVAILABLE");
                    notification.hideAfter(Duration.seconds(3));
                    notification.position(Pos.CENTER);
                    notification.darkStyle();
                    notification.showWarning();
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addSystemUsers() throws FileNotFoundException {
        
        try {
             String checkIfRowExists = "select count(*) as no_rows from ecase.user";
             int row_found = 0;
             preparedStatement = handler.connection.prepareStatement(checkIfRowExists);
             handler.result = preparedStatement.executeQuery(checkIfRowExists);
             while(handler.result.next()){
                 row_found = handler.result.getInt("no_rows");
                 if(row_found == 0){
                     String insert = "INSERT INTO ecase.user (UserId, Username, Firstname, Lastname, Email, Password, Usertype, Image) "
                            + "VALUES (?,?,?,?,?,?,?,?)";
                    preparedStatement = handler.connection.prepareStatement(insert);
                    preparedStatement.setString(1, getEmpId());
                    preparedStatement.setString(2, getEmpUsername());
                    preparedStatement.setString(3, getEmpFname());
                    preparedStatement.setString(4, getEmpLname());
                    preparedStatement.setString(5, getEmpEmail());
                    preparedStatement.setString(6, getEmpPassword());
                    preparedStatement.setString(7, getEmpType());
                    inputStream = new FileInputStream(getImage());
                    preparedStatement.setBinaryStream(8, inputStream, (int) getImage().length());

                    if (preparedStatement.execute()) {
                        Notifications notification = Notifications.create();
                        notification.title("CREATING USER");
                        notification.text("FAILED TO ADD USER");
                        notification.hideAfter(Duration.seconds(5));
                        notification.position(Pos.BOTTOM_RIGHT);
                        notification.darkStyle();
                        notification.showError();
                    } else {

                        Notification notification = new Notification(5, "CREATING USER", "USER ADDED SUCCESSFULLY");
                        notification.start();
                    }
                 }
             
            else if(row_found > 0){
            String pullId = "Select * FROM ecase.user where UserId ='" + getEmpId() + "'";
            String id = "";
            preparedStatement = handler.connection.prepareStatement(pullId);
            handler.result = preparedStatement.executeQuery(pullId);
            if (handler.result.next()) {
                id = handler.result.getString("UserId");
                System.out.println("The pulled id is " + id);
                if (getEmpId().equalsIgnoreCase(id) == true) {
                    Notifications notification = Notifications.create();
                    notification.title("CREATING USER");
                    notification.text("USER WITH SUCH ID ALREADY EXISTS");
                    notification.hideAfter(Duration.seconds(5));
                    notification.position(Pos.CENTER);
                    notification.darkStyle();
                    notification.showWarning();
                }

                } 
            else {
                
                    String insert = "INSERT INTO ecase.user (UserId, Username, Firstname, Lastname, Email, Password, Usertype, Image) "
                            + "VALUES (?,?,?,?,?,?,?,?)";
                    preparedStatement = handler.connection.prepareStatement(insert);
                    preparedStatement.setString(1, getEmpId());
                    preparedStatement.setString(2, getEmpUsername());
                    preparedStatement.setString(3, getEmpFname());
                    preparedStatement.setString(4, getEmpLname());
                    preparedStatement.setString(5, getEmpEmail());
                    preparedStatement.setString(6, getEmpPassword());
                    preparedStatement.setString(7, getEmpType());
                    inputStream = new FileInputStream(getImage());
                    preparedStatement.setBinaryStream(8, inputStream, (int) getImage().length());

                    if (preparedStatement.execute()) {
                        Notifications notification = Notifications.create();
                        notification.title("CREATING USER");
                        notification.text("FAILED TO ADD USER");
                        notification.hideAfter(Duration.seconds(5));
                        notification.position(Pos.BOTTOM_RIGHT);
                        notification.darkStyle();
                        notification.showError();
                    } else {

                        Notification notification = new Notification(5, "CREATING USER", "USER ADDED SUCCESSFULLY");
                        notification.start();
                    }
               }
            }
             }
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setAdminValues() {
        String query = "select * from ecase.user where Username='" + SignUpPageController.Username + "'"
                + "and Password ='" + SignUpPageController.passcode + "'";
        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            while (handler.result.next()) {
                empEmail = handler.result.getString("Email");
                empFname = handler.result.getString("Firstname");
                empId = handler.result.getString("UserId");
                empLname = handler.result.getString("Lastname");
                empPassword = handler.result.getString("Password");
                empType = handler.result.getString("Usertype");
                empUsername = handler.result.getString("Username");

                InputStream inputstream;
                inputstream = handler.result.getBinaryStream("Image");
             
                Image = new File("Admin.png");
                OutputStream outputStream = new FileOutputStream(Image);
                byte[] content = new byte[1024];
                int size = 0;
                while ((size = inputstream.read(content)) != -1) {
                    outputStream.write(content, 0, size);
                }
                outputStream.close();
                inputstream.close();
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editAccountUserDetails() {

        try {
            String updateQuery = "Update ecase.user set Username=?, Firstname=?, Lastname=?, Email=?, "
                    + " Password=?, Image=?  where UserId ='" + getEmpId() + "'";
            preparedStatement = handler.connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, getEmpUsername());
            preparedStatement.setString(2, getEmpFname());
            preparedStatement.setString(3, getEmpLname());
            preparedStatement.setString(4, getEmpEmail());
            preparedStatement.setString(5, getEmpPassword());
            try {
                inputStream = new FileInputStream(getImage());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            }
            preparedStatement.setBinaryStream(6, inputStream, (int) getImage().length());

            if (preparedStatement.execute() == true) {
                Notifications notification = Notifications.create();
                notification.title("Updating User Account details");
                notification.text("Failed to update");
                notification.hideAfter(Duration.seconds(5));
                notification.position(Pos.BOTTOM_CENTER);
                notification.darkStyle();
                notification.showError();
            } else {

                Notifications notification = Notifications.create();
                notification.title("Updating User Account details");
                notification.text("Update Done Sucessfully");
                notification.hideAfter(Duration.seconds(5));
                notification.position(Pos.BOTTOM_CENTER);
                notification.darkStyle();
                notification.showConfirm();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void logIn() {
        SwitchWindow window = new SwitchWindow();
        String id = "";
        String usertype = "";
        String Username = "";
        String Password = "";

        try {
            String systemUserData = "select * from ecase.user where Username=? and Password=?";
            preparedStatement = handler.connection.prepareStatement(systemUserData);
            preparedStatement.setString(1, getEmpUsername());
            preparedStatement.setString(2, getEmpPassword());
            handler.result = preparedStatement.executeQuery();

            if (handler.result.next()) {
                usertype = handler.result.getString("Usertype");
                if (usertype.toLowerCase().equals("admin")) {
                    window.loadNewWindow("/View/AdminPanel.fxml", "Admin Section", true, true);
                    SignUpPageController.tempStackPane.getScene().getWindow().hide();

                } else if (usertype.toLowerCase().equals("case officer")) {
                    window.loadNewWindow("/View/AdminPanel.fxml", "Admin Section", true, true);
                    SignUpPageController.tempStackPane.getScene().getWindow().hide();
                } else if (usertype.toLowerCase().equals("case allocator")) {
                    window.loadNewWindow("/View/CasePanel.fxml", "Case Section", true, true);
                    SignUpPageController.tempStackPane.getScene().getWindow().hide();
                   /* Organization org = new Organization();
                    org.assignCaseAutomatically();*/
                    
                } else if (usertype.toLowerCase().equals("director")) {
                    window.loadNewWindow("/View/AdminPanel.fxml", "Admin Section", true, true);
                    SignUpPageController.tempStackPane.getScene().getWindow().hide();
                }
            } else {
                Notifications notification = Notifications.create();
                notification.title("Login Form");
                notification.text("Incorrect Credetials, Re-Enter the details!");
                notification.hideAfter(Duration.seconds(5));
                notification.position(Pos.CENTER);
                notification.darkStyle();
                notification.showError();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void LoadUserAccountDetails() {
        String query = "select * from ecase.user";
        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            ManageUsersController.userAccountList.clear();
            while (handler.result.next()) {
                empId = handler.result.getString("UserId");
                empUsername = handler.result.getString("Username");
                empFname = handler.result.getString("Firstname");
                empLname = handler.result.getString("Lastname");
                empEmail = handler.result.getString("Email");
                empType = handler.result.getString("Usertype");
                ManageUsersController.userAccountList.add(new Employee(empId, empUsername, empFname, 
                empLname, empEmail, empType));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void numberOfAdmins() {

        try {
            String query = "select count(*) from ecase.user where Usertype='Admin'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            int AdminCount = 0;
            if (handler.result.next()) {
                AdminCount = handler.result.getInt(1);
            }
            DashBoardController.tempLabelAdmin.setText("" + AdminCount);
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void numberOfCaseOfficer() {

        try {
            String query = "select count(*) from ecase.user where Usertype='Case Officer'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            officerCount = 0;
            if (handler.result.next()) {
                officerCount = handler.result.getInt(1);
            }
            DashBoardController.tempLabelCaseOfficer.setText("" + officerCount);
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Case Officer", "Case Allocator", "Director
    }

    public void numberOfCaseAllocators() {

        try {
            String query = "select count(*) from ecase.user where Usertype='Case Allocator'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            allocatorCount = 0;
            if (handler.result.next()) {
                allocatorCount = handler.result.getInt(1);
            }
            DashBoardController.tempLabelCaseAllocator.setText("" + allocatorCount);
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void numberOfDirectors() {

        try {
            String query = "select count(*) from ecase.user where Usertype='Director'";
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            directorCount = 0;
            if (handler.result.next()) {
                directorCount = handler.result.getInt(1);
            }
            DashBoardController.tempLabelDirector.setText("" + directorCount);

        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dashBoardPieChart() {
        String query = "select count(*) from ecase.user where Usertype='Admin'";

        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            AdminCount = 0;
            if (handler.result.next()) {
                AdminCount = handler.result.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dashBoardPieChart1() {
        String query = "select count(*) from ecase.user where Usertype='Case Officer'";

        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            officerCount = 0;
            if (handler.result.next()) {
                officerCount = handler.result.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dashBoardPieChart2() {
        String query = "select count(*) from ecase.user where Usertype='Director'";

        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            directorCount = 0;
            if (handler.result.next()) {
                directorCount = handler.result.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dashBoardPieChart3() {
        String query = "select count(*) from ecase.user where Usertype='Case Allocator'";

        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            allocatorCount = 0;
            if (handler.result.next()) {
                allocatorCount = handler.result.getInt(1);
            }
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Admins", AdminCount),
                    new PieChart.Data("Case Officers", officerCount),
                    new PieChart.Data("Directors", directorCount),
                    new PieChart.Data("Case Allocators", allocatorCount));
            DashBoardController.tempPieChart.setData(pieChartData);
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dashboardBarChart() {
        String query = "select count(*) from ecase.user where Usertype='Admin'";

        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            AdminCount = 0;
            if (handler.result.next()) {
                AdminCount = handler.result.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dashBoardbarChart1() {
        String query = "select count(*) from ecase.user where Usertype='Case Officer'";

        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            officerCount = 0;
            if (handler.result.next()) {
                officerCount = handler.result.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dashBoardBarChart2() {
        String query = "select count(*) from ecase.user where Usertype='Director'";

        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            directorCount = 0;
            if (handler.result.next()) {
                directorCount = handler.result.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dashBoardbarChart3() {
        String query = "select count(*) from ecase.user where Usertype='Case Allocator'";

        try {
            preparedStatement = handler.connection.prepareStatement(query);
            handler.result = preparedStatement.executeQuery(query);
            allocatorCount = 0;
            if (handler.result.next()) {
                allocatorCount = handler.result.getInt(1);
            }
            XYChart.Series setData = new XYChart.Series<>();
            setData.getData().add(new XYChart.Data("Admins", AdminCount));
            setData.getData().add(new XYChart.Data("Case Officers", officerCount));
            setData.getData().add(new XYChart.Data("Case Allocator", allocatorCount));
            setData.getData().add(new XYChart.Data("Directors", directorCount));
            DashBoardController.tempBarChar.getData().addAll(setData);

        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //   @Override
    public void run() {
        try {
            Thread.sleep(3000);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    dashBoardPieChart();
                    dashBoardPieChart1();
                    dashBoardPieChart2();
                    dashBoardPieChart3();

                    dashboardBarChart();
                    dashBoardBarChart2();
                    dashBoardbarChart1();
                    dashBoardbarChart3();;
                }
            });
        } catch (InterruptedException ex) {
            // Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
