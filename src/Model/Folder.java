/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;

/**
 *
 * @author bounce
 */
public class Folder {

    public void createFolders(String name) {
        File theDir = null;
        boolean bool = false;

        // returns pathnames for files and directory     String myDocumentPath = System.getProperty("user.home") + "\\Documents";  
        String myDocumentPath = System.getProperty("user.home") + "\\Documents";
        theDir = new File(myDocumentPath + "\\" + name + "\\");//creates the new folder name "Adonai Enterprises"

        // create
        bool = theDir.mkdir();

        // print
        System.out.println("Directory created? " + bool);
    }
}
