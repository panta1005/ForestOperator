/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import GraphGenerator.CustomNameForestGraphGenerator;
import GraphGenerator.ForestGraphWithColoredNodesGenerator;
import forestoperator.ForestOperator;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import shared.Forest;
import shared.ForestOperations;
import shared.FormulaParser;

/**
 * FXML Controller class
 *
 * @author Paolo
 */
public class GodelFormulaInputManagerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private int variableNumber;
    private char[] variableAlphabet;
    private Forest g_n;
    @FXML
    private Label legendLabel;
    @FXML
    private TextField formulaTextField;
    @FXML
    private Button evaluateFormulaButton;
    @FXML
    private Label errorMessageLabel;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setNumberOfVariables(int n) {
        variableNumber=n;
        variableAlphabet=new char[variableNumber];
        String legend=legendLabel.getText();
        String[] legendSplit=legend.split("Alphabet:");
        String alphabet="Alphabet: ";
        for(int i=0;i<n;i++){
            variableAlphabet[i]=ForestOperations.ALPHABET[i];
            alphabet+=ForestOperations.ALPHABET[i]+" ";
        }
        legend=legendSplit[0]+alphabet+legendSplit[1];
        legendLabel.setText(legend);
        g_n=ForestOperations.generateGnByProduct(variableNumber);
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @FXML
    private void handleEvaluateFormulaButtonAction(ActionEvent event) {
        
        String formula=formulaTextField.getText();
        try {
            //evaluate formula to obtain list of nodes to be colored
            List<String> nodesToBeColored=FormulaParser.evaluateFormula(g_n.getForestNodesMap(), formula, variableAlphabet);
            System.out.println("LIST OF RESULT NODES OF FORMULA EVALUATION");
            System.out.println(nodesToBeColored);
            ForestGraphWithColoredNodesGenerator forestGenerator=new ForestGraphWithColoredNodesGenerator
            ("GodelFormulaRepresentation", g_n.getEdgeList(),false,true,"svg",nodesToBeColored);
            forestGenerator.generateGraph();
            //FXMLLoader loader= new FXMLLoader(getClass().getResource("CoproductandCartesianProductVisualizator.fxml"));
            String graphName="GodelFormulaRepresentationgraph";
            String labelText="Godel Formula Representation";
            openResultVisualizer(graphName,labelText,g_n.getNodeCount());
            //generate forest with colored nodes
        } catch (Exception ex) {
            errorMessageLabel.setText(ex.getMessage());
            errorMessageLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            Logger.getLogger(GodelFormulaInputManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void openResultVisualizer(String graphName,String labelText,int nodeCount) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FreeAlgebraVisualizator.fxml"));
            Parent root = loader.load();
            //get the UI controller and set the right flags
            Scene scene = new Scene(root);
            Stage stage= new Stage();
            stage.setScene(scene);
            stage.setTitle(ForestOperator.getVersion()+": "+labelText);
            FreeAlgebraVisualizatorController controller=loader.getController();
//            System.out.println(forestGenerator.getPngFileName());
//            controller.setResultImage(graphName+".png");
            controller.setSVGImage(graphName+".svg");
            controller.setImageLabel(labelText+":");
            controller.setCountLabel("Node count: "+ nodeCount);
            controller.setGraphName(graphName);
            stage.show();
    } 
}
