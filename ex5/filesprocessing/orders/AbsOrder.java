package filesprocessing.orders;
import java.io.File;



/**
 * A class that responsible for sorting the files by the ABS order.
 * Created by rani.aboraya on 5/24/16.
 */
public class AbsOrder extends Order{

    public AbsOrder(File[] filtered){

        super(filtered);
    }

    /**
     * This method compares the files name by its abc order.
     * @param first the first file to compare.
     * @param second the other file to compare.
     * @return 1 if the first file is bigger, -1 if the other file is bigger, 0 otherwise.
     */
    public int compare(File first, File second){
        String firstFileName = first.getAbsolutePath().toString();
        String secondFileName = second.getAbsolutePath().toString();
        if(firstFileName.compareTo(secondFileName) > 0){
            return 1;
        }
        else if (firstFileName.compareTo(secondFileName) < 0){
            return -1;
        }
        else{
            return 0;
        }
    }

}
