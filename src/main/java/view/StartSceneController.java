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
public class StartSceneController implements Initializable {

    @FXML
    private Button GodelButton;
    @FXML
    private Button NMButton;
    @FXML
    private Button NPcButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleGodelButtonAction(ActionEvent event) throws IOException {
        //open old main scene (godel controller)
        Parent root = FXMLLoader.load(getClass().getResource("GodelScene.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion());
        stage.show();
    }
    @FXML
    private void handleNMButtonAction(ActionEvent event) throws IOException {
        //open nilpotent minimum scene
        Parent root = FXMLLoader.load(getClass().getResource("NilpotentMinimumOperationsMenu.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion());
        stage.show();
        //for now, the FreeNMalgebragenerationscene will be used (until NMDownset will be implemented)
        /*
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FreeAlgebraGeneration.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion());
        FreeAlgebraGenerationController controller=loader.getController();
        controller.setAlgebraType(FreeAlgebraGenerationController.NILPOTENT_MINIMUM);
        stage.show();
*/
    }

    @FXML
    private void handleNPcButtonAction(ActionEvent event) {
        //open nelson pc scene
    }
    
}
