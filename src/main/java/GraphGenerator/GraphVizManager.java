/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import shared.Edge;


/**
 *
 * @author Paolo
 */
public class GraphVizManager {
    private final static String SET_ELEMENT_LEFT_DELIMITER = "\"{";
    private final static String SET_ELEMENT_RIGHT_DELIMITER = "}\"";
    private final static String SET_DELIMITER = "\"";
    private final static String LINK = " -- ";

    
    public GraphVizManager(){} //is the constructor even useful?
    
    
    public static void launchDot(String fileInputname, String fileOutputName, String outputType) throws IOException{
        
        ProcessBuilder builder = new ProcessBuilder(
       //     "cmd.exe", "/c", "cd " + System.getProperty("user.dir") + "\\&& dot -Tpng " + fileInputname +  " > " + fileOutputName);
               "cmd.exe", "/c", "cd " + System.getProperty("user.dir") + "\\&& dot -v -T"+outputType+" " + fileInputname +  " > " + fileOutputName);
        builder.redirectErrorStream(true);
//        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        System.out.println("dot process created successfully");
        //waits for the result
        while (true) {
            line = r.readLine();
            if (line == null){
                break;
            }else{
                System.err.println(line);
            }
        }
    }
    
    public static void createForestGraphDotFile(List<Edge> edges) throws IOException{
        //create and populate the file
        String dotFileName = ForestGraphGenerator.getDotFileName();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dotFileName))) {
            writer.write("graph forest_graph{ rankdir=BT\n");
            for(Edge edge: edges){
                if(edge.getChild()==null){
//                    writer.write(SET_ELEMENT_LEFT_DELIMITER+edge.getFather()+SET_ELEMENT_RIGHT_DELIMITER+"\n");
                    writer.write(edge.getFather()+"\n");
                }else{
//                    writer.write(SET_ELEMENT_LEFT_DELIMITER+edge.getFather()+SET_ELEMENT_RIGHT_DELIMITER+LINK+SET_ELEMENT_LEFT_DELIMITER+edge.getChild()+SET_ELEMENT_RIGHT_DELIMITER+"\n");
                    writer.write(edge.getFather()+LINK+edge.getChild()+"\n");
                }
                
            }
            writer.write("}");
            writer.close();
            
//            launchDot(dotFileName, ForestGraphGenerator.getPngFileName());
        }
    }
    public static void createDownsetForestGraphDotFile(String dotFileName,List<Edge> edges) throws IOException{
        //create and populate the file
//        String dotFileName = DownsetGraphGenerator.getDotFileName();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dotFileName))) {
            writer.write("graph downset_graph{ rankdir=BT\n");
            for(Edge edge: edges){
                if(edge.getChild()==null){
                    writer.write(SET_ELEMENT_LEFT_DELIMITER+edge.getFather()+SET_ELEMENT_RIGHT_DELIMITER+"\n");
//                    writer.write(edge.getFather()+"\n");
                }else{
                    writer.write(SET_ELEMENT_LEFT_DELIMITER+edge.getFather()+SET_ELEMENT_RIGHT_DELIMITER+LINK+SET_ELEMENT_LEFT_DELIMITER+edge.getChild()+SET_ELEMENT_RIGHT_DELIMITER+"\n");
//                    writer.write(edge.getFather()+LINK+edge.getChild()+"\n");
                }
                
            }
            writer.write("}");
            writer.close();
            
//            launchDot(dotFileName, ForestGraphGenerator.getPngFileName());
        }
    }
    
    public static void createCustomNameForestGraphWithSubforestsDotFile(String dotFileName,Map<Integer,List<List<Edge>>> edgesMap) throws IOException{
        //create and populate the file

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dotFileName))) {
            writer.write("graph forest_graph{\n "
                    + "rankdir=BT \n"
//                    + "layout=sfdp\n"
//                    + "overlap=false\n"
            );
            int count=0;
            //for each labelled subforest
            for(Map.Entry<Integer,List<List<Edge>>> forestMap : edgesMap.entrySet()){
                //for each subforest
                for(List<Edge> subForest:forestMap.getValue()){
                    //create subgraph with label forestmap.getKey
                    //generate dot of its edges
                    writer.write("subgraph cluster_" + count++ + "{\n");
                    writer.write("label = \"" + String.valueOf(forestMap.getKey()) + "\"\n");
                    //convert edgelist to dot lines
                    for(Edge edge: subForest){
                        if(edge.getChild()==null){
                            writer.write(SET_DELIMITER+edge.getFather()+SET_DELIMITER+"\n");
        //                    writer.write(edge.getFather()+"\n");
                        }else{
                            writer.write(SET_DELIMITER+edge.getFather()+SET_DELIMITER+LINK+SET_DELIMITER+edge.getChild()+SET_DELIMITER+"\n");
        //                    writer.write(edge.getFather()+LINK+edge.getChild()+"\n");
                        }
                
                    }
                    writer.write(";}\n");
                }
                    
            }
            writer.write("}");
            writer.close();
        }
            /*
            for(Edge edge: edges){
                if(edge.getChild()==null){
                    writer.write(SET_DELIMITER+edge.getFather()+SET_DELIMITER+"\n");
//                    writer.write(edge.getFather()+"\n");
                }else{
                    writer.write(SET_DELIMITER+edge.getFather()+SET_DELIMITER+LINK+SET_DELIMITER+edge.getChild()+SET_DELIMITER+"\n");
//                    writer.write(edge.getFather()+LINK+edge.getChild()+"\n");
                }
                
            }
            writer.write("}");
            writer.close();
            */
//            launchDot(dotFileName, ForestGraphGenerator.getPngFileName());
        }
    
    
     
    public static void createCustomNameForestGraphDotFile(String dotFileName,List<Edge> edges) throws IOException{
        //create and populate the file
        
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dotFileName))) {
            writer.write("graph forest_graph{ rankdir=BT\n");
            for(Edge edge: edges){
                if(edge.getChild()==null){
//                    writer.write(SET_ELEMENT_LEFT_DELIMITER+edge.getFather()+SET_ELEMENT_RIGHT_DELIMITER+"\n");
                    writer.write(edge.getFather()+"\n");
                }else{
//                    writer.write(SET_ELEMENT_LEFT_DELIMITER+edge.getFather()+SET_ELEMENT_RIGHT_DELIMITER+LINK+SET_ELEMENT_LEFT_DELIMITER+edge.getChild()+SET_ELEMENT_RIGHT_DELIMITER+"\n");
                    writer.write(edge.getFather()+LINK+edge.getChild()+"\n");
                }
                
            }
            writer.write("}");
            writer.close();
            
//            launchDot(dotFileName, ForestGraphGenerator.getPngFileName());
        }
    }
    public static void createCustomNameForestGraphWithSeparatorDotFile(String dotFileName,List<Edge> edges) throws IOException {
         //create and populate the file
        
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dotFileName))) {
            writer.write("graph forest_graph{ rankdir=BT\n");
            for(Edge edge: edges){
                if(edge.getChild()==null){
                    writer.write(SET_ELEMENT_LEFT_DELIMITER+edge.getFather()+SET_ELEMENT_RIGHT_DELIMITER+"\n");
//                    writer.write(edge.getFather()+"\n");
                }else{
                    writer.write(SET_ELEMENT_LEFT_DELIMITER+edge.getFather()+SET_ELEMENT_RIGHT_DELIMITER+LINK+SET_ELEMENT_LEFT_DELIMITER+edge.getChild()+SET_ELEMENT_RIGHT_DELIMITER+"\n");
//                    writer.write(edge.getFather()+LINK+edge.getChild()+"\n");
                }
                
            }
            writer.write("}");
            writer.close();
            
//            launchDot(dotFileName, ForestGraphGenerator.getPngFileName());
        }
    }
    public static void createCustomNameForestGraphWithDelimiterDotFile(String dotFileName,List<Edge> edges) throws IOException {
         //create and populate the file
        
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dotFileName))) {
            writer.write("graph forest_graph{\n "
                    + "rankdir=BT \n"
//                    + "layout=sfdp\n"
//                    + "overlap=false\n"
            );
            for(Edge edge: edges){
                if(edge.getChild()==null){
                    writer.write(SET_DELIMITER+edge.getFather()+SET_DELIMITER+"\n");
//                    writer.write(edge.getFather()+"\n");
                }else{
                    writer.write(SET_DELIMITER+edge.getFather()+SET_DELIMITER+LINK+SET_DELIMITER+edge.getChild()+SET_DELIMITER+"\n");
//                    writer.write(edge.getFather()+LINK+edge.getChild()+"\n");
                }
                
            }
            writer.write("}");
            writer.close();
            
//            launchDot(dotFileName, ForestGraphGenerator.getPngFileName());
        }
    }
    public static Set<Edge> convertDotFileToEdgeSet(File dotFile) throws IOException{
        if (dotFile == null) {
            return null;
        }
        BufferedReader br = new BufferedReader(new FileReader(dotFile));

        Set<Edge> resultEdgeSet = new LinkedHashSet<>();
        //first line is always skipped
        String st=br.readLine();

        while ((st = br.readLine()) != null && st.compareTo("}") != 0) {
            //skip declaration lines (there are no set delimeters present, so length of the split =1)
            String[] setDelimSplit = st.split(SET_DELIMITER);
            //check that string doesn't contain a = (meaning there is an assignment (like rankdir=BT)
            if(st.contains("=")) continue;
            //delete set delimiter
            st = st.replaceAll(SET_DELIMITER, "");
            //split by link
            String[] linkSplit = st.split(LINK);
            //only two possible cases: father -- son and father
            Edge e;
            if (linkSplit.length == 1) {
                //link is not present-->create (father,null) edge 
                e = new Edge(linkSplit[0], null);
            } else {
                //link is present --> create (father,child)edge
                e = new Edge(linkSplit[0], linkSplit[1]);
            }
            resultEdgeSet.add(e);

        }

        return resultEdgeSet;
    }
    public static void createCustomNameForestGraphWithColoredNodesDotFile(String dotFileName, List<Edge> edges, List<String> nodesToBeColored) throws IOException {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dotFileName))) {
            writer.write("graph forest_graph{\n "
                    + "rankdir=BT \n"
//                    + "layout=sfdp\n"
//                    + "overlap=false\n"
            );
            for(Edge edge: edges){
                if(edge.getChild()==null){
                    writer.write(SET_DELIMITER+edge.getFather()+SET_DELIMITER+"\n");
//                    writer.write(edge.getFather()+"\n");
                }else{
                    writer.write(SET_DELIMITER+edge.getFather()+SET_DELIMITER+LINK+SET_DELIMITER+edge.getChild()+SET_DELIMITER+"\n");
//                    writer.write(edge.getFather()+LINK+edge.getChild()+"\n");
                }
                
            }
            for(String label :nodesToBeColored){
                writer.write("\""+label+"\" "+"[color=\"red\"] \n");
            }
            writer.write("}");
            writer.close();
            
//            launchDot(dotFileName, ForestGraphGenerator.getPngFileName());
        }
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
