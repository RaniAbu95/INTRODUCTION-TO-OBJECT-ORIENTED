package filesprocessing.filters;
import filesprocessing.exceptions.Warnings;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by rani.aboraya on 5/24/16.
 */
public class GreaterThanFilter implements Filter {

    private File[] allFiles;
    private LinkedList<File> filesToReturn;
    private double valToCompare;
    private static final int BYTE = 1024;
    private File directory;

    /**
     * The constructor of the class which creates an "greater_than" filter.
     * @param dir The constructor of the class which creates an all filter.
     * @param kb the value that all the files size have to be above.
     */
    public GreaterThanFilter(File dir, double kb) {
        this.directory = dir;
        this.allFiles = dir.listFiles();
        this.filesToReturn = new LinkedList<File>();
        this.valToCompare = kb;
    }


    /**
     * This function creates the counter filtered files.
     * @return Array of files which contains all the files that didn't pass the filteration.
     * @throws Warnings throws a warning if the vals are illegalble.
     */
    public File[] makeFilter() throws Warnings {
        if(this.valToCompare < 0 ){
            throw new Warnings();
        }
        for (File file : this.allFiles) {
            if (file.isFile() && file.length() / BYTE >= this.valToCompare) {
                this.filesToReturn.add(file);
            }
        }
        int numOfFiles = this.filesToReturn.size();
        return this.filesToReturn.toArray(new File[numOfFiles]);

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
