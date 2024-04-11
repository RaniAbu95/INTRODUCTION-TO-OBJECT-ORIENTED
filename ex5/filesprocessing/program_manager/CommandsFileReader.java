package filesprocessing.program_manager;

import filesprocessing.exceptions.Errors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by rani.aboraya on 5/24/16.
 */
public class CommandsFileReader {
    private File file;
    /**
     * This Constructor creates an object that is responsible fot reading the file commands.
     * @param file The file commands
     */
    public CommandsFileReader(File file){
        this.file  = file;
    }

    /**
     * This methods iterates over the lines in the file commands.
     * @return An String array that contains all the lines in the file Commands.
     * @throws IOException throws io exception if and system err is occures.
     * @throws Errors throws an error message which prints an err to the user.
     */
    public String[] readFile() throws IOException, Errors {
        try {
            LinkedList<String> listOfLines = new LinkedList<String>();
            BufferedReader commandsFile = new BufferedReader(new FileReader(this.file));
            String line = null;
            while ((line = commandsFile.readLine()) != null) {
                listOfLines.add(line);
            }
            int numLines = listOfLines.size();
            return listOfLines.toArray(new String[numLines]);
        }
        catch (IOException e){
            System.out.print(e.getMessage());
            return new String[0];
        }
    }

}
