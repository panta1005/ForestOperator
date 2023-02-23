/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view;

import GraphGenerator.CustomNameForestGraphGenerator;
import GraphGenerator.ForestWithSubforestsGraphGenerator;
import forestoperator.ForestOperator;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
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
import shared.Edge;
import shared.Forest;
import shared.ForestOperations;
import shared.NMForest;
import shared.Node;
import shared.UtilityFunctions;

/**
 * FXML Controller class
 *
 * @author Paolo
 */
public class FreeAlgebraGenerationController implements Initializable {
    private int algebraType;
    public final static int GODEL=0;
    public final static int NILPOTENT_MINIMUM=1;
    private boolean godelFormulaRepresentation;
    @FXML
    private TextField VariableNumberTextField;
    @FXML
    private Label InformationLabel;
    @FXML
    private Label ErrorLabel;
    @FXML
    private Button GenerateAlgebraButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleGenerateAlgebraButtonAction(ActionEvent event) throws IOException {
        //for now, only the godel case will be considered
        //can be extended to the other free algebras generation process
        int n;
        //check if value of textfield is valid
        //if not valid, set error label (color too)
        //empty check
        if(VariableNumberTextField.getText().isEmpty()){
            ErrorLabel.setText("Please insert the value of N");
            ErrorLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            return;
        }
        //check if the value in the textfield is a number
        try {
            n=Integer.parseInt(VariableNumberTextField.getText());
        } catch (NumberFormatException e) {
            ErrorLabel.setText("Please insert a valid value of N");
            ErrorLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            return;
        }
        // generate free algebra
        //case n=0: set error label
        if (n==0){
            ErrorLabel.setText("with N=0, the generated forest would contain a single node");
            ErrorLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            return;
        }
        switch (algebraType) {
            case GODEL -> {
                generateGodelAlgebra(n);
            }
            case NILPOTENT_MINIMUM -> {
                generateNMAlgebra(n);
            }
            
        }
        
        
    }
    
    
    
    public Forest generateGnByHn(int n){
        
        //we are always in the case n>0
        //count will be used to label the nodes
        int count;
        //int tmpN=1;
        //create map of Hi
        Map<Integer,Forest> mapHn=buildHnMap(n);
        
        System.out.println("h"+n+" created");
        //use Hn to generate Fn
       //REMEMBER Fn=Hn+bottom(Hn)
       //take Hn, duplicate it and use bottom function
       Forest hn=mapHn.get(n);
       count=hn.getNodeCount();
       
       Forest hn_bottom=ForestOperations.bottom(ForestOperations.duplicateForest(hn, count+1),Integer.toString(count)); 
       return ForestOperations.sum(hn, hn_bottom);
    }
    
    public Map<Integer, List<Forest>> generateGnNilpotentMinimum(int n){
        //case similar to generate Gn, but has labels for each tree --> i have to save the tree value (maybe in the root?)
        //keep a list or map containing the copies to separate forests with labels 0 and 1
        //i have to build a different graph generator to produce and print correctly the resulting forest
        //i can't return a forest here, either i return a map<integer(or boolean),list<Forest>(that will contain the resulting trees)
        //or i print them directly from here
        //get map hn
        Map<Integer,Forest> mapHn=buildHnMap(n);
        //initialize result map 
        Map<Integer,List<Forest>> resultMap=new HashMap<>();
        //generate forests with label 0
        List<Forest> zeroLabelledForests=new ArrayList<>();
        int count=0;
        
        int[] hiCopies=new int[n];
        //TO DO:these are separated only to see if the correct number of copies is requested, may merge them later
        for(int i=0;i<n;i++){
            int Hnumber= (int) UtilityFunctions.binomialCoefficient(n, i)*(int)Math.pow(2, i);
            hiCopies[i]=Hnumber;
            System.out.println("copies of H"+i+" needed for the creation of H"+n+": "+Hnumber);
        }
        for(int i=0;i<n;i++){
            //make duplicates of the prototype of H 
            for(int j=0;j<hiCopies[i];j++){
                //get prototype from map
                //duplicate forest
                //the copies have to be unique (change labels with a count integer passed from here)
                Forest h=ForestOperations.duplicateForest(mapHn.get(i),count);
                count=count+h.getNodeCount();
                Forest h_bottom=ForestOperations.bottom(h,Integer.toString(count));
                count++;
                //add duplicated forests to final forest
                zeroLabelledForests.add(h_bottom);
            }
        }
        //put generated 0 labelled forests in the result map
        resultMap.put(0, zeroLabelledForests);
        //generate forests with label 1
        List<Forest> oneLabelledForests=new ArrayList<>();
        //copies of hn needed: 2^n
        int hnCopies=(int) Math.pow(2, n);
        for(int i=0;i<hnCopies;i++){
            Forest h=ForestOperations.duplicateForest(mapHn.get(n),count);
            count=count+h.getNodeCount();
            Forest h_bottom=ForestOperations.bottom(h,Integer.toString(count));
            count++;
            //add duplicated forests to final forest
            oneLabelledForests.add(h_bottom);
        }
        //put generated 1 labelled forests in the result map
        resultMap.put(1, oneLabelledForests);
        return resultMap;
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

    private Map<Integer, Forest> buildHnMap(int n) {
        Map<Integer,Forest> mapHn=new HashMap<>();
        //generate base case for Hn (H0)
        //H0 is an empty forest
        //base case: n=0 return forest with a single node
        int count;
        Forest h0=new Forest();
        //add h0 to the map
        mapHn.put(0,h0);
        //generate until Hn
            for(int tmpN=1;tmpN<=n;tmpN++){
                //generate Hi
                count=0;
                //induction step: create Hn with H0,H1,...,Hn-1 with formula (that uses the binomial coefficient)
                //calculate how many copies of each h0,h1,...hi-1 are needed
                int[] hiCopies=new int[tmpN];
                for(int i=0;i<tmpN;i++){
                    int Hnumber= (int) UtilityFunctions.binomialCoefficient(tmpN, i);
                    hiCopies[i]=Hnumber;
                    System.out.println("copies of H"+i+" needed for the creation of H"+tmpN+": "+Hnumber);
                }
                Forest tmp=new Forest();
                //make different copies (determined by binomial coefficient)
                for(int i=0;i<hiCopies.length;i++){
                    //make duplicates of the prototype of H
                    
                    for(int j=0;j<hiCopies[i];j++){
                        //get prototype from map
                        //duplicate forest
                        //the copies have to be unique (change labels with a count integer passed from here)
                        Forest h=ForestOperations.duplicateForest(mapHn.get(i),count);
                        count=count+h.getNodeCount();
                        Forest h_bottom=ForestOperations.bottom(h,Integer.toString(count));
                        count++;
                        //add duplicated forests to final forest
                        tmp=ForestOperations.sum(tmp,h_bottom);
                    }
                   
                   
                    //sum duplicates in the forest Hi
                    //put it in mapHn (using i as index
                }
                 //save a prototype for different H forests
                 mapHn.put(tmpN, tmp);
                 System.out.println("h"+tmpN+" node count: "+tmp.getNodeCount());
            }
        return mapHn;

    }

    private void generateGodelAlgebra(int n) throws IOException {
        //Forest resultForest=ForestOperations.generateGnByProduct(n);
        if(godelFormulaRepresentation){
            //open godel formula input manager
            FXMLLoader loader= new FXMLLoader(getClass().getResource("GodelFormulaInputManager.fxml"));
            Parent root = loader.load();
            //get the UI controller and set the right flags
            Scene scene = new Scene(root);
            Stage stage= new Stage();
            stage.setScene(scene);
            stage.setTitle(ForestOperator.getVersion()+": godel formula representation");
            GodelFormulaInputManagerController controller=loader.getController();
            controller.setNumberOfVariables(n);
            
//            System.out.println(forestGenerator.getPngFileName());
//            controller.setResultImage(graphName+".png");
            
            stage.show();
        }else{
            //case free algebra generation
            Forest resultForest=generateGnByHn(n);
            System.out.println("Forest nodes number: "+ resultForest.getNodeCount());
            System.out.println("Forest edges number: "+ resultForest.getEdgeList().size());

            //display generated forest (with ForestOperationsOutputVisualizator)
            CustomNameForestGraphGenerator forestGenerator=new CustomNameForestGraphGenerator("GodelFreeAlgebra", resultForest.getEdgeList(),false,true,"svg");
            forestGenerator.generateGraph();
            //FXMLLoader loader= new FXMLLoader(getClass().getResource("CoproductandCartesianProductVisualizator.fxml"));
            String graphName="GodelFreeAlgebragraph";
            String labelText="Godel Free Algebra";
            openResultVisualizer(graphName,labelText,resultForest.getNodeCount());
        }
        
    }

    private void generateNMAlgebra(int n) throws IOException {
        NMForest resultForest=new NMForest(generateGnNilpotentMinimum(n));
        /*System.out.println("Forest nodes number: "+ resultForest.getNodeCount());
        System.out.println("Forest edges number: "+ resultForest.getEdgeList().size());*/
        ForestWithSubforestsGraphGenerator forestGenerator=new ForestWithSubforestsGraphGenerator("NMFreeAlgebra", resultForest.getEdgeListMap(),false,true,"svg");
        forestGenerator.generateGraph();
        //display generated forest (with ForestOperationsOutputVisualizator)
        //i have to create another graph generator
       
       
        //FXMLLoader loader= new FXMLLoader(getClass().getResource("CoproductandCartesianProductVisualizator.fxml"));
        String graphName="NMFreeAlgebragraph";
        String labelText="NM Free Algebra";
        openResultVisualizer(graphName,labelText,resultForest.getNodeCount());

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setAlgebraType(int algebraType) {
        this.algebraType=algebraType;
        //modify labels
        /*
        switch (algebraType) {
            case GODEL -> InformationLabel.setText("Note: for N>4, the program may take a while to compute the resulting forest");
            case NILPOTENT_MINIMUM -> InformationLabel.setText("Note: for N>4, the program may take a while to compute the resulting forest");
            default -> throw new AssertionError();  
        }
        */
    }

    /**
     * Set true if the scene refers to godel formula representation scene, or false if it refers to free algebra generation scene
     * @param godelFormulaRepresentation the boolean value to set
     */
    public void setGodelFormulaRepresentation(boolean godelFormulaRepresentation) {
        this.godelFormulaRepresentation = godelFormulaRepresentation;
    }

    

    
}
