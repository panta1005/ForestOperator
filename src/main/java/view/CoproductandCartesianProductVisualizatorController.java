/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paolo
 */
public class CoproductandCartesianProductVisualizatorController implements Initializable {

    private ImageView firstImageView;
    private Label firstImageLabel;
    private ImageView secondImageView;
    private Label secondImageLabel;
    private String dotFileName;
    private String graphName;
    @FXML
    private ImageView resultImageView;
    @FXML
    private Label resultImageLabel;
    @FXML
    private Button viewDotFileButton;
    @FXML
    private Label nodeCountLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setResultImage(String imageName){
        File f = new File(imageName);
        Image image = new Image(f.toURI().toString());
        resultImageView.setImage(image);
        
        resultImageView.setPreserveRatio(true);
        resultImageView.setFitHeight(image.getHeight());
        resultImageView.setFitWidth(image.getWidth());
        System.out.println("image view height: "+resultImageView.getFitHeight());
        System.out.println("image view width: "+resultImageView.getFitWidth());
        
   }
//    public void setSecondImage(String imageName){
//        File f = new File(imageName);
//        Image image = new Image(f.toURI().toString());
//        secondImageView.setImage(image);
//        secondImageView.setPreserveRatio(true);
//    }
    public void setCountLabel(String label){
        nodeCountLabel.setText(label);
    }
    public void setImageLabel(String label){
        resultImageLabel.setText(label);
    }
    public void setGraphName(String graphName){
        this.graphName=graphName;
        this.dotFileName=graphName+".dot";
        
    }
    
    @FXML
    private void handleViewDotFileButtonAction(ActionEvent event) throws IOException {
//        open dot file viewer scene
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/DotFileTextViewer.fxml"));
        Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": "+dotFileName+" file text");
        DotFileTextViewerController controller=loader.getController();
        controller.initializeDotTextArea(dotFileName);
        stage.show();

    }
}
