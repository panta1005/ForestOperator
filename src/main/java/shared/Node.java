/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paolo
 */
public class Node {
    private String data;
    private List<Node> children = new ArrayList<>();
    private Node parent;

    public Node(String data){
        this.data = data;
    }

    public void addChild(Node node){
        children.add(node);
    }

    public List<Node> getChildren(){
        return children;
    }

    public String getData(){
        return data;
    }
    public void setData(String data){
        this.data=data;
    }
    public Node getParent(){
        return parent;
    }
    public void setParent(Node parent){
        this.parent=parent;
    }
    public Boolean isParent(Node node){
       
        while(node.getParent()!=null){
            if(node.getParent().equals(this)){
                return true;
            }else{
                node=node.getParent();
            }
        }
        return false;
    }
}