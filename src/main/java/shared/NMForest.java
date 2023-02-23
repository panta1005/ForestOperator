/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Paolo
 */
public class NMForest {
    private Map<Integer, List<Forest>> forestMap;
    
    public NMForest(Map<Integer,List<Forest>> forestMap){
        this.forestMap=forestMap;
    }
    public NMForest(Forest forest, String[] labels){
        //method to divide each tree and assign them to each label/part of the map?
        //for each root create a new forest that contains the subtree
        //put the tree in the list based on the labelList (is assumed that labelList.size==rootList.size)
        //labelList, obviously, is assumed to contain only 0,1 values (List<Integer> may be better?)
        forestMap=new HashMap<>();
        List<Forest> zeroLabelledForests=new ArrayList<>();
        List<Forest> oneLabelledForests=new ArrayList<>();
        int i=0;
        for(Node root:forest.getRootList()){
            Forest f=new Forest(root);
            switch(labels[i]){
                case "0":
                    zeroLabelledForests.add(f);
                break;
                case "1":
                    oneLabelledForests.add(f);
                break;
            }
            i++;
        }
        forestMap.put(0, zeroLabelledForests);
        forestMap.put(1, oneLabelledForests);
    }
    public Map<Integer,List<List<Edge>>> getEdgeListMap(){
        Map<Integer,List<List<Edge>>> edgeListMap=new HashMap<>();
        for(Map.Entry<Integer,List<Forest>> entry : getForestMap().entrySet()){
            int label=entry.getKey();
            List<List<Edge>> forestEdgeList=new ArrayList<>();
            List<Forest> forestList=entry.getValue();
            for(Forest f:forestList){
                forestEdgeList.add(f.getEdgeList());
            }
            edgeListMap.put(label, forestEdgeList);
        }
        return edgeListMap;
    }
    public int getNodeCount(){
        int count=0;
        for(Map.Entry<Integer,List<Forest>> entry : getForestMap().entrySet()){
            List<Forest> forestList=entry.getValue();
            for(Forest f:forestList){
                count=count+f.getNodeCount();
            }
            
        }
        return count;
    }

    /**
     * @return the forestMap
     */
    public Map<Integer, List<Forest>> getForestMap() {
        return forestMap;
    }
    
}
