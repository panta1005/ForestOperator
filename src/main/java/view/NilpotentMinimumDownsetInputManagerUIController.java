/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import GraphGenerator.CustomNameForestGraphGenerator;
import GraphGenerator.DownsetGraphGenerator;
import GraphGenerator.ForestGraphGenerator;
import GraphGenerator.ForestWithSubforestsGraphGenerator;
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
import java.util.Map;
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

public class NilpotentMinimumDownsetInputManagerUIController implements Initializable {

    private Forest inputForest;
    private NMForest nmForest;
    String baseForestName = "NMbaseforest";
    String nmForestName = "NMForest";
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
    private Label NMLabel;
    @FXML
    private TextField NMLabelTextField;
    @FXML
    private Button generateNMForestGraphButton;
    @FXML
    private Label rootsNumberLabel;
    @FXML
    private Button addBaseForestFromFileButton;

    public NilpotentMinimumDownsetInputManagerUIController() {

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

        ObservableList<Edge> pairList = addedPairsListView.getItems();
        pairList.sort((Edge e1, Edge e2) -> e1.toString().compareTo(e2.toString()));
        Set<Edge> pairSet = new LinkedHashSet<>(pairList);

        // pairSet.addAll(pairList);
        if (!pairSet.isEmpty()) {
            System.out.println("there are pairs");
            try {
                inputForest = new Forest(pairSet, true);
                System.out.println("forest created successfully");
                forestGenerationMessagesLabel.setText("forest created successfully");
                forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#2bd90d"));
                //use graphviz to create the image of the forest

                //generate forest image
                //ForestGraphGenerator generator=new ForestGraphGenerator(forest.getEdgeList());
                String forestName = "NMbaseforest";
                CustomNameForestGraphGenerator generator = new CustomNameForestGraphGenerator(forestName, inputForest.getEdgeList(), "png");
                generator.generateGraph();
                //display forest image
                setBaseForestImage();
                

            } catch (Exception e) {
                forestGenerationMessagesLabel.setText(e.getMessage());
                forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            }

        } else {
            forestGenerationMessagesLabel.setText("no input pairs found");
            forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#ff0000"));
        }
        //input pair generation
        //generate forest
        //check for loops

    }

    @FXML
    private void handleAddPairButtonAction(ActionEvent event) {
        if (forestInputTextField.getText() != null && !forestInputTextField.getText().isEmpty()) {
            String inputPair = forestInputTextField.getText();
            ObservableList<Edge> addedPair = FXCollections.<Edge>observableArrayList();
            System.out.println(inputPair);
            //remove spaces
            inputPair = inputPair.trim();
            inputPair = inputPair.replaceAll("\\s", "");
            //separate pairs
            String[] rawPairs = inputPair.split(Edge.getSeparator());
            int i = 0;
            boolean correctPairSintax = true;
            //check for correct sintax of the pairs es.(x,y);(z)
            while (i < rawPairs.length && correctPairSintax) {
                //remove parenthesis
                String singleRawPair = rawPairs[i];
                if (singleRawPair.length() > 0) {
                    if (singleRawPair.charAt(0) == '(') {

                        singleRawPair = singleRawPair.substring(1);
                    } else {
                        correctPairSintax = false;
                    }
                    if (singleRawPair.charAt(singleRawPair.length() - 1) == ')') {
                        singleRawPair = singleRawPair.substring(0, singleRawPair.length() - 1);
                    } else {
                        correctPairSintax = false;
                    }

                } else {
                    correctPairSintax = false;
                }

                System.out.println(singleRawPair);
                //extract pair
                String[] pair = singleRawPair.split(",");
                if (pair.length > 2) {

                    correctPairSintax = false;
                } else if (pair[0].compareTo("") == 0) {
                    //case ()
                    correctPairSintax = false;
                }
                //check to see if there is already a pair 

                if (correctPairSintax) {
                    //save pair
                    Edge p;
                    if (pair.length == 1) {
                        p = new Edge(pair[0], null);
                        addedPair.add(p);
                    } else {
                        p = new Edge(pair[0], pair[1]);
                        addedPair.add(p);
                    }

                } else {
                    inputMessageLabel.setText("invalid input, wrong sintax");
                    inputMessageLabel.textFillProperty().set(Color.valueOf("#ff0000"));
                    System.err.println("invalid input, wrong sintax");
                }
                i++;
            }
            if (correctPairSintax) {
                ObservableList<Edge> pairsAlreadyAdded = addedPairsListView.getItems();
                int j = 0;
                while (j < addedPair.size() && correctPairSintax) {
                    Edge e = addedPair.get(j);
                    Edge eReversed = new Edge(e.getChild(), e.getFather());
                    //check for edges with the same node data es.(a,a)
                    if (e.equals(eReversed)) {
                        correctPairSintax = false;
                    }
                    int k = j + 1;
                    //check for edges that are the same es. (a,b);(a,b)
                    while (k < addedPair.size() && correctPairSintax) {
                        if (e.equals(addedPair.get(k)) || eReversed.equals(addedPair.get(k))) {
                            correctPairSintax = false;
                        }
                        k++;
                    }
                    int l = 0;
                    while (l < pairsAlreadyAdded.size() && correctPairSintax) {
                        if (e.equals(pairsAlreadyAdded.get(l)) || eReversed.equals(pairsAlreadyAdded.get(l))) {
                            correctPairSintax = false;
                        }
                        l++;
                    }
                    //                    if(pairsAlreadyAdded.contains(e)||pairsAlreadyAdded.contains(eReversed)||addedPair.contains(eReversed)){
                    //                        correctPairSintax=false;
                    //                        break;
                    //                        System.err.println("invalid input, wrong sintax");
                    //                    }
                    j++;
                }

                if (correctPairSintax) {
                    //add pairs to list of correct pairs
                    //set correct label
                    inputMessageLabel.setText("list of pairs added successfully!");
                    inputMessageLabel.textFillProperty().set(Color.valueOf("#2bd90d"));
                    //reset the input TextField
                    forestInputTextField.clear();

                    addedPairsListView.getItems().addAll(addedPair);
                    //***********

                } else {
                    //set error label
                    inputMessageLabel.setText("invalid input, pairs invalid or colliding with other pairs");
                    inputMessageLabel.textFillProperty().set(Color.valueOf("#ff0000"));
                    System.err.println("invalid input, pairs invalid or colliding with other pairs");
                }
            }

        } else {
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
    private void handleGenerateDownsetForestGraphButtonAction(ActionEvent event) throws IOException {
        
        if (nmForest == null) {
            forestGenerationMessagesLabel.setText("create a NMforest first");
            forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            return;
        }

        //create downset
        List<DownsetGraph> downsetList=ForestOperations.generateDownsetGraph(nmForest);
        //create downset image pre cartesian product
        List<Edge> downsetEdgeListPreCP=new ArrayList<>();
        List<ElementList> downsetListPreCP=new ArrayList<>();
        DownsetGraph downsetGraphPostCP = null;
        DownsetGraph downsetGraphPreCP;
        for(DownsetGraph g:downsetList){
            if(downsetGraphPostCP==null){
                downsetGraphPostCP=g;
            }else{
                downsetGraphPostCP=ForestOperations.cartesianProduct(downsetGraphPostCP, g);
            }
            downsetEdgeListPreCP.addAll(g.getDownsetEdgeList());
            downsetListPreCP.addAll(g.getDownsetList());
        }
        //test
        /*
        downsetGraphPostCP=ForestOperations.cartesianProduct(downsetList.get(0), downsetList.get(1));
        */
        //create downset image pre cartesian product
        downsetGraphPreCP=new DownsetGraph(downsetListPreCP, downsetEdgeListPreCP);
        CustomNameForestGraphGenerator generator=new CustomNameForestGraphGenerator(nmForestName+"_downsetpreCP", downsetGraphPreCP.getDownsetEdgeList(), true, false, "svg");
        generator.generateGraph();
        openResultVisualizer(nmForestName+"_downsetpreCPgraph", "downset operation pre cartesian product",downsetGraphPreCP.getDownsetList().size());
        System.out.println("Elements in pre CP downset graph");
        for(ElementList e :downsetListPreCP){
            System.out.println(e.printSet()+"");
        }
        //create downset image post cartesian product
        CustomNameForestGraphGenerator generatorPostCP=new CustomNameForestGraphGenerator(nmForestName+"_downsetpostCP", downsetGraphPostCP.getDownsetEdgeList(), true, false, "svg");
        generatorPostCP.generateGraph();
        openResultVisualizer(nmForestName+"_downsetpostCPgraph", "downset operation post cartesian product",downsetGraphPostCP.getDownsetList().size());
        /*
        CustomNameForestGraphGenerator generator=new CustomNameForestGraphGenerator(nmForestName+"_downset", downset.getDownsetEdgeList(), true, false, "svg");
        generator.generateGraph();
        openResultVisualizer(nmForestName+"_downsetgraph", "downset operation pre cartesian product",downset.getDownsetList().size());
        */
        System.out.println("finished");
        /*
        DownsetGraphGenerator generator = new DownsetGraphGenerator(forest);
        try {
            generator.generateGraph();d
//                GraphVizManager.createDownsetForestGraphDotFile(generator.getDownsetEdgeList());
            //visualize downset in another scene
            Parent root = FXMLLoader.load(getClass().getResource("/view/DownsetVisualizator.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(ForestOperator.getVersion());
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DownsetInputManagerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
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
    @FXML
    private void handleResetButtonAction(ActionEvent event) {

        inputForest = null;
        forestInputTextField.clear();
        inputMessageLabel.setText("");
        addedPairsListView.getItems().clear();
        forestGenerationMessagesLabel.setText("");
        forestImageView.setImage(null);
        nmForest = null;
        //set visible false for generate downset button, generate nmforest button, the two labels and the NMlabel textfield
        rootsNumberLabel.setVisible(false);
        generateNMForestGraphButton.setVisible(false);
        NMLabel.setVisible(false);
        NMLabelTextField.setVisible(false);
        generateDownsetForestGraphButton.setVisible(false);
    }

    @FXML
    private void handleAddPairFromFileButtonAction(ActionEvent event) throws FileNotFoundException, IOException {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getFile();
        dialog.dispose();
        if (file != null) {
            System.out.println(file + " chosen.");
            //        File f=new File(file);

            BufferedReader br = new BufferedReader(new FileReader(file));
            forestInputTextField.setText(br.readLine());
            handleAddPairButtonAction(event);

        } else {
            System.out.println("no file chosen.");
        }

    }

    @FXML
    private void handleGenerateNMForestGraphButtonAction(ActionEvent event) throws IOException {
        //generate NMForest
        String nmLabelsText = NMLabelTextField.getText();
        nmLabelsText = nmLabelsText.trim();
        String[] nmLabels = nmLabelsText.split(",");
        //check roots number==number of labels  
        if (nmLabels.length != inputForest.getRootList().size()) {
            forestGenerationMessagesLabel.setText("the number of labels don't match the number of roots");
            forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            return;
        }
        //check for correct label elements
        for (String label : nmLabels) {
            label = label.trim();
            if (label.compareTo("0") != 0 && label.compareTo("1") != 0) {
                forestGenerationMessagesLabel.setText("one or more of the labels inserted are not valid, please check and try again");
                forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#ff0000"));
                return;
            }
        }

        nmForest = new NMForest(inputForest, nmLabels);
        
        ForestWithSubforestsGraphGenerator forestGenerator = new ForestWithSubforestsGraphGenerator(nmForestName, nmForest.getEdgeListMap(), false, true, "png");
        forestGenerator.generateGraph();
        //visualize NMForest
        //display forest image

        File f = new File(nmForestName + "graph.png");
        Image image = new Image(f.toURI().toString());
        forestImageView.setImage(image);

        //set generate downset button visible 
        generateDownsetForestGraphButton.setVisible(true);
    }

    @FXML
    private void handleAddBaseForestFromFileButtonAction(ActionEvent event) throws IOException {
        //get directory
        String programDir = System.getProperty("user.dir");
        File initialDir = new File(programDir);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(initialDir);
        fileChooser.setTitle("Scegli il File da aprire");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Dot files", "*.dot")
        );

        File selectedFile = fileChooser.showOpenDialog(addBaseForestFromFileButton.getScene().getWindow());
        if (selectedFile == null) {
            System.out.println("no file chosen.");
            return;
        }
        Set<Edge> edgeSet = GraphVizManager.convertDotFileToEdgeSet(selectedFile);
        generateForest(edgeSet);
    }

    private void generateForest(Set edgeSet) throws IOException {
        inputForest = new Forest(edgeSet, true);
        System.out.println("forest created successfully");
        forestGenerationMessagesLabel.setText("forest created successfully");
        forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#2bd90d"));
        //use graphviz to create the image of the forest

        //generate forest image
        CustomNameForestGraphGenerator generator = new CustomNameForestGraphGenerator(baseForestName, inputForest.getEdgeList(), "png");
        generator.generateGraph();
        //display forest image
        setBaseForestImage();
        //pass the forest to the caller
        /*
        if (bottomController != null) {
            //case bottom operations
            bottomController.setInputForest(forest);
        } else if (multiInputForestOperationsController != null) {
            //case multi input operation (sum, product, sum bottom)
            if (forestName.compareTo(MultiInputForestOperationsUIController.INPUTFOREST_NAME_F) == 0) {
                multiInputForestOperationsController.setInputForestF(forest);
            } else if (forestName.compareTo(MultiInputForestOperationsUIController.INPUTFOREST_NAME_G) == 0) {
                multiInputForestOperationsController.setInputForestG(forest);
            } else {
                forestGenerationMessagesLabel.setText("something went wrong with the forest name");
                forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            }
        } else if (multiInputOperationsController != null) {
            if (forestName.compareTo(MultiInputOperationsUIController.INPUTFOREST_NAME_F) == 0) {
                multiInputOperationsController.setInputForestF(forest);
            } else if (forestName.compareTo(MultiInputOperationsUIController.INPUTFOREST_NAME_G) == 0) {
                multiInputOperationsController.setInputForestG(forest);
            } else {
                forestGenerationMessagesLabel.setText("something went wrong with the forest name");
                forestGenerationMessagesLabel.textFillProperty().set(Color.valueOf("#ff0000"));
            }
        }
*/
    }
    private void setBaseForestImage(){
        File f = new File(baseForestName + "graph.png");
        Image image = new Image(f.toURI().toString());
        forestImageView.setImage(image);
        forestImageView.setPreserveRatio(true);
        rootsNumberLabel.setText("Number of roots in forest: " + Integer.toString(inputForest.getRootList().size()));
        rootsNumberLabel.setVisible(true);
        generateNMForestGraphButton.setVisible(true);
        NMLabel.setVisible(true);
        NMLabelTextField.setVisible(true);
    }

}
