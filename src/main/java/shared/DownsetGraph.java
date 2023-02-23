/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paolo
 */
public class DownsetGraph {
    private List<Edge> downsetEdgeList;
    private List<ElementList> downsetList;
    public DownsetGraph(){
        downsetList=new ArrayList<>();
        downsetEdgeList=new ArrayList<>();
    }
    public DownsetGraph(List<ElementList> downsetList,List<Edge> downsetEdgeList){
        this.downsetList=downsetList;
        this.downsetEdgeList=downsetEdgeList;
    }

    /**
     * @return the downsetEdgeList
     */
    public List<Edge> getDownsetEdgeList() {
        return downsetEdgeList;
    }

    /**
     * @return the downsetList
     */
    public List<ElementList> getDownsetList() {
        return downsetList;
    }
    
    
}
