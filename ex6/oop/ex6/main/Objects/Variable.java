package oop.ex6.main.Objects;

/**
 *A class for all the vars in the file. the variable has the following attributes :
 * name, type, scope, declaration place, number of line appearance, have val or not,
 * local or global, final or not, the number of function it's defined in.
 */
public class Variable {

    private String varName;

    private String type;

    private int scope;

    private String declerationPlace;

    private int lineNum;

    private boolean haveValue;

    private String permission;

    private boolean isFinal;

    private int numOfScope;

    private static  final String BOOLEAN = "boolean";

    private static  final String DOUBLE = "double";

    private static  final String INT = "int";

    private static  final String CHAR = "char";

    private static  final String STRING = "String";

    private static final String GLOBAL_SCOPE = "globalScope";

    /**
     *
     * A constructor that creates a new variable object.
     * @param varName name of the variable.
     * @param type the type of the variable.
     * @param scope which scope it is placed.
     * @param place "global scope" or the name of the function it's defined in.
     * @param lineNum which number of line it is placed at.
     * @param haveValue has a value or not.
     * @param permission global or local variable.
     * @param isFinal a final variable or not.
     * @param numOfScope the number of function it's defined in.
     */
    public Variable(String varName, String type, int scope, String place, int lineNum, boolean haveValue,
                    String permission,boolean isFinal,  int numOfScope){
        this.varName = varName;
        this.type = type;
        this.scope = scope;
        this.declerationPlace = place;
        this.lineNum = lineNum;
        this.haveValue = haveValue;
        this.permission = permission;
        this.isFinal = isFinal;
        this.numOfScope  = numOfScope;
    }

    /**
     * Getter for the name of the variable.
     * @return A String that represents the name of the variable.
     */
    public String getVarName(){
        return this.varName;
    }

    /**
     * A getter for the type of the variable.
     * @return A String that represents the type of the variable.
     */
    public String getType(){
        return this.type;
    }

    /**
     * A getter of the number of scope that contains the variable.
     * @return 0 if the variable is global, an index > 0 that represent the scope number.
     */
    public int getScope(){
        return this.scope;
    }

    /**
     *
     * @return
     */
    public int getNumOfScope(){
        return this.numOfScope;
    }

    public String getPlace(){
        return this.declerationPlace;
    }

    /**
     * @return the number of line in which the variable has first seen in the file.
     */
    public int getLineNum(){
        return this.lineNum;
    }

    /**
     * @return true if the variable has a value, false otherwise.
     */
    public boolean isHaveValue(){
        return this.haveValue;
    }

    /**
     * @return true if the  variable is final, false otherwise.
     */
    public boolean isFinal(){
        return this.isFinal;
    }

    /**
     * @return "global" if the variable is global, "local" otherwise.
     */
    public String getPermission(){
        return this.permission;
    }

    /**
     * this function checks if the variable that placed right next
     * to the '=' char is legal.
     * @param var the var we desire to check.
     * @return true if the variable attributes suitable, false otherwise.
     */
    public boolean equal(Variable var) {
        if (var.getVarName().equals(this.varName)
                && var.scope <= this.scope
                && (var.getPlace().equals(this.declerationPlace) || var.declerationPlace.equals("globalScope"))
                && var.haveValue
                && checkType(this.type,var.type)) {
            if((var.getLineNum() < this.lineNum))
                return true;
            else if(var.declerationPlace.equals(GLOBAL_SCOPE) &&
                    !this.declerationPlace.equals(GLOBAL_SCOPE))
                return true;
        }
        return false;
    }

    /**
     * checks if an assignment between two vars are legal.
     * @param other the variable that we want to check with.
     * @return true if the assignment is legal, false otherwise.
     */
    public boolean legalAssignment(Variable other) {
        if (other.scope <= this.scope
                && (other.getPlace().equals(this.declerationPlace) || other.permission.equals(GLOBAL_SCOPE))
                && other.haveValue
                && checkType(this.type,other.type)) {
            return true;
        }
        return false;
    }


    /**
     * Checks if the types of the two variables is equals.
     * @param type1 the type of the first variable to check.
     * @param type2 the type of the second variable to check.
     * @return true if the two types are equals, false otherwise.
     */
    private boolean checkType(String type1  ,String type2){
        if(type1.equals(INT)) {
            if (type2.equals(INT))
                return true;
        }

        if(type1.equals(DOUBLE)) {
            if (type2.equals(INT) || type2.equals(DOUBLE))
                return true;
        }

        if(type1.equals(BOOLEAN)) {
            if (type2.equals(INT) || type2.equals(DOUBLE) ||  type2.equals(BOOLEAN))
                return true;
        }

        if(type1.equals(STRING)) {
            if (type2.equals(STRING))
                return true;
        }

        if(type1.equals(CHAR)) {
            if (type2.equals(CHAR))
                return true;
        }
        return false;
    }
}
