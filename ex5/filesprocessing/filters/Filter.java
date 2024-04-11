package filesprocessing.filters;
import filesprocessing.exceptions.Warnings;

import java.io.File;

/**
 * An interface for the filters classes, this class have two methods which is shared for
 * all the filters.
 * Created by Rani on 5/26/2016.
 */
public interface  Filter {

    File[] makeFilter() throws Warnings;

    File[] negativeFilter() throws Warnings;

}

