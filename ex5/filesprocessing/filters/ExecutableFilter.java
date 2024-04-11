package filesprocessing.filters;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by rani.aboraya on 5/24/16.
 */
public class ExecutableFilter implements Filter {

    private File directory;
    private CounterFilter counterFilter;
    private File[] allFiles;
    private LinkedList<File> filesToReturn;
    private Boolean isExecutable;
    private static final String EXECUTABLE = "YES";

    /**
     * The constructor of the class which creates an "executable" filter.
     * @param dir The directory which we want to filter its files.
     * @param status this String is the word "YES" or "NO" which indicates whether
     * to check if the file is executable or not.
     */
    public ExecutableFilter(File dir, String status) {
        this.directory = dir;
        this.allFiles = dir.listFiles();
        this.filesToReturn  = new LinkedList<File>();
        if(status.equals(EXECUTABLE)){
            this.isExecutable = true;
        }
        else {
            this.isExecutable = false;
        }

    }


    /**
     * This function creates the filtered files.
     * @return Array of filtered files.
     */
    public File[] makeFilter() {

        for (File file: this.allFiles){

            if(this.isExecutable){
                if(file.isFile() && file.canExecute()){
                    this.filesToReturn.add(file);

                }
            }
            else{
                if(!file.canExecute() && file.isFile()){
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
     */
    public File[] negativeFilter(){
        return new CounterFilter(this.directory, makeFilter()).oppositeFilter();
    }

}
