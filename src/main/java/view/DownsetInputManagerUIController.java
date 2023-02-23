/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import GraphGenerator.DownsetGraphGenerator;
import GraphGenerator.ForestGraphGenerator;
import GraphGenerator.GraphVizManager;
import forestoperator.ForestOperator;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import shared.*;


public class DownsetInputManagerUIController implements Initializable {
    
    
    private Forest forest;
    
    //FXML variables-----------------------------
    @FXML 
    private Button generateForestButton;
    @FXML 
    private Button addPairButton;
    @FXML
    private TextField forestInputTextField;
    @FXML
    private Label inputMessageLabel;
    @FXML
    private ListView<Edge> addedPairsListView;
    @FXML
    private Label forestGenerationMessagesLabel;
    @FXML
    private ImageView forestImageView;
    @FXML
    private StackPane stackPane;
    @FXML
    private Button generateDownsetForestGraphButton;
    @FXML
    private Button removePairsButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button addPairFromFileButton;
    @FXML
    private Button addForestFromFileButton;
    
    
    
    public DownsetInputManagerUIController(){
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addedPairsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    
    @FXML
    private void handleGenerateForestButtonAction(ActionEvent event) {
        System.out.println(generateForestButton.getId().toString());
        //input pair check
        
        ObservableList<Edge> pairList=addedPairsListView.getItems();
        pairList.sort((Edge e1, Edge e2) -> e1.toString().compareTo(e2.toString()));
        Set<Edge> pairSet=new LinkedHashSet<>(pairList);
        
       // pairSet.addAll(pairList);
        
        if(!pairSet.isEmpty()){
            System.out.println("there are pairs");
            try{
                forest=new Forest(pairSet,true);
                System.out.println("forest created successfully");
                forestGenerationMessagesLabel.setText("forest created successfully");
                forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#2bd90d"));
                //use graphviz to create the image of the forest
            
                //generate forest image
                ForestGraphGenerator generator=new ForestGraphGenerator(forest.getEdgeList());
                generator.generateGraph();
                //display forest image
                File f = new File(ForestGraphGenerator.getPngFileName());
                Image image = new Image(f.toURI().toString());
                forestImageView.setImage(image);
                forestImageView.setPreserveRatio(true);
                
                generateDownsetForestGraphButton.setVisible(true);
    
            }catch(Exception e){
                forestGenerationMessagesLabel.setText(e.getMessage());
                forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            }
            
        }else{
            forestGenerationMessagesLabel.setText("no input pairs found");
            forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#ff0000"));    
        }
        //input pair generation
        //generate forest
        //check for loops
        
    }

    @FXML
    private void handleAddPairButtonAction(ActionEvent event) {
         if(forestInputTextField.getText()!=null&&!forestInputTextField.getText().isEmpty()){
            String inputPair=forestInputTextField.getText();
            ObservableList<Edge> addedPair=FXCollections.<Edge>observableArrayList();
            System.out.println(inputPair);
             //remove spaces
            inputPair=inputPair.trim();
            inputPair=inputPair.replaceAll("\\s", "");
            //separate pairs
            String[] rawPairs=inputPair.split(Edge.getSeparator());
            int i=0;
            boolean correctPairSintax=true;
            //check for correct sintax of the pairs es.(x,y);(z)
            while(i<rawPairs.length&&correctPairSintax){
                //remove parenthesis
                String singleRawPair=rawPairs[i];
                if(singleRawPair.length()>0){
                    if (singleRawPair.charAt(0)=='('){

                        singleRawPair=singleRawPair.substring(1);
                    }else{
                        correctPairSintax=false;
                    }
                    if(singleRawPair.charAt(singleRawPair.length()-1)==')'){
                        singleRawPair=singleRawPair.substring(0, singleRawPair.length()-1);
                    }else{
                        correctPairSintax=false;
                    }
                    
                }else{
                    correctPairSintax=false;
                }
                
                
                System.out.println(singleRawPair);
                //extract pair
                String[] pair=singleRawPair.split(",");
                if(pair.length>2){
                    
                    correctPairSintax=false;
                }else if(pair[0].compareTo("")==0){
                    //case ()
                    correctPairSintax=false;
                }
                //check to see if there is already a pair 
                
                
                if(correctPairSintax){
                    //save pair
                    Edge p;
                    if(pair.length==1){
                        p=new Edge(pair[0],null);
                        addedPair.add(p);
                    }else{
                        p=new Edge(pair[0],pair[1]);
                        addedPair.add(p);
                    }
                    
                }else{
                    inputMessageLabel.setText("invalid input, wrong sintax");
                    inputMessageLabel.textFillProperty().set(Color.valueOf("#ff0000"));
                    System.err.println("invalid input, wrong sintax");
                }
                i++;
            }
            if(correctPairSintax){
                ObservableList<Edge> pairsAlreadyAdded = addedPairsListView.getItems();
                int j=0;
                while(j<addedPair.size()&&correctPairSintax){
                    Edge e=addedPair.get(j);
                    Edge eReversed=new Edge(e.getChild(), e.getFather());
                        //check for edges with the same node data es.(a,a)
                        if(e.equals(eReversed)) correctPairSintax=false;
                        int k=j+1;
                        //check for edges that are the same es. (a,b);(a,b)
                        while(k<addedPair.size()&&correctPairSintax){
                            if(e.equals(addedPair.get(k))||eReversed.equals(addedPair.get(k))) correctPairSintax=false;
                            k++;
                        }
                        int l=0;
                        while(l<pairsAlreadyAdded.size()&&correctPairSintax){
                            if(e.equals(pairsAlreadyAdded.get(l))||eReversed.equals(pairsAlreadyAdded.get(l))) correctPairSintax=false;
                            l++;
                        }
    //                    if(pairsAlreadyAdded.contains(e)||pairsAlreadyAdded.contains(eReversed)||addedPair.contains(eReversed)){
    //                        correctPairSintax=false;
    //                        break;
    //                        System.err.println("invalid input, wrong sintax");
    //                    }
                        j++;
                }

                if(correctPairSintax){
                    //add pairs to list of correct pairs
                    //set correct label
                    inputMessageLabel.setText("list of pairs added successfully!");
                    inputMessageLabel.textFillProperty().set(Color.valueOf("#2bd90d"));
                    //reset the input TextField
                    forestInputTextField.clear();

                    addedPairsListView.getItems().addAll(addedPair);
                    //***********


                }else{
                    //set error label
                    inputMessageLabel.setText("invalid input, pairs invalid or colliding with other pairs");
                    inputMessageLabel.textFillProperty().set(Color.valueOf("#ff0000"));
                    System.err.println("invalid input, pairs invalid or colliding with other pairs");
                }
            }
            
            
        
        }else{
            System.err.println("input pair textfield has no text");
            inputMessageLabel.setText("input pair textfield has no text");
            inputMessageLabel.textFillProperty().set(Color.valueOf("#ff0000"));
        }
        
     
    }

    @FXML
    private void handleRemovePairsButtonAction(ActionEvent event) {
         //get selected items
        //remove them from the itemlist
//        addedPairsListView.getSelectionModel().getSelectedItems();
        addedPairsListView.getItems().removeAll(addedPairsListView.getSelectionModel().getSelectedItems());
    }

    @FXML
    private void handleGenerateDownsetForestGraphButtonAction(ActionEvent event) {
         
        if(forest!=null){
            //create downset
            DownsetGraphGenerator generator=new DownsetGraphGenerator(forest);
            try {
                generator.generateGraph();
//                GraphVizManager.createDownsetForestGraphDotFile(generator.getDownsetEdgeList());
                //visualize downset in another scene
                Parent root = FXMLLoader.load(getClass().getResource("/view/DownsetVisualizator.fxml"));
                Scene scene = new Scene(root);
                Stage stage= new Stage();
                stage.setScene(scene);
                stage.setTitle(ForestOperator.getVersion());
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(DownsetInputManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            forestGenerationMessagesLabel.setText("create a forest first");
            forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#ff0000"));
        }
      
    }

    @FXML
    private void handleResetButtonAction(ActionEvent event) {
        
        forest=null;
        forestInputTextField.clear();
        inputMessageLabel.setText("");
        addedPairsListView.getItems().clear();
        forestGenerationMessagesLabel.setText("");
        forestImageView.setImage(null);
    }

    @FXML
    private void handleAddPairFromFileButtonAction(ActionEvent event) throws FileNotFoundException, IOException {
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getFile();
        dialog.dispose(); 
        if(file!=null){
            System.out.println(file + " chosen.");
    //        File f=new File(file);

            BufferedReader br = new BufferedReader(new FileReader(file));
            forestInputTextField.setText(br.readLine());
            handleAddPairButtonAction(event);

        }else{
            System.out.println("no file chosen.");
        }
    
    }

    @FXML
    private void handleAddForestFromFileButtonAction(ActionEvent event) throws IOException {
        String programDir=System.getProperty("user.dir");
        File initialDir=new File(programDir);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(initialDir);
        fileChooser.setTitle("Scegli il File da aprire");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Dot files", "*.dot")
        );
        
        File selectedFile = fileChooser.showOpenDialog(addForestFromFileButton.getScene().getWindow());
        if(selectedFile==null){
            System.out.println("no file chosen.");
            return;
        }
        Set<Edge> edgeSet=GraphVizManager.convertDotFileToEdgeSet(selectedFile);
        generateForest(edgeSet);
    }

    private void generateForest(Set<Edge> pairSet) throws IOException {
        forest = new Forest(pairSet, true);
        System.out.println("forest created successfully");
        forestGenerationMessagesLabel.setText("forest created successfully");
        forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#2bd90d"));
        //use graphviz to create the image of the forest

        //generate forest image
        ForestGraphGenerator generator = new ForestGraphGenerator(forest.getEdgeList());
        generator.generateGraph();
        //display forest image
        File f = new File(ForestGraphGenerator.getPngFileName());
        Image image = new Image(f.toURI().toString());
        forestImageView.setImage(image);
        forestImageView.setPreserveRatio(true);

        generateDownsetForestGraphButton.setVisible(true);
    }
}
