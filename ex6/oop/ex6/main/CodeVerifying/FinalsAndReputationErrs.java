package oop.ex6.main.CodeVerifying;

import oop.ex6.main.Reading.LineReader;
import oop.ex6.main.Reading.ReadFile;
import oop.ex6.main.Objects.Variable;

import java.io.File;
import java.io.IOException;

/**
 *The main goal of this class is to determine if the Sjava file doesn't contain illegal variables names
 * reputation and that finals variable is initialized with a val and that val doesn't change all along the
 * file.
 */

public class FinalsAndReputationErrs {
    private  File file;
    private ReadAndValidateData data;
    private Variable[] definedVars;
    private static  final String GLOBAL_SCOPE = "globalScope";
    private static  final String VOID = "void";
    private static  final String LEFT_CURLED_BRACKET = "{";
    private static  final String RIGHT_CURLED_BRACKET = "}";
    private static  final char LEFT_BRACKET = '(';
    private static  final char RIGHT_BRACKET = ')';
    private static  final String BOOLEAN = "boolean";
    private static  final String DOUBLE = "double";
    private static  final String INT = "int";
    private static  final String BEGINNING_OF_IF_CONDTITION = "if(";
    private static  final String BEGINNING_OF_WHILE_LOOP = "while(";


    /**
     * The constructor of the class.
     * @param txt the S-java file.
     */
    public FinalsAndReputationErrs(File txt){
        this.file = txt;
    }

    /**
     * checks if there is a variable name that is repeated illegally.
     * @return true if there is no such illegal reputation
     * @throws IOException throws the java I.O exception if an error occurs.
     */
    public boolean checkVarsNamesReputation() throws IOException{
        this.data = new ReadAndValidateData(this.file);
        this.definedVars = data.createVariables();
        int numOfApperance;
        for(Variable firstVar: definedVars) {
            numOfApperance= 0;
            for(Variable secondVar : definedVars){
                if(sameName(firstVar,secondVar)){
                    numOfApperance ++;
                }
            }
            if(numOfApperance > 1){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the two vars share the same name and defined in the same scope.
     * @param first first var to check.
     * @param second second var to check.
     * @return true if the two vars are compose an illegal definition.
     */
    private boolean sameName(Variable first, Variable second){
        if(first.getVarName().equals(second.getVarName())){
            if(first.getPlace().equals(second.getPlace())){
                if(first.getScope() == second.getScope() && first.getNumOfScope() == second.getNumOfScope()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A function that found if a final variable doesn't have a value when defined.
     * @return true if the var has a value when defined,  else false.
     * @throws IOException throws the java I.O exception if an error occurs.
     */
    public boolean badFinalAssignment() throws IOException{
        this.data = new ReadAndValidateData(this.file);
        this.definedVars = data.createVariables();
        for(Variable var: definedVars) {
            if(var.isFinal() && !var.isHaveValue()){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the all the "if" and the "while" that appears in the S-java file has a legal condition
     * which means it has a boolean value.
     * @return true if all the conditions are legal, false otherwise.
     * @throws IOException throws the java I.O exception if an error occurs while reading the file.
     */
    public boolean checkIfAndWhile() throws IOException {
        String[] lines = new ReadFile(this.file).readFile();
        this.data = new ReadAndValidateData(this.file);
        this.definedVars = data.createVariables();
        int differnceBetweenBrckets = 0;
        String funcName = GLOBAL_SCOPE;
        for(int i=0 ; i < lines.length-1;i++) {
            if (differnceBetweenBrckets == 0) {
                funcName = GLOBAL_SCOPE;
            }

            LineReader read = new LineReader(lines[i]);
            String[] pureLine = read.pureLine();

            if (pureLine.length > 0) {
                if (pureLine[0].equals(VOID)) {
                    funcName = (pureLine[1].split("\\("))[0];
                }
                if (lines[i].contains(LEFT_CURLED_BRACKET)) {
                    differnceBetweenBrckets++;

                }
                if (lines[i].contains(RIGHT_CURLED_BRACKET)) {
                    differnceBetweenBrckets--;
                }
                String newLine = pureLine[0] + this.data.getOrderedLine(pureLine);
                if (newLine.contains(BEGINNING_OF_IF_CONDTITION) || newLine.contains(BEGINNING_OF_WHILE_LOOP)) {
                    int beginBracket = this.data.getCharIndex(LEFT_BRACKET, newLine);
                    int endBracket = this.data.getCharIndex(RIGHT_BRACKET, newLine);
                    if(endBracket - beginBracket <= 1) {
                        return false;
                    }
                    String parmterName = newLine.substring(beginBracket + 1, endBracket);
                    Variable var = this.data.getVariable(parmterName, funcName);
                    if (var != null) {
                        if(!var.isHaveValue())
                            return false;
                        if (!var.getType().equals(BOOLEAN) && !var.getType().equals(INT) &&
                                !var.getType().equals(DOUBLE)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


}
