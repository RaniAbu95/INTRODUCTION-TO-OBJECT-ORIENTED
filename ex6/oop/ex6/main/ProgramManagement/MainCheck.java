package oop.ex6.main.ProgramManagement;

import oop.ex6.main.CodeVerifying.FinalsAndReputationErrs;
import oop.ex6.main.CodeVerifying.ReadAndValidateData;
import oop.ex6.main.CodeVerifying.RegexFactory;
import oop.ex6.main.Reading.ReadFile;
import oop.ex6.main.SjavaExcetions.SjavaExceptions;

import java.io.File;
import java.io.IOException;

/**
 * This class iterates over the file lines and checks the following things:
 * - runs through the regexes in each line of the file.
 * - runs through the variables name reputation and legality of final vars.
 * - runs through the definition  of the variables and functions and all
 * the assignment that appears in the file in addition to the functions calls
 * and checks the legality of all the above.
 */
public class MainCheck {
    private File file;

    /**
     * A constructor for the class.
     * @param file the S-java file.
     */
    public MainCheck(File file){
        this.file = file;
    }

    /**
     * This function the order of the checks, and returns true if the file is A legal S-java file.
     * @return true if the file that we check is an S-java file, throws exception otherwise.
     * @throws IOException IOException throws the java I.O exception if an error occurs.
     * @throws SjavaExceptions throws an exception if the file is illegal.
     */
    public boolean manager() throws IOException ,SjavaExceptions{
        String[] lines = new ReadFile(this.file).readFile();
        for(String line:lines){
            RegexFactory regexFactory = new RegexFactory(line);
            if(!regexFactory.vererfier()){
                throw new SjavaExceptions();
            }
        }

        FinalsAndReputationErrs finalsAndReputationErrs = new FinalsAndReputationErrs(this.file);
        if(!finalsAndReputationErrs.checkVarsNamesReputation() || !finalsAndReputationErrs.badFinalAssignment() ||
            !finalsAndReputationErrs.checkIfAndWhile()) {
            throw new SjavaExceptions();
        }

        ReadAndValidateData readAndFillData = new ReadAndValidateData(this.file);
        readAndFillData.createVariables();
        if(!readAndFillData.makeAssignmentVars()) {
            throw new SjavaExceptions();
        }

        return true;

    }
}
