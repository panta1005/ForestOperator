/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphGenerator;

import static GraphGenerator.GraphGenerator.DOT_FILE_NAME;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import shared.Edge;

/**
 *
 * @author Paolo
 */
public class CustomNameForestGraphGenerator extends GraphGenerator{
      private final List<Edge> edges;
      private boolean withSeparator=false;
      private boolean withDelimiter=false;
      private String OutputFileType;
    public CustomNameForestGraphGenerator(String name,List<Edge> edgeList,String outputFileType) {
        this.edges=new ArrayList(edgeList);
        GraphGenerator.PNG_FILE_NAME = name+"graph."+outputFileType;
        GraphGenerator.DOT_FILE_NAME = name+"graph.dot";
        this.OutputFileType=outputFileType;
    }
    public CustomNameForestGraphGenerator(String name,List<Edge> edgeList, boolean withSeparator,boolean withDelimiter, String outputFileType) {
        this.edges=new ArrayList(edgeList);
        this.withSeparator=withSeparator;
        this.withDelimiter=withDelimiter;
//        GraphGenerator.PNG_FILE_NAME = name+"graph.png";
        GraphGenerator.PNG_FILE_NAME = name+"graph."+outputFileType;
        GraphGenerator.DOT_FILE_NAME = name+"graph.dot";
        this.OutputFileType=outputFileType;
    }
    @Override
    public void generateGraph() throws IOException {
//       GraphVizManager.createForestGraphDotFile(edges);
       if(withSeparator){
           GraphVizManager.createCustomNameForestGraphWithSeparatorDotFile(DOT_FILE_NAME, edges);
       }else if(withDelimiter){
        
           GraphVizManager.createCustomNameForestGraphWithDelimiterDotFile(DOT_FILE_NAME, edges);
       }else{
           GraphVizManager.createCustomNameForestGraphDotFile(DOT_FILE_NAME, edges);
       }
       
       GraphVizManager.launchDot(DOT_FILE_NAME, PNG_FILE_NAME,OutputFileType);
    }
    
 
}
