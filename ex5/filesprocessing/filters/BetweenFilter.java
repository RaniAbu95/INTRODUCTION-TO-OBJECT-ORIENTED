package filesprocessing.filters;
import filesprocessing.exceptions.Warnings;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by rani.aboraya on 5/24/16.
 */
public class BetweenFilter implements Filter {

    private File directory;
    private File[] allFiles;
    private LinkedList<File> filesToReturn;
    private double firstVal;
    private double secondVal;
    private static final double BYTE = 1024;

    /**
     * The constructor of the class which creates an "between" filter.
     * @param dir The directory which we want to filter its files.
     * @param first The smaller files size.
     * @param second the biggest file size.
     */
    public BetweenFilter(File dir, double first, double second) {
        this.directory = dir;
        this.allFiles = dir.listFiles();
        this.firstVal = first;
        this.secondVal = second;
        this.filesToReturn  = new LinkedList<File>();
    }


    /**
     * This function creates the filtered files.
     * @return Array of filtered files.
     * @throws Warnings throws a warning if the vals are illegalble.
     */
    public File[] makeFilter() throws Warnings {
        if(this.firstVal < 0 || this.secondVal < 0 ){
            throw new Warnings();
        }
        else if(this.firstVal > this.secondVal){
            throw new Warnings();
        }
        for (File file : this.allFiles) {
            if(file.isFile()) {
                if (file.length() / BYTE >= this.firstVal && file.length() / BYTE <= this.secondVal) {
                    this.filesToReturn.add(file);
                }
            }
        }
        int size = this.filesToReturn.size();
        return this.filesToReturn.toArray(new File[size]);
    }

    /**
     * This function creates the counter filtered files.
     * @return Array of files which contains all the files that didn't pass the filteration.
     * @throws Warnings throws a warning if the vals are illegalble.
     */
    public File[] negativeFilter() throws Warnings{
        return new CounterFilter(this.directory, makeFilter()).oppositeFilter();
    }

}
