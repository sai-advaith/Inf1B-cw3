import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ListCmd extends LibraryCommand {
    /**This is to store the user input*/
    private String listField;

    /**String for long listing*/
    public final String LONG = "long";
    /**String 1 for short listing*/
    public final String SHORT = "short";

    /**
     * Parameterized constructor to call the super class constructor
     * @param listField this is the constructor argument and is initialized to the list argument by default
     */
    public ListCmd(String listField) {
        super(CommandType.LIST,listField);
        Objects.requireNonNull(listField);
    }

    /**
     * This method executes the List Command of the code. 
     * @param data book data to be considered for command execution.
     * The Titles or the books are printed as per the necessity of the user as stated in the List command
     */ 
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data,"Cannot be null"); //  the data object cannot be null
        List<BookEntry> listCmdBooks = data.getBookData();
        Iterator<BookEntry> iterator = listCmdBooks.iterator(); //  Declaring an iterator which will be used in both the loops
        StringBuilder listOutput = new StringBuilder(); //  The output of the code for List command
        String output=listOutput.toString();
        if (listCmdBooks.size() == 0) {
            listOutput.append("The library has no book entries.");
        }
        else {
            listOutput.append(listCmdBooks.size()).append(" books in library:\n");
            if (listField.equalsIgnoreCase(SHORT) || listField.isBlank()) {
                while (iterator.hasNext()) { //  Short printing
                    BookEntry book = iterator.next();
                    listOutput.append(book.getTitle()).append('\n');
                }
                output = listOutput.toString().trim();
                System.out.println(output);
            }
            else if (listField.equalsIgnoreCase(LONG)) {
                while (iterator.hasNext()){ //  Long printing
                    BookEntry book = iterator.next();
                    listOutput.append(book);
                    listOutput.append("\n\n");
                }
                output = listOutput.toString();
                System.out.print(output);
            }
        }
    }

    /**
     * This method is an overriding method for parsing the arguments and making sure the arguments received are long or short
     * Instead of using magic strings we have written it as constants in the class
     * @param listInput this is the argument coming along with the list command type
     * @return This gives whether our argument is valid or not based on whether it is short or long
     */
    @Override
    protected boolean parseArguments(String listInput) {
        Objects.requireNonNull(listInput,"Cannot be null");
        if  (listInput.equals(LONG) || listInput.equals(SHORT) || listInput.isBlank()) {
            listField = listInput;
            return true;
        }
        else {
            return false;
        }
    }
}
