package filesprocessing.filters;


import filesprocessing.exceptions.Warnings;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by rani.aboraya on 5/24/16.
 */
public class HiddenFilter implements Filter {

    private File directory;
    private File[] allFiles;
    private LinkedList<File> filesToReturn;
    private Boolean isHidden;
    private static final String HIDDEN = "YES";

    /**
     * The constructor of the class which creates an "hidden" filter.
     * @param dir The directory which we want to filter its files.
     * @param status this String is the word "YES" or "NO" which indicates whether
     * to check if the file is hidden or not.
     */
    public HiddenFilter(File dir, String status) {
        this.directory = dir;
        this.allFiles = dir.listFiles();
        this.filesToReturn = new LinkedList<File>();
        if (status.equals(HIDDEN)){
            this.isHidden = true;
        }
        else {
            this.isHidden = false;
        }
    }

    /**
     * This function creates the filtered files.
     * @return Array of filtered files.
     * @throws Warnings throws a warning if the vals are illegalble.
     */
    public File[] makeFilter() {
        for (File file: this.allFiles){
            if(file.isFile()) {
                if (this.isHidden) {
                    if (file.isHidden()) {
                        this.filesToReturn.add(file);
                    }
                } else {
                    if (!file.isHidden()) {
                        this.filesToReturn.add(file);
                    }
                }
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
