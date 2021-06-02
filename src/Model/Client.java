/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.jfoenix.controls.JFXCheckBox;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bounce
 */
public class Client extends Person{
    
    private String type;
    private String redDate;
    
    DBHandler handler = new DBHandler();
    PreparedStatement preparedStatement;

    public Client() {
       
    }
    

 public Client(String nationalId, String firstName, String id, String lastName, String postalAddress,
          String email, String dob, String contact, String nationality, String gender,
          String residence,String type, String redDate){
          super(nationalId, firstName, id, lastName, postalAddress, email, dob, 
          contact, nationality, gender, residence);
          this.type = type;
          this.redDate = redDate;
    }   

    public Client(String type, String redDate, String id, String postalAddress, String email, String contact) {
        super(id, postalAddress, email, contact);
        this.type = type;
        this.redDate = redDate;
    }

    public Client(String id, String postalAddress, String email) {
        super(id, postalAddress, email);
    }

  
    public void setType(String type) {
        this.type = type;
    }

    public void setRedDate(String redDate) {
        this.redDate = redDate;
    }

    public String getType() {
        return type;
    }

    public String getRedDate() {
        return redDate;
    }

   
    
    
}
