package filesprocessing.filters;

import java.io.File;
import java.util.LinkedList;
import filesprocessing.filters.CounterFilter;

/**
 * Created by rani.aboraya on 5/24/16.
 */
public class AllFilter implements Filter {

    private File directory;
    private File[] allFiles;
    private LinkedList<File> filesToReturn;

    /**
     * The constructor of the class which creates an "all" filter.
     * @param dir The directory which we want to filter its files.
     */
    public AllFilter(File dir) {
        this.directory = dir;
        this.allFiles = dir.listFiles();
        this.filesToReturn  = new LinkedList<File>();
    }

    /**
     * This function creates the filtered files
     * @return Array of filtered files.
     */
    public File[] makeFilter() {
        for(File file : this.allFiles){
            if(file.isFile()){
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
