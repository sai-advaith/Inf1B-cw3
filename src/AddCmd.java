import java.nio.file.Paths;
import java.util.Objects;
/**
 * This command allows the user to add additional books to the library from a book data csv file.
 */
public class AddCmd extends LibraryCommand {
    private String addField; // instance field

    /**
     * This is the constructor of the Add Command class which calls the superclass constructor
     * @param addField is the field which contains the book file path to be loaded
     */
    public AddCmd(String addField) {
        super(CommandType.ADD,addField);// calling superclass constructor
    }

    /**
     * This is to parse the file path as an argument input and then return whether it is a valid file type
     * @param addInput is the file name/path which will give us a greater understanding of whether the file is valid
     * @return true if the it is a csv file
     * @throws NullPointerException if the string input is null
     */
    @Override
    protected boolean parseArguments(String addInput) {
        Objects.requireNonNull(addInput, StdMsg.STD_NULL_MSG.toString()); // checking for non null
        String csvExtension = ".csv";
        if (addInput.endsWith(csvExtension)) { // if ends with ".csv"
            addField = addInput;
            return true;
        }
        return false;
    }

    /**
     * This is a void method which is used to execute the loadData method
     * @param data is an object of LibraryData class which is used to load the data to the method
     * @throws NullPointerException if the LibraryData object is null
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data,StdMsg.STD_NULL_MSG.toString());
        data.loadData(Paths.get(addField)); // calling method to load data
    }
}
