/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class ForestOperationsMenuController implements Initializable {
    
    
    
    @FXML
    private Button bottomOperationButton;
    @FXML
    private Button sumOperationButton;
    @FXML
    private Button sumBottomOperationButton;
    @FXML
    private Button multiplicationOperationButton;
    @FXML
    private Button bottomlessOperationButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleBottomOperationButtonAction(ActionEvent event) throws IOException{
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/BottomInputManager.fxml"));
         Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": F bottom");
        BottomInputManagerController controller=loader.getController();
        //set proper variables
        controller.setBottomless(false);
        
         stage.show();
    }

    @FXML
    private void handleSumOperationButtonAction(ActionEvent event) throws IOException{
        //sum, sumbottom and multiplication will have the same UI, but will have different flags set to know what operation has to be done
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/MultiInputForestOperationsUI.fxml"));
        Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": F+G");
        MultiInputForestOperationsUIController controller=loader.getController();
        //set proper variables
        controller.setSum(true);
        
        stage.show();
    }

    @FXML
    private void handleSumBottomOperationButtonAction(ActionEvent event) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/MultiInputForestOperationsUI.fxml"));
         Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": (F+G)_");
        MultiInputForestOperationsUIController controller=loader.getController();
        //set proper variables
        controller.setSum(true);
        controller.setBottom(true);
        stage.show();
    }

    @FXML
    private void handleMultiplicationOperationButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/MultiInputForestOperationsUI.fxml"));
         Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": FxG");
        MultiInputForestOperationsUIController controller=loader.getController();
        //set proper variables
        controller.setProduct(true);
        stage.show();
    }

    @FXML
    private void handleBottomLessOperationButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/BottomInputManager.fxml"));
         Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": F bottomless");
        BottomInputManagerController controller=loader.getController();
        //set proper variables
        controller.setBottomless(true);
        stage.show();
    }

    
}
