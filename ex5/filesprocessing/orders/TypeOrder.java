package filesprocessing.orders;

import java.io.File;
import java.util.LinkedList;

/**
 * A class that responsible for sorting the files by the files types name size.
 * Created by rani.aboraya on 5/26/16.
 */
public class TypeOrder extends Order {

    private AbsOrder absOrder;
    public TypeOrder(File[] filtered){
        super(filtered);
        this.absOrder = new AbsOrder(filtered);
    }

    /**
     * This method compares the files name by the type file name.
     * @param first the first file to compare.
     * @param second the other file to compare.
     * @return 1 if the first file is bigger, -1 if the other file is bigger, 0 otherwise.
     */
    public int compare(File first, File second){
        String firstFileType = first.getName().substring(first.getName().lastIndexOf(".") + 1);
        String secondFileType = second.getName().substring(second.getName().lastIndexOf(".") + 1);

        if(firstFileType.compareTo(secondFileType) > 0){
            return 1;
        }
        else if (firstFileType.compareTo(secondFileType) < 0){
            return -1;
        }
        else{
            return this.absOrder.compare(first,second);
        }
    }

}
