/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
public class ForestOperationsOutputVisualizatorController implements Initializable {

    @FXML
    private ImageView forestImageView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setForestImage(String fileName){
        File f = new File(fileName);
                Image image = new Image(f.toURI().toString());
                
                forestImageView.setImage(image);
                
//                forestImageView.setPreserveRatio(true);
    }
}
