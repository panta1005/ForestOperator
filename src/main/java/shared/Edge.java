/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.util.Set;

/**
 *
 * @author Paolo
 */
public class Edge {
    private static String SEPARATOR=";";
    private String father;
    private String child;
    
    public Edge(String father,String child){
        this.father=father;
        this.child=child;
    }
    public static String getSeparator(){
        return SEPARATOR;
    }
    public String toString(){
        if(child==null){
             return "("+father+")";
        }else{
             return "("+father+","+child+")";
        }
       
    }
    public String getFather(){
        return father;
    }
    public String getChild(){
        return child;
    }
    public void setFather(String father){
        this.father=father;
    }
    public void setChild(String child){
        this.child=child;
    }
    public Edge getReverse(){
        return new Edge(child,father);
    }
    @Override
    public boolean equals(Object o) {
        if(o==null){
            return false;
        }else{
            //Edge reverse=new Edge(this.getChild(), this.getFather());
            return this.toString().compareTo(o.toString())==0;
            //return this.toString().compareTo(o.toString())==0||reverse.toString().compareTo(o.toString())==0;
        }
        
     }
    public static boolean containsEdge(Set<Edge> edgeList, Edge edge) {
        return edgeList.stream().anyMatch((e) -> (e.equals(edge)));
    }
}
