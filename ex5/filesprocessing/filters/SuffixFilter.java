package filesprocessing.filters;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by rani.aboraya on 5/24/16.
 */
public class SuffixFilter implements Filter {

    private File directory;
    private File[] allFiles;
    private LinkedList<File> filesToReturn ;
    private String part;

    /**
     * The constructor of the class which creates an "suffix" filter.
     * @param dir The directory which we want to filter its files.
     * @param str the String that we want to compare with the end of the files name.
     */
    public SuffixFilter(File dir, String str) {
        this.directory = dir;
        this.allFiles = dir.listFiles();
        this.filesToReturn = new LinkedList<File>();
        this.part = str;
    }

    /**
     *  This function creates the filtered files
     * @return Array of filtered files.
     */
    public File[] makeFilter() {
        for (File file: this.allFiles){
            if(file.getName().endsWith(this.part) && file.isFile()){
                this.filesToReturn.add(file);
            }
        }
        return this.filesToReturn.toArray(new File[this.filesToReturn.size()]);
    }

    /**
     * This function creates the counter filtered files.
     * @return Array of files which contains all the files that didn't pass the filteration.
     */
    public File[] negativeFilter(){
        return new CounterFilter(this.directory, makeFilter()).oppositeFilter();
    }
}
