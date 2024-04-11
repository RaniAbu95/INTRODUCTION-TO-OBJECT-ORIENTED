package filesprocessing;
import filesprocessing.exceptions.Warnings;
import filesprocessing.filters.Filter;
import filesprocessing.exceptions.Errors;
import filesprocessing.orders.Order;
import filesprocessing.program_manager.CommandsFileReader;
import filesprocessing.program_manager.FilterFactory;
import filesprocessing.program_manager.OrderFactory;

import java.io.File;
import java.io.IOException;


/**
 * The class that manage the program and connect all the necessary classes with
 * each other. It has a main method which runs the whole program, the main's args
 * is a directory and a command file, the command file make its command on this
 * directory and at the end we get sorted printed files which passed a filteration
 * first.
 * Created by rani.aboraya on 5/20/16.
 */
public class DirectoryProcessor {

    private static final String FLT = "FILTER";
    private static final String ORD = "ORDER";
    private static final String REV = "REVERSE";
    private static final String OPPOSITE_FILTER = "NOT";
    private static final int START = 0;
    private static final int END = 1;
    private static final String DEFAULT_FILTER = "all";
    private static final String DEFAULT_ORDER = "abs";
    public static String errMesaage;
    private static final int LEGAL_ARGUMENTS_NUM = 2;
    private static final int MAX_ORDER_LINE_LENGTH = 2;
    private FilterFactory filterProducer;
    private OrderFactory orderProducer;
    private File commandsFile;
    public File dir;

    /**
     * The constructor of the class that manage the program.
     * @param dirPath The path of the directory which contains all the files.
     * @param commandsFilePath The command file which filter and sort the files
     *        the directory.
     */
    public DirectoryProcessor(String dirPath, String commandsFilePath){
        this.dir = new File(dirPath);
        this.commandsFile = new File(commandsFilePath);
        this.filterProducer = null;
        this.orderProducer = null;
        this.errMesaage = null;
    }

    /**
     * This method prints all the files that at the array that it gets
     * as an argument.
     * @param listFiles The files to be printed.
     */
    static void printFiles(File[] listFiles) {
        if(listFiles != null && listFiles.length > 0) {
            for (File file : listFiles) {
                System.out.println(file.getName());
            }
        }
    }

    /**
     * This method checks if the format of each section is correct and
     * prints error if there was a problem.
     * @param file The command file.
     * @throws IOException is throw when an system java err ocures.
     * @throws Errors when the format is elligal.
     */
    static void filterOrderValidation(File file) throws IOException, Errors{
        CommandsFileReader fileBuff = new CommandsFileReader(file);
        String[] lines = fileBuff.readFile();
        int filterOrderApperance = 0;
        if(lines.length < 3){
            errMesaage = "NUMBER OF LINES";
            throw new Errors();
        }
        if(!lines[START].equals(FLT)){
            errMesaage = "START OF COMMAND FILE";
            throw new Errors();
        }
        for (int i =0; i < lines.length - 1; i++){
            if(!lines[i].equals(FLT) && !lines[i+1].equals(ORD) && !lines[i+1].equals(FLT) && !lines[i].equals(ORD)){
                errMesaage = "FILTER OR ORDER FORMAT";
                throw new Errors();
            }
            if(lines[i].equals(FLT)){
                filterOrderApperance ++;
            }
            else if (lines[i].equals(ORD)){
                filterOrderApperance--;
            }
            if(filterOrderApperance < 0){
                errMesaage = "FILE FORMAT";
                throw new Errors();
            }
        }

    }

    /**
     * This function checks if the program arguments are suitable. checking if the
     * first arg is a path of a directory and the second is for a file.
     * @param firstArg The path of the directory.
     * @param secondArg The path of the command file.
     * @throws Errors an error if one of the conditions above doesn't fulfilled.
     */
    static void mainArgumentsValidation(String firstArg, String secondArg) throws Errors{
        File dir = new File(firstArg);
        File commandsFile = new File(secondArg);
        if(!dir.isDirectory() || !commandsFile.isFile()){
            errMesaage = "BAD ARGUMENTS FORMAT";
            throw new Errors();
        }
    }


    /**
     *
     * @param args String array which contains the program arguments.
     * @throws IOException io exception is thrown when java error happens.
     * @throws Warnings warning is thrown when there is a problem in a sub-section.
     * @throws Errors err is thrown when there is a problem in the command file.
     */
    public static void main(String args[]) throws IOException, Warnings,Errors {
        try {
            if(args.length != LEGAL_ARGUMENTS_NUM){
                errMesaage = "NUMBER OF ARGUMENTS";
                throw new Errors();
                }
            mainArgumentsValidation(args[0], args[1]);
            DirectoryProcessor dirProcessor = new DirectoryProcessor(args[0], args[1]);
            filterOrderValidation(dirProcessor.commandsFile);
            CommandsFileReader reader = new CommandsFileReader(dirProcessor.commandsFile);
            String[] arrayLines = reader.readFile();
            File[] filteredFiles = null;

            //FILTER SUB-SECTION.
            for (int i = 0; i < arrayLines.length - 1; i++) {
                if (arrayLines[i].equals(FLT)) {
                    try{
                        String[] filterParts = arrayLines[i + 1].split("#");
                        dirProcessor.filterProducer = new FilterFactory(dirProcessor.dir, arrayLines[i + 1]);
                        Filter filter = dirProcessor.filterProducer.createFilter();
                        if (filterParts[filterParts.length - 1].equals(OPPOSITE_FILTER)) {
                        filteredFiles = filter.negativeFilter();
                        } else {
                        filteredFiles = filter.makeFilter();
                        }
                    }
                    catch (Warnings w){
                        dirProcessor.filterProducer = new FilterFactory(dirProcessor.dir, DEFAULT_FILTER);
                        Filter filter = dirProcessor.filterProducer.createFilter();
                        filteredFiles = filter.makeFilter();
                        w.printWarning(i + 2);
                    }
                }
                //ORDER SUB-SECTION
                if(arrayLines[i].equals(ORD)) {
                    try{
                        String[] lineParts = arrayLines[i + 1].split("#");
                        Order order = null;
                        dirProcessor.orderProducer = new OrderFactory(filteredFiles, lineParts[START]);
                        order = dirProcessor.orderProducer.createOrder();
                        File[] sortedFiles = null;
                        if (lineParts.length > MAX_ORDER_LINE_LENGTH) {
                            throw new Warnings();
                        }
                        if(lineParts.length == MAX_ORDER_LINE_LENGTH) {
                            if (lineParts[END].equals(REV)) {
                                sortedFiles = order.makeReverseOrder();
                            }
                        }
                        else {
                            sortedFiles = order.makeOrder();
                        }
                        printFiles(sortedFiles);
                    }
                    catch (Warnings w){
                        dirProcessor.orderProducer = new OrderFactory(filteredFiles,DEFAULT_ORDER );
                        Order order = dirProcessor.orderProducer.createOrder();
                        w.printWarning(i + 2);
                        printFiles(order.makeOrder());
                    }
                }
            }
            if(arrayLines[arrayLines.length - 1].equals(ORD)){
                dirProcessor.orderProducer = new OrderFactory(filteredFiles, DEFAULT_ORDER);
                Order order = dirProcessor.orderProducer.createOrder();
                printFiles(order.makeOrder());
            }
        }
        catch (Errors e){
            e.printError(errMesaage);
        }
    }
}


