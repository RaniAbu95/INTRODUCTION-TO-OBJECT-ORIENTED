package filesprocessing.exceptions;

/**
 * An warnings class which extends the Exception and it is responsible for
 * printing the warning messages.
 * Created by Rani on 5/29/2016.
 */
public class Warnings extends Exception{

    /**
     * Constructor for the warnings objects in the program.
     */
    public Warnings(){
        super();
    }

    /**
     * This method prints the warning message in the program.
     * @param line the cause of the warning.
     */
    public void printWarning(int line){
        System.err.println("Warning in line "+ line);
    }
}
