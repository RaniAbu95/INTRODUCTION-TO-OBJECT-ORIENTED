package filesprocessing.program_manager;
import filesprocessing.exceptions.Warnings;
import filesprocessing.filters.*;

/**
 * Created by rani.aboraya on 5/24/16.
 */



import java.io.File;
public class FilterFactory {

    private static final String GREATER_THAN = "greater_than";
    private static final String BETWEEN = "between";
    private static final String SMALLER_THAN = "smaller_than";
    private static final String FILE = "file";
    private static final String CONTAINS = "contains";
    private static final String PREFIX = "prefix";
    private static final String SUFFIX = "suffix";
    private static final String WRITABLE = "writable";
    private static final String EXECUTABLE = "executable";
    private static final String HIDDEN = "hidden";
    private static final String ALL = "all";

    private String filterCommand;
    private File directory;

    /**
     * A constructor which creates a filter factory.
     * @param dir The directory which we want to filter its files.
     * @param filter the filter object.
     */
    public FilterFactory(File dir, String filter){
        this.filterCommand = filter;
        this.directory = dir;
    }


    public  Filter createFilter() throws Warnings {
        String[] command = this.filterCommand.split("#");
        String filterName = command[0];
        switch (filterName){
            case GREATER_THAN:
                double numberToCompare = Double.parseDouble(command[1]);
                Filter greaterThan = new GreaterThanFilter(this.directory, numberToCompare);
                return greaterThan;

            case BETWEEN:
                double firstNum = Double.parseDouble(command[1]);
                double secondNum = Double.parseDouble(command[2]);
                Filter between = new BetweenFilter(this.directory, firstNum, secondNum);
                return between;

            case SMALLER_THAN:
                double num = Double.parseDouble(command[1]);
                Filter samllerThan = new SmallerThanFilter(this.directory, num);
                return samllerThan;

            case FILE:
                String fileName = command[1];
                Filter fileFilter = new FileFilter(this.directory, fileName);
                return fileFilter;

            case CONTAINS:
                String str = command[1];
                Filter contains = new ContainsFilter(this.directory, str);
                return contains;

            case PREFIX:
                String word = command[1];
                Filter prefix = new PrefixFilter(this.directory, word);
                return prefix;

            case SUFFIX:
                String subString = command[1];
                Filter suffix = new SuffixFilter(this.directory, subString);
                return suffix;

            case WRITABLE:
                String writingPermition = command[1];
                if(!writingPermition.equals("YES") && !writingPermition.equals("NO")){
                    throw new Warnings();
                }
                Filter writable = new WritableFilter(this.directory, writingPermition);
                return writable;

            case EXECUTABLE:
                String executablePermition = command[1];
                if(!executablePermition.equals("YES") && !executablePermition.equals("NO")){
                    throw new Warnings();
                }
                Filter executable = new ExecutableFilter(this.directory, executablePermition);
                return executable;

            case HIDDEN:
                String hiddenPermition = command[1];
                if(!hiddenPermition.equals("YES") && !hiddenPermition.equals("NO")){
                    throw new Warnings();
                }
                Filter hidden = new HiddenFilter(this.directory, hiddenPermition);
                return hidden;

            case ALL:

                Filter all = new AllFilter(this.directory);
                return all;

            default:
                throw new Warnings();

        }
    }
}
