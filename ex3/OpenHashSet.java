import org.omg.CORBA.INITIALIZE;

import java.util.Iterator;

/**
 * The class represents a set which called OpenHashSet. This data structure is an array which has
 * a linked list within each cell of it. the add, contain, delete takes O(1) on average case.
 * This class extends the SimpleHashSet class and thus it's considered as a sub-class of it.
 * Created by Rani on 3/29/2016.
 */
public class OpenHashSet extends SimpleHashSet {

    private OpenBucket[] openHashTable;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
     * factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet(){
        this.openHashTable = new OpenBucket[SimpleHashSet.INITIAL_CAPACITY];
        this.upperLoadFactor = (float)SimpleHashSet.DEFAULT_UPPER_LOAD_FACTOR;
        this.lowerLoadFactor = (float)SimpleHashSet.DEFAULT_LOWER_LOAD_FACTOR;
        buildArray(this.openHashTable);
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
        this.openHashTable = new OpenBucket[SimpleHashSet.INITIAL_CAPACITY];
        buildArray(this.openHashTable);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75), and
     * lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public OpenHashSet(java.lang.String[] data){
        this.openHashTable = new OpenBucket[SimpleHashSet.INITIAL_CAPACITY];
        this.upperLoadFactor = (float)OpenHashSet.DEFAULT_UPPER_LOAD_FACTOR;
        this.lowerLoadFactor = (float)OpenHashSet.DEFAULT_LOWER_LOAD_FACTOR;
        buildArray(this.openHashTable);
        for(int i = 0; i < data.length; i++){
            if(!this.contains(data[i])){
                this.add(data[i]);
            }
        }
    }

    /**
     * initialize the table (array) of the hash set.
     * @param arrayToBuild the array we want to set.
     */
    private void buildArray(OpenBucket[] arrayToBuild){
        for(int i = 0; i < arrayToBuild.length; i++){
            arrayToBuild[i] = new OpenBucket();
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(String newValue){
        if(contains(newValue)){
            return false;
        }
        int index = (int)(Math.abs(newValue.hashCode()) % capacity());
        this.openHashTable[index].add(newValue);
        this.curElements += 1;
        if(getLoadFactor() > getUpperLoadFactor()){
            rehashing(this.openHashTable, SimpleHashSet.INCREASE_HASH_TABLE);

        }
        return true;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for.
     * @return True iff searchVal is found in the set.
     */
    public boolean contains(String searchVal){
        int index = (Math.abs(searchVal.hashCode()) % capacity());
        return(this.openHashTable[index].contains(searchVal));
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(String toDelete){
        int index = (int)(Math.abs(toDelete.hashCode()) % capacity());
        if(this.openHashTable[index].contains(toDelete)){
            this.openHashTable[index].delete(toDelete);
            this.curElements-=1;
            if(getLoadFactor() < getLowerLoadFactor()) {
                rehashing(this.openHashTable, SimpleHashSet.DECREASE_HASH_TABLE);
            }
            return true;
        }
        return false;
    }

    /**
     * This function is called when ever we have to make rehashing for the table as a result of getting
     * a load factor that is bigger than the upper load or smaller than the lower load which increase and
     * decrease the table respectively.
     * @param tempHashTable The table we want to rehash.
     * @param state A number that tells whether increasing or decreasing is needed.
     */
    private void rehashing(OpenBucket [] tempHashTable, int state){
        int newTableSize;
        if(state == SimpleHashSet.INCREASE_HASH_TABLE){
            newTableSize = capacity() * SimpleHashSet.RESIZING_FACTOR;
        }
        else{
            newTableSize = capacity() / SimpleHashSet.RESIZING_FACTOR;
        }
        OpenBucket[] newHashTable = new OpenBucket[newTableSize];
        buildArray(newHashTable);
        for (int i =0; i < tempHashTable.length; i++) {
            if (tempHashTable[i] != null) {
                Iterator OpenBucketIterator = tempHashTable[i].getBucketIterator();
                while (OpenBucketIterator.hasNext()) {
                    String value = (String) OpenBucketIterator.next();
                    int index = (int)(Math.abs(value.hashCode()) % newTableSize);
                    newHashTable[index].add(value);
                }
            }
        }
        this.openHashTable = null;
        this.openHashTable = newHashTable;
    }

    /**
     * The length of the hash set table.
     * @return the capacity of the hash set.
     */
    public int capacity(){
        return this.openHashTable.length;
    }



}


