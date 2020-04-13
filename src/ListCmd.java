import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ListCmd extends LibraryCommand {
    /**This is to store the user input*/
    private String listField;

    /**String for long listing*/
    private final String LONG = "long";
    /**String 1 for short listing*/
    private final String SHORT = "short"; // Since used in this class only, private visibility

    /**
     * Parameterized constructor to call the super class constructor
     * @param listField this is the constructor argument and is initialized to the list argument by default
     */
    public ListCmd(String listField) {
        super(CommandType.LIST,listField); // calling the baseclass constructor
    }

    /**
     * This method is an overriding method for parsing the arguments and making sure the arguments received are long or short
     * Instead of using magic strings we have written it as constants in the class
     * @param listInput this is the argument coming along with the list command type
     * @return This gives whether our argument is valid or not based on whether it is short or long
     */
    @Override
    protected boolean parseArguments(String listInput) {
        Objects.requireNonNull(listInput, StdMsg.STD_NULL_MSG.toString());
        if  (listInput.equals(LONG) || listInput.equals(SHORT) || listInput.isBlank()) {
            listField = listInput; // if blank, short, or long the input is valid
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * This method executes the List Command of the code. 
     * @param data book data to be considered for command execution.
     * The Titles or the books are printed as per the necessity of the user as stated in the List command
     * @throws NullPointerException if the BookEntry list contains a null reference
     */ 
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, StdMsg.STD_NULL_MSG.toString()); //  the data object cannot be null
        List<BookEntry> listCmdBooks = data.getBookData();
        Objects.requireNonNull(listCmdBooks, StdMsg.STD_NULL_MSG.toString()); //  the list of books cannot be null

        if (listCmdBooks.contains(null)) {
            throw new NullPointerException(StdMsg.STD_NULL_MSG.toString()); // cannot contain null
        }

        Iterator<BookEntry> iterator = listCmdBooks.iterator(); //  Declaring an iterator for both the loops
        StringBuilder listOutput = new StringBuilder(); //  The output of the code for List command

        if (listCmdBooks.isEmpty()) {
            listOutput.append(StdMsg.EMPTY_LIBRARY_MSG.toString()); // output for empty list
        }

        else {
            String output; // output of short data
            listOutput.append(listCmdBooks.size()).append(StdMsg.BOOK_NUM_MSG.toString()); // header of output

            if (listField.equals(SHORT) || listField.isBlank()) { // if blank or short
                while (iterator.hasNext()) { //  Short printing
                        BookEntry book = iterator.next();
                        listOutput.append(book.getTitle()).append('\n');
                }
                output = listOutput.toString().trim(); // trimming the line break appearing in the last line
                System.out.println(output);
            }

            else if (listField.equals(LONG)) { // long
                while (iterator.hasNext()){ //  Long printing
                        BookEntry book = iterator.next();
                        listOutput.append(book);
                        listOutput.append("\n\n");// since there is new line after every output
                }
                System.out.print(listOutput); // print to avoid extra linebreaks
            }
        }
    }


}
