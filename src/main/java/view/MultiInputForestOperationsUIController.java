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
public class MultiInputForestOperationsUIController implements Initializable {
    public static final String INPUTFOREST_NAME_F="F";
    public static final String INPUTFOREST_NAME_G="G";
    private Forest inputForest_F;
    private Forest inputForest_G;
    private Forest outputForest;
    private boolean bottom,sum,product;
//    private boolean downset;
    //FXML variables-----------------------------
    @FXML
    private ImageView inputForestFImageView;
    @FXML
    private Label inputForestFLabel;
    @FXML
    private ImageView inputForestGImageView;
    @FXML
    private Label inputForestGLabel;
    @FXML
    private Button generateInputForestFButton;
    @FXML
    private Button generateInputForestGButton;
    @FXML
    private Button generateOutputForestButton;
    @FXML
    private Label errorLabel;

    public MultiInputForestOperationsUIController() {
        this.bottom=false;
        this.sum=false;
        this.product=false;
//        this.downset=false;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleGenerateInputForestFButtonAction(ActionEvent event) throws IOException {
        //call forest input with name F
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ForestInputManager.fxml"));
         Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": generate input forest "+INPUTFOREST_NAME_F);
        //set forest name
        ForestInputManagerController controller=loader.getController();
        //set ref to this controller
        controller.setforestName(INPUTFOREST_NAME_F);
        controller.setMultiInputForestOperationsController(this);
        
        stage.show();
    }

    @FXML
    private void handleGenerateInputForestGButtonAction(ActionEvent event) throws IOException {
        //call forest input with name G
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ForestInputManager.fxml"));
         Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": generate input forest "+INPUTFOREST_NAME_G);
        //set forest name
        ForestInputManagerController controller=loader.getController();
        //set ref to this controller
        controller.setforestName(INPUTFOREST_NAME_G);
        controller.setMultiInputForestOperationsController(this);
        stage.show();
    }
    public void setInputForestF(Forest forest){
        this.inputForest_F=forest;
        //set input forest image
        String pngFileName=INPUTFOREST_NAME_F+"graph.png";
        File f = new File(pngFileName);
        Image image = new Image(f.toURI().toString());
        inputForestFImageView.setImage(image);
        inputForestFImageView.setPreserveRatio(true);
        inputForestFLabel.setVisible(true);
//        
    }
    public void setInputForestG(Forest forest){
        this.inputForest_G=forest;
        //set input forest image
        String pngFileName=INPUTFOREST_NAME_G+"graph.png";
        File f = new File(pngFileName);
        Image image = new Image(f.toURI().toString());
        inputForestGImageView.setImage(image);
        inputForestGImageView.setPreserveRatio(true);
        inputForestGLabel.setVisible(true);
//        
    }
    @FXML
    private void handleGenerateOutputForestButtonAction(ActionEvent event) throws IOException {
        //check on both input forest
        
        if(inputForest_F==null||inputForest_G==null){
            //set error label
            if(inputForest_F==null){
                errorLabel.setText("Input forest "+INPUTFOREST_NAME_F+" missing");
                errorLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            }else{
                errorLabel.setText("Input forest "+INPUTFOREST_NAME_G+" missing");
                errorLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            }
//            errorLabel.setText("Input forest missing");
//            errorLabel.textFillProperty().set(Color.valueOf("#ff0000"));
        }else if(product||sum){
            String outputForestName="";
            //check which operation has to be executed
            if(product){
                outputForest=ForestOperations.product(inputForest_F,inputForest_G);
                outputForestName="F_G_product";
            }else{
                if(bottom){
                    outputForest=ForestOperations.sumAndBottom(inputForest_F, inputForest_G);
                    outputForestName="F_G_sumBottom";
                }else{
                    outputForest=ForestOperations.sum(inputForest_F, inputForest_G);
                    outputForestName="F_G_sum";
                }
            }
//            if(isBottomless){
//                outputForest=ForestOperations.bottomless(inputForest);
//                outputForestName="F_less";
//            }else{
//                outputForest=ForestOperations.bottom(inputForest);
//                outputForestName="F_";
//            }
            System.out.println("outputforest created");
//create  output image
            CustomNameForestGraphGenerator generator=new CustomNameForestGraphGenerator(outputForestName,outputForest.getEdgeList(),false,true,"png");
            generator.generateGraph();
//display output image
            String outputPngFileName=outputForestName+"graph.png";
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ForestOperationsOutputVisualizator.fxml"));
            Parent root = loader.load();
            //get the UI controller and set the right flags
            Scene scene = new Scene(root);
            Stage stage= new Stage();
            stage.setScene(scene);
            stage.setTitle(ForestOperator.getVersion()+": "+outputForestName);
            ForestOperationsOutputVisualizatorController controller=loader.getController();
            //set proper variables
            controller.setForestImage(outputPngFileName);

             stage.show();

        }else{
            //case downset
            //generate D(F) and D(G)
            System.out.println("f and g downset created");
            //pass them to next scene
        }
    }
    public void setBottom(boolean isBottom){
        this.bottom=isBottom;
    }
    public void setSum(boolean isSum){
        this.sum=isSum;
    }
    public void setProduct(boolean isProduct){
        this.product=isProduct;
    }

//    public void setDownset(boolean isDownset) {
//        this.downset=isDownset;
//    }
}
