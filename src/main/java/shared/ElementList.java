/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Paolo
 */
public class ElementList {

    private List<String> elements;

    public ElementList() {
        elements = new ArrayList<>();
    }

    public List<String> getElements() {
        return elements;
        
    }

    public int compareLists(ElementList e) {
        //returns the number of elements that are in common with the two lists
        int output = 0;
        output = elements.stream().filter((element) -> (e.getElements().contains(element))).map((_item) -> 1).reduce(output, Integer::sum);
        return output;
    }

    public ElementList mergeLists(ElementList secondElementList) {
        Set<String> mergedList = new HashSet<>();
        mergedList.addAll(elements);
        mergedList.addAll(secondElementList.getElements());
        ElementList output = new ElementList();
        output.getElements().addAll(mergedList);
        return output;
    }

    @Override
    public String toString() {
        if (elements.isEmpty()) {
            return "{}";
        }

//            String output="{";
        String output = "";
//            int i=1;
        for (int i = 0; i < elements.size() - 1; i++) {
            output = output + elements.get(i) + ",";
        }

//            while(i<elements.size()-2){
//                    output=output+elements.get(i)+",";
//            }
//            output=output+elements.get(elements.size()-1)+"}";
        output = output + elements.get(elements.size() - 1);
        return output;

    }

    public String printSet() {
        if (elements.isEmpty()) {
            return "{}";
        }

//            String output="{";
        String output = "{";
//            int i=1;
        for (int i = 0; i < elements.size() - 1; i++) {
            if (elements.get(i).compareTo("") == 0) {
                output = output + "{}" + ",";
            } else {
                output = output + elements.get(i) + ",";
            }
//                output=output+elements.get(i)+",";
        }
//            while(i<elements.size()-2){
//                    output=output+elements.get(i)+",";
//            }
//            output=output+elements.get(elements.size()-1)+"}";
        if (elements.get(elements.size() - 1).compareTo("") == 0) {
            output = output + "{}" + ",";
        } else {
            output = output + elements.get(elements.size() - 1) + "}";
        }
//            output=output+elements.get(elements.size()-1)+"}";
        return output;

    }

    @Override
    public boolean equals(Object o) {
        return this.toString().compareTo(o.toString()) == 0;
    }
   
    public static boolean containsElementList(Set<ElementList> elementLists, ElementList element) {
        return elementLists.stream().anyMatch((e) -> (e.equals(element)));
    }
}
