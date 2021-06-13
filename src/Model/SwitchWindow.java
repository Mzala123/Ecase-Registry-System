/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author bounce
 */
public class SwitchWindow {

    public static Stack<String> location = new Stack<>();
    public static Stage stage;
    public static Scene scene;

    public void loadNewWindow(String location, String title, boolean resizable, boolean setMaximized) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(location));
            stage = new Stage();
            scene = new Scene(root);
            stage.setTitle(title);
            stage.setResizable(resizable);
            stage.setMaximized(resizable);
            stage.setScene(scene);
          /*  TranslateTransition translate = new TranslateTransition(Duration.seconds(.7), root);
            translate.setFromX(100);
            translate.setToX(0);     
//            FadeTransition fadeIn = new FadeTransition(Duration.seconds(.4), root);
//            fadeIn.setFromValue(0);
//            fadeIn.setToValue(1);
//            fadeIn.setCycleCount(1); 
            translate.play();*/
         //   fadeIn.play();
            stage.show();
  
        } catch (IOException ex) {
            Logger.getLogger(SwitchWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void loadToLoginWindow(String location, String title, boolean resizable, boolean setMaximized) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(location));
            stage = new Stage();
            scene = new Scene(root);
            stage.setTitle(title);
            stage.setResizable(resizable);
            stage.setMaximized(resizable);
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
            TranslateTransition translate = new TranslateTransition(Duration.seconds(.4), root);
            translate.setFromX(100);
            translate.setToX(0);     
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(.4), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1); 
            translate.play();
            fadeIn.play();
            stage.show();
  
        } catch (IOException ex) {
            Logger.getLogger(SwitchWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
