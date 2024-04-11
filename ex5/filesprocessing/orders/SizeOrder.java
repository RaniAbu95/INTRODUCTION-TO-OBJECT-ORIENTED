package filesprocessing.orders;

import java.io.File;


/**
 * A class that responsible for sorting the files by the files size.
 * Created by rani.aboraya on 5/26/16.
 */
public class SizeOrder extends Order{

    private AbsOrder absOrder;
    public SizeOrder(File[] filtered){
        super(filtered);
        this.absOrder = new AbsOrder(filtered);
    }

    /**
     * This method compares the files name by its size.
     * @param first the first file to compare.
     * @param second the other file to compare.
     * @return 1 if the first file is bigger, -1 if the other file is bigger, 0 otherwise.
     */
    public int compare(File first, File second){
        double firstSize = first.length();
        double secondSize = second.length();
        if(firstSize > secondSize){
            return 1;
        }
        else if(secondSize > firstSize){
            return -1;
        }
        else {
            return first.getName().compareTo(second.getName());
            //return this.absOrder.compare(first,second);
        }
    }

}
