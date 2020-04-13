import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * This command should allow the user to display all books
 * currently loaded into the library in different formats
 */
public class ListCmd extends LibraryCommand {
    private String listField; // instance field

    /**String for long listing*/
    private final String LONG = "long";
    /**String for short listing*/
    private final String SHORT = "short";

    /**
     * Parameterized constructor to call the superclass constructor
     * @param listField is the constructor parameter
     */
    public ListCmd(String listField) {
        super(CommandType.LIST,listField); // calling the superclass constructor
    }

    /**
     * This method is an overriding method for parsing the arguments and
     * making sure the arguments received are long, short, or blank
     * @param listInput argument coming along with the list command type
     * @return whether our argument is valid or not
     */
    @Override
    protected boolean parseArguments(String listInput) {
        Objects.requireNonNull(listInput, StdMsg.STD_NULL_MSG.toString());
        if  (listInput.equals(LONG) || listInput.equals(SHORT) || listInput.isBlank()) {
            listField = listInput; // if blank, short, or long the input is valid
            return true;
        }
        return false;

    }


    /**
     * This method executes the List Command of the code. 
     * @param data book data to be considered for command execution.
     * The Titles or the books are printed as per the necessity of the user as stated in the List command
     * @throws NullPointerException if the BookEntry list contains a null reference or data object is null
     */ 
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, StdMsg.STD_NULL_MSG.toString()); //  the data object cannot be null

        List<BookEntry> listCmdBooks = data.getBookData();
        Objects.requireNonNull(listCmdBooks, StdMsg.STD_NULL_MSG.toString()); //  the list of books cannot be null
        if (listCmdBooks.contains(null)) {
            throw new NullPointerException(StdMsg.STD_NULL_MSG.toString()); // cannot contain null
        }

        Iterator<BookEntry> iterator = listCmdBooks.iterator();
        StringBuilder listOutput = new StringBuilder(); //  The output for List command

        if (listCmdBooks.isEmpty()) {
            listOutput.append(StdMsg.EMPTY_LIBRARY_MSG.toString()); // output for empty list
            System.out.println(listOutput);
        }

        else {
            String output; // output for short/blank
            listOutput.append(listCmdBooks.size()).append(StdMsg.BOOK_NUM_MSG.toString()); // header of output

            if (listField.equals(SHORT) || listField.isBlank()) { // if blank or short
                while (iterator.hasNext()) { //  Short printing
                        BookEntry book = iterator.next();
                        listOutput.append(book.getTitle()).append("\n"); // appending titles of the books
                }
                output = listOutput.toString().trim(); // trimming the line break appearing in the last line
                System.out.println(output);
            }

            else if (listField.equals(LONG)) { // long
                while (iterator.hasNext()){ //  Long printing
                        BookEntry book = iterator.next();
                        listOutput.append(book); // appending the string representation of book
                        listOutput.append("\n\n");// since there a line break after every output
                }
                System.out.print(listOutput); // print to avoid extra linebreaks
            }
        }
    }
}
