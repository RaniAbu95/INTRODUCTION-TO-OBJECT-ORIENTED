package oop.ex6.main.CodeVerifying;

import oop.ex6.main.Objects.Function;
import oop.ex6.main.Objects.Variable;
import oop.ex6.main.Reading.LineReader;
import oop.ex6.main.Reading.ReadFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * runs through the definition  of the variables and functions and all
 * the assignment that appears in the file in addition to the functions calls
 * and checks the legality of all the above.
 */
public class ReadAndValidateData {
    private LinkedList<Variable> variables;
    private LinkedList<Function> function;
    private File txt;
    private String[] lines;
    CheckCompensation comparing;

    private static final String legalVariableName = "([a-zA-Z]|_+\\w+)+\\w*|_*";

    private static  final String BOOLEAN = "boolean";

    private static  final String DOUBLE = "double";

    private static  final String INT = "int";

    private static  final String CHAR = "char";

    private static  final String STRING = "String";

    private static final String GLOBAL_SCOPE = "globalScope";

    private static  final String LEFT_BRACKET_STRING = "\\(";

    private static  final char RIGHT_BRACKET_CHAR = ')';

    private static  final char LEFT_BRACKET_CHAR = '(';

    private static  final String VOID = "void";

    private static  final String LEFT_CURLED_BRACKET = "{";

    private static  final String RIGHT_CURLED_BRACKET = "}";

    private static  final String FINAL = "final";

    private static  final String EMPTY_STRING = "";

    private static  final char COMMA_CHAR = ',';

    private static  final String STRING_COMMA = ",";

    private static  final String END_OF_LINE = ";";

    private static  final String LOCAL = "local";

    private static  final String GLOBAL = "global";

    private static  final String EQUALS_STRING= "=";

    private static final int IN_GLOBAL_SCOPE = 0;

    private static final String TRUE = "true";

    private static final String FALSE = "false";

    private static final String SPACE = " ";

    private static final String IF = "if";

    private static final String WHILE = "while";

    private static  final String RIGHT_BRACKET_STRING = ")";

    private static  final String COMMA_STRING = ",";

    private static  final char EQUALS_CHAR = '=';


    /**
     * the constructor of the class.
     * @param file the S-java file.
     */
    public ReadAndValidateData(File file){
        this.txt = file;
        this.variables = new LinkedList<Variable>();
        this.comparing = new CheckCompensation(this.txt);
        this.function = new LinkedList<Function>();
    }

    /**
     * This method creates all the defined variables in the file.
     * @return an array of variables containing all the defined variables in the file.
     * @throws IOException throws the java I.O exception if an error occurs.
     */
    public Variable[] createVariables() throws IOException{
        this.lines = new ReadFile(this.txt).readFile();
        int differnceBetweenBrckets = 0, numberofScope = 0;
        String funcName = EMPTY_STRING;
        for(int i =0; i < this.lines.length; i++){
            boolean isFinal = false;
            if(differnceBetweenBrckets == 0) {
                funcName = GLOBAL_SCOPE;
            }
            LineReader readLine = new LineReader(this.lines[i]);
            String[] pureLine = readLine.pureLine();
            if(pureLine.length  >  0) {
                if (pureLine[0].equals(VOID)) {
                    funcName = (pureLine[1].split(LEFT_BRACKET_STRING))[0];

                    addToFunction(funcName,i,differnceBetweenBrckets,numberofScope);
                }
                if (this.lines[i].contains(LEFT_CURLED_BRACKET)) {
                    differnceBetweenBrckets++;
                    numberofScope++;

               }
                if (this.lines[i].contains(RIGHT_CURLED_BRACKET)) {
                    differnceBetweenBrckets--;
                }
                String type1;
                if(!pureLine[0].equals(FINAL)) {
                    type1 = (pureLine[0]);
                }
                else {
                    type1 = (pureLine[1]);
                    isFinal = true;
                }
                if (type1.equals(INT) || type1.equals(DOUBLE) || type1.equals(CHAR)
                        || type1.equals(STRING) || type1.equals(BOOLEAN)) {
                    String type = type1;
                    buildVariables(getOrderedLine(pureLine), type, differnceBetweenBrckets, funcName, i,
                            isFinal,numberofScope);
                }
            }
        }
        return this.variables.toArray(new Variable[variables.size()]);
    }

    /**
     * Prepare the data for the new function to add.
     * @param funcName the name of the new function to add.
     * @param index the line in which the function defined.
     * @param differnceBetweenBrckets in what scope the function is called.
     * @param numberofScope the number of the function (first func is 1,
     *  second is 2 etc..)
     */
    private void addToFunction(String funcName, int index, int differnceBetweenBrckets, int numberofScope){
        int beginBracket = getCharIndex(LEFT_BRACKET_CHAR,lines[index]);
        int endBracket = getCharIndex(RIGHT_BRACKET_CHAR ,lines[index]);
        if( (endBracket - beginBracket) >= 1) {
            String functionParameters = lines[index].substring(beginBracket +1, endBracket);
            if(!functionParameters.equals(EMPTY_STRING)) {
                buildVariablesForfunc(functionParameters, differnceBetweenBrckets + 1, funcName, index,
                        numberofScope + 1);
                addFunction(functionParameters,funcName);
            }
            else {
                addFunction(EMPTY_STRING,funcName);
            }

        }
    }

    /**
     *  Adds a new function to the existing.
     * @param types the types of the parameters of the function.
     * @param names  the names of the parameters
     */
    private void addFunction(String types , String names) {
        Function newFunction = new Function(names);
        if (types != EMPTY_STRING) {
            String lineOfFun = getOrderedFunctionLine(types);
            String[] parameters = new LineReader(lineOfFun).pureLine();
            for (int i = 0; i < parameters.length; i += 2) {
                if (parameters[i].equals(FINAL)) {
                    i++;
                }
                newFunction.addToFuncParametersTypes(parameters[i]);
            }
        }
        this.function.add(newFunction);
    }

    /**
     * Checks all the assignments between two variables.
     * @param line the line in which the assignment was made.
     * @param type the type of the second var in the assignment.
     * @param scope the scope of the second var in the assignment
     * @param declerationPlace the declaration place of the second var in the assignment.
     * @param lineNum the line in which the second var in the assignment was defined.
     * @param numberofScope the number of scope of the second var in the assignment.
     * @return true if the assignment is legal, otherwise false.
     */
    private Variable[] checkAssignmentVariables(String line, String type, int scope, String declerationPlace,
            int lineNum ,int numberofScope) {
        LinkedList<Variable> listOfVars = new LinkedList<Variable>();
        String[] varibales = line.split(STRING_COMMA);
        for (String var : varibales) {
            if (var.contains(EQUALS_STRING)) {
                String name = var.split(EQUALS_STRING)[1];
                if(isLegalVarName(name.split(END_OF_LINE)[0])) {
                    Variable varObject = new Variable(name.split(END_OF_LINE)[0], type, scope, declerationPlace,
                            lineNum, true, LOCAL,false,numberofScope);
                    listOfVars.add(varObject);
                }
            }
        }
        return listOfVars.toArray(new Variable[listOfVars.size()]);
    }

    /**
     * Checks the name of the variable.
     * @param name the name of the variable.
     * @return true if the name of the variable is legal, else false.
     */
    private boolean isLegalVarName(String name){
        if(name.equals(TRUE) || name.equals(FALSE)){
            return false;
        }
        Pattern p = Pattern.compile(legalVariableName);
        Matcher m = p.matcher(name);
        return m.matches();

    }

    /**
     * return the connected words withing a String array.
     * @param pureLine the String array.
     * @return A String representing the name in the String array connected together.
     */
    public String getOrderedLine(String[] pureLine){
        String orderedLine = EMPTY_STRING;
        for(int i=1; i < pureLine.length; i++){
            orderedLine+=pureLine[i];
        }
        return orderedLine;
    }

    /**
     * This function takes all the attributes of the variable and creates a new object and adds it
     * to the existing.
     * @param line the line in which the new variable is defined.
     * @param type the type of the new variable.
     * @param scope the scope of the new variable.
     * @param declerationPlace the  declaration place of the new variable.
     * @param lineNum the line number of the new variable.
     * @param isFinal a val that indicates if the variable is final or not.
     * @param numberofScope the number of scope of the new variable.
     */
    private void buildVariables(String line, String type, int scope, String declerationPlace, int lineNum
            ,boolean isFinal,int numberofScope){

        String newline;
        if(isFinal){
            newline = line.split(type)[1];
        }
        else{
            newline = line;
        }
        String[] varibales = newline.split(STRING_COMMA);
        for (String var : varibales){
            if(scope != 0 ){
                if(var.contains(EQUALS_STRING)) {
                    String name = var.split(EMPTY_STRING)[0];
                    Variable varObject = new Variable(name, type, scope, declerationPlace, lineNum, true,
                            LOCAL,isFinal ,numberofScope);
                    this.variables.add(varObject);
                }
                else {
                    Variable varObject = new Variable(var.split(END_OF_LINE)[0], type, scope, declerationPlace,
                            lineNum, false, LOCAL,isFinal,numberofScope);
                    this.variables.add(varObject);
                }
            }
            else if(scope == IN_GLOBAL_SCOPE){
                String name = var.split(EQUALS_STRING)[0];
                boolean  haveValue;
                if(var.contains(EQUALS_STRING)){
                    haveValue = true;
                }
                else {
                    haveValue = false;
                }
                Variable varObject = new Variable(name.split(END_OF_LINE)[0],type,scope,declerationPlace,lineNum,
                        haveValue, GLOBAL,isFinal,numberofScope);
                this.variables.add(varObject);
            }
        }
    }

    /**
     * This method find the index of the first appearance of a char in a String.
     * @param chossenChar the char that we are searching for.
     * @param line the String that has to be checked.
     * @return index of the first appearance of the char.
     */
    public int getCharIndex(char chossenChar ,String line){
        for(int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if(c == chossenChar)
                return i;

        }
        return 0;
    }

    /**
     * This method takes the line between the brackets of the function.
     * @param pureLine the line in which the function appears.
     * @return the String that contains all the types and the parameters that the func receives.
     */
    public String getOrderedFunctionLine(String pureLine){
            String orderedLine = EMPTY_STRING;
        int cnt = 0;
        char space = ' ';
        for(int i=0; i < pureLine.length(); i++){
            char c = pureLine.charAt(i);
            if(c == space)
                cnt++;

            if(c == COMMA_CHAR){
                orderedLine += SPACE;
            }
            else if(c != space){
                orderedLine += c;
                cnt = 0;
            }
            if(c == space && cnt == 1 && !orderedLine.equals(EMPTY_STRING)){
                orderedLine += c;
            }
        }
        return orderedLine;
    }

    /**
     * adds the parameters(vars) of the function to the array of defined variables.
     * @param line the line of the function.
     * @param scope the scope in which the parameters is placed.
     * @param declerationPlace the declaration place of the function parameters.
     * @param lineNum the line of the function.
     * @param numberofScope the number of scope of the function's parameters.
     */
    private void buildVariablesForfunc(String line, int scope, String declerationPlace, int
    lineNum ,int numberofScope) {
        String lineOfFun = getOrderedFunctionLine(line);
        String[] parameters = new LineReader(lineOfFun).pureLine();
        boolean isFinal;
        for (int i = 1; i < parameters.length; i += 2) {
            isFinal = false;
            if(parameters[i-1].equals(FINAL)){
                i++;
                isFinal = true;
            }
            Variable varObject = new Variable(parameters[i],parameters[i-1],scope,
                    declerationPlace,lineNum, true, LOCAL, isFinal,numberofScope);
            this.variables.add(varObject);
        }
    }

    /**
     * checks every assignment in the file.
     * @return true if the two variable can be in the same assignment, or if an assignment of a var to
     * a const value is ok. in addition it checks all the functions calls.
     * @throws IOException throws the java I.O exception if an error occurs.
     */
    public boolean makeAssignmentVars() throws IOException{
        this.lines = new ReadFile(this.txt).readFile();
        int differnceBetweenBrckets = 0;
        int numberofScope = 0;
        String funcName = EMPTY_STRING;
        for(int i =0; i < this.lines.length; i++){
            if(differnceBetweenBrckets == 0) {
                funcName = GLOBAL_SCOPE;
            }
            LineReader readLine = new LineReader(this.lines[i]);
            String[] pureLine = readLine.pureLine();
            if(pureLine.length  >  0) {
                if (pureLine[0].equals(VOID)) {
                    funcName = (pureLine[1].split(LEFT_BRACKET_STRING))[0];
                }
                if (this.lines[i].contains(LEFT_CURLED_BRACKET)) {
                    differnceBetweenBrckets++;
                    numberofScope++;
                }
                if (this.lines[i].contains(RIGHT_CURLED_BRACKET)) {
                    differnceBetweenBrckets--;
                }
                if (lines[i].contains(EQUALS_STRING)) {
                    String type1;
                    if (!pureLine[0].equals(FINAL)) {
                        type1 = (pureLine[0]);
                    } else {
                        type1 = (pureLine[1]);
                    }
                    if (type1.equals(INT) || type1.equals(DOUBLE) || type1.equals(CHAR)
                            || type1.equals(STRING) || type1.equals(BOOLEAN)) {
                        String type = type1;
                        Variable[] vars = checkAssignmentVariables(getOrderedLine(pureLine), type,
                                differnceBetweenBrckets, funcName, i,numberofScope);
                        for (Variable var : vars) {
                            if (!comparing.getVariable(var)) {
                                return false;
                            }
                        }
                        continue;
                    }
                    if (isLegalVarName(pureLine[0].split(EQUALS_STRING)[0])) {
                        String orderline = (pureLine[0] + getOrderedLine(pureLine));
                        String firstVarName = orderline.split(EQUALS_STRING)[0];
                        String secondVarName = orderline.split(EQUALS_STRING)[1].split(END_OF_LINE)[0];
                        Variable variable = getVariable(firstVarName, funcName);
                        if(variable != null)
                            if(variable.isFinal()) {
                                return false;
                            }

                        if (isLegalVarName(secondVarName)) {
                            if (!AssignmentBetweenTwoVars(firstVarName, secondVarName, funcName)) {
                                return false;
                            }
                        } else {
                            Variable var = getVariable(firstVarName, funcName);
                            if (!secondVarName.contains(LEFT_BRACKET_STRING)) {
                                if(var != null) {
                                    String line = var.getType() + SPACE + lines[i];
                                    RegexFactory regex = new RegexFactory(line);
                                    if (!regex.vererfier()) {
                                        return false;
                                    }
                                }
                            }
                            else{
                                if(!checkFunctionCall(pureLine))
                                    return false;
                            }
                        }
                    }
                }else if(lines[i].contains("(")){
                    if(!pureLine[0].equals(VOID) && !pureLine[0].contains(IF) && !pureLine[0].contains
                            (WHILE)) {
                        String orderLine = pureLine[0] + getOrderedLine(pureLine);
                        String[] orderLines = {EMPTY_STRING,orderLine};
                        if(!checkFunctionCall(orderLines))
                            return false;
                    }

                }
            }
        }
        return true;
    }

    /**
     * Checks the functions call at the S-java file.
     * @param pureLine the value that the function takes in the call.
     * @return true if all the function calls is legal, false otherwise.
     */
    public boolean checkFunctionCall(String[] pureLine){
        if(!funcsNameReputation()) {
            return false;
        }
        String orderedFuncCall = getOrderedLine(pureLine);
        int leftBoundFunc = -1;
        if(orderedFuncCall.contains(EQUALS_STRING)) {
            leftBoundFunc = getCharIndex(EQUALS_CHAR, orderedFuncCall);
        }
        int rightBoundFunc = getCharIndex(LEFT_BRACKET_CHAR, orderedFuncCall);
        String func = orderedFuncCall.substring(leftBoundFunc+1, rightBoundFunc);
        if (!isFuncFound(func)){
            return false;
        }
        Function myFunc = getFuncObject(func);
        if(myFunc != null){
            int rightBracket = getCharIndex(RIGHT_BRACKET_CHAR, orderedFuncCall);
            String functionValues = orderedFuncCall.substring(rightBoundFunc + 1, rightBracket);
            String[] vals = new String[0];
            if(!functionValues.equals(EMPTY_STRING)){
                vals = functionValues.split(COMMA_STRING);
            }
            String[] funcTypes = myFunc.getFuncTypes();
            if(vals.length != funcTypes.length){
                return false;
            }
            else{
                for(int j = 0; j < vals.length; j++){
                    String someVarName = "parameter = ";
                    String funcLine = funcTypes[j] + SPACE + someVarName + vals[j]+END_OF_LINE;
                    RegexFactory regex = new RegexFactory(funcLine);
                    if (!regex.vererfier()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks the functions name's reputation.
     * @return false if there is two function with the same name, else true.
     */
    public boolean funcsNameReputation(){
        int counter;
        for(Function firstfunction:this.function){
            counter = 0;
            for(Function secondfunction:this.function){
                if(firstfunction.getFuncName().equals(secondfunction.getFuncName()))
                    counter++;
            }
            if(counter > 1)
                return false;
        }
        return true;
    }

    /**
     * Searching for a function based on its name.
     * @param funcName the name of the function we want to search for.
     * @return true if the function is found, otherwise false.
     */
    private boolean isFuncFound(String funcName){
        Function[] functions = this.function.toArray(new Function[this.function.size()]);
        for (Function func: functions){
            if(func.getFuncName().equals(funcName)){
                return true;
            }
        }
        return false;
    }

    /**
     * return the function object based on its name.
     * @param name the name of the function.
     * @return the object or objects that have the received name.
     */
    private Function getFuncObject(String name){
        for (Function func: this.function){
            if(func.getFuncName().equals(name)){
                return func;
            }
        }
        return null;
    }

    /**
     * Getter for a variable based on its name and the function in which it's declared.
     * @param name the name of the var.
     * @param funName the name of the function that contains the variable.
     * @return true if the variable was found, else false.
     */
    public Variable getVariable(String name,String funName){
        Variable[] allVariable = this.variables.toArray(new Variable[this.variables.size()]);
        Variable Var = null;
        for(Variable var:allVariable) {
            if (var.getVarName().equals(name) && (var.getPlace().equals(funName) ||
                    var.getPlace().equals(GLOBAL_SCOPE)))
                Var = var;
        }
        return Var;
    }

    /**
     * Checks all the assignments between two vars that is placed in the same function.
     * @param first the first var name.
     * @param second the second var name.
     * @param funcName the name of the function of that contains the two vars.
     * @return true if the assignment is legal, else false.
     */
    private boolean AssignmentBetweenTwoVars(String first, String second,String funcName){
        Variable[] allvariable = this.variables.toArray(new Variable[this.variables.size()]);
        Variable firstVar = null, secondVar = null;
        firstVar = getVariable(first,funcName);
        if(firstVar.isFinal()){
            return false;
        }
        for(Variable var:allvariable){
            if(var.getVarName().equals(second) && (var.getPlace().equals(funcName) || var.getPermission()
                    .equals(GLOBAL_SCOPE)))
                secondVar = var;
        }
        if(firstVar == null || secondVar == null){
            return false;
        }
        return firstVar.legalAssignment(secondVar);
    }
}
