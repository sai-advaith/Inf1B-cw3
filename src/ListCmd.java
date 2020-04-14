import java.util.List;
import java.util.Objects;

/**
 * This command should allow the user to display all books
 * currently loaded into the library in different formats
 */
public class ListCmd extends LibraryCommand {
    private String listField; // instance field

    /**Command for long listing*/
    private final String LONG = "long";
    /**Command for short listing*/
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
     * @return whether the argument is valid or not
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
     * The Titles of the books or the books itself are printed as per the necessity of the user as stated in the List command
     * @throws NullPointerException if the BookEntry list contains a null reference or data object is null
     * @throws IllegalArgumentException if the the command argument is not LONG, SHORT or Blank
     */ 
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, StdMsg.STD_NULL_MSG.toString()); //  the data object cannot be null
        List<BookEntry> listCmdBooks = data.getBookData();
        Objects.requireNonNull(listCmdBooks, StdMsg.STD_NULL_MSG.toString()); //  the list of books cannot be null
        if (listCmdBooks.contains(null)) {
            throw new NullPointerException(StdMsg.STD_NULL_MSG.toString()); // cannot contain null
        }

        StringBuilder listOutput = new StringBuilder(); //  The output for List command

        if (listCmdBooks.isEmpty()) {
            listOutput.append(StdMsg.EMPTY_LIBRARY_MSG.toString()); // output for empty list
            System.out.println(listOutput);
        } else {
            listOutput.append(listCmdBooks.size()).append(" books in library:\n"); // header of output

            if (listField.equals(SHORT) || listField.isBlank()) {
                System.out.println(shortPrint(listCmdBooks,listOutput));
            } else if (listField.equals(LONG)) {
                System.out.print(longPrint(listCmdBooks,listOutput)); // print to avoid extra linebreaks
            } else {
                throw new IllegalArgumentException(StdMsg.STD_ILLEGAL_MSG.toString()); // if not long short or blank
            }
        }
    }

    /**
     * Printing the titles of the book without linebreaks in between them
     * @param listCmdBooks is the list of BookEntry objects
     * @param listOutput is to store the output of list command
     * @return the trimmed string, without any linebreaks in the end
     */
    private String shortPrint(List<BookEntry> listCmdBooks, StringBuilder listOutput) {
        for (BookEntry book : listCmdBooks) { //  Short printing
            listOutput.append(book.getTitle()).append("\n"); // appending titles of the books
        }
        int indexFromLast = listOutput.lastIndexOf("\n"); // removing additional linebreak from the loop
        return listOutput.toString().substring(0,indexFromLast);
    }

    /**
     * Printing the book as a string with linebreaks in between them
     * @param listCmdBooks is the list of BookEntry objects
     * @param listOutput is to store the output of list command
     * @return return the StringBuilder with a linebreak in the end
     */
    private StringBuilder longPrint(List<BookEntry> listCmdBooks, StringBuilder listOutput) {
        for (BookEntry book : listCmdBooks) { //  Long printing
            listOutput.append(book).append("\n\n"); // appending the string representation of book
        }
        return listOutput;
    }
}
