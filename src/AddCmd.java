import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;
public class AddCmd extends LibraryCommand {
    private String addField; // contains the input for adding files

    /**A constant defined to represent a csv file*/
    public final String CSV_EXTENSION = ".csv";

    /**
     * This is the constructor of the Add Command class calling the superclass constructor
     * @param addField is the field which contains the book file to be added
     */
    public AddCmd(String addField) {
        super(CommandType.ADD,addField);// calling baseclass constructor
    }

    /**
     * This is to parse the file path as an argument input and then return whether ut us a valid file type
     * @param addInput is the file name/path which will give us a greater understanding of whether the file is valid
     * @return true if the it is a csv file.
     */
    @Override
    protected boolean parseArguments(String addInput) {
        Objects.requireNonNull(addInput,StdMsgs.STD_NULL_MSG.toString()); // checking for non null
        if (addInput.endsWith(CSV_EXTENSION)) { // if ends with ".csv" return true
            addField = addInput;
            return true;
        }
        return false;
    }

    /**
     * This is a void method which is used to execute the loadData method from the Path object
     * @param data is an object of LibraryData class which is used to load the data to the method
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data,"Object cannot be null");
        data.loadData(Paths.get(addField)); // calling method to load data
    }


}
