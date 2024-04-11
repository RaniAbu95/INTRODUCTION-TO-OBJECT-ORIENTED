package filesprocessing.filters;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by rani.aboraya on 5/24/16.
 */
public class PrefixFilter implements Filter {

    private File directory;
    private File[] allFiles;
    private LinkedList<File> filesToReturn;
    private String part;

    /**
     * The constructor of the class which creates an "prefix" filter.
     * @param dir The directory which we want to filter its files.
     * @param str the String that we want to compare with the begginings of the files name.
     */
    public PrefixFilter(File dir, String str) {
        this.directory = dir;
        this.allFiles = dir.listFiles();
        this.part = str;
        this.filesToReturn = new LinkedList<File>();
    }


    /**
     * This function creates the filtered files
     * @return Array of filtered files.
     */
    public File[] makeFilter() {
        for (File file : this.allFiles) {
            if (file.getName().startsWith(this.part) && file.isFile()) {
                this.filesToReturn.add(file);
            }
        }
        int numOfFiles = this.filesToReturn.size();
        return this.filesToReturn.toArray(new File[numOfFiles]);
    }

    /**
     * This function creates the counter filtered files.
     * @return Array of files which contains all the files that didn't pass the filteration.
     */
    public File[] negativeFilter(){
        return new CounterFilter(this.directory, makeFilter()).oppositeFilter();
    }
}
