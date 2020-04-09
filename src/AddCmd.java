import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;
public class AddCmd extends LibraryCommand {
    private String addField;
    public final String CSV_EXTENSION = ".csv";
    public final String EXTENSION_SEPARATOR = ".";
    /**
     * This is the constructor of the Add Command class calling the superclass constructor
     * @param addField is the field which contains the book file to be added
     */
    public AddCmd(String addField) {
        super(CommandType.ADD,addField);
    }
    /**
     * This is a void method which is used to execute the loadData method from the Path object
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data,"Object cannot be null");
        data.loadData(Paths.get(addField));
    }

    /**
     * This is to parse the file path as an argument input and then return whether ut us a valid file type
     * @param addInput is the file name/path which will give us a greater understanding of whether the file is valid
     * @return true if the it is a csv file.
     */
    @Override
    protected boolean parseArguments(String addInput) {
        Objects.requireNonNull(addInput,StdMsgs.STD_NULL_MSG.toString());
        String fileNameParsed = new File(addInput).getName(); // name of the file parsed
        if (!fileNameParsed.contains(EXTENSION_SEPARATOR)) {
            return false;
        }
        else {
            int pathSeparatorIndex = fileNameParsed.indexOf(EXTENSION_SEPARATOR);
            String extensionType = fileNameParsed.substring(pathSeparatorIndex);
            if (extensionType.equals(CSV_EXTENSION)) {
                addField = addInput;
                return true;
            }
            else {
                return false;
            }
        }
    }
}
