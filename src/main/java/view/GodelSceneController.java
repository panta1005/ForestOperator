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
public class GodelSceneController implements Initializable {

    @FXML
    private Button DownsetButton;
    @FXML
    private Button ForestOperationButton;
    @FXML
    private Button coproductButton;
    @FXML
    private Button FreeAlgebraButton;
    @FXML
    private Button representFormulaButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleDownsetButtonAction(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("DownsetInputManagerUI.fxml"));
       
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion());
        stage.show();
    //    System.out.println("You clicked me!");
    //    label.setText("Hello World!");
    }

    @FXML
    private void handleForestOperationAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ForestOperationsMenu.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+"Forest Operations");
        stage.show();
    }

    @FXML
    private void handleCoproductButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("MultiInputOperationsUI.fxml"));
         Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": D(F) and D(G)");
//        MultiInputForestOperationsUIController controller=loader.getController();
//        //set proper variables
//        controller.setDownset(true);
        stage.show();
    }

    @FXML
    private void handleFreeAlgebraButtonAction(ActionEvent event) throws IOException {
        //load free algebra scene
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FreeAlgebraGeneration.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+"Godel free algebra generation");
        FreeAlgebraGenerationController controller=loader.getController();
        controller.setAlgebraType(FreeAlgebraGenerationController.GODEL);
        controller.setGodelFormulaRepresentation(false);
        stage.show();
    }

    @FXML
    private void handleRepresentFormulaButtonAction(ActionEvent event) throws IOException {
        //load free algebra scene
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FreeAlgebraGeneration.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": Represent a Godel formula");
        FreeAlgebraGenerationController controller=loader.getController();
        controller.setAlgebraType(FreeAlgebraGenerationController.GODEL);
        controller.setGodelFormulaRepresentation(true);
        stage.show();
    }
    
}
