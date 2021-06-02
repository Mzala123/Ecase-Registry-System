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
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author bounce
 */
public class PopWindow {
    public static  Stack<String> loc = new Stack<>();
    public static Stage primaryStage;
    public static Stage childStage;
    public static double xOffset = 0;
    double yOffset = 0;
    
    
    public  void loadWindow(String loc, String title,boolean resizable){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            primaryStage = new Stage();
            primaryStage.setTitle(title);
            primaryStage.setTitle(title);
            primaryStage.setResizable(resizable);
            primaryStage.setScene(new Scene(parent));
            primaryStage.show();
         
 
        }catch (IOException ex){
            Logger.getLogger(PopWindow.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
     public  void loadWindow(String loc, String title,boolean resizable,boolean setMaximized){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            primaryStage = new Stage();
            primaryStage.setTitle(title);
            primaryStage.setTitle(title);
            primaryStage.setResizable(resizable);
            primaryStage.setMaximized(setMaximized);
            primaryStage.setScene(new Scene(parent));
              
            primaryStage.show();
          
         
 
        }catch (IOException ex){
            Logger.getLogger(PopWindow.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
      public  void loadWindow(String loc, String title,boolean resizable,boolean setMaximized,boolean decorated){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
             primaryStage = new Stage();
              primaryStage.setTitle(title);
              if(!decorated){
                  primaryStage.initStyle(StageStyle.DECORATED);
              }
            
             primaryStage.setTitle(title);
            primaryStage.setResizable(resizable);
            primaryStage.setMaximized(setMaximized);
             primaryStage.setScene(new Scene(parent));
             primaryStage.show();
         
 
        }catch (IOException ex){
            Logger.getLogger(PopWindow.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
      public void loadWindow(String loc, String title, boolean resizable,boolean setMaximized, boolean decorated,boolean modal){
            try{
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
             primaryStage = new Stage();
              primaryStage.setTitle(title);
              if(!decorated){
                  primaryStage.initStyle(StageStyle.DECORATED);
              }
              if(modal){
                 primaryStage.initModality(Modality.APPLICATION_MODAL);
              }
             primaryStage.setTitle(title);
            primaryStage.setResizable(resizable);
            primaryStage.setMaximized(setMaximized);
             primaryStage.setScene(new Scene(parent));
             primaryStage.show();
         
               parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
             });
             
            parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            });
        }catch (IOException ex){
            Logger.getLogger(PopWindow.class.getName()).log(Level.SEVERE,null,ex);
        }

      }
      
       public void loadSmallWindow(String loc, String title, boolean resizable,boolean setMaximized, boolean decorated,boolean modal){
            try{
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
             childStage = new Stage();
             childStage.setTitle(title);
              if(!decorated){
                  childStage.initStyle(StageStyle.DECORATED);
              }
              if(modal){
                 childStage.initModality(Modality.APPLICATION_MODAL);
              }
             childStage.setTitle(title);
             childStage.setResizable(resizable);
             childStage.setMaximized(setMaximized);
             childStage.setScene(new Scene(parent));
             childStage.show();
         
               parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
             });
             
            parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    childStage.setX(event.getScreenX() - xOffset);
                    childStage.setY(event.getScreenY() - yOffset);
                }
            });
        }catch (IOException ex){
            Logger.getLogger(PopWindow.class.getName()).log(Level.SEVERE,null,ex);
        }

      }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Stage getChildStage() {
        return childStage;
    }

    
    public static void setPrimaryStage(Stage primaryStage) {
        
        PopWindow.primaryStage = primaryStage;
        
    }
   

}
