package oop.ex6.main;

import oop.ex6.main.ProgramManagement.MainCheck;
import oop.ex6.main.Reading.ReadFile;
import oop.ex6.main.SjavaExcetions.SjavaExceptions;

import java.io.File;
import java.io.IOException;

/**
 * This is the main class that manages all the program, it reads the S-java file and based on all the
 * tests that are made in the other classes it determines if the the file is a legal S-java file or not.
 */
public class Sjavac {

    private ReadFile reader;

    private static final String SJAVA_END ="sjava";

    /**
     * the constructor of the class.
     * @param file The file we want to check.
     */
    public Sjavac(File file) {
        this.reader = new ReadFile(file);
    }

    /**
     * This method runs the program and catches an errors if there are and prints the desired message to the
     * user. printing 0 means that the file is legal, 1 means that it's not legal, 2 means that an error
     * occurs.
     * @param args the array that contains the user input.
     * @throws IOException throws the java I.O exception if an error occurs while reading the file.
     */
    public static void main(String[] args) throws IOException{
        try {
            File file = new File(args[0]);
            if(!file.isFile() && !file.getName().endsWith(SJAVA_END)) {
                throw new IOException();
            }
            MainCheck verify = new MainCheck(file);
            verify.manager();
            System.out.println(0);

        }
        catch (IOException e) {
            System.out.println(2);
        }
        catch (SjavaExceptions sjavaExceptions) {
            sjavaExceptions.printMessage();
        }

    }
}
