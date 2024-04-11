package filesprocessing.filters;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by rani.aboraya on 5/24/16.
 */
public class ContainsFilter implements Filter {

    private File directory;
    private File[] allFiles;
    private LinkedList<File> filesToReturn;
    private String part;

    /**
     * The constructor of the class which creates "contains" filter.
     * @param dir The directory which we want to filter its files.
     * @param str the word that we want to check for.
     */
    public ContainsFilter(File dir, String str) {
        this.directory = dir;
        this.allFiles = dir.listFiles();
        this.part = str;
        this.filesToReturn  = new LinkedList<File>();
    }


    /**
     * This function creates the filtered files.
     * @return Array of filtered files.
     */
    public File[] makeFilter() {
        for (File file: this.allFiles){
            if(file.isFile() && file.getName().contains(this.part)){
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
