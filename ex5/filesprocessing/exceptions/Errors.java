package filesprocessing.exceptions;

/**
 * an Eroors class which extends the Exception and it is responsible for
 * printing the errors messages.
 * Created by Rani on 5/30/2016.
 */


public class Errors extends Exception{
    /**
     * Constructor for the errors objects in the program.
     */
    public Errors(){
        super();
    }

    /**
     * This method prints the error message in the program.
     * @param errorType the cause of the error.
     */
    public void printError(String errorType){
        System.err.println("ERROR: error in "+errorType);
    }
}
