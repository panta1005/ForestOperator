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
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import shared.Edge;
import shared.ElementList;
import shared.Forest;
import shared.ForestOperations;

/**
 * FXML Controller class
 *
 * @author Paolo
 */
public class MultiDownsetVisualizatorController implements Initializable {

    private String f_ImageName;
    private String g_ImageName;
    private String f_DotFileName,g_DotFileName;
    private Forest f,g;
    private List<Edge> f_downsetEdgeList,g_downsetEdgeList;
    private List<ElementList> f_downsetList,g_downsetList;
    @FXML
    private ImageView F_DownsetImageView;
    @FXML
    private ImageView G_DownsetImageView;
    @FXML
    private Button viewDownsetFDotFileButton;
    @FXML
    private Button viewDownsetGDotFileButton;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
//        File f = new File(f_ImageName);
//        Image image = new Image(f.toURI().toString());
//        F_DownsetImageView.setImage(image);
//        F_DownsetImageView.setPreserveRatio(true);
//        File f2 = new File(g_ImageName);
//        Image image2 = new Image(f2.toURI().toString());
//        G_DownsetImageView.setImage(image2);
//        G_DownsetImageView.setPreserveRatio(true);
    } 
    public void setFDotFileName(String name){
        this.f_DotFileName=name;
    }
    public void setGDotFileName(String name){
        this.g_DotFileName=name;
    }
    public void setFImage(String name){
        this.f_ImageName=name;
        File f = new File(f_ImageName);
        Image image = new Image(f.toURI().toString());
        F_DownsetImageView.setImage(image);
        F_DownsetImageView.setPreserveRatio(true);
    }
    public void setGImage(String name){
        this.g_ImageName=name;
        File f = new File(g_ImageName);
        Image image = new Image(f.toURI().toString());
        G_DownsetImageView.setImage(image);
        G_DownsetImageView.setPreserveRatio(true);
    }
    public void setFDownsetList(List<ElementList> downsetList){
        this.f_downsetList=downsetList;
    }
    public void setGDownsetList(List<ElementList> downsetList){
        this.g_downsetList=downsetList;
    }
     public void setFDownsetEdgeList(List<Edge> downsetEdgeList){
        this.f_downsetEdgeList=downsetEdgeList;
    }
    public void setGDownsetEdgeList(List<Edge> downsetEdgeList){
        this.g_downsetEdgeList=downsetEdgeList;
    }
    public void setForestF(Forest forest){
        this.f=forest;
    }
    public void setForestG(Forest forest){
        this.g=forest;
    }
    
    
    private void handleGenerateCartesianProductButtonAction(ActionEvent event) throws IOException {
        //make F+G and D(F)x D(G)(cartesian product)
        Forest result=ForestOperations.sum(f, g);
        CustomNameForestGraphGenerator forestGenerator=new CustomNameForestGraphGenerator("F+G", result.getEdgeList(),"png");
        forestGenerator.generateGraph();
        //generate cartesian product
        List<Edge> cartesianProductEdgeList=cartesianProduct(f_downsetList,g_downsetList);
        CustomNameForestGraphGenerator generator=new CustomNameForestGraphGenerator("CartesianProduct", cartesianProductEdgeList,true,false,"png");
        generator.generateGraph();
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/CoproductandCartesianProductVisualizator.fxml"));
            Parent root = loader.load();
            //get the UI controller and set the right flags
            Scene scene = new Scene(root);
            Stage stage= new Stage();
            stage.setScene(scene);
            stage.setTitle(ForestOperator.getVersion()+": Cartesian product");
            CoproductandCartesianProductVisualizatorController controller=loader.getController();
//            controller.setLabels(false);
////            System.out.println(forestGenerator.getPngFileName());
//            controller.setFirstImage("F+Ggraph.png");
//            controller.setSecondImage("CartesianProductgraph.png");
            stage.show();
    }

    private void handleGenerateCoproductButtonAction(ActionEvent event) throws IOException {
        //make FxG and downset(FxG)
        Forest result=ForestOperations.product(f, g);
        CustomNameForestGraphGenerator forestGenerator=new CustomNameForestGraphGenerator("FxG", result.getEdgeList(),"png");
        forestGenerator.generateGraph();
        DownsetGraphGenerator generator=new DownsetGraphGenerator("FxG", result);
        generator.generateGraph();
        List<Edge> downsetList=generator.getDownsetEdgeList();
        //pass them to the next scene
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/CoproductandCartesianProductVisualizator.fxml"));
            Parent root = loader.load();
            //get the UI controller and set the right flags
            Scene scene = new Scene(root);
            Stage stage= new Stage();
            stage.setScene(scene);
            stage.setTitle(ForestOperator.getVersion()+": Coproduct");
            CoproductandCartesianProductVisualizatorController controller=loader.getController();
//            controller.setLabels(true);
//            System.out.println(forestGenerator.getPngFileName());
//            controller.setFirstImage("FxGgraph.png");
//            controller.setSecondImage("FxG_downsetgraph.png");
            stage.show();
        
    }

    private List<Edge> cartesianProduct(List<ElementList> firstList, List<ElementList> secondList) {
        System.out.println("f downset edge list");
        List<ElementList> resultList=new ArrayList<>();
        List<Edge> resultEdgeList=new ArrayList<>();
        
//        System.out.println("g downset edge list");
//        for(Edge e:f_downsetEdgeList){
//            System.out.println(e.toString());
//        }
        for(ElementList element_f:firstList){
            List<String> f_Children=new ArrayList<>();
           //check if element_f has children, in case save them
           for(Edge e:f_downsetEdgeList){
               if(e.getFather().compareTo(element_f.toString())==0){
                   System.out.println("element {"+e.getChild()+"} is a child of "+element_f.toString());
                   f_Children.add("{"+e.getChild()+"}");
               }
           }
            for(ElementList element_g:secondList){
            //create cartesian Element=(element_f, element_g) and its edges
            ElementList cartesianElement=new ElementList();
            cartesianElement.getElements().add(element_f.printSet());
            cartesianElement.getElements().add(element_g.printSet());
            resultList.add(cartesianElement);
            //edges to be created:Cartesian element-->(child(f),element_g) DONE
//                                                -->(f,child(g)) DONE 
//                                                -->(child(f),(child(g)) DONE
//           for each child of f and each child of g
            //child(f),g
            for(String child:f_Children){
                ElementList childElement=new ElementList();
                childElement.getElements().add(child);
                childElement.getElements().add(element_g.printSet());
                Edge e=new Edge(cartesianElement.toString(),childElement.toString());
                resultEdgeList.add(e);
            }
             for(Edge e:g_downsetEdgeList){
//                 (f,child(g))
               if(e.getFather().compareTo(element_g.toString())==0){
//                   System.out.println("element {"+e.getChild()+"} is a child of "+element_g.toString());
                   ElementList childElement=new ElementList();
                   childElement.getElements().add(element_f.printSet());
                   childElement.getElements().add("{"+e.getChild()+"}");
                   Edge childEdge=new Edge(cartesianElement.toString(),childElement.toString());
                   resultEdgeList.add(childEdge);
//                (child(f),(child(g))
                  for(String child:f_Children){
                    ElementList bothChildElement=new ElementList();
                    bothChildElement.getElements().add(child);
                    bothChildElement.getElements().add("{"+e.getChild()+"}");
                    Edge bothChildEdge=new Edge(cartesianElement.toString(),bothChildElement.toString());
                    resultEdgeList.add(bothChildEdge);
                  }
               }
           }

            }
//            System.out.println("G cycle finished");    
            }
            
            
        
//        System.out.println("cartesian product edge list: ");
//        for(Edge e:resultEdgeList){
//            System.out.println(e.toString());
//        }
        return resultEdgeList;
    }
    
        @FXML
    private void handleViewDownsetFDotFileButtonAction(ActionEvent event) throws IOException {
//        open view dot file scene 
//        get and open dot file
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/DotFileTextViewer.fxml"));
        Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": D(F) dot File text");
        DotFileTextViewerController controller=loader.getController();
        controller.initializeDotTextArea(f_DotFileName);
        stage.show();
    }
    @FXML
    private void handleViewDownsetGDotFileButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/DotFileTextViewer.fxml"));
        Parent root = loader.load();
        //get the UI controller and set the right flags
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(ForestOperator.getVersion()+": D(G) dot File text");
        DotFileTextViewerController controller=loader.getController();
        controller.initializeDotTextArea(g_DotFileName);
        stage.show();
    }


    
}
