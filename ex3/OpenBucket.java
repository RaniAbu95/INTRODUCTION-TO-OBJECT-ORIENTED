import java.util.Iterator;
import java.util.LinkedList;

/**
 * A class that represents a bucket in the OpenHashSet.
 * Created by Rani on 3/28/2016.
 */


public class OpenBucket {
    private LinkedList <String> linkedListBucket;
    private int bucketSize;

    /**
     * The constructor of the class.
     */
    public OpenBucket(){
        this.linkedListBucket = new LinkedList<>();
        this.bucketSize = 0;
    }

    /**
     * Adds the given string to the linked list within the bucket.
     * @param newValue the value we want to add.
     * @return true if the value was added successfully, false otherwise.
     */
    public boolean add(String newValue){
        if(this.linkedListBucket.add(newValue)){
            this.bucketSize += 1;
            return true;
        }
        return false;

    }

    /**
     * Checks if the given string is in the linked list within the bucket.
     * @param searchVal The value to check.
     * @return true if the value was is in the linked list , false otherwise.
     */
    public boolean contains(String searchVal){
        return (this.linkedListBucket.contains(searchVal));
    }

    /**
     * Delets the given string from the linked list within the bucket.
     * @param toDelete The value we want to delete.
     * @return True if the value was deleted successfully, False otherwise.
     */
    boolean delete(String toDelete){
        if(this.linkedListBucket.remove(toDelete)){
            this.bucketSize -= 1;
            return true;
        }
            return false;
    }

    /**
     * A getter for an iterator for the bucket(the linked list in the bucket).
     * @return An iterator for the open bucket.
     */
    public Iterator getBucketIterator(){
        return this.linkedListBucket.iterator();
    }

}
