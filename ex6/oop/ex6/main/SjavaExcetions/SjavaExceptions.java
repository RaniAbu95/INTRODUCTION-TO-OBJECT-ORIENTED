package oop.ex6.main.SjavaExcetions;

/**
 * A class that represents an error in the S-java file. When an error is a syntax error that found in the
 * text and every time that we found such an error we throw an object of this class.
 */
public class SjavaExceptions extends Exception{

    /**
     * A constructor of the exception object.
     */
    public SjavaExceptions(){
        super();
    }

    /**
     * A method that prints 1 if an error was thrown.
     */
    public void printMessage(){
        System.out.println(1);
    }
}
