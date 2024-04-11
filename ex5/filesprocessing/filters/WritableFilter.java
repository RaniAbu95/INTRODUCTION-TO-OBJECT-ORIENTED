package filesprocessing.filters;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by rani.aboraya on 5/24/16.
 */
public class WritableFilter implements Filter {

    private File directory;
    private File[] allFiles;
    private LinkedList<File> filesToReturn;
    private Boolean isWritable;
    private static final String Writable = "YES";

    /**
     * The constructor of the class which creates an "writable" filter.
     * @param dir The directory which we want to filter its files.
     * @param status this String is the word "YES" or "NO" which indicates whether
     * to check if the file is writable or not.
     */
    public WritableFilter(File dir, String status) {
        this.directory = dir;
        this.allFiles = dir.listFiles();
        this.filesToReturn  = new LinkedList<File>();
        if(status.equals(Writable)){
            this.isWritable = true;
        }
        else{
            this.isWritable = false;
        }
    }

    public File[] makeFilter() {
        for (File file: this.allFiles){
            if(file.isFile()) {
                if (this.isWritable) {
                    if (file.canWrite()) {
                        this.filesToReturn.add(file);
                    }
                } else {
                    if (!file.canWrite()) {
                        this.filesToReturn.add(file);
                    }
                }
            }

        }
        return this.filesToReturn.toArray(new File[this.filesToReturn.size()]);
    }

    public File[] negativeFilter(){
        return new CounterFilter(this.directory, makeFilter()).oppositeFilter();
    }
}
