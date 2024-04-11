package filesprocessing.orders;
import java.io.File;

/**
 * Created by rani.aboraya on 5/24/16.
 */
public abstract class Order {

    private static final int NO_MAX = -1;
    File[] filteredFiles;
    int numberOfFiles;

    /**
     * A super class which represents and order in the program.
     * @param result the files that passed the filter proccess.
     */
    public Order(File[] result){
        this.filteredFiles = result;
        this.numberOfFiles = result.length;
    }

    /**
     * This method is responsible for sorting the filtered files based on the
     * specif order type.
     * @return Array of the sorted files.
     */
    public File[] makeOrder() {

        if (this.numberOfFiles == 0) {
            return null;
        }
        File[] sortedFiles = new File[numberOfFiles];
        int maxIndex = NO_MAX;
        for (int j = this.numberOfFiles - 1; j >= 0; j--) {
            for (int i = 0; i < this.numberOfFiles; i++) {
                if (this.filteredFiles[i] != null) {
                    if (maxIndex == NO_MAX) {
                        maxIndex = i;
                    }
                    if(compare(this.filteredFiles[maxIndex], this.filteredFiles[i]) < 0) {
                        maxIndex = i;
                    }
                }
            }

            sortedFiles[j] = this.filteredFiles[maxIndex];
            this.filteredFiles[maxIndex] = null;
            maxIndex = NO_MAX;
        }
        return sortedFiles;
    }


    /**
     * This method reverse the order of the sorted files.
     * @return The array of the sorted files in reverse.
     */
    public File[] makeReverseOrder(){
        File[] files = makeOrder();

        File[] reversedFiles = new File[files.length];
        int j = 0;
        for(int i = files.length - 1; i >= 0; i--){
            reversedFiles[j] = files[i];
            j++;
        }
        return reversedFiles;
    }


    /**
     * The compare method which is implemented by every order class, and it manage the
     * sorting process.
     * @param file the first file to compare.
     * @param another the other file to compare.
     * @return 1 if the first file is bigger, -1 if the other file is bigger, 0 otherwise.
     */
    public abstract int compare(File file, File another);
}
