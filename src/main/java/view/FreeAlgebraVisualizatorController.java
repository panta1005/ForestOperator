/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import forestoperator.ForestOperator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paolo
 */
public class FreeAlgebraVisualizatorController implements Initializable {

    @FXML
    private Label resultImageLabel;
    @FXML
    private Button viewDotFileButton;
    @FXML
    private Label nodeCountLabel;
    private String dotFileName;
    private String graphName;
    @FXML
    private WebView resultWebView;
    //private ScrollPane webViewScrollPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    }    
    public void setSVGImage(String imageName) throws IOException{
        String st;
        String content="";
        File f = new File(imageName);
        BufferedReader br= new BufferedReader(new FileReader(f));
        //transports the svg file into a string
        while ((st = br.readLine()) != null) content=content+st;

        //put the string in the web view
        /*
        webViewScrollPane.setFitToHeight(true);
        webViewScrollPane.setFitToWidth(true);
        */
        resultWebView.getEngine().loadContent(content);
   }

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
