/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Paolo
 */
public class FormulaParser {
    public FormulaParser(){
        //is it needed?
    }
    //symbol usage
    private final static char AND='^';
    private final static char OR='V';
    private final static char IMPLIES='-';
    private final static char NOT='!';
    
    //- or ! NOT
    //^ or & AND
    //v or | OR
    //-> or → IMPLIES
    public static List<String> evaluateFormula(Map<String, Node> forestMap,String formula,char[] variableAlphabet)throws Exception{
        //alphabet in input? probably needed
        //substitute list of elementList of all elements with either forest map, or forest node list
        //forest map pro: has both label and node list ready
        //forest node list: has to get label from node
        //throw exception if formula is null
        if(formula.length()==0) throw new Exception("formula is null");
        //preliminary phase        
        //for each variable, calculate the set of nodes associated with a formula
        //remove white spaces in formula
        
        formula=formula.trim();
        formula=formula.replaceAll("\\s", "");
        //put formula in uppercase 
        formula=formula.toUpperCase();
        char token;
        char[] tokens=formula.toCharArray();
        /*
        //check if parenthesis are balanced
        int r_par=0,l_par=0;
        for(int i=0;i<tokens.length;i++){
            if(tokens[i]=='(') r_par++;
            if(tokens[i]==')') l_par++;
        }
        if(r_par!=l_par) throw new Exception("Parenthesis in formula are not balanced");
        */
        //get all the labels of the nodes
        Set<String> forestNodeLabelsSet=forestMap.keySet();
        List<String> forestLabelsList=new ArrayList<>(forestNodeLabelsSet);
        Map<Character,List<String>> variablesDownsetMap= new HashMap<>();
        
        for(char variable:variableAlphabet){
            //calculate downset of each variable
            List<String> variableDownset=downset(variable,forestNodeLabelsSet);
            //put the obtained list of labels in the variables downset map
            variablesDownsetMap.put(variable, variableDownset);
        }
        Stack<List<String>> valuesStack=new Stack<>();
        Stack<Character> opStack=new Stack<>();
        int i=0;
        //prelim phase DONE
        //evaluation phase       
        //until we reach the end of the formula string
        while (i<tokens.length){
            //check character
            token=tokens[i];
            //if blank space, continue
            if (token==' '){
                i++;
                continue;
            }
            //if variable -> put char downset in value stack
            if(isVariable(token,variableAlphabet)){
                valuesStack.push(variablesDownsetMap.get(token));
                i++;
            }else{
                //if operator or right parenthesis : put char in op stack, advance pointer by 1
                //special case NEGATION: 
                    //solve the negation (either of a variable or of an expression) 
                    //(negation could call the evaluate formula to obtain the value of the expression) 
                    // Advance pointer by 1 if term was a variable, or length of expression
                //special case LEFT PARENTHESIS ): 
                    //evaluate the expression (until right parenthesis is encountered,
                    // pop operations in the op stack and solve them by popping two values from the values stack) 
                    //advance pointer by 1
                //(only AND, OR and IMPLIES operations are expected, since negations are executed immediately)
                switch (token){
                    case ')' -> {
                        //special case LEFT PARENTHESIS
                        if(opStack.isEmpty()) throw new Exception("Expected right parenthesis in sub expression not found, position in formula: "+(i+1));
                        while(opStack.peek()!='('){
                            //evaluate the expression (until right parenthesis is encountered,
                            // pop operations in the op stack and solve them by popping two values from the values stack) 
                            //the first element of the stack is actually the second element of the operation 
                            //(not important for AND/OR operations, but order is important for IMPLIES)
                            List<String> firstArgument=new ArrayList<>();
                            List<String> secondArgument=new ArrayList<>();
                            //throw exception if there are too few values in 
                            if(valuesStack.size()<2) throw new Exception("too few values in resolution of sub expression, position in formula: "+(i+1));
                            secondArgument.addAll(valuesStack.pop());
                            firstArgument.addAll(valuesStack.pop());
                            valuesStack.push(executeOperation(opStack.pop(),firstArgument,secondArgument,forestMap));
                            if(opStack.isEmpty()) throw new Exception("Expected right parenthesis in sub expression not found, position in formula: "+(i+1));
                        }
                        //for right parenthesis
                        opStack.pop();
                        //advance pointer by 1
                        i++;
                    }
                    case NOT -> {
                        //special case NEGATION
                        //solve the negation (either of a variable or of an expression)
                        if(isVariable(tokens[i+1], variableAlphabet)){
                            //value to negate is a variable
                            //get value list of variable
                            char variable=tokens[i+1];
                            List<String> varDownset=variablesDownsetMap.get(variable);
                            valuesStack.push(negation(varDownset,forestMap));
                            /*
                            //calculate upset of variable downset list
                            Set varUpset=new LinkedHashSet<>();
                            for(String label:varDownset){
                                varUpset.addAll(upset(label, forestMap));
                            }
                            //apply difference between all forest labels and upset of variable value list
                            List<String> varUpsetList=new ArrayList<>(varUpset);
                            valuesStack.push(difference(forestLabelsList, varUpsetList));
                            */
                            // Advance pointer by 2 (op char+ var char) if term was a variable
                            i=i+2;
                        }else if(tokens[i+1]=='('){
                            //value to negate is an expression
                            //select expression substring to be evaluated
                            //how: we count the number of expected left parenthesis encountered 
                            //(if we encounter another right parenthesis, the value increases)
                            int expectedClosurePar=1;
                            int j=i+1;
                            while(expectedClosurePar>0){
                                //take the char
                                char app=tokens[++j];
                                if (app==')') expectedClosurePar--;
                                else if (app=='(') expectedClosurePar++;
                            }
                            //take expression substring and evaluate formula
                            
                            String subExpression=new String(tokens, i+1, j-i);
                            List<String> expressionValue=evaluateFormula(forestMap, subExpression, variableAlphabet);
                            valuesStack.push(negation(expressionValue,forestMap));
                            // Advance pointer by n+1 (expression length+ var char) if term was an expression (use j
                            // that already points to the end of the negated expression)
                            i=j+1;
                        }else{
                            //unexpected char in formula
                            throw new Exception("Unexpected character for negation in position "+(i+2));
                        }
                         
                        //(negation could call the evaluate formula to obtain the value of the expression) 
                        
                        
                    }
                    case '(', AND, OR, IMPLIES -> {
                        //normal operation case
                        //add to op stack
                        opStack.push(token);
                        i++;
                    }
                    default -> {
                        //case input not recognized
                        //throw exception?
                        throw new Exception("Unexpected character in position "+(i+1));
                    }
                }
            }
            
        }
        //after the formula has been parsed, apply remaining ops to remaining values
        //precedence order is not needed since the two ops are complementary(?) 
        while(!opStack.isEmpty()){
            List<String> firstArgument=new ArrayList<>();
            List<String> secondArgument=new ArrayList<>();
            if(valuesStack.size()<2) throw new Exception("Unexpected "+opStack.pop()+" operator in formula");
            secondArgument.addAll(valuesStack.pop());
            firstArgument.addAll(valuesStack.pop());
            valuesStack.push(executeOperation(opStack.pop(),firstArgument,secondArgument,forestMap));
        }
        //return remaining element of stack
        if(valuesStack.isEmpty()) throw new Exception("Value stack is empty, variable missing");
        else if (valuesStack.size()>1) throw new Exception("too many values in value stack, operator missing");
        return valuesStack.pop();
        
            //OPERATIONS to implement:
            // AND: intersection of values (in our case, list of elements)
            // OR: Union of values (in our case, list of elements)
            // IMPLIES X->Y: all elements minus the UPSET of (DIFFERENCE between the first element X of implication and the second one Y)
            //NEGATION: all elements minus the UPSET of the element
            //OTHER USED OPERATIONS TO IMPLEMENT:
            //DIFFERENCE BETWEEN TWO LISTS
            //INTERSECTION
            //UNION
            //UPSET OF A LIST OF ELEMENTS--> UPWARD CLOSURE 
            //DOWNSET OF AN ELEMENT (X) -->ALL THE ELEMENTS THAT CONTAIN X
        //return null;
    }

    private static boolean isVariable(char token, char[] variableAlphabet) {
        //returns true if token is in variableSymbols
        for(char c:variableAlphabet){
            if(token==c) return true;
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static List<String> downset(char variable, Set<String> forestNodeLabels) {
        //returns the list of labels that contain the variable char
        List<String> returnList=new ArrayList<>();
        for(String label:forestNodeLabels){
            //index of returns the index if the char is present, or -1 if the char doesn't occur
            if(label.indexOf(variable)>0){
                returnList.add(label);
            }
        }
        return returnList;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private static List<String> upset(String nodeLabel, Map<String, Node> forestMap) {
        //visit each children and put it in result list
        List<String> resultList=new ArrayList<>();
        Node inputNode=forestMap.get(nodeLabel);
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(inputNode);
        //until nodequeue is empty
        while(!nodeQueue.isEmpty()){
            //get a child
            Node n=nodeQueue.remove();
            //put the node label in result list
            n.getData();
            resultList.add(n.getData());
            //put all its children in queue, if children list is not empty
            if(!n.getChildren().isEmpty()) nodeQueue.addAll(n.getChildren());
        }
        return resultList;
       //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private static List<String> executeOperation(Character operation, List<String> firstArgument, List<String> secondArgument,Map<String, Node> forestMap) {
        
        switch(operation){
            case AND -> {
                return intersection(firstArgument,secondArgument);
            }    
            case OR -> {
                return union(firstArgument,secondArgument);
            }
            case IMPLIES -> {
                return implication(firstArgument,secondArgument,forestMap);
            }
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static List<String> intersection(List<String> firstArgument, List<String> secondArgument) {
        List<String> returnList=new ArrayList<>();
        for(String label: firstArgument){
            //probabile punto di errore
            //should work
            if(secondArgument.contains(label)){
                returnList.add(label);
            }
        }
        return returnList;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static List<String> union(List<String> firstArgument, List<String> secondArgument) {
        Set<String> setApp=new LinkedHashSet<>();
        List<String> returnList=new ArrayList<>();
        /*for(String label: firstArgument){
            //probabile punto di errore
            //should work
            if(secondArgument.contains(label)){
                setApp.add(label);
            }
        }*/
        setApp.addAll(firstArgument);
        setApp.addAll(secondArgument);
        returnList.addAll(setApp);
        return returnList;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static List<String> implication(List<String> firstArgument, List<String> secondArgument,Map<String, Node> forestMap) {
        //F-upset(firstARG-secondARG)
        //with minus -->difference of sets
        //calculate the difference between the two sets
        
        List<String> differenceList=difference(firstArgument,secondArgument);
        List<String> differenceUpsetList=new ArrayList<>();
        //calculate upset of the difference list
        Set differenceAppSet=new LinkedHashSet<>();
        for(String label:differenceList){
            differenceAppSet.addAll(upset(label, forestMap));
        }
        differenceUpsetList.addAll(differenceAppSet);
        //subtract upset list from all forest labels set
        List<String> ForestLabelList=new ArrayList<>();
        ForestLabelList.addAll(forestMap.keySet());
        return difference(ForestLabelList,differenceUpsetList);
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static List<String> difference(List<String> firstArgument, List<String> secondArgument) {
        //remove elements the intersection elements (of the two lists) from first set
        List<String> returnList=new ArrayList<>();
        returnList.addAll(firstArgument);
        for(String label: secondArgument){
            //probabile punto di errore
            //should work
            if(returnList.contains(label)){
                returnList.remove(label);
            }
        }
        return returnList;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static List<String> negation(List<String> inputValuesList, Map<String, Node> forestMap) {
        //calculate upset of variable downset list
        Set varUpset=new LinkedHashSet<>();
        List<String> forestLabelsList=new ArrayList<>(forestMap.keySet());
        for(String label:inputValuesList){
            varUpset.addAll(upset(label, forestMap));
        }
        //apply difference between all forest labels and upset of variable value list
        List<String> varUpsetList=new ArrayList<>(varUpset);
        return difference(forestLabelsList, varUpsetList);
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
