/**
 * An abstract class that represents a hash sets. this class contains the basic methods of a simple hash
 * set data structure. this is class is considered as a super class of the SimpleHashSet and the
 * ClosedHashSet.
 * Created by Rani on 3/26/2016.
 */
public abstract class SimpleHashSet implements SimpleSet{
    protected static final int INITIAL_CAPACITY = 16;
    protected static final double DEFAULT_UPPER_LOAD_FACTOR = 0.75;
    protected static final double DEFAULT_LOWER_LOAD_FACTOR = 0.25;
    protected static final int INCREASE_HASH_TABLE = 1;
    protected static final int DECREASE_HASH_TABLE = -1;
    protected static final int RESIZING_FACTOR = 2;
    protected float upperLoadFactor;
    protected float lowerLoadFactor;
    protected int curElements = 0;

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public abstract boolean add(String newValue);

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public abstract boolean contains(String searchVal);

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public abstract boolean delete(String toDelete);

    /**
     * The length of the hash set table.
     * @return the capacity of the hash set.
     */
    protected abstract int capacity();


    /**
     * A getter for the lower load factor which is a the minimal value that the hash set can load.
     * @return return a float number that represents the lower load factor.
     */
    protected float getLowerLoadFactor(){
        return this.lowerLoadFactor;
    }

    /**
     * A getter for the upper lower load factor which is a the maximal value that the hash set can load.
     * @return return a float number that represents the upper load factor.
     */
    protected float getUpperLoadFactor(){
        return this.upperLoadFactor;
    }

    /**
     * @return The number of elements currently in the set
     */
    public int size(){
        return this.curElements;
    }

    /**
     * A getter for the load factor of the hash set. the load factor is defined as the size/capacity.
     * @return float number between 0 and 1 which represents the load factor of the hash set.
     */
    protected float getLoadFactor(){
        return (float)(size()) / (float)(capacity());
    }

}
