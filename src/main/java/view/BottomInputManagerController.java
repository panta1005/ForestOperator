/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import GraphGenerator.CustomNameForestGraphGenerator;
import forestoperator.ForestOperator;
import java.io.File;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import shared.Forest;
import shared.ForestOperations;

/**
 * FXML Controller class
 *
 * @author Paolo
 */
public class BottomInputManagerController implements Initializable {
    
    private Forest inputForest;
    private Forest outputForest;
    private static final String INPUTFOREST_NAME="F";
    private boolean isBottomless;
    @FXML
    private Button generateInputForestButton;
    @FXML
    private ImageView inputForestImageView;
    @FXML
    private ImageView outputForestImageView;
    @FXML
    private Button generateOutputForestButton;
    @FXML
    private Label inputForestLabel;
    @FXML
    private Label outputForestLabel;
    @FXML
    private Label errorLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleGenerateInputForestButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ForestInputManager.fxml"));
         Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": generate input forest "+INPUTFOREST_NAME);
        //set forest name
        ForestInputManagerController controller=loader.getController();
        //set ref to this controller
        controller.setforestName(INPUTFOREST_NAME);
        controller.setBottomController(this);
        
        stage.show();
    }

   

    @FXML
    private void handleGenerateOutputForestButtonAction(ActionEvent event) throws IOException{
        if(inputForest!=null){
            String outputForestName;
            if(isBottomless){
                outputForest=ForestOperations.bottomless(inputForest);
                outputForestName="F_less";
            }else{
                outputForest=ForestOperations.bottom(inputForest);
                outputForestName="F_";
            }
            System.out.println("outputforest created");
            //create  output image
            CustomNameForestGraphGenerator generator=new CustomNameForestGraphGenerator(outputForestName,outputForest.getEdgeList(),"png");
            generator.generateGraph();
            //display output image
            String outputPngFileName=outputForestName+"graph.png";
            File f = new File(outputPngFileName);
            Image image = new Image(f.toURI().toString());
            outputForestImageView.setImage(image);
            outputForestImageView.setPreserveRatio(true);
            outputForestLabel.setVisible(true);
            errorLabel.setText("");
        }else{
            //set error label
            errorLabel.setText("Input forest missing");
            errorLabel.textFillProperty().set(Color.valueOf("#ff0000"));
        }
    }
     public void setInputForest(Forest forest){
        this.inputForest=forest;
        //set input forest image
        String pngFileName=INPUTFOREST_NAME+"graph.png";
        File f = new File(pngFileName);
        Image image = new Image(f.toURI().toString());
        inputForestImageView.setImage(image);
        inputForestImageView.setPreserveRatio(true);
        inputForestLabel.setVisible(true);
//        
    }
    
    public void setBottomless(boolean isBottomless){
        this.isBottomless=isBottomless;
    }
}
