/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bounce
 */
public class DBHandler {
    
    static final String DatabaseURL = "jdbc:mysql://localhost:3307/ecase";
    static final String DB_Username = "root";
    static final String DB_password = "";
    
    String DatabaseURL1;
    String DB_Username1 ;
    String DB_password1;
    
    private Formatter output;
    
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;
    PreparedStatement preparedStatement = null;

    public DBHandler() {
        createDBConnection1();
        createTableUser();
        createTablePerson();
        createTableClients();
        createTableOrganization();
        createTableComplaint();
        createTableComplaintDetails();
    }

    public DBHandler(String DatabaseURL, String DB_Username, String DB_password) {
        setDatabaseURL(DatabaseURL);
        setDB_Username(DB_Username);
        setDB_password(DB_password);
        
    }
    
     public void openFile() {
        try {
            output = new Formatter("Database.txt");
        } catch (SecurityException security) {
            System.err.println("You do not have write prividges to this file");
            System.exit(1);
        } catch (FileNotFoundException filex) {
            System.err.println("Error opening or creating a file");
            System.exit(1);
        }
    }
      
        public void closeFile() {
        if (output != null) {
            output.close();
        }
    }
    
//   private void createDBConnection(){
//         
//             
//        try {
//            connection = DriverManager.getConnection(getDatabaseURL(), getDB_Username(), getDB_password());
//            System.out.println("Database connection established successfully");
//        } 
//        catch (SQLException ex) {
//            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
//            System.err.println("Failed to establish database connection");
//        }
//        
//         
//   }
//         
         
   private void createDBConnection1(){
        try {
            connection = DriverManager.getConnection(DatabaseURL, DB_Username, DB_password);

            System.out.println("Database Connection Established Sucessfully");
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Failed to establish database connection");
        }
   }

    public void setDatabaseURL(String DatabaseURL) {
        this.DatabaseURL1 = DatabaseURL;
    }

    public void setDB_Username(String DB_Username) {
        this.DB_Username1 = DB_Username;
    }

    public void setDB_password(String DB_password) {
        this.DB_password1 = DB_password;
    }

    public String getDatabaseURL() {
        return DatabaseURL1;
    }

    public String getDB_Username() {
        return DB_Username1;
    }

    public String getDB_password() {
        return DB_password1;
    }
     
    void createTableUser(){
        String TableName = "User";
        try
        {
            statement = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TableName.toUpperCase(), null);
            if(tables.next())
            {
                System.out.println("Table " + TableName +" Already exists in the database");
                
            }
            else
            {
                statement.execute("CREATE TABLE " +TableName+ "(" + ""
                        + "Id int NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
                        + "UserId varchar(200),\n"
                        + "UserName varchar(40),\n"
                        + "Firstname varchar(40),\n"
                        + "Lastname varchar(40),\n"
                        + "Email varchar(40),\n"
                        + "Password varchar(1000),\n"
                        + "DateIncluded varchar(40),\n"
                        + "Usertype varchar(100),\n"
                        + "Image LONGBLOB\n"
                        + ")");
                System.out.println("Table User has been Created successfully");
            }
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage()+"setup database please");
        }
        finally
        {
            
        }
    }
    

    private void createTablePerson(){
        
         String TableName = "Person";
        try
        {
            statement = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TableName.toUpperCase(), null);
            if(tables.next())
            {
                System.out.println("Table " + TableName +" Already exists in the database");
                
            }
            else
            {
                statement.execute("CREATE TABLE " +TableName+ "(" + ""
                        + "id int NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
                        + "nationalId varchar(40),\n"
                        + "firstname varchar(40),\n"
                        + "lastname varchar(40),\n"
                        + "gender varchar(40),\n"
                        + "dob varchar(100),\n"
                        + "type varchar(100),\n"
                        + "nationality varchar(50),\n"
                        + "address longtext,\n"
                        + "residence varchar(50),\n"
                        + "phonenumber varchar(50),\n"
                        + "email varchar(50)\n"
                        + ")");
                System.out.println("Table Person has been Created successfully");
            }
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage()+"setup database please");
        }
        finally
        {
            
        }
        
               
    }
    
    
    private void createTableClients(){
        
         String TableName = "Clients";
        try
        {
            statement = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TableName.toUpperCase(), null);
            if(tables.next())
            {
                System.out.println("Table " + TableName +" Already exists in the database");
                
            }
            else
            {
                statement.execute("CREATE TABLE " +TableName+ "(" + ""
                        + "id int NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
                        + "typeId varchar(40),\n"
                        + "clientType varchar(100)\n"
                        + ")");
                System.out.println("Table Clients has been Created successfully");
            }
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage()+"setup database please");
        }
        finally
        {
            
        }
                   
    }
    
    private void createTableOrganization(){
        String tableName = "Organization";
        
        try {
            statement = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName.toUpperCase(), null);
            if(tables.next()){
                System.out.println("Table " + tableName +" Already exists in the database");
            }
            else{
                statement.execute("CREATE TABLE " +tableName+"(" + ""
                         + "id int NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
                         + "registrationNo varchar(50),\n"
                         + "orgName varchar(100),\n"
                         + "orgType varchar(40),\n"
                         + "postalAddress longtext,\n"
                         + "businessType varchar(100),\n"
                         + "email varchar(40),\n"
                         + "regDate datetime\n"
                         +")");
                System.out.println("Table " +tableName+ " has been created successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            
        }
       
    }
    
    public void createTableComplaint(){
          String tableName = "Complaint";
        try {
            statement = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName, null);
            if(tables.next()){
                System.out.println("Table " +tableName+ " Already exists in the database");
            }
            else{
                statement.execute("CREATE TABLE "+tableName+"("
                        + "complaintId int NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
                        + "natureComplaint longtext,\n"
                        + "complaintDescription longtext,\n"
                        + "registrationMode varchar(50),\n"
                        + "financialYear varchar(20), \n"
                        + "department varchar(20),\n"
                        + "regDate varchar(50)\n"
                        + ")");
               System.out.println("Table " +tableName+ " has been created successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    private void createTableComplaintDetails(){
        String tableName = "complaint_details";
        try {
            statement = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName, null);
            if(tables.next()){
                System.out.println("Table " +tableName+ " Already exists in the database");
            }
            else{
                statement.execute("CREATE TABLE "+tableName+"("
                        + "detailId int NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
                        + "complaintId int,\n"
                        + "complainantId varchar(50),\n"
                        + "respondentId varchar(50),\n"  
                        + "isAssigned boolean default false,\n"
                        + "caseOfficerId int ,\n"
                        + "progressMade longtext default 'New Case',\n"
                        + "status varchar(100) default 'Ongoing' \n"
                        + ")");
                
                
               System.out.println("Table " +tableName+ " has been created successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


