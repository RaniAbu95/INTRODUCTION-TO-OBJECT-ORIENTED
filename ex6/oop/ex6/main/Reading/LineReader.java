package oop.ex6.main.Reading;

import java.util.LinkedList;

/**
 * Created by rani.aboraya on 6/9/16.
 * This class manage manage the reading of a line at the text, it has a number of methods to ease the reading
 * of a line and to make it easier for us to determine which regex should take the line.
 */

public class LineReader {
    private String line;

    private static final String EMPTY_STRING = "";

    /**
     * A constructor of the class.
     * @param line a line in the s-java file.
     */
    public LineReader(String line){
        this.line = line;
    }

    /**
     * A method that cut all the white-spaces in the line and return an array that holds all the words in the
     * line.
     * @return an array that stores all the words in the line as Strings.
     */
    public String[] pureLine(){
        String newString = EMPTY_STRING;
        LinkedList<String> line = new LinkedList<String>();
        for(int i = 0; i < this.line.length(); i++){
            char c = this.line.charAt(i);
            if(c != ' ' && c != '\t' && c != '\n'){
                newString = newString + c;
            }
            else if (!newString.equals("")){
                line.add(newString);
                newString = "";
            }
            if(i == this.line.length()-1 && !newString.equals("")){
                line.add(newString);
                newString = "";
            }
        }
        return line.toArray(new String[line.size()]);

    }


    /**
     * A function that return the first expression in the line.
     * @return a String which is the first word that appearce in the line.
     */
    public String beginingOfLine(){
        String[] line = this.pureLine();
        if(line.length >0) {
            if (line[0].equals("final")) {
                return line[1];
            } else {
                if (line[0].length() >= 2) {
                    if (line[0].charAt(0) == '/' && line[0].charAt(1) == '/') {
                        return "//";

                    } else {
                        if (line[0].charAt(0) == 'i' && line[0].charAt(1) == 'f')
                            return "if";
                    }
                }if (line[0].length() >= 5) {
                    if (line[0].substring(0, 5).equals("while")) {
                        return "while";
                    }
                }
                return line[0];
            }
        }
        return "";
    }


}
