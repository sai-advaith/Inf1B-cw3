import java.awt.print.Book;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** 
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {

    /**
     * Contains all lines read from a book data file using
     * the loadFileContent method.
     * 
     * This field can be null if loadFileContent was not called
     * for a valid Path yet.
     * 
     * NOTE: Individual line entries do not include line breaks at the 
     * end of each line.
     */
    private List<String> fileContent;

    /**Separator for values in csv files*/
    private final String COMMA_SEPARATOR = ",";
    /**Separator for authors in the csv files*/
    private final String AUTHOR_SEPARATOR = "-";    // private since used only in this class


    /** Create a new loader. No file content has been loaded yet. */
    public LibraryFileLoader() { 
        fileContent = null;
    }

    /**
     * Load all lines from the specified book data file and
     * save them for later parsing with the parseFileContent method.
     * 
     * This method has to be called before the parseFileContent method
     * can be executed successfully.
     * 
     * @param fileName file path with book data
     * @return true if book data could be loaded successfully, false otherwise
     * @throws NullPointerException if the given file name is null
     */
    public boolean loadFileContent(Path fileName) {
        Objects.requireNonNull(fileName, "Given filename must not be null.");
        boolean success = false;

        try {
            fileContent = Files.readAllLines(fileName);
            success = true;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: Reading file content failed: " + e);
        }

        return success;
    }

    /**
     * Has file content been loaded already?
     * @return true if file content has been loaded already.
     */
    public boolean contentLoaded() {
        return fileContent != null;
    }

    /**
     * Parse file content loaded previously with the loadFileContent method.
     * 
     * @return books parsed from the previously loaded book data or an empty list
     * if no book data has been loaded yet.
     */
    public List<BookEntry> parseFileContent() {
        List<BookEntry> bookEntryFileContent = new ArrayList<>(); // BookEntry list
        if (contentLoaded()) { // if not null, add data to the list
            for (int i = 1; i <fileContent.size();i++) {
                try {
                    BookEntry book = castData(fileContent.get(i));
                    bookEntryFileContent.add(book); // adding the data to the book if no issues with
                }
                catch (NullPointerException | NumberFormatException e) { // expected exceptions from castData
                    System.err.println("ERROR: Parsing book data failed: "+e);
                }
            }
        }

        else {
            System.err.println("ERROR: No content loaded before parsing."); // error message for null list
        }
        return bookEntryFileContent; // returning empty list
    }

    /**
     * This is to cast the string data into bookEntry readable form
     * @param fileData is the string which contains all the data separated by commas
     * @return the BookEntry object which contains authors,title, ISBN, pages, and the rating of the book
     */
    public BookEntry castData(String fileData) {
        Objects.requireNonNull(fileData,StdMsgs.STD_NULL_MSG.toString());

        String[] parsedData = fileData.split(COMMA_SEPARATOR,0); //splitting by comma

        String title = parsedData[0];
        String[] authors = parsedData[1].split(AUTHOR_SEPARATOR,0); // splitting by hyphen
        float rating = Float.parseFloat(parsedData[2]); // casting the data
        String ISBN = parsedData[3];
        int pages = Integer.parseInt(parsedData[4]);
        // if errors in casting, they are handled in the parseFileContent method

        return new BookEntry(title,authors,rating,ISBN,pages); // returning book entry
    }
}
