/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphGenerator;

import java.io.IOException;

/**
 *
 * @author Paolo
 */
public abstract class GraphGenerator {
    
    protected String[] setElements;
    protected static String DOT_FILE_NAME;
    protected static String PNG_FILE_NAME;
    
    public abstract void generateGraph() throws IOException;
    
    public static String getDotFileName(){
        return DOT_FILE_NAME;
    }
    
    public static String getPngFileName(){
        return PNG_FILE_NAME;
    }
    
}