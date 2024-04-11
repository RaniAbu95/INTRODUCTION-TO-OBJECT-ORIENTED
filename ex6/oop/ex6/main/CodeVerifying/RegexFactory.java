package oop.ex6.main.CodeVerifying;

import oop.ex6.main.Reading.LineReader;

import java.util.regex.*;

/**
 * This class is responsible for checking each line in the s-java files. It returns true if the line is a legal,
 * else it returns false.
 * Created by rani.aboraya on 6/9/16.
 */
public class RegexFactory {

    private String lineToCheck;

    /**
     * this variable holds the regex that is suitable for a legal variable name, which is shared by all variables
     * declaration regexes.
     */
    private static final String legalVariableName = "(([a-zA-Z])+|(_+\\w+)+)+(\\w*|_*)*";

    private static final String legalFunctionName = "(([a-zA-Z]))+(\\w*|_*)*";

    private static final String intRegex = "(final\\s)?\\s*int\\s+((" + legalVariableName + ")(\\s*=\\s*-?(" +
            "([0-9]+)|" + legalVariableName + "\\s*))*\\s*)\\s*(\\s*,\\s*(" + legalVariableName + "" +
            "(\\s*=\\s*(-?([0-9])+|("+ legalVariableName + ")\\s*))*\\s*))*;\\s*";

    private static final String doubleRegex = "(final\\s)?\\s*double\\s+(" + legalVariableName + "+" +
            "(\\s*=\\s*-?(" +
            "([0-9]+(.[0-9]+)*)|" + legalVariableName + "\\s*))*\\s*)+\\s*(\\s*,\\s*(" +
            legalVariableName + " (\\s*=\\s*-?(([0-9]+(.[0-9]+)*)|" + legalVariableName + "\\s*))" +
            "*\\s*))*;\\s*";

    private static final String StringRegex = "(final\\s)?\\s*String\\s+(" + legalVariableName + "+(\\s*=\\s*(" +
            "(\"+\\s*.*(.|\\s)*\"+)*|" + legalVariableName + "\\s*))*\\s*)+\\s*(,\\s*((" +
            legalVariableName + " (\\s*=\\s*((\"+.*(.|\\s)*\"+)|" + legalVariableName + "\\s*))*)" +
            "*\\s*))*;\\s*";

    private static final String charRegex = "(final\\s)?\\s*char\\s+(" + legalVariableName + "+(\\s*=\\s*(" +
            "(\'.?\')|" + legalVariableName + "\\s*))*)+\\s*(,\\s*(" + legalVariableName + " *(\\s*=\\s*(" +
            "(\'.?\')*|" + legalVariableName + "\\s*)*)*))*;\\s*";

    private static final String booleanRegex = "(final\\s)?\\s*boolean\\s+(" + legalVariableName + "" +
            "(\\s*=\\s*((true|false|-?\\d+|-?\\d+.\\d+)| " + legalVariableName + ")+)*)+\\s*(,\\s*(" +
            legalVariableName + "(\\s*=\\s*((true|false|-?\\d+|-?\\d+.\\d+)|" + legalVariableName + "\\s*))" +
            "*))*;\\s*";

    private static final String FunctionRegex = "\\s*void\\s+((" + legalFunctionName + ")\\s*\\(\\s*(" +
            "(final\\s)?(" +
            "(int|double|String|char|boolean)\\s+" + legalVariableName + "\\s*)|\\s*))+(\\s*,\\s*" +
            "(final\\s)?(int|double|String|char|boolean)\\s+" + legalVariableName + ")*\\s*\\)\\s*\\{\\s*\\s*";

    private static final String FunctionCallRegex = "\\s*(" + legalVariableName + "\\s*\\(\\s*(" +
            legalVariableName + "\\s*|\\s*|('.?')|(true|false|-?\\d+|-?\\d+.\\d+)|(\"+ *.*(.|\\s)*\"+))" +
            "\\s*)+(\\s*,\\s*((" + legalVariableName + ")\\s*|('.?')|(true|false|-?\\d+|-?\\d+.\\d+)|" +
            "(\"+\\s*.*(.|\\s)*\"+))\\s*)*\\)\\s*;\\s*";

    private static final String VariablesAssignmentRegex = "\\s*(" + legalVariableName + ")\\s*=\\s*((" +
            legalVariableName +
            "\\s*)|('.?')|true|false|-?\\d+|-?(\\d)+.(\\d)+|(\"+\\s*.*(.|\\s)*\"+)|(\\s*(" +
            legalVariableName + "\\s*(\\s*(" + legalVariableName + "\\s*|\\s*|('.?')|" +
            "(true|false|-?\\d+|-?\\d+.\\d+)|(\"+ *.*(.|\\\\s)*\"+))\\s*)+(\\s*,\\s*((" + legalVariableName
            + ")\\s*|('.?')|(true|false|-?\\d+|-?\\d+.\\d+)|(\"+\\s*.*(.|\\s)*\"+))\\s*)*)\\s*;\\s*\"))\\s*;\\s*";

    private static final String IfOrWhileRegex = "\\s*(if|while)\\s*\\(\\s*((" + legalVariableName + ")|" +
            "(true|false|-?\\d+|-?\\d+.\\d+))\\s*( *((\\|\\|)|(&&))\\s*((" + legalVariableName + ")" +
            "|true|false|-?\\d+|-?\\d+.\\d+) *)*\\s*\\)\\s*\\{\\s*\\s*";

    private static final String returnRegex = "\\s*return\\s*;\\s*";

    private static final String LineStatementRegex = "\\s*|(\\s*\\}\\s*)";

    private static  final String BOOLEAN = "boolean";

    private static  final String DOUBLE = "double";

    private static  final String INT = "int";

    private static  final String CHAR = "char";

    private static  final String STRING = "String";

    private static  final String VOID = "void";

    private static  final String IF = "if";

    private static  final String WHILE = "while";

    private static  final String COMMENT = "//";

    private static  final String RETURN_WITHOUT_END = "return";

    private static  final String RETURN_WITH_END = "return;";

    private static  final char SPACE = ' ';

    private static  final String LEFT_BRACKET = "(";

    private static  final String RIGHT_BRACKET = ")";

    private static  final String EQUAL = "=";



    /**
     * Constructor for the class. takes a line in the text and return 1 if it is a legal statement in
     * s-java else 0.
     * @param line
     */
    public RegexFactory(String line){
        this.lineToCheck = line;
    }



    /**
     * A method that holds the legal regex for an int variable declaration.
     * @return true if the line that we are checking is a legal declaration of an int, else false.
     */
    private boolean checkIntVariables(){
        Pattern p = Pattern.compile(intRegex);
        Matcher m = p.matcher(this.lineToCheck);
        return m.matches();

    }

    /**
     * A method that holds the legal regex for an double variable declaration.
     * @return true if the line that we are checking is a legal declaration of an double, else false.
     */
    private boolean checkDoubleVariables(){
        Pattern p = Pattern.compile(doubleRegex);
        Matcher m = p.matcher(this.lineToCheck);
        return m.matches();

    }

    /**
     * A method that holds the legal regex for an String variable declaration.
     * @return true if the line that we are checking is a legal declaration of an String, else false.
     */
    private boolean checkStringVariables(){
        Pattern p = Pattern.compile(StringRegex);
        Matcher m = p.matcher(this.lineToCheck);
        return m.matches();

    }

    /**
     * A method that holds the legal regex for an char variable declaration.
     * @return true if the line that we are checking is a legal declaration of an char, else false.
     */
    private boolean checkCharVariables(){
        Pattern p = Pattern.compile(charRegex);
        Matcher m = p.matcher(this.lineToCheck);
        return m.matches();

    }

    /**
     * A method that holds the legal regex for an boolean variable declaration.
     * @return true if the line that we are checking is a legal declaration of an boolean, else false.
     */
    private boolean checkBoolean(){
        Pattern p = Pattern.compile(booleanRegex);
        Matcher m = p.matcher(this.lineToCheck);
        return m.matches();

    }

    /**
     *A method that holds the legal regex for a function declaration.
     * @return true if the line that we are checking is a legal declarat"(([a-zA-Z]))+(\\w*|_*)*"ion of a function, else false.
     */
    private boolean checkFunctionDecleration(){
        Pattern p = Pattern.compile(FunctionRegex);
        Matcher m = p.matcher(this.lineToCheck);
        return m.matches();
    }

    /**
     * A method that holds the legal regex for a function call.
     * @return  true if the line that we are checking is a legal call of a function, else false.
     */
    private boolean checkFunctionCall(){
        Pattern p = Pattern.compile(FunctionCallRegex);
        Matcher m = p.matcher(this.lineToCheck);
        return m.matches();
    }

    /**
     * A method that holds the legal regex for if/while condition.
     * @return  true if the line that we are checking is a legal declaration of if/while condition, else false.
     */
    private boolean checkIfOrWhileCondition(){
        Pattern p = Pattern.compile(IfOrWhileRegex);
        Matcher m = p.matcher(this.lineToCheck);
        return m.matches();
    }

    /**
     * A method that holds the legal regex for a variable assignment.
     * @return true if the line that we are checking is a legal declaration of a variable assignment, else false.
     */
    private boolean checkVariablesAssignment(){
        Pattern p = Pattern.compile(VariablesAssignmentRegex);
        Matcher m = p.matcher(this.lineToCheck);
        return m.matches();

    }

    /**
     * A method that holds the legal regex for a return statement.
     * @return true if the line that we are checking is a legal return statement, else false.
     */
    private boolean checkReturnStatement(){
        Pattern p = Pattern.compile(returnRegex);
        Matcher m = p.matcher(this.lineToCheck);
        return m.matches();
    }

    /**
     * A method that holds the legal regex for an empty or a line that contains only '}'.
     * @return true if the line that we are checking is a legal, else false.
     */
    private boolean checkLineStatement(){
        Pattern p = Pattern.compile(LineStatementRegex);
        Matcher m = p.matcher(this.lineToCheck);
        return m.matches();
    }

    /**
     * This method send the line to the suitable regex based on the begining of the line.
     * @return true if the line is a legal s-java line, false otherwise.
     */
    public boolean vererfier(){
        LineReader lines = new LineReader(this.lineToCheck);
        String order = lines.beginingOfLine();
        switch (order){
            case INT:
                return this.checkIntVariables();

            case DOUBLE:
                return this.checkDoubleVariables();

            case STRING:
                return this.checkStringVariables();

            case CHAR:
                return this.checkCharVariables();

            case BOOLEAN:
                return this.checkBoolean();

            case VOID:
                return this.checkFunctionDecleration();

            case RETURN_WITH_END:case RETURN_WITHOUT_END:
                return this.checkReturnStatement();

            case IF:case WHILE:
                return this.checkIfOrWhileCondition();

            case COMMENT:
                if(order.charAt(0) != SPACE)
                    return true;

            default:{
                if(lines.pureLine().length > 1) {
                    if(lines.pureLine()[1].contains(EQUAL) || order.contains(EQUAL)) {
                        return this.checkVariablesAssignment();
                    }
                    else if (lines.pureLine()[1].contains(LEFT_BRACKET) || order.contains(EQUAL)) {
                        return this.checkFunctionCall();
                    }
                }
                else {
                    if (order.contains(EQUAL)) {
                        return this.checkVariablesAssignment();
                    }
                    else if (order.contains(LEFT_BRACKET)) {
                        return this.checkFunctionCall();
                    }
                }
            }
        }
        return this.checkLineStatement();
    }


}
