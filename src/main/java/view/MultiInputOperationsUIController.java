/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import GraphGenerator.CustomNameForestGraphGenerator;
import GraphGenerator.DownsetGraphGenerator;
import forestoperator.ForestOperator;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import shared.DownsetGraph;
import shared.Edge;
import shared.ElementList;
import shared.Forest;
import shared.ForestOperations;


/**
 * FXML Controller class
 *
 * @author Paolo
 */
public class MultiInputOperationsUIController implements Initializable {

    static String INPUTFOREST_NAME_F="F";
    static String INPUTFOREST_NAME_G="G";
    private Forest inputForest_F;
    private Forest inputForest_G;
    private List<Edge> f_downsetEdgeList,g_downsetEdgeList;
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
    private Label errorLabel;
    @FXML
    private Button generateDownsetButton;
    @FXML
    private Button generateProductButton;
    @FXML
    private Button generateSumButton;
    @FXML
    private Button generateProductDownsetButton;
    @FXML
    private Button generateSumDownsetButton;
    @FXML
    private Button generateCartesianProductButton;

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
        controller.setMultiInputOperationsController(this);
        
        stage.show();
    }
    @FXML
    private void handleGenerateInputForestGButtonAction(ActionEvent event) throws IOException {
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
        controller.setMultiInputOperationsController(this);
        
        stage.show();
    }
    

   

    @FXML
    private void handleGenerateDownsetButtonAction(ActionEvent event) throws IOException {
        if(forestCheck()){
            DownsetGraphGenerator f_Generator=new DownsetGraphGenerator(INPUTFOREST_NAME_F,inputForest_F);
            f_Generator.generateGraph();
            List<Edge> f_downsetEdgeList=f_Generator.getDownsetEdgeList();
            DownsetGraphGenerator g_Generator=new DownsetGraphGenerator(INPUTFOREST_NAME_G,inputForest_G);
            g_Generator.generateGraph();
            List<Edge> g_downsetEdgeList=g_Generator.getDownsetEdgeList();
            //open new scene and pass it the two downsets
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/MultiDownsetVisualizator.fxml"));
            Parent root = loader.load();
            //get the UI controller and set the right flags
            Scene scene = new Scene(root);
            Stage stage= new Stage();
            stage.setScene(scene);
            stage.setTitle(ForestOperator.getVersion()+":Downset(F) and Downset(G)");
            MultiDownsetVisualizatorController controller=loader.getController();
            //set proper variables
            controller.setFDownsetList(f_Generator.getDownsetList());
            controller.setGDownsetList(g_Generator.getDownsetList());
            controller.setFDownsetEdgeList(f_downsetEdgeList);
            controller.setGDownsetEdgeList(g_downsetEdgeList);
            controller.setForestF(inputForest_F);
            controller.setForestG(inputForest_G);
            //set images
            String F_OutputPngFileName=INPUTFOREST_NAME_F+"_downsetgraph.png";
            String G_OutputPngFileName=INPUTFOREST_NAME_G+"_downsetgraph.png";
            controller.setFDotFileName(INPUTFOREST_NAME_F+"_downsetgraph.dot");
            controller.setGDotFileName(INPUTFOREST_NAME_G+"_downsetgraph.dot");
            controller.setFImage(F_OutputPngFileName);
            controller.setGImage(G_OutputPngFileName);
           
            stage.show();
        }
    }

    private boolean forestCheck(){
        if(inputForest_F==null||inputForest_G==null){
            //set error label
            if(inputForest_F==null){
                errorLabel.setText("Input forest "+INPUTFOREST_NAME_F+" missing");
                errorLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            }else{
                errorLabel.setText("Input forest "+INPUTFOREST_NAME_G+" missing");
                errorLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            }
            return false;
         }else{
            errorLabel.setText("");
            return true;
        }
    }
    public void setInputForestF(Forest forest) {
        this.inputForest_F=forest;
        String pngFileName=INPUTFOREST_NAME_F+"graph.png";
        File f = new File(pngFileName);
        Image image = new Image(f.toURI().toString());
        inputForestFImageView.setImage(image);
        inputForestFImageView.setPreserveRatio(true);
        inputForestFLabel.setVisible(true);
    }

    public void setInputForestG(Forest forest) {
        this.inputForest_G=forest;
        String pngFileName=INPUTFOREST_NAME_G+"graph.png";
        File f = new File(pngFileName);
        Image image = new Image(f.toURI().toString());
        inputForestGImageView.setImage(image);
        inputForestGImageView.setPreserveRatio(true);
        inputForestGLabel.setVisible(true);
    }

    @FXML
    private void handleGenerateProductButtonAction(ActionEvent event) throws IOException {
        if(forestCheck()){
//            make product forest
            Forest result=ForestOperations.product(inputForest_F, inputForest_G);
            CustomNameForestGraphGenerator forestGenerator=new CustomNameForestGraphGenerator("FxG", result.getEdgeList(),false,true,"png");
            forestGenerator.generateGraph();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/CoproductandCartesianProductVisualizator.fxml"));
            String graphName="FxGgraph";
            String labelText="FxG";
            openResultVisualizator(graphName,labelText,result.getNodeCount());

        }
    }

    @FXML
    private void handleGenerateSumButtonAction(ActionEvent event) throws IOException {
        if(forestCheck()){
            Forest result=ForestOperations.sum(inputForest_F, inputForest_G);
            CustomNameForestGraphGenerator forestGenerator=new CustomNameForestGraphGenerator("F+G", result.getEdgeList(),"png");
            forestGenerator.generateGraph();
            String graphName="F+Ggraph";
            String labelText="F+G";
            openResultVisualizator(graphName,labelText,result.getNodeCount());
            
        }
    }

    @FXML
    private void handleGenerateProductDownsetButtonAction(ActionEvent event) throws IOException {
        if(forestCheck()){
            Forest productForest=ForestOperations.product(inputForest_F,inputForest_G);
//            CustomNameForestGraphGenerator forestGenerator=new CustomNameForestGraphGenerator("F+G", result.getEdgeList());
//            forestGenerator.generateGraph();
            DownsetGraphGenerator downset_Generator=new DownsetGraphGenerator("downsetFxG",productForest);
            downset_Generator.generateGraph();
            String graphName="downsetFxG_downsetgraph";
            String labelText="downset(FxG)";
            openResultVisualizator(graphName, labelText,downset_Generator.getDownsetList().size());
        }
    }

    @FXML
    private void handleGenerateSumDownsetButtonAction(ActionEvent event) throws IOException {
        if(forestCheck()){
            Forest sumForest=ForestOperations.sum(inputForest_F,inputForest_G);
//            CustomNameForestGraphGenerator forestGenerator=new CustomNameForestGraphGenerator("F+G", result.getEdgeList());
//            forestGenerator.generateGraph();
            DownsetGraphGenerator downset_Generator=new DownsetGraphGenerator("downsetF+G",sumForest);
            downset_Generator.generateGraph();
            String graphName="downsetF+G_downsetgraph";
            String labelText="downset(F+G)";
            openResultVisualizator(graphName, labelText,downset_Generator.getDownsetList().size());
        }
    }

    @FXML
    private void handleGenerateCartesianProductButtonAction(ActionEvent event) throws IOException {
        if(forestCheck()){
//            generate downsets
            DownsetGraphGenerator f_Generator=new DownsetGraphGenerator(INPUTFOREST_NAME_F,inputForest_F);
            f_Generator.generateGraph();
            f_downsetEdgeList=f_Generator.getDownsetEdgeList();
            List<ElementList> f_downsetList=f_Generator.getDownsetList();
            DownsetGraphGenerator g_Generator=new DownsetGraphGenerator(INPUTFOREST_NAME_G,inputForest_G);
            g_Generator.generateGraph();
            List<ElementList> g_downsetList=g_Generator.getDownsetList();
            g_downsetEdgeList=g_Generator.getDownsetEdgeList();
//            generate cartesian product
            DownsetGraph f=new DownsetGraph(f_downsetList,f_downsetEdgeList),g=new DownsetGraph(g_downsetList, g_downsetEdgeList);
            DownsetGraph cartesianProductDownsetGraph=ForestOperations.cartesianProduct(f,g);
            List<Edge> cartesianProductEdgeList=cartesianProductDownsetGraph.getDownsetEdgeList();
            CustomNameForestGraphGenerator generator=new CustomNameForestGraphGenerator("CartesianProduct", cartesianProductEdgeList,true,false,"png");
            generator.generateGraph();
            String graphName="CartesianProductgraph";
            String labelText="D(F)xD(G)";
//            calculate nodecount
            int nodeCount=getDistinctNodeCount(cartesianProductEdgeList);
            openResultVisualizator(graphName, labelText,nodeCount);
        }
    }
    private void openResultVisualizator(String graphName,String labelText,int nodeCount) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/CoproductandCartesianProductVisualizator.fxml"));
            Parent root = loader.load();
            //get the UI controller and set the right flags
            Scene scene = new Scene(root);
            Stage stage= new Stage();
            stage.setScene(scene);
            stage.setTitle(ForestOperator.getVersion()+": "+labelText);
            CoproductandCartesianProductVisualizatorController controller=loader.getController();
//            System.out.println(forestGenerator.getPngFileName());
            controller.setResultImage(graphName+".png");
            controller.setImageLabel(labelText+":");
            controller.setCountLabel("Node count: "+ nodeCount);
            controller.setGraphName(graphName);
            stage.show();
    }


    private int getDistinctNodeCount(List<Edge> edgeList) {
        int count=0;
        List<String> nodesAlreadyCounted=new ArrayList<>();
//        scorro l'edgelist
//        se trovo un nodo nuovo, aggiorno il count, se no vado avanti
        for(Edge e:edgeList){
            String father=e.getFather(),child=e.getChild();
            if(!nodesAlreadyCounted.contains(father)){
                count++;
                nodesAlreadyCounted.add(father);
            }
            if(!nodesAlreadyCounted.contains(child)){
                count++;
                nodesAlreadyCounted.add(child);
            }
        }
        return count;
    }
}
