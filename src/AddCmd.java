import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

public class AddCmd extends LibraryCommand {
    String testPath;
    public AddCmd(String testPath) {
        this.testPath = testPath;
        // TODO call the library command constructor?
    }
    @Override
    /**
     * This is a void method which is used to execute the loadData method from the Path object
     */
    public void execute(LibraryData data) {
        Objects.requireNonNull(data,"Object cannot be null");
        data.loadData(Paths.get(testPath));
    }

    /**
     * This is to parse the file path as an argument input and then return whether ut us a valid file type
     * @param argumentInput is the file name/path which will give us a greater understanding of whether the file is valid
     * @return true if the it is a csv file.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput,"String type cannot be null");
        String fileNameParsed = new File(argumentInput).getName(); // name of the file parsed
        if (fileNameParsed.indexOf('.') == -1) {
            return false;
        }
        else {
            String extensionType = fileNameParsed.substring(fileNameParsed.indexOf('.'));
            return extensionType.equals(".csv");
        }
    }
}
