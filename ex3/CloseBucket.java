/**
 * A class that represents a bucket in the ClosedHashSet.
 * Created by Rani on 4/3/2016.
 */
public class CloseBucket {

    private Boolean deleted;
    private String data;

    /**
     * The class Contsructor.
     * @param data The string that we want to put in the bucket.
     */
    public CloseBucket(String data){
        this.data = data;
        this.deleted = false;
    }

    /**
     * Checks if the data in the bucket is equal to the given data.
     * @param searchVal The data to comparing.
     * @return True if the data in the bucket is equal to the given data, False otherwise.
     */
    public Boolean equalData(String searchVal){
        if(this.data.equals(searchVal)){
            return true;
        }
        return false;
    }

    /**
     * Marks the bucket as deleted.
     * @return true if the delete was made successfully, False otherwise.
     */
    public boolean delete(){
        this.deleted = true;
        return true;
    }

    /**
     * @return return the data in the bucket.
     */
    public String getData(){
        return this.data;
    }

    /**
     * Checks if the bucket is deleted.
     * @return true if the bucked is deleted, False otherwise.
     */
    public Boolean isDataDeleted(){
        return this.deleted;
    }

}
