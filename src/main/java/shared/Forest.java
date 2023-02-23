/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Paolo
 */
public class Forest {
    
    
    private List<Node> rootNodes;
    private List<Edge> edgeList;
    private int nodeCount;
    private Map<String, Node> forestNodes;
    
    public Forest(Set<Edge> edges,boolean checkForCycles){
        Set<String> possibleRoots=new HashSet<>();
        
        rootNodes=new ArrayList<>();
        forestNodes=new HashMap<>();
        
        for(Edge edge: edges){
            String father=edge.getFather();
            String child=edge.getChild();
            if(!forestNodes.containsKey(father)){
                possibleRoots.add(father);
            }
            boolean newFather;
            newFather = (forestNodes.putIfAbsent(father, new Node(father)))==null;
            if(child!=null){
                
                //if the child data is already in forestNodes, use its reference
                if(forestNodes.containsKey(child)){
                    Node childNode=forestNodes.get(child);
                    if(!newFather){
                        //both of the nodes aren't new
                        //check if the child node is not a parent of the father node(creating a cycle)
                        Node fatherNode=forestNodes.get(father);
                        if(childNode.isParent(fatherNode)){
                            throw new IllegalArgumentException("Input pair "+edge.toString()+" creates a cycle in the forest");
                        }
                    }
                    
                    
                    forestNodes.get(father).addChild(forestNodes.get(child));
                }else{
                    Node childNode=new Node(child);
                    forestNodes.put(child, childNode);
                    forestNodes.get(father).addChild(childNode);
                    
                }
                //check if the childnode already has a parent
                Node childNode=forestNodes.get(child);
                forestNodes.get(child).setParent(forestNodes.get(father));
                possibleRoots.remove(child);
            }
        }
//        System.out.println("shared.Forest.<init>()");
        //iterate each root and create the trees
        for(String rootData:possibleRoots){
            //create tree
            rootNodes.add(forestNodes.get(rootData));
            forestNodes.get(rootData).getChildren().sort((Node f1, Node f2) -> f1.getData().compareTo(f2.getData()));
            
        }
        
          
        if (rootNodes.isEmpty()) {
            throw new IllegalArgumentException("Input pairs contain a cycle with the root");
        }else if (checkForCycles){
           //check for cycles
           Map<Node,List<Node>> treeNodes=new HashMap<>();
           for(Node x:forestNodes.values()){
               treeNodes.put(x,x.getChildren());
           }
            if(checkChildCycles(treeNodes)){
                throw new IllegalArgumentException("Input pairs contain cycle with child nodes");
            } 
        }
         //populate the edgelist
            edgeList=new ArrayList<>(edges);
            
            nodeCount=forestNodes.size();
            
    }
    public Forest(List<Node> inputRootNodes){
        //from the root nodes, build the edgelist,forestNodes map
//        (a,b);(b,l);(a,c);(e);(f,g);(g,h);(h,i)
        this.rootNodes=new ArrayList<>();
//        for(Node root:inputRootNodes){
//            
//        }
//        this.rootNodes=inputRootNodes;
        edgeList=new ArrayList<>();
        forestNodes=new HashMap<>();
        List<Node> nodeList=new ArrayList<>();
        for(Node root:inputRootNodes){
//            Node treeRoot=new Node(root.getData());
//            rootNodes.add(treeRoot);
            if(root.getChildren().isEmpty()){
                Node treeRoot=new Node(root.getData());
                rootNodes.add(treeRoot);
                forestNodes.put(treeRoot.getData(), treeRoot);
                edgeList.add(new Edge(treeRoot.getData(), null));
            }else{
                nodeList.add(root);
            }
        }
//        nodeList.addAll(rootNodes);
//        (a,b);(b,c);(c,d)
//        (e,f);(f,g);(g,h)
        while(!nodeList.isEmpty()){
           Node node=nodeList.get(0);
           Node treeNode=new Node(node.getData());
           if(node.getParent()==null){
               rootNodes.add(treeNode);
           }else{
//               if(fatherNode==null){
//                   //the only case this happens is when bottomless is called, because the new rootnode has still the 
//               }
               Edge e=new Edge(node.getParent().getData(),treeNode.getData());
               edgeList.add(e);
               Node fatherNode=forestNodes.get(node.getParent().getData());
               fatherNode.addChild(treeNode);
               treeNode.setParent(fatherNode);
           }
           //add the node to forestNodes
           
           //add edges to the node's children
           List<Node> childrenList=node.getChildren();
           
//           for(Node child:childrenList){
//               //add the child to the respective child list
////               Node fatherNode=forestNodes.get(node.getData());
//               Node childNode=new Node(child.getData());
////               forestNodes.put(child.getData(), childNode);
////               treeNode.addChild(childNode);
//               //add children's edge
//               
//               
//           }
           //add node's children to the nodelist
           forestNodes.put(treeNode.getData(), treeNode);
           nodeList.addAll(childrenList);
           //remove node from the list
           nodeList.remove(0);
       }
        
        //sort the edgelist?
        
//        edgeList.sort((Edge o1, Edge o2) -> {
//            int res = o1.getFather().compareTo(o2.getFather());
//            if (res == 0){
//                if(o1.getChild()==null){
//                    return -1;
//                }else if(o2.getChild()==null){
//                    return 1;
//                }
//                return o1.getChild().compareTo(o2.getChild());
//            }else{
//                return res;
//            }
//        });
       
       nodeCount=forestNodes.size();
    }
    
    public Forest(Node root){
        //from the root nodes, build the edgelist,forestNodes map
//        (a,b);(b,l);(a,c);(e);(f,g);(g,h);(h,i)
        
//        treeRoot.getChildren().addAll(root.getChildren());
        this.rootNodes=new ArrayList<>();
//        this.rootNodes.add(treeRoot);
        edgeList=new ArrayList<>();
        forestNodes=new HashMap<>();
        List<Node> nodeList=new ArrayList<>();
        if(root.getChildren().isEmpty()){
            Node treeRoot=new Node(root.getData());
            rootNodes.add(treeRoot);
            forestNodes.put(treeRoot.getData(), treeRoot);
            edgeList.add(new Edge(treeRoot.getData(), null));
        }else{
           nodeList.add(root);
        }
//        for(Node node:rootNodes){
//            
//            if(node.getChildren().isEmpty()){
//                Node treeNode=new Node(node.getData());
//                forestNodes.put(treeNode.getData(), treeNode);
//                edgeList.add(new Edge(treeNode.getData(), null));
//            }else{
//                nodeList.add(node);
//            }
//            
//        }
//        nodeList.addAll(rootNodes);
        while(!nodeList.isEmpty()){
           Node node=nodeList.get(0);
           Node treeNode=new Node(node.getData());
           if(node.getParent()==null){
               rootNodes.add(treeNode);
           }else{
               Edge e=new Edge(node.getParent().getData(),treeNode.getData());
               edgeList.add(e);
               Node fatherNode=forestNodes.get(node.getParent().getData());
               fatherNode.addChild(treeNode);
               treeNode.setParent(fatherNode);
           }
           //add the node to forestNodes
           
           //add edges to the node's children
           List<Node> childrenList=node.getChildren();
           
//           for(Node child:childrenList){
//               //add the child to the respective child list
////               Node fatherNode=forestNodes.get(node.getData());
//               Node childNode=new Node(child.getData());
////               forestNodes.put(child.getData(), childNode);
////               treeNode.addChild(childNode);
//               //add children's edge
//               
//               
//           }
           //add node's children to the nodelist
           forestNodes.put(treeNode.getData(), treeNode);
           nodeList.addAll(childrenList);
           //remove node from the list
           nodeList.remove(0);
       }
//        while(!nodeList.isEmpty()){
//           Node node=nodeList.get(0);
//           Node treeNode=new Node(node.getData());
//           //add the node to forestNodes
//           if(node.getParent()==null){
//               rootNodes.add(treeNode);
//           }else{
//               Node fatherNode=forestNodes.get(node.getParent().getData());
//               treeNode.setParent(fatherNode);
//           }
//           
//           //add edges to the node's children
//           List<Node> childrenList=node.getChildren();
//           
//           for(Node child:childrenList){
//               //add the child to the respective child list
////               Node fatherNode=forestNodes.get(node.getData());
//               Node childNode=new Node(child.getData());
//               forestNodes.put(child.getData(), childNode);
//               treeNode.addChild(childNode);
//               
//               Edge e=new Edge(treeNode.getData(),child.getData());
//               edgeList.add(e);
//           }
//           forestNodes.putIfAbsent(treeNode.getData(), treeNode);
//           //add node's children to the nodelist
//           nodeList.addAll(node.getChildren());
//           //remove node from the list
//           nodeList.remove(0);
//       }
        
        //sort the edgelist?
        
//        edgeList.sort((Edge o1, Edge o2) -> {
//            int res = o1.getFather().compareTo(o2.getFather());
//            if (res == 0){
//                if(o1.getChild()==null){
//                    return -1;
//                }else if(o2.getChild()==null){
//                    return 1;
//                }
//                return o1.getChild().compareTo(o2.getChild());
//            }else{
//                return res;
//            }
//        });
       
       nodeCount=forestNodes.size();
    }
    public Forest(){
        //used to create an empty forest
        this.rootNodes=new ArrayList<>();
        this.edgeList=new ArrayList<>();
        this.forestNodes=new HashMap<>();
        this.nodeCount=0;
    }
    
    public static boolean checkChildCycles(Map<Node, List<Node>> treeNodes){
       
        //for each node in the children  list
        
        
        
        for(List<Node> childrenIterator: treeNodes.values()){
           
           for(Node x : childrenIterator){
               // check against each value list to find occurrences
                int childOccurance = 0;
                for(List<Node> checkSets: treeNodes.values()){
                    if(checkSets.contains(x)){
                        childOccurance++;
                        if(childOccurance > 1){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public void removeRootLevel(){
        List<Node> newRootList=new ArrayList<>();
        for(Node oldRoot:rootNodes){
            newRootList.addAll(oldRoot.getChildren());

        }
        System.out.println("new rootlist created");
    }
    
    
    
    //getter and setter
    public List<Edge> getEdgeList(){
        return edgeList;
    }
    public List<Node> getRootList(){
        return rootNodes;
    }
    public void setRootList(List<Node> rootNodes){
        this.rootNodes=rootNodes;
    }
    public int getNodeCount(){
        return nodeCount;
    }
    public void setNodeCount(int count){
        this.nodeCount=count;
    }
    public Map<String, Node> getForestNodesMap(){
        return forestNodes;
    }
    
    
}
