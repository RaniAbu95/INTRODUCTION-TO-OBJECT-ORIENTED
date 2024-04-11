package filesprocessing.filters;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by Rani on 5/27/2016.
 */
public class CounterFilter {

    private File directory;
    private File [] directoryFiles;
    private File[] filteredFiles;

    /**
     * A constructor for the counter filter.
     * @param dir The directory which we want to filter its files.
     * @param filteredFiles The array of the filtered files.
     */
    public CounterFilter(File dir , File[] filteredFiles) {
        this.directory = dir;
        this.directoryFiles = dir.listFiles();
        this.filteredFiles =filteredFiles;
    }

    /**
     * This function return the files doesn't fulfill the filter condition.
     * @return the Array of files that doesn't fulfill the filter condition.
     */
    public File[] oppositeFilter(){
        LinkedList<File> notFiltered = new LinkedList<File>();
        boolean notContained = true;

        for(int i = 0; i < this.directoryFiles.length; i++){
            notContained = true;
            for(File file: this.filteredFiles){
                if(file.equals(this.directoryFiles[i]) || !this.directoryFiles[i].isFile()){
                    notContained = false;
                }
            }
            if(notContained){
                notFiltered.add(this.directoryFiles[i]);
            }
        }
        int size = notFiltered.size();
        return notFiltered.toArray(new File[size]);
    }
}

