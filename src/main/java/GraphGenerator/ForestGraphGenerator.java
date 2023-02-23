/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import shared.Edge;

/**
 *
 * @author Paolo
 */
public class ForestGraphGenerator extends GraphGenerator{

    private final List<Edge> edges;
    
    
    public ForestGraphGenerator(List<Edge> edgeList) {
        this.edges=new ArrayList(edgeList);
        GraphGenerator.PNG_FILE_NAME = "forestgraph.png";
        GraphGenerator.DOT_FILE_NAME = "forestgraph.dot";
    }
    @Override
    public void generateGraph() throws IOException {
       GraphVizManager.createForestGraphDotFile(edges);
       GraphVizManager.launchDot(DOT_FILE_NAME, PNG_FILE_NAME,"png");
    }
    
    
}
