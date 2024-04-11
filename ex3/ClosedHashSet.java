/**
 * The class represents a set which called ClosedHashSet. This data structure is an array which has
 * an object of type ClosedBucket . the add, contain, delete takes O(1) on average case.
 * This class extends the SimpleHashSet class and thus it's considered as a sub-class of it.
 * Created by Rani on 4/2/2016.
 */
public class ClosedHashSet extends SimpleHashSet{

    private CloseBucket [] closeHashTable;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet(){
        this.closeHashTable = new CloseBucket[SimpleHashSet.INITIAL_CAPACITY];
        this.upperLoadFactor = (float)SimpleHashSet.DEFAULT_UPPER_LOAD_FACTOR;
        this.lowerLoadFactor = (float)SimpleHashSet.DEFAULT_LOWER_LOAD_FACTOR;
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){
        this.closeHashTable = new CloseBucket[SimpleHashSet.INITIAL_CAPACITY];
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data){
        this.closeHashTable = new CloseBucket[SimpleHashSet.INITIAL_CAPACITY];
        this.upperLoadFactor = (float)SimpleHashSet.DEFAULT_UPPER_LOAD_FACTOR;
        this.lowerLoadFactor = (float)SimpleHashSet.DEFAULT_LOWER_LOAD_FACTOR;
        for(int i = 0; i < data.length; i++){
            if(!this.contains(data[i])){
                this.add(data[i]);
            }
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
        for(int i = 0; i < capacity(); i++){
            int index = (int)((Math.abs((long) newValue.hashCode()) + (i + (long) i * i)/2) % capacity());
            if(this.closeHashTable[index] == null || this.closeHashTable[index].isDataDeleted()){
                this.closeHashTable[index] = null;
                this.closeHashTable[index] = new CloseBucket(newValue);
                this.curElements +=1;
                if(getLoadFactor() > getUpperLoadFactor()){
                    rehashing(this.closeHashTable, SimpleHashSet.INCREASE_HASH_TABLE);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for.
     * @return True iff searchVal is found in the set.
     */
    public boolean contains(String searchVal){
        for(int i = 0; i < capacity(); i++){
            int index = (int)((Math.abs((long) searchVal.hashCode()) + (i + (long) i * i)/2) % capacity());
            if(this.closeHashTable[index] == null){
                return false;
            }
            else if (this.closeHashTable[index].equalData(searchVal)){
                if(this.closeHashTable[index].isDataDeleted()){
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        if(!contains(toDelete)){
            return false;
        }
        for (int i = 0; i < capacity(); i++) {
            int index = (int)((Math.abs((long) toDelete.hashCode()) + (i + (long) i * i)/2) % capacity());
            if(this.closeHashTable[index].equalData(toDelete) && !this.closeHashTable[index].isDataDeleted()){
                this.closeHashTable[index].delete();
                this.curElements -=1;
                if(getLoadFactor() < getLowerLoadFactor()){
                    rehashing(this.closeHashTable, SimpleHashSet.DECREASE_HASH_TABLE);
                }
                return true;
            }
        }
        return false;
    }

    // Helper function that puts the elements(strings) in the new table after the rehashing.
    private void hashToNewTable(CloseBucket [] newHashTable, String valueToAdd){
        for(int j = 0; j < newHashTable.length; j++){
            int index = (int)((Math.abs((long) valueToAdd.hashCode()) + (j + (long) j * j)/2) % newHashTable.length);
            if(newHashTable[index] == null){
                newHashTable[index] = new CloseBucket(valueToAdd);
                break;
            }
        }
    }

    /**
     * This function is called when ever we have to make rehashing for the table as a result of getting
     * a load factor that is bigger than the upper load or smaller than the lower load which increase and
     * decrease the table respectively.
     * @param tempHashTable The table we want to rehash.
     * @param state A number that tells whether increasing or decreasing is needed.
     */
    private void rehashing(CloseBucket [] tempHashTable, int state){
        int newTableSize;
        if(state == SimpleHashSet.INCREASE_HASH_TABLE){
            newTableSize = capacity() * SimpleHashSet.RESIZING_FACTOR;
        }
        else{
            newTableSize = capacity() / SimpleHashSet.RESIZING_FACTOR;
        }
        CloseBucket[] newHashTable = new CloseBucket[newTableSize];
        for (int i = 0; i < tempHashTable.length; i++) {
            if(tempHashTable[i] != null && !tempHashTable[i].isDataDeleted()){
                String valToHash = tempHashTable[i].getData();
                hashToNewTable(newHashTable, valToHash);
            }


        }
        this.closeHashTable = null;
        this.closeHashTable = newHashTable;
        }

    /**
     * The length of the hash set table.
     * @return the capacity of the hash set.
     */
    public int capacity(){
        return this.closeHashTable.length;
    }

}
