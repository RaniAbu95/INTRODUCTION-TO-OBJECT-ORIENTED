package filesprocessing.filters;

import java.io.File;
import java.util.LinkedList;


/**
 * Created by rani.aboraya on 5/24/16.
 */
public class FileFilter implements Filter {

    private File directory;
    private File[] allFiles;
    private LinkedList<File> filesToReturn;
    private String nameToCompare;

    /**
     * The constructor of the class which creates an "file" filter.
     * @param dir The directory which we want to filter its files.
     * @param fileName the file name to check.
     */
    public FileFilter(File dir, String fileName) {
        this.directory = dir;
        this.allFiles = dir.listFiles();
        this.nameToCompare = fileName;
        this.filesToReturn = new LinkedList<File>();
    }


    /**
     * This function creates the filtered files.
     * @return Array of filtered files.
     */
    public File[] makeFilter() {
        for (File file: this.allFiles){
            if(file.isFile() && file.getName().equals(this.nameToCompare)){
                this.filesToReturn.add(file);
            }
        }
        int size = this.filesToReturn.size();
        File[] files = this.filesToReturn.toArray(new File[size]);
        return files;
    }

    /**
     * This function creates the counter filtered files.
     * @return Array of files which contains all the files that didn't pass the filteration.
     */
    public File[] negativeFilter(){
        return new CounterFilter(this.directory, makeFilter()).oppositeFilter();
    }

}
