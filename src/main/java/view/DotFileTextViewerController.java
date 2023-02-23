/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Paolo
 */
public class DotFileTextViewerController implements Initializable {

    @FXML
    private TextArea dotFileTextArea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void initializeDotTextArea(String dotFileName) throws IOException{
        //open file
//        write file lines in text area 
        File dotFile=new File(dotFileName);
        if (dotFile != null) {
            BufferedReader br = null;
            //open file
                br = new BufferedReader(new FileReader(dotFile));
                String st; 
                
                while ((st = br.readLine()) != null){
                    System.out.println(st);
                    dotFileTextArea.setText(dotFileTextArea.getText()+st+"\n");
//                    controller.getDbManager().insert(workerData[0],workerData[1]);
//                    count++;
                    
                }
            br.close();
            
        }
        dotFileTextArea.setEditable(false);
    }
}
