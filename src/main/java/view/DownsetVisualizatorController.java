/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import GraphGenerator.DownsetGraphGenerator;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Paolo
 */
public class DownsetVisualizatorController implements Initializable {

   
    @FXML
    private ImageView downsetImageView;

    
    
    public DownsetVisualizatorController(){
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         File f = new File(DownsetGraphGenerator.getPngFileName());
                Image image = new Image(f.toURI().toString());
                downsetImageView.setImage(image);
                downsetImageView.setPreserveRatio(true);
    }    
    
}
