package com.ultra.vision.implementations;

import java.util.*;

public class GFG2 {

    // Function to remove duplicates from an ArrayList
    public static List<String> removeDuplicates(List<String> list)
    {

        // Create a new LinkedHashSet
        Set<String> set = new LinkedHashSet<>();

        // Add the elements to set
        set.addAll(list);

        // Clear the list
        list.clear();

        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);

        // return the list
        return list;
    }

    // Driver code
    /*public static void main(String args[])
    {

        // Get the ArrayList with duplicate values
        ArrayList<Integer>
            list = new ArrayList<>(
			Arrays
			.asList(1, 10, 1, 2, 2, 3, 10, 3, 3, 4, 5, 5));

        // Print the Arraylist
        System.out.println("ArrayList with duplicates: "
                           + list);

        // Remove duplicates
        ArrayList<String>
            newList = removeDuplicates(list);

        // Print the ArrayList with duplicates removed
        System.out.println("ArrayList with duplicates removed: "
                           + newList);
    }*/
}
