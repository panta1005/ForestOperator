/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Paolo
 */
public class Tree {
    
    private Node rootNode;
    private List<Edge> edgeList;
    private int nodeCount;
    private Map<String, Node> treeNodes;
    
    
    
    
    public Tree(){
        
    }
    
    
    
    
    
    //getter and setter
    /**
     * @return the rootNode
     */
    public Node getRootNode() {
        return rootNode;
    }

    /**
     * @param rootNode the rootNode to set
     */
    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * @return the edgeList
     */
    public List<Edge> getEdgeList() {
        return edgeList;
    }

    /**
     * @param edgeList the edgeList to set
     */
    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    /**
     * @return the nodeCount
     */
    public int getNodeCount() {
        return nodeCount;
    }

    /**
     * @param nodeCount the nodeCount to set
     */
    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    /**
     * @return the forestNodes
     */
    public Map<String, Node> getTreeNodes() {
        return treeNodes;
    }

    
    
}
