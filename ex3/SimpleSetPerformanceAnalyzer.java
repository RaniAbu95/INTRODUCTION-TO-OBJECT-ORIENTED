/**
 * A class that have the five data structures. this class tests the performance for all of the
 * data structures by adding two files containing strings to each of them and checks if they
 * contains the mentioned words by calling the "contains()" method for each of the data
 * structures.
 *
 * Created by Rani on 4/4/2016.
 */

import java.util.*;

public class SimpleSetPerformanceAnalyzer {

    private long timeBefore, timeAfter;

    private String[] dataOne = Ex3Utils.file2array("data1.txt");
    private String[] dataTwo = Ex3Utils.file2array("data2.txt");

    private OpenHashSet openHashSet = new OpenHashSet();
    private ClosedHashSet closeHashSet = new ClosedHashSet();
    private SimpleSet linkedList = new CollectionFacadeSet (new LinkedList<String>());
    private SimpleSet treeSet = new CollectionFacadeSet (new TreeSet<String>());
    private SimpleSet hashSet = new CollectionFacadeSet (new HashSet<String>());

    private SimpleSet[] dataStructures = {openHashSet, closeHashSet,treeSet, linkedList, hashSet};
    private String[] dataStructuresNames = {"openHashSet", "closeHashSet", "treeSet", "linkedList", "hashSet"};

    static final int DATA_NUMBER_ONE = 1;
    static final int DATA_NUMBER_TWO = 2;

    static final int TO_MILL_SECONDS = 1000000;
    static final int CASES_TO_TEST = 6;


    public SimpleSetPerformanceAnalyzer(){
        clearDataStructures();
    }
    /**
     * clear all the data structures.
     */

    public void clearDataStructures(){
        this.openHashSet = new OpenHashSet();
        this.closeHashSet = new ClosedHashSet();
        this.linkedList = new CollectionFacadeSet (new LinkedList<String>());
        this.treeSet = new CollectionFacadeSet (new TreeSet<String>());
        this.hashSet = new CollectionFacadeSet (new HashSet <String>());

        dataStructures[0] = openHashSet;
        dataStructures[1] = closeHashSet;
        dataStructures[2] = treeSet;
        dataStructures[3] = linkedList;
        dataStructures[4] = hashSet;


}


    /**
     * adds the the given data array to a data structure.
     * @param data the data array to add.
     * @param indexInArray the index of the specific data structure in the array.
     * @param dataNumber the number of the data we want to add.
     */
    public void addToDataStructure(String [] data, int indexInArray, int dataNumber){
        timeBefore = System.nanoTime();
        System.out.println("The time it takes to add data "+dataNumber+" to -"+ dataStructuresNames[indexInArray]+"-");
        for(int i =0; i < data.length; i++){
            dataStructures[indexInArray].add(data[i]);
        }

        timeAfter = System.nanoTime() - timeBefore;
        System.out.println(timeAfter / TO_MILL_SECONDS);
    }

    /**
     *  This method checks if the data structure contains the given string and print the time to perform this method.
     * @param indexOfdataStructure the index of the given data structure in the array.
     * @param valueToCheck the value we want to check if it's within the data structure.
     * @param theInitializedData the data that was initialized.
     */
    public void contains(int indexOfdataStructure, String valueToCheck, int theInitializedData){
        long timeBefore = System.nanoTime();
        System.out.println("The time it takes to check if -" +
                dataStructuresNames[indexOfdataStructure]+"- contains "+ valueToCheck+" when initialized with data "+theInitializedData);
        dataStructures[indexOfdataStructure].contains(valueToCheck);
        long timeAfter = System.nanoTime() - timeBefore;
        System.out.println(timeAfter);
    }

    /**
     * The main method which checks adds data1 and data2 to all the data structures, and make some checks using the
     * contains method.
     * @param args
     */
    public static void main(String[] args) {
        SimpleSetPerformanceAnalyzer Analyzer = new SimpleSetPerformanceAnalyzer();
        for (int k = 0; k < CASES_TO_TEST; k++) {
            for (int i = 0; i < Analyzer.dataStructures.length; i++) {
                if(k == 0){
                    Analyzer.addToDataStructure(Analyzer.dataOne, i, DATA_NUMBER_ONE);
                }
                if(k == 1){
                    Analyzer.contains(i, "hi", DATA_NUMBER_ONE);
                }
                if(k == 2){
                    Analyzer.contains(i, "-13170890158",DATA_NUMBER_ONE);
                }
                if(k == 3){
                Analyzer.clearDataStructures();
                Analyzer.addToDataStructure(Analyzer.dataTwo, i, DATA_NUMBER_TWO);
                }
                if(k == 4){
                Analyzer.contains(i, "23", DATA_NUMBER_TWO);
                }
                if(k == 5){
                    Analyzer.contains(i, "hi", DATA_NUMBER_TWO);
                }
            }
        }
    }
}




