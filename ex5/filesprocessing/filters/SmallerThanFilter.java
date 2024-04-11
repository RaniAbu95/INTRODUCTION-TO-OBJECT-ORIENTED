package filesprocessing.filters;


import filesprocessing.exceptions.Warnings;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by rani.aboraya on 5/24/16.
 */
public class SmallerThanFilter implements Filter{

    private File directory;
    private File[] allFiles;
    private LinkedList<File> filesToReturn;
    private double toCompare;
    private static final int BYTE = 1024;

    /**
     * The constructor of the class which creates an "smaller_than" filter.
     * @param dir dir The constructor of the class which creates an all filter.
     * @param kb the value that all the files size have to be samller than.
     */
    public SmallerThanFilter(File dir, double kb) {
        this.directory = dir;
        this.allFiles = dir.listFiles();
        this.filesToReturn = new LinkedList<File>();
        this.toCompare = kb;
    }


    /**
     * This function creates the counter filtered files.
     * @return Array of files which contains all the files that didn't pass the filteration.
     * @throws Warnings throws a warning if the vals are illegalble.
     */
    public File[] makeFilter() throws Warnings{
        if(this.toCompare < 0 ){
            throw new Warnings();
        }
        for (File file : this.allFiles) {
            if (file.length() / BYTE < this.toCompare && file.isFile()) {
                this.filesToReturn.add(file);
            }
        }
        return this.filesToReturn.toArray(new File[this.filesToReturn.size()]);

    }

    /**
     * This function creates the counter filtered files.
     * @return Array of files which contains all the files that didn't pass the filteration.
     * @throws Warnings throws a warning if the vals are illegalble.
     */
    public File[] negativeFilter()throws Warnings{
        return new CounterFilter(this.directory, makeFilter()).oppositeFilter();
    }

}
