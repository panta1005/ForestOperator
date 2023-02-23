/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GraphGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import shared.Edge;

/**
 *
 * @author Paolo
 */
public class ForestWithSubforestsGraphGenerator extends GraphGenerator{
    private final Map<Integer,List<List<Edge>>> edgesMap;
    private boolean withSeparator=false;
    private boolean withDelimiter=false;
    private String OutputFileType;
    public ForestWithSubforestsGraphGenerator(String name,Map<Integer,List<List<Edge>>> edgesMap, boolean withSeparator,boolean withDelimiter, String outputFileType) {
        this.edgesMap=new HashMap<>(edgesMap);
        this.withSeparator=withSeparator;
        this.withDelimiter=withDelimiter;
//        GraphGenerator.PNG_FILE_NAME = name+"graph.png";
        GraphGenerator.PNG_FILE_NAME = name+"graph."+outputFileType;
        GraphGenerator.DOT_FILE_NAME = name+"graph.dot";
        this. OutputFileType=outputFileType;
    }
    @Override
    public void generateGraph() throws IOException {
        /*
        if(withSeparator){
           GraphVizManager.createCustomNameForestGraphWithSeparatorDotFile(DOT_FILE_NAME, edges);
       }else if(withDelimiter){
        
           GraphVizManager.createCustomNameForestGraphWithDelimiterDotFile(DOT_FILE_NAME, edges);
       }else{
           GraphVizManager.createCustomNameForestGraphDotFile(DOT_FILE_NAME, edges);
       }
       
       GraphVizManager.launchDot(DOT_FILE_NAME, PNG_FILE_NAME,OutputFileType);
    */
        GraphVizManager.createCustomNameForestGraphWithSubforestsDotFile(DOT_FILE_NAME, edgesMap);
        GraphVizManager.launchDot(DOT_FILE_NAME, PNG_FILE_NAME,OutputFileType);
    }
    
}
