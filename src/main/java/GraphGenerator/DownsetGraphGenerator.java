/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphGenerator;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import shared.DownsetGraph;
import shared.Edge;
import shared.ElementList;
import shared.Forest;
import shared.ForestOperations;
import shared.Node;

/**
 *
 * @author Paolo
 */

public class DownsetGraphGenerator extends GraphGenerator{
    Forest inputForest;
   
    DownsetGraph downsetGraph;
    
    public DownsetGraphGenerator(Forest f) {
        this.inputForest=f;
        
        GraphGenerator.PNG_FILE_NAME = "downsetgraph.png";
        GraphGenerator.DOT_FILE_NAME = "downsetgraph.dot";
    }
    public DownsetGraphGenerator(String name,Forest f) {
        this.inputForest=f;
        
        GraphGenerator.PNG_FILE_NAME = name+"_downsetgraph.png";
        GraphGenerator.DOT_FILE_NAME = name+"_downsetgraph.dot";
    }
    
    @Override
    public void generateGraph() throws IOException {
        //TO DO creazione classe setelementi per la gestione dei downset
        //TO DO impostazione di questo metodo
        //create downsetList
        //step 0 add {} to the downsetList
//        Set<ElementList> previousDownset=new LinkedHashSet<>();
/*
        List<ElementList> previousDownset=new ArrayList<>();
        
        List<Node> nodesToBeParsed=new ArrayList<>();
        downsetList.add(new ElementList());
        Map<String,ElementList> nodesDownsetMap=new HashMap<>();
        
        //step 0.1 add roots to downset, initialize nodestobeparsed 
        for(Node root: inputForest.getRootList()){
            //add downset to the downset map

            //add downset to downset list
            ElementList e=new ElementList();
            e.getElements().add(root.getData());
            downsetList.add(e);
            nodesDownsetMap.put(root.getData(),e);
            //add ({},root) to downset edge list 
            downsetEdgeList.add(new Edge(downsetList.get(0).toString(),e.toString()));
            //add root to  nodesotbeparsed
            nodesToBeParsed.addAll(root.getChildren());
            //initialize previousDownset
            previousDownset.add(e);
            
        }
        System.out.println("step 0 done");
        //variable that tracks how many elements have to be in common to merge the sets
       int elementsInCommonNumber=0;
       int forestNodeCount=inputForest.getNodeCount();
       
       
       boolean allDownsetCreated=false;
       //while the last downset created has lesser elements than the nodes of the forest
       while(elementsInCommonNumber<forestNodeCount-1){
           Set<ElementList> currentDownset=new LinkedHashSet<>();
           Set<Edge> currentEdgeList=new LinkedHashSet<>();
           //check if there are any node that are not yet being visited and add them
           //handle nodes that have to be parsed
           if(!nodesToBeParsed.isEmpty()){
               List<Node> newNodesToBeParsed=new ArrayList<>();
               while(nodesToBeParsed.size()>0){
                   //create downset
                    Node node=nodesToBeParsed.get(0);
                    ElementList downset=new ElementList();
                    //get the parent's downset 
                    ElementList parentDownset=nodesDownsetMap.get(node.getParent().getData());
                    downset.getElements().addAll(parentDownset.getElements());
//                    downset.addAll(pathToRoot);
                    downset.getElements().add(node.getData());
                     //add downset to the downset map
                    nodesDownsetMap.put(node.getData(),downset);
                   //add downset to downset list and current downset list
//                   downsetList.add(downset);
                   currentDownset.add(downset);
                   //add Edge(parent,current node) to downset edge list
//                   currentEdgeList.add(new Edge(parentDownset.toString(),downset.toString()));
                   downsetEdgeList.add(new Edge(parentDownset.toString(),downset.toString()));
                    //add node's children to newnodesotbeparsed
                    newNodesToBeParsed.addAll(node.getChildren());
                    nodesToBeParsed.remove(0);
                    
               }
               if(!newNodesToBeParsed.isEmpty()){
//                   update list of nodes to be parsed(with children of the nodes that are being visited this time)
                   nodesToBeParsed.addAll(newNodesToBeParsed);
                   
               }else{
                    System.out.println("children step done");
               }   
           }
         //check on the previousDownset list if there are sets that have elements-1 elements in common
        //if so, merge them and add them to the currentdownset
       //then add the currentdownset on the downsetlist
       //if currentdownset has a set containing inputforest.nodeCount elements-->end
       //else update previousdownset(with currentDownset)
        //before adding the current downset to the downset list, sort them
        
            for(int i=0;i<previousDownset.size();i++){
                ElementList elementList=previousDownset.get(i);
                for(int j=i+1;j<previousDownset.size();j++){
//                    if(elementsInCommonNumber==4){
//                        System.out.println("prova");
//                    }
                    ElementList secondElementList=previousDownset.get(j);
                    if(elementList.compareLists(secondElementList)==elementsInCommonNumber){
                        ElementList downset=elementList.mergeLists(secondElementList);  
//                        String elementListString=elementList.toString();
//                        String secondElementListString=secondElementList.toString();
//                        String downsetString=downset.toString();
                        //add Edge(first list merged,new merged element) and (second list merged,new merged element) to downset edge list
                        Edge edge1=new Edge(elementList.toString(),downset.toString());
                        if(!containsEdge(currentEdgeList, edge1)){
                            currentEdgeList.add(edge1);
                        }
                        Edge edge2=new Edge(secondElementList.toString(),downset.toString());
                        if(!containsEdge(currentEdgeList, edge2)){
                             currentEdgeList.add(edge2);
                        }
                        
                        
                       
//                        downsetEdgeList.add(edge1);
//                        downsetEdgeList.add(edge2);
                        //add new merged downset to element list
                        if(!containsElementList(currentDownset, downset)){
                            currentDownset.add(downset);
                        }
                        if(currentDownset.contains(downset)){
                            System.out.println("GraphGenerator.DownsetGraphGenerator.generateGraph()");
                            System.out.println("current downset size: "+currentDownset.size());
//                            System.out.println("");
                            
                        }
                        
                        
                    }
                }
            }
            elementsInCommonNumber++;

            System.out.println("merging step done");
            //add all edges in currentEdgeList to the downsetEdgeList
            downsetEdgeList.addAll(currentEdgeList);
            //reset the currentEdgeList
            currentEdgeList=new LinkedHashSet<>();
//            update previousdownset(with currentDownset)
            previousDownset=new ArrayList<>(currentDownset);
            previousDownset.sort((ElementList e1, ElementList e2) -> e1.toString().compareTo(e2.toString()));
            downsetList.addAll(previousDownset);
            
            
        
            
        }
*/
//        System.out.println("downset list created");
//        //print downset
//        System.out.println("downset list:");
//        for(ElementList e:downsetList){
//            System.out.println(e.toString());
//        }
//        //print downset list
//         System.out.println("downset edge list:");
//        for(Edge e:downsetEdgeList){
//            System.out.println(e.toString());
//        }
       //above there is the downset creation 
       downsetGraph=ForestOperations.generateDownsetGraph(inputForest,true);
       GraphVizManager.createDownsetForestGraphDotFile(DOT_FILE_NAME,downsetGraph.getDownsetEdgeList());
       GraphVizManager.launchDot(DOT_FILE_NAME, PNG_FILE_NAME,"png");
    }

    
    public List<Edge> getDownsetEdgeList(){
        return downsetGraph.getDownsetEdgeList();
    }
    public List<ElementList> getDownsetList(){
        return downsetGraph.getDownsetList();
    }
}

