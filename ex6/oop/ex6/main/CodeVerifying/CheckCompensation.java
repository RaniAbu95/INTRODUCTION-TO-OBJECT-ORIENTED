package oop.ex6.main.CodeVerifying;

import oop.ex6.main.Objects.Variable;

import java.io.File;
import java.io.IOException;

/**
 * A class that checks an assignment between two variables. It has a function that tells if this assignment is
 * legal or not.
 */
public class CheckCompensation {
    private File file;
    private String[] allLines;

    /**
     * The constructor of the class.
     * @param file the file that we are working with.
     */
    public CheckCompensation(File file){
        this.file = file;
    }

    /**
     * This method iterates over all the defined methods and checks if the varObject that it receives is found
     * and has the suitable attributes.
     * @param varObject the variable to check.
     * @return true if the var it takes (placed right after the "=") is legal, false otherwise.
     * @throws IOException  throws the java I.O exception if an error occurs.
     */
    public boolean getVariable(Variable varObject) throws IOException {
        Variable[] vars = new ReadAndValidateData(this.file).createVariables();
        for(int i=0; i < vars.length;i++){
            if(varObject != null) {
                if (varObject.equal(vars[i]))
                    return true;
            }
        }
        return false;
    }


}
