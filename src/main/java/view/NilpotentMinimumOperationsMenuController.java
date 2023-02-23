/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import forestoperator.ForestOperator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paolo
 */
public class NilpotentMinimumOperationsMenuController implements Initializable {

    @FXML
    private Button DownsetButton;
    @FXML
    private Button FreeAlgebraButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleDownsetButtonAction(ActionEvent event) throws IOException {
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("NilpotentMinimumDownsetInputManagerUI.fxml"));
        Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": Nilpotent minimum Downset generation");
        /*MultiInputForestOperationsUIController controller=loader.getController();
        //set proper variables
        controller.setSum(true);
        */
        stage.show();
        
    //    System.out.println("You clicked me!");
    //    label.setText("Hello World!");
    }

    @FXML
    private void handleForestOperationAction(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FreeAlgebraGeneration.fxml"));
        Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": Nilpotent Minimum free algebra generation");
        FreeAlgebraGenerationController controller=loader.getController();
        controller.setAlgebraType(FreeAlgebraGenerationController.NILPOTENT_MINIMUM);
        
        //set proper variables
        //controller.setSum(true);
        stage.show();
    //    System.out.println("You clicked me!");
    //    label.setText("Hello World!");
    }
    
}
