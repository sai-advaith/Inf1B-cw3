import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * This command should allow the user to remove specific books from the library.
 */
public class RemoveCmd extends LibraryCommand {
    private String removeField; // instance field

    /**Command which indicates author removal*/
    public static final String AUTHOR = "AUTHOR";
    /**Command which indicates title removal*/
    public static final String TITLE = "TITLE";
    /**Constant string which indicates white space*/
    public static final String WHITE_SPACE = " ";

    /**
     * public constructor to call the super class constructor
     * @param removeField the argument being assigned to the class variable removeArgument
     */
    public RemoveCmd(String removeField) {
        super(CommandType.REMOVE,removeField);
    }

    /**
     * Used to check if the string argument to be removed is valid. Two aspects are checked,
     * One if the first argument is AUTHOR or TITLE, and the other if whatever being removed is not blank
     * @param removeInput contains the string which gives information about what has to be removed
     * @return whether input being removed is valid
     * @throws NullPointerException if the string input is null
     */
    @Override
    protected boolean parseArguments(String removeInput) {
        Objects.requireNonNull(removeInput, StdMsg.STD_NULL_MSG.toString());

        int firstSpace = removeInput.indexOf(WHITE_SPACE); //  Checking the first occurrence of whitespace
        int notFoundIndex = -1;
        if (firstSpace != notFoundIndex){
            String removeType = getRemoveType(removeInput); // first argument: AUTHOR/TITLE
            String removeArg = getRemoveArg(removeInput); // second argument: cannot be blank

            if ((removeType.equals(AUTHOR) || removeType.equals(TITLE)) && !(removeArg.isBlank())) {
                removeField = removeInput;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * This an overriding method to execute the removeCmd
     * There are different procedures for removal of AUTHOR and TITLE from the Library
     * @param data LibraryData object to access the BookEntry list.
     * @throws NullPointerException if the data contains a null object
     * @throws IllegalArgumentException if the removal type is not AUTHOR or TITLE
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, StdMsg.STD_NULL_MSG.toString());
        List<BookEntry> bookList = data.getBookData();
        Objects.requireNonNull(bookList, StdMsg.STD_NULL_MSG.toString());

        if (bookList.contains(null)) {
            throw new NullPointerException(StdMsg.STD_NULL_MSG.toString()); // cannot contain null
        }
        StringBuilder removalOutput = new StringBuilder(); // creating a string builder which will be printed

        switch (getRemoveType(removeField)) {
            case AUTHOR:
                String author = getRemoveArg(removeField); //  the author of the book to be removed
                int removedBooks = authorRemoval(bookList,author);
                removalOutput.append(removedBooks).append(" books removed for author: ").append(author);
                break;
            case TITLE:
                String title = getRemoveArg(removeField); // the title of the book to be removed
                if (titleRemoval(bookList,title)) {
                    removalOutput.append(title).append(": removed successfully."); // removed
                } else {
                    removalOutput.append(title).append(": not found.");// title not found
                }
                break;
            default:
                throw new IllegalArgumentException(StdMsg.STD_ILLEGAL_MSG.toString());
        }
        System.out.println(removalOutput);//  printing the entire string to reduce coupling
    }

    /**
     * Implements the removal of the books written by an author from the Library
     * @param books is a list of BookEntry objects
     * @param author is the author whose books need to be removed from the list
     * @return the number of authors that have been removed from the list
     * @throws NullPointerException if the author or the list of books is empty
     */
    private int authorRemoval(List<BookEntry> books, String author) {
        Objects.requireNonNull(books, StdMsg.STD_NULL_MSG.toString());
        Objects.requireNonNull(author, StdMsg.STD_NULL_MSG.toString());
        int removedBooks = 0; // counting the books removed
        Iterator<BookEntry> bookIter = books.iterator(); // declaring iterator
        while (bookIter.hasNext()) {
            BookEntry book = bookIter.next();
            List<String> bookAuthors = Arrays.asList(book.getAuthors());
            if (bookAuthors.contains(author)) { // if list of authors contain the user input
                bookIter.remove(); // removing the book
                removedBooks++; // updating
            }
        }
        return removedBooks; //  returning the removed books
    }

    /**
     * Implement the removal of the book with a title specified by the user
     * @param books is a list of bookEntry objects
     * @param title is the title of the book to be removed from the list
     * @return if the book has been successfully removed from the Library
     * @throws NullPointerException if the books or the title of the book is null
     */
    private boolean titleRemoval(List<BookEntry> books, String title) {
        Objects.requireNonNull(books, StdMsg.STD_NULL_MSG.toString());
        Objects.requireNonNull(title, StdMsg.STD_NULL_MSG.toString());

        boolean removalSuccess = false; // checking if the removal is successful

        Iterator<BookEntry> bookIter = books.iterator();
        while(bookIter.hasNext()) {
            BookEntry book = bookIter.next();
                if (book.getTitle().equals(title)) {
                    removalSuccess = true; // updating
                    bookIter.remove(); //  removing
                }
        }
        return removalSuccess;
    }

    /**
     * Split the AUTHOR/TITLE part of the input String from the author name/book title
     * @param bookRemoval from which we split the AUTHOR/TITLE
     * @return a string containing the removal type (i.e. AUTHOR or TITLE)
     * @throws NullPointerException if the bookRemoval string is null
     */
    private String getRemoveType(String bookRemoval) {
        Objects.requireNonNull(bookRemoval, StdMsg.STD_NULL_MSG.toString());
        return bookRemoval.substring(0, bookRemoval.indexOf(WHITE_SPACE));
    }

    /**
     * Split the author name/book title part of the input String from the AUTHOR/TITLE
     * @param bookRemoval from which we split the author name/book title
     * @return a string containing the removal argument (i.e. author or title of the book)
     * @throws NullPointerException if the bookRemoval string is null
     */
    private String getRemoveArg(String bookRemoval) {
        Objects.requireNonNull(bookRemoval, StdMsg.STD_NULL_MSG.toString());
        return bookRemoval.substring(bookRemoval.indexOf(WHITE_SPACE)+1); // everything after the removal type
    }
}
