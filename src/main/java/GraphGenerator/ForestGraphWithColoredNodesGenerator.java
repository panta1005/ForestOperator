/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GraphGenerator;

import static GraphGenerator.GraphGenerator.DOT_FILE_NAME;
import static GraphGenerator.GraphGenerator.PNG_FILE_NAME;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import shared.Edge;

/**
 *
 * @author Paolo
 */
public class ForestGraphWithColoredNodesGenerator extends GraphGenerator{
    private final List<Edge> edges;
      private boolean withSeparator=false;
      private boolean withDelimiter=false;
      private String OutputFileType;
      private List<String> nodesToBeColored;
    public ForestGraphWithColoredNodesGenerator(String name,List<Edge> edgeList,String outputFileType,List<String> nodesToBeColored) {
        this.edges=new ArrayList(edgeList);
        GraphGenerator.PNG_FILE_NAME = name+"graph."+outputFileType;
        GraphGenerator.DOT_FILE_NAME = name+"graph.dot";
        this.OutputFileType=outputFileType;
        this.nodesToBeColored=nodesToBeColored;
    }
    public ForestGraphWithColoredNodesGenerator(String name,List<Edge> edgeList, boolean withSeparator,boolean withDelimiter, String outputFileType,List<String> nodesToBeColored) {
        this.edges=new ArrayList(edgeList);
        this.withSeparator=withSeparator;
        this.withDelimiter=withDelimiter;
//        GraphGenerator.PNG_FILE_NAME = name+"graph.png";
        GraphGenerator.PNG_FILE_NAME = name+"graph."+outputFileType;
        GraphGenerator.DOT_FILE_NAME = name+"graph.dot";
        this.OutputFileType=outputFileType;
        this.nodesToBeColored=nodesToBeColored;
    }
    @Override
    public void generateGraph() throws IOException {
//       GraphVizManager.createForestGraphDotFile(edges);
/*
       if(withSeparator){
           GraphVizManager.createCustomNameForestGraphWithSeparatorDotFile(DOT_FILE_NAME, edges);
       }else if(withDelimiter){
           //case free
           GraphVizManager.createCustomNameForestGraphWithDelimiterDotFile(DOT_FILE_NAME, edges);
       }else{
           GraphVizManager.createCustomNameForestGraphDotFile(DOT_FILE_NAME, edges);
       }
       */
        GraphVizManager.createCustomNameForestGraphWithColoredNodesDotFile(DOT_FILE_NAME, edges,nodesToBeColored);
        GraphVizManager.launchDot(DOT_FILE_NAME, PNG_FILE_NAME,OutputFileType);
    }
    
}
