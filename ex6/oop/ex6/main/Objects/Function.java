package oop.ex6.main.Objects;

import java.util.LinkedList;

/**
 * A class that represents a function in the S-java file.
 */
public class Function {


    private String funcName;

    /**
     * A linked list that contains all the types of the parameters that the function takes.
     */
    private LinkedList<String> funcParametersTypes;

    /**
     * Constructor that creates a new function object.
     * @param funcNmae the name of the function.
     */
    public Function(String funcNmae){
        this.funcName = funcNmae;
        funcParametersTypes = new LinkedList<String>();
    }

    /**
     * Adds another type to the types of variables that the function takes.
     * @param type a string representing a new type.
     */
    public void addToFuncParametersTypes(String type){
        this.funcParametersTypes.add(type);
    }

    /**
     * A getter for the name of the function.
     * @return the name of the function.
     */
    public String getFuncName(){
        return this.funcName;
    }

    /**
     * A getter for the types of variables that the function takes.
     * @return all the types that the function receives.
     */
    public String[] getFuncTypes(){
        return this.funcParametersTypes.toArray(new String[funcParametersTypes.size()]);
    }
}

