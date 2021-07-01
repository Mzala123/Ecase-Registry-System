/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e.pkgcase.registry.system;

import Model.DBHandler;
import Model.Folder;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author bounce
 */
public class ECaseRegistrySystem extends Application {

    public static Boolean isLoaded = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Folder folder = new Folder();
        folder.createFolders("Ecase");
        folder.createFolders("Ecase\\Reports");

        Parent root = FXMLLoader.load(getClass().getResource("/View/signUpPage.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("E-Case Registry System");
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBHandler handler = new DBHandler();
        launch(args);

    }

}
