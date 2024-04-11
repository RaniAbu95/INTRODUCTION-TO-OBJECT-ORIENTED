package oop.ex6.main.Reading;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * A class that reads a File object (S-java file) and returns all its lines.
 */

public class ReadFile {
    private File file;

    /**
     * the Constructor of the class.
     * @param file The S-java file.
     */
    public ReadFile(File file){
        this.file  = file;
    }

    /**
     * A method that iterates over all the lines of the text and return all of them.
     * @return A string array that contains all the lines in the text.
     * @throws IOException throws the java I.O exception if an error occurs while reading the file.
     */
    public String[] readFile() throws IOException {
        try {
            LinkedList<String> listOfLines = new LinkedList<String>();
            BufferedReader commandsFile = new BufferedReader(new java.io.FileReader(this.file));
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
