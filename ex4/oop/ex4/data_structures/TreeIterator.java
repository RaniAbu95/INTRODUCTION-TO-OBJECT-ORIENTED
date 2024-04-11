package oop.ex4.data_structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by rani.aboraya on 5/7/16.
 * A class that implements an Iterator interface. This class have three method which
 * is hasNext(), next() and remove(). the main purpose of this class is to iterate
 * over the tree values.
 */
public class TreeIterator implements Iterator<Integer> {
    private int numberOfElements;
    private LinkedList<Integer> list;

    /**
     * constructor that build an iterator for the tree.
     * @param treeList A linked list that have all the value within the tree in ascending oreder.
     */
    public TreeIterator(LinkedList<Integer> treeList){
        this.list = treeList;
        this.numberOfElements = list.size();
    }

    /**
     *Returns true if the iteration has more elements. (In other words, returns true if next() would return an
     *  element rather than throwing an exception.)
     * @return true if the iteration has more elements.
     */
    public boolean hasNext(){
        return (this.numberOfElements != 0);
    }

    /**
     * Returns the next element in the iteration.Throw NoSuchElementException if the iteration has no more elements
     * @return the next element in the iteration
     */
    public Integer next(){
        if(hasNext()){
            this.numberOfElements --;
            return this.list.pop();

        }
        throw new NoSuchElementException();
    }

    /**
     * Throw UnsupportedOperationException as the remove operation is not supported by this iterator
     */
    public void remove(){
        throw new UnsupportedOperationException();
    }

}
