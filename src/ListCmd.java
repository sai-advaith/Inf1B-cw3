import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ListCmd extends LibraryCommand {
    /**This is to store the user input*/
    String listArgument;

    /**String for long listing*/
    public final String LONG = "long";
    /**String 1 for short listing*/
    public final String SHORT_1 = "short";
    /**String 2 for short listing*/
    public final String SHORT_2 = "";

    /**
     * Parameterized constructor to call the super class constructor
     * @param listArgument this is the constructor argument and is initialized to the list argument by default
     */
    public ListCmd(String listArgument) {
        super(CommandType.LIST,listArgument);
        this.listArgument = listArgument;
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
        if (listCmdBooks.size() == 0) {
            listOutput.append("The library has no book entries.");
        }
        else {
            listOutput.append(listCmdBooks.size()).append(" books in library:\n");
            if (listArgument.equalsIgnoreCase(SHORT_1) || listArgument.equalsIgnoreCase(SHORT_2)) {
                while (iterator.hasNext()) { //  Short printing
                    BookEntry book = iterator.next();
                    listOutput.append(book.getTitle()).append('\n');
                }
            }
            else if (listArgument.equalsIgnoreCase(LONG)) {
                while (iterator.hasNext()){ //  Long printing
                    BookEntry book = iterator.next();
                    listOutput.append(book.toString());
                    listOutput.append("\n\n");
                }
            }
        }

        System.out.println(listOutput);
    }

    /**
     * This method is an overriding method for parsing the arguments and making sure the arguments received are long or short
     * Instead of using magic strings we have written it as constants in the class
     * @param listType this is the argument coming along with the list command type
     * @return This gives whether our argument is valid or not based on whether it is short or long
     */
    @Override
    protected boolean parseArguments(String listType) {
        Objects.requireNonNull(listType,"Cannot be null");
        listArgument = listType;
        return  listType.equalsIgnoreCase(LONG) ||
                listType.equalsIgnoreCase(SHORT_1) ||
                listType.equalsIgnoreCase(SHORT_2); //  checking all cases
    }
}
