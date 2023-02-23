/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Paolo
 */
public class ForestOperations {
    public static char[] ALPHABET= "xyzabcdefghijklmnopqrstuw".toUpperCase().toCharArray();
    public static Forest bottom(Forest f) {

        String rootData = getValidData("r", f.getForestNodesMap());
        Node root = new Node(rootData);
        //case nodecount=0
        if (f.getNodeCount() == 0) {
            //add new node to forest
            f.getForestNodesMap().put(rootData, root);
            //set node as root
            f.getRootList().add(root);
            //add new edge
            Edge e = new Edge(rootData, null);
            f.getEdgeList().add(e);
            //update nodecount
            f.setNodeCount(1);
            return f;
        }
        //otherwise, normal bottom operation
        //update edgelist
        List<Edge> edgeList = new ArrayList<>();
        for (Node oldRoot : f.getRootList()) {
            //set new node as parent
            oldRoot.setParent(root);
            //add child to new root
            root.addChild(oldRoot);
            Edge e = new Edge(rootData, oldRoot.getData());
            edgeList.add(e);
        }
        f.getEdgeList().addAll(edgeList);
        //update rootlist
        List<Node> updatedRootList = new ArrayList<>();
        updatedRootList.add(root);
        f.setRootList(updatedRootList);
        //update nodeMap
        f.getForestNodesMap().put(rootData, root);
        //update nodesCount
        int oldNodeCount = f.getNodeCount();
        f.setNodeCount(++oldNodeCount);
        System.out.println("bottom operation");
        return f;
    }

    public static Forest bottom(Forest f, String rootLabel) {

        Forest result = new Forest(f.getRootList());
        String rootData = getValidData(rootLabel, result.getForestNodesMap());
        Node root = new Node(rootData);

        //update edgelist
        List<Edge> edgeList = new ArrayList<>();
        if (result.getRootList().size() > 0) {
            for (Node oldRoot : result.getRootList()) {
                //set new node as parent
                oldRoot.setParent(root);
                //add child to new root
                root.addChild(oldRoot);
                Edge e = new Edge(rootData, oldRoot.getData());
                edgeList.add(e);
            }
        } else {
            result.getEdgeList().add(new Edge(rootData, null));
        }
        result.getEdgeList().addAll(edgeList);
        //update rootlist
        List<Node> updatedRootList = new ArrayList<>();
        updatedRootList.add(root);
        result.setRootList(updatedRootList);
        //update nodeMap
        result.getForestNodesMap().put(rootData, root);
        //update nodesCount
        int oldNodeCount = result.getNodeCount();
        result.setNodeCount(++oldNodeCount);
        System.out.println("bottom operation");
        return result;
    }

    public static Forest bottomless(Forest f) {

//        //get all the children of the roots and use them to create a new forest
//        f.removeRootLevel();
        List<Node> nodesList = new ArrayList<>();
        for (Node root : f.getRootList()) {
            //recreate the rootlist with the children of the roots and with parent node=null
            for (Node child : root.getChildren()) {
                Node newRootNode = new Node(child.getData());
                newRootNode.getChildren().addAll(child.getChildren());
                nodesList.add(newRootNode);
            }
//            nodesList.addAll(root.getChildren());
        }

        System.out.println("bottomless operation");
        return new Forest(nodesList);
//        return null;

    }

    public static Forest sum(Forest f, Forest g) {

        System.out.println("sum operation");
//        Set<Edge> f_EdgeList=new HashSet<Edge>(f.getEdgeList());
//        Set<Edge> g_EdgeList=new HashSet<Edge>(g.getEdgeList());

        try {
//        Forest output=new Forest(f_EdgeList);
//        Forest secondInputForest=new Forest(g_EdgeList);
            Forest output = new Forest(f.getRootList());
            Forest secondInputForest = new Forest(g.getRootList());
            //
            for (Node node : secondInputForest.getForestNodesMap().values()) {
                String validData = getValidData(node.getData(), output.getForestNodesMap());

                //add node to the forestnodesmap
                //there an error here, maybe
                if (validData.compareTo(node.getData()) != 0) {
                    //checks in the secondInputForest in case there's already a node with this data
                    validData = getValidData(validData, secondInputForest.getForestNodesMap());
                    String oldData = node.getData();
                    //update g.rootlist(if root)
                    if (node.getParent() == null) {

                        for (Node root : secondInputForest.getRootList()) {
                            if (root.getData().compareTo(oldData) == 0) {
                                root.setData(validData);
                            }
                        }
                    }

                    //update g.edgelist
                    for (Edge e : secondInputForest.getEdgeList()) {
                        if (e.getFather().compareTo(oldData) == 0) {
                            e.setFather(validData);
                        } else if (e.getChild() != null && e.getChild().compareTo(oldData) == 0) {
                            e.setChild(validData);
                        }
                        System.out.println("shared.ForestOperations.sum()");
                    }
//                List<Edge> secondForestEdgeList=secondInputForest.getEdgeList();
//                for(int i=0;i<secondForestEdgeList.size();i++){
//                    Edge e=secondForestEdgeList.get(i);
//                    if(e.getFather().compareTo(oldData)==0){
//                        e.setFather(validData);
//                        secondInputForest.getEdgeList().set(i, e);
//                    }else if(e.getChild()!=null&&e.getChild().compareTo(oldData)==0){
//                        e.setChild(validData);
//                        secondInputForest.getEdgeList().set(i, e);
//                    }
//                }

                    node.setData(validData);

                }
                output.getForestNodesMap().put(validData, node);

            }
            //merge edgelists
            output.getEdgeList().addAll(secondInputForest.getEdgeList());
            //merge rootlists
            output.getRootList().addAll(secondInputForest.getRootList());
            //update nodeCount
            int outputNodeCount = f.getNodeCount() + g.getNodeCount();
            output.setNodeCount(outputNodeCount);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Forest sumAndBottom(Forest f, Forest g) {
        return bottom(sum(f, g));
    }

    public static Forest product(Forest f, Forest g) {
        //base cases
        System.out.println("product");
        Forest resultForest;
        List<Node> firstInputRootNodesList = new ArrayList<>(f.getRootList());
        Forest firstInputForest = new Forest(firstInputRootNodesList);
        List<Node> secondInputRootNodesList = new ArrayList<>(g.getRootList());
        Forest secondInputForest = new Forest(secondInputRootNodesList);
        if (secondInputForest.getNodeCount() == 1) {
//            generate right node labels
//            substitute them
            buildLabels(firstInputForest, secondInputForest);

            return firstInputForest;
        }
        if (firstInputForest.getNodeCount() == 1) {

//            generate right node labels
//            substitute them
            buildLabels(firstInputForest, secondInputForest);
            return secondInputForest;
        }

        //if there are multiple trees in F or G
        //apply formula 1-->G*(F+F')=(GxF)+(GxF')
        if (secondInputForest.getRootList().size() > 1) {
            //same thing with f but for g
            List<Forest> treeList = new ArrayList<>();
            //get the list of trees 
            for (Node root : secondInputForest.getRootList()) {
                Forest tree = new Forest(root);
                treeList.add(tree);
            }
            List<Forest> outputList = new ArrayList<>();
            //calculate (FxG),(F'xG),(F''xG)...
            for (Forest tree : treeList) {
                outputList.add(product(tree, firstInputForest));
            }
            //sum the forest in the outputList
            Forest output = outputList.get(0);
            outputList.remove(0);
            while (!outputList.isEmpty()) {
                output = sum(output, outputList.get(0));
                outputList.remove(0);
            }
            return output;

        } else if (firstInputForest.getRootList().size() > 1) {

            List<Forest> treeList = new ArrayList<>();
            //get the list of single trees 
            for (Node root : firstInputForest.getRootList()) {
                Forest tree = new Forest(root);
                treeList.add(tree);
            }
            List<Forest> outputList = new ArrayList<>();
            //calculate (FxG),(F'xG),(F''xG)...
            for (Forest tree : treeList) {
                outputList.add(product(tree, secondInputForest));
            }
            //sum the forest in the outputList
            Forest output = outputList.get(0);
            outputList.remove(0);
            while (!outputList.isEmpty()) {
                output = sum(output, outputList.get(0));
                outputList.remove(0);
            }
            return output;
        }
        //if it arrives here, use formula 2
        //F_xG_=((F_xG)+(FxG)+(FxG_))_
        //calculate F and G
        Forest f_Bottomless = bottomless(firstInputForest);
        Forest g_Bottomless = bottomless(secondInputForest);
        List<Forest> outputList = new ArrayList<>();
//        (F_xG)
        outputList.add(product(firstInputForest, g_Bottomless));
//        (FxG)
        outputList.add(product(f_Bottomless, g_Bottomless));
//        (FxG_)        
        outputList.add(product(f_Bottomless, secondInputForest));
        Forest output = outputList.get(0);
        //sum the partial results
        outputList.remove(0);
        while (!outputList.isEmpty()) {
            output = sum(output, outputList.get(0));
            outputList.remove(0);
        }
        //add the bottom
        String f_RootLabel = firstInputForest.getRootList().get(0).getData();
        String g_RootLabel = secondInputForest.getRootList().get(0).getData();
        String rootLabel = "(" + f_RootLabel + "," + g_RootLabel + ")";
        resultForest = bottom(output, rootLabel);
        polishProductEdgeList(resultForest.getEdgeList());
        return resultForest;
//        return null;
    }

    public static String getValidData(String data, Map<String, Node> nodesMap) {
//        posso usare questa funzione per rinominare in modo corretto il bottom
        int count = 1;
        boolean isDataValid = false;
        String tempData = data;
        //rootdata contains the string data without numbers
//        String rootData=data.replaceAll("[\\d.]", "");
        String rootData = data;
        while (!isDataValid) {
            if (nodesMap.containsKey(tempData)) {
                tempData = rootData + Integer.toString(count);
                count++;
            } else {
                isDataValid = true;
            }
        }
        return tempData;
    }

    private static void buildLabels(Forest firstInputForest, Forest secondInputForest) {
        List<Node> nodesToBeParsed = new ArrayList<>();
        if (secondInputForest.getNodeCount() == 1) {
//            case g nodecount==1
            Map<String, Node> firstForestNodesMap = firstInputForest.getForestNodesMap();
//            get f label
            String g_Label = secondInputForest.getRootList().get(0).getData();
//            modify f labels and f nodemap key
            nodesToBeParsed.addAll(firstInputForest.getRootList());
            while (!nodesToBeParsed.isEmpty()) {
                Node n = nodesToBeParsed.get(0);

//                get old n label
                String oldLabel = n.getData();
//                update n label
                String newLabel = "(" + oldLabel + "," + g_Label + ")";
                n.setData(newLabel);
//                update nodeMap
                firstForestNodesMap.remove(oldLabel);
                firstForestNodesMap.put(newLabel, n);
//                update edges
                for (Edge e : firstInputForest.getEdgeList()) {
                    if (e.getChild() != null) {
                        if (e.getChild().compareTo(oldLabel) == 0) {
                            e.setChild(newLabel);
                        }
                    }
                    if (e.getFather().compareTo(oldLabel) == 0) {
                        e.setFather(newLabel);
                    }
                }
//                update nodesToBeParsedList
                nodesToBeParsed.addAll(n.getChildren());
                nodesToBeParsed.remove(n);
            }
        } else {
            Map<String, Node> secondForestNodesMap = secondInputForest.getForestNodesMap();
//            get f label
            String f_Label = firstInputForest.getRootList().get(0).getData();
//            modify g labels and g nodemap key
            nodesToBeParsed.addAll(secondInputForest.getRootList());
            while (!nodesToBeParsed.isEmpty()) {
                Node n = nodesToBeParsed.get(0);

//                get old n label
                String oldLabel = n.getData();
//                update n label
                String newLabel = "(" + f_Label + "," + oldLabel + ")";
                n.setData(newLabel);
//                update nodeMap
                secondForestNodesMap.remove(oldLabel);
                secondForestNodesMap.put(newLabel, n);
//                update edges
                for (Edge e : secondInputForest.getEdgeList()) {
                    if (e.getChild() != null) {
                        if (e.getChild().compareTo(oldLabel) == 0) {
                            e.setChild(newLabel);
                        }
                    }
                    if (e.getFather().compareTo(oldLabel) == 0) {
                        e.setFather(newLabel);
                    }
                }
//                update nodesToBeParsedList
                nodesToBeParsed.addAll(n.getChildren());
                nodesToBeParsed.remove(n);
            }
        }
    }

    public static void polishProductEdgeList(List<Edge> edgeList) {
        List<Edge> appEdgeList = new ArrayList<>();
        appEdgeList.addAll(edgeList);
        for (Edge e : appEdgeList) {
            if (e.getChild() == null) {
                edgeList.remove(e);
            }
        }
//        check if there are any Edge with no children
    }

    public static Forest duplicateForest(Forest prototype, int count) {
        if (prototype.getNodeCount() == 0) {
            return prototype;
        }

        //Forest resultForest;
        //List<Node> nodesToBeVisited=new ArrayList<>(prototype.getRootList());
        //prototype.get
        /*
        //follow the forest structure and copy it (using count as label)
        //order: left most root, left subtree, rightsubtree-->preorder
        //copy root node
        //for each root
        for(Node root: prototype.getRootList()){
            //visit and copy the subtree in preorder order
            Node n=new Node(Integer.toString(count));
            count++;
            //visit its children
        }
         */
        Set<String> forestLabels = prototype.getForestNodesMap().keySet();
        Forest tmp = new Forest(prototype.getRootList());
        //TO DO: order of the labels is weird, i could get the order of the nodes by visiting the forest and setting the right substition order (the bottom label is correct, so it's a substitution issue)
        // Map<String,String> substitutionMap=new HashMap<>();
        for (String label : forestLabels) {
            //  substitutionMap.put(label, Integer.toString(count));

            tmp.getForestNodesMap().get(label).setData(Integer.toString(count));
            count++;
        }
        /*
        for(Edge e:prototype.getEdgeList()){
            
        }
         */
        return new Forest(tmp.getRootList());

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static DownsetGraph cartesianProduct(DownsetGraph f, DownsetGraph g) {
        System.out.println("f downset edge list");
        Set<ElementList> resultSet = new LinkedHashSet<>();
        Set<Edge> resultEdgeSet = new LinkedHashSet<>();
        List<ElementList> f_ElementList = f.getDownsetList();
        List<ElementList> g_ElementList = g.getDownsetList();
        List<Edge> f_downsetEdgeList = f.getDownsetEdgeList();
        List<Edge> g_downsetEdgeList = g.getDownsetEdgeList();
//        System.out.println("g downset edge list");
//        for(Edge e:f_downsetEdgeList){
//            System.out.println(e.toString());
//        }
        for (ElementList element_f : f_ElementList) {
            List<String> f_Children = new ArrayList<>();
            //check if element_f has children, in case save them
            for (Edge e : f_downsetEdgeList) {
                if (e.getFather().compareTo(element_f.toString()) == 0) {
                    System.out.println("element {" + e.getChild() + "} is a child of " + element_f.toString());
                    f_Children.add("{" + e.getChild() + "}");
                }
            }
            for (ElementList element_g : g_ElementList) {
                //create cartesian Element=(element_f, element_g) and its edges
                ElementList cartesianElement = new ElementList();
                cartesianElement.getElements().add(element_f.printSet());
                cartesianElement.getElements().add(element_g.printSet());
                resultSet.add(cartesianElement);
                //edges to be created:Cartesian element-->(child(f),element_g) DONE
//                                                -->(f,child(g)) DONE 
//                                                -->(child(f),(child(g)) DONE
//           for each child of f and each child of g
                //child(f),g
                for (String child : f_Children) {
                    ElementList childElement = new ElementList();
                    childElement.getElements().add(child);
                    childElement.getElements().add(element_g.printSet());
                    Edge e = new Edge(cartesianElement.toString(), childElement.toString());
                    if (!resultEdgeSet.contains(e.getReverse())) {
                        resultEdgeSet.add(e);
                    }
                }
                for (Edge e : g_downsetEdgeList) {
//                 (f,child(g))
                    if (e.getFather().compareTo(element_g.toString()) == 0) {
//                   System.out.println("element {"+e.getChild()+"} is a child of "+element_g.toString());
                        ElementList childElement = new ElementList();
                        childElement.getElements().add(element_f.printSet());
                        childElement.getElements().add("{" + e.getChild() + "}");
                        Edge childEdge = new Edge(cartesianElement.toString(), childElement.toString());

                        if (!resultEdgeSet.contains(e.getReverse())) {
                            resultEdgeSet.add(childEdge);
                        }

//                (child(f),(child(g))

                        for (String child : f_Children) {
                            ElementList bothChildElement = new ElementList();
                            bothChildElement.getElements().add(child);
                            bothChildElement.getElements().add("{" + e.getChild() + "}");
                            Edge bothChildEdge = new Edge(cartesianElement.toString(), bothChildElement.toString());
                            if (!resultEdgeSet.contains(e.getReverse())) {
                                resultEdgeSet.add(bothChildEdge);
                            }
                        }
                        
                    }
                }

            }
//            System.out.println("G cycle finished");    
        }

//        System.out.println("cartesian product edge list: ");
//        for(Edge e:resultEdgeList){
//            System.out.println(e.toString());
//        }
        List<ElementList> resultList = new ArrayList<>(resultSet);
        List<Edge> resultEdgeList = new ArrayList<>(resultEdgeSet);
        return new DownsetGraph(resultList, resultEdgeList);
    }

    public static DownsetGraph generateDownsetGraph(Forest inputForest, boolean withEmptySet) {
        //generate downset graph of a forest
        //create downsetList
        //step 0 add {} to the downsetList
//        Set<ElementList> previousDownset=new LinkedHashSet<>();
        List<ElementList> downsetList = new ArrayList<>();
        List<ElementList> previousDownset = new ArrayList<>();
        List<Edge> downsetEdgeList = new ArrayList<>();
        List<Node> nodesToBeParsed = new ArrayList<>();
        Map<String, ElementList> nodesDownsetMap = new HashMap<>();

        if (withEmptySet) {
            downsetList.add(new ElementList());
        }

        //step 0.1 add roots to downset, initialize nodestobeparsed 
        for (Node root : inputForest.getRootList()) {
            //add downset to the downset map
            ElementList e = new ElementList();
            e.getElements().add(root.getData());
            downsetList.add(e);
            nodesDownsetMap.put(root.getData(), e);
            //add downset to downset list
            if (withEmptySet) {

                //nodesDownsetMap.put(root.getData(), e);
                //add ({},root) to downset edge list 
                downsetEdgeList.add(new Edge(downsetList.get(0).toString(), e.toString()));
                //add root to  nodesotbeparsed
                nodesToBeParsed.addAll(root.getChildren());
                //initialize previousDownset
                previousDownset.add(e);
            } else {
                if (root.getChildren().size() > 0) {
                    nodesToBeParsed.addAll(root.getChildren());

                } else {
                    //add root to downsetlist and downsetedgelist
                    /*ElementList e = new ElementList();
                    e.getElements().add(root.getData());
                    downsetList.add(e);
                     */
                    downsetEdgeList.add(new Edge(e.toString(), null));
                }

            }

        }
        System.out.println("step 0 done");
        //variable that tracks how many elements have to be in common to merge the sets
        int elementsInCommonNumber = 0;
        int forestNodeCount = inputForest.getNodeCount();

        boolean allDownsetCreated = false;
        //while the last downset created has lesser elements than the nodes of the forest
        while (elementsInCommonNumber < forestNodeCount - 1) {
            Set<ElementList> currentDownset = new LinkedHashSet<>();
            Set<Edge> currentEdgeList = new LinkedHashSet<>();
            //check if there are any node that are not yet being visited and add them
            //handle nodes that have to be parsed
            if (!nodesToBeParsed.isEmpty()) {
                List<Node> newNodesToBeParsed = new ArrayList<>();
                while (nodesToBeParsed.size() > 0) {
                    //create downset
                    Node node = nodesToBeParsed.get(0);
                    ElementList downset = new ElementList();
                    //get the parent's downset 
                    ElementList parentDownset = nodesDownsetMap.get(node.getParent().getData());
                    downset.getElements().addAll(parentDownset.getElements());
//                    downset.addAll(pathToRoot);
                    downset.getElements().add(node.getData());
                    //add downset to the downset map
                    nodesDownsetMap.put(node.getData(), downset);
                    //add downset to downset list and current downset list
//                   downsetList.add(downset);
                    currentDownset.add(downset);
                    //add Edge(parent,current node) to downset edge list
//                   currentEdgeList.add(new Edge(parentDownset.toString(),downset.toString()));
                    downsetEdgeList.add(new Edge(parentDownset.toString(), downset.toString()));
                    //add node's children to newnodesotbeparsed
                    newNodesToBeParsed.addAll(node.getChildren());
                    nodesToBeParsed.remove(0);

                }
                if (!newNodesToBeParsed.isEmpty()) {
//                   update list of nodes to be parsed(with children of the nodes that are being visited this time)
                    nodesToBeParsed.addAll(newNodesToBeParsed);

                } else {
                    System.out.println("children step done");
                }
            }
            //check on the previousDownset list if there are sets that have elements-1 elements in common
            //if so, merge them and add them to the currentdownset
            //then add the currentdownset on the downsetlist
            //if currentdownset has a set containing inputforest.nodeCount elements-->end
            //else update previousdownset(with currentDownset)
            //before adding the current downset to the downset list, sort them

            for (int i = 0; i < previousDownset.size(); i++) {
                ElementList elementList = previousDownset.get(i);
                for (int j = i + 1; j < previousDownset.size(); j++) {
//                    if(elementsInCommonNumber==4){
//                        System.out.println("prova");
//                    }
                    ElementList secondElementList = previousDownset.get(j);
                    if (elementList.compareLists(secondElementList) == elementsInCommonNumber) {
                        ElementList downset = elementList.mergeLists(secondElementList);
//                        String elementListString=elementList.toString();
//                        String secondElementListString=secondElementList.toString();
//                        String downsetString=downset.toString();
                        //add Edge(first list merged,new merged element) and (second list merged,new merged element) to downset edge list
                        Edge edge1 = new Edge(elementList.toString(), downset.toString());
                        if (!Edge.containsEdge(currentEdgeList, edge1)) {
                            currentEdgeList.add(edge1);
                        }
                        Edge edge2 = new Edge(secondElementList.toString(), downset.toString());
                        if (!Edge.containsEdge(currentEdgeList, edge2)) {
                            currentEdgeList.add(edge2);
                        }

//                        downsetEdgeList.add(edge1);
//                        downsetEdgeList.add(edge2);
                        //add new merged downset to element list
                        if (!ElementList.containsElementList(currentDownset, downset)) {
                            currentDownset.add(downset);
                        }
                        if (currentDownset.contains(downset)) {
                            System.out.println("GraphGenerator.DownsetGraphGenerator.generateGraph()");
                            System.out.println("current downset size: " + currentDownset.size());
//                            System.out.println("");

                        }

                    }
                }
            }
            elementsInCommonNumber++;

            System.out.println("merging step done");
            //add all edges in currentEdgeList to the downsetEdgeList
            downsetEdgeList.addAll(currentEdgeList);
            //reset the currentEdgeList
            currentEdgeList = new LinkedHashSet<>();
//            update previousdownset(with currentDownset)
            previousDownset = new ArrayList<>(currentDownset);
            previousDownset.sort((ElementList e1, ElementList e2) -> e1.toString().compareTo(e2.toString()));
            downsetList.addAll(previousDownset);

        }
        return new DownsetGraph(downsetList, downsetEdgeList);
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static List<DownsetGraph> generateDownsetGraph(NMForest nmForest) {

        //for each tree in the forest, generate downsets and mirrors (add node in the middle based on the label value)
        //Map<Integer,List<Forest>> forestMap=nmForest.getForestMap();
        List<DownsetGraph> resultDownsetGraphList = new ArrayList<>();
        List<Edge> resultDownsetEdgeList = new ArrayList<>();
        List<ElementList> resultDownsetList = new ArrayList<>();
        int count = 1;
        for (Map.Entry<Integer, List<Forest>> entry : nmForest.getForestMap().entrySet()) {
            List<Forest> forestList = entry.getValue();
            //for each forest
            for (Forest f : forestList) {
                DownsetGraph treeDownset = ForestOperations.generateDownsetGraph(f, false);
                System.out.println("forest");

                Set<ElementList> rotatedDownsetSet = new LinkedHashSet<>();
                Set<Edge> rotatedDownsetEdgeList = new LinkedHashSet<>();

                //create rotation of the downset
                for (Edge e : treeDownset.getDownsetEdgeList()) {
                    //create new elementlists with prime elements(a->a',ab->a'b'...)
                    //create father prime ElementList

                    ElementList fatherElementList = new ElementList(),
                            childElementList = new ElementList();

                    String[] fatherLabelElements = e.getFather().split(",");
                    for (String element : fatherLabelElements) {
                        fatherElementList.getElements().add(element + "'");
                    }
                    //add, if not present, the ElementList (father) to the rotated downsetList
                    if (!ElementList.containsElementList(rotatedDownsetSet, fatherElementList)) {
                        rotatedDownsetSet.add(fatherElementList);
                    }
                    System.out.println(rotatedDownsetSet.contains(fatherElementList));
                    //only time an edge as null child is when there is a single node tree
                    if (e.getChild() != null) {
                        //create child prime ElementList
                        String[] childLabelElements = e.getChild().split(",");
                        for (String element : childLabelElements) {
                            childElementList.getElements().add(element + "'");
                        }
                        //add, if not present, the ElementList (child) to the rotated downsetList
                        //if(!ElementList.containsElementList(rotatedDownsetSet, fatherElementList))rotatedDownsetSet.add(childElementList);
                        if (!ElementList.containsElementList(rotatedDownsetSet, childElementList)) {
                            rotatedDownsetSet.add(childElementList);
                        }
                        //add rotated edge to downsetEdgeList (child',father')
                        Edge edge = new Edge(childElementList.toString(), fatherElementList.toString());
                        if (!Edge.containsEdge(rotatedDownsetEdgeList, edge)) {
                            rotatedDownsetEdgeList.add(edge);
                        }
                    }

                    //if(!ElementList.containsElementList(rotatedDownsetList, fatherElementList)) {}
                }
                String firstElementLabel = treeDownset.getDownsetList().get(0).toString();
                if(treeDownset.getDownsetList().size()==1){
                        //if it is a single node tree, remove (root,null) edge and put (root,root')
                        treeDownset.getDownsetEdgeList().remove(0);
                }
                if (entry.getKey() == 0) {
                    //connect first element with first element prime
                    Edge e = new Edge(firstElementLabel + "'", firstElementLabel);
                    
                    treeDownset.getDownsetEdgeList().add(e);
                } else {
                    //case label=1
                    ElementList middle = new ElementList();
                    middle.getElements().add("1/2(" + Integer.toString(count++) + ")");
                    treeDownset.getDownsetList().add(middle);
                    Edge e1 = new Edge(middle.toString(), firstElementLabel);
                    Edge e2 = new Edge(firstElementLabel + "'", middle.toString());
                    //connect label with a middle node
                    treeDownset.getDownsetEdgeList().add(e1);
                    treeDownset.getDownsetEdgeList().add(e2);

                }
                treeDownset.getDownsetList().addAll(rotatedDownsetSet);
                treeDownset.getDownsetEdgeList().addAll(rotatedDownsetEdgeList);
                resultDownsetGraphList.add(treeDownset);

            }
        }

        //it is handled like this in case the list of downset graphs is needed, which will be
        return resultDownsetGraphList;
        //united in a single downsetgraph
        /*
        for(DownsetGraph g:resultDownsetGraphList){
            resultDownsetEdgeList.addAll(g.getDownsetEdgeList());
            resultDownsetList.addAll(g.getDownsetList());
        }
        return new DownsetGraph(resultDownsetList, resultDownsetEdgeList);
         */
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }
    public static Forest generateGnByProduct(int n){
        Set<Edge> edges=new HashSet();
        Forest resultForest;
        //String[] variables={"X","Y","Z"};
        
        int i=0;
        //case n>1, use product n-1 times between result and initial forests
        //case n=1, result forest=initial forest
        //forest f1 generation
        String variableName=String.valueOf(ALPHABET[i++]);
        edges.add(new Edge("0",null));
        edges.add(new Edge(variableName,"1"));
        System.out.println("value of n:"+ n);
        Forest f1;
        resultForest=new Forest(edges,false);
        while(n>1){
            //product between F1 and forest to be returned
            variableName=String.valueOf(ALPHABET[i++]);
            edges=new HashSet();
            edges.add(new Edge("0",null));
            edges.add(new Edge(variableName,"1"));
            f1=new Forest(edges,false);
            resultForest=ForestOperations.product(resultForest, f1);
            
            
            n--;
        }
        //Collection<Node> nodeList=resultForest.getForestNodesMap().values();
        //Set<String> nodeStringList= resultForest.getForestNodesMap().keySet();
        return resultForest;
    }
}
