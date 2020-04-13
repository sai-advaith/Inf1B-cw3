import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * This command should allow the user to remove specific books from the library.
 */
public class RemoveCmd extends LibraryCommand {
    private String removeField;
    /**Constant string which indicates author removal*/
    public static final String AUTHOR = "AUTHOR";
    /**Constant string which indicates title removal*/
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
     * One if the argument is AUTHOR or TITLE, and the other if whatever being removed is not an empty string
     * @param removeInput is the argument which contains the string where we are told about what to be removed
     * @return true if the argument being returned is valid and can hence be executed by us
     * @throws NullPointerException if the string input is null
     */
    @Override
    protected boolean parseArguments(String removeInput) {
        Objects.requireNonNull(removeInput, StdMsg.STD_NULL_MSG.toString());
        int firstSpace = removeInput.indexOf(WHITE_SPACE); //  Checking the first occurrence of whitespace
        if (firstSpace != -1){
            String removeType = getRemoveType(removeInput); // first argument
            String removeArg = getRemoveArg(removeInput);
            if ((removeType.equals(AUTHOR) || removeType.equals(TITLE)) && !(removeArg.isBlank())) {
                removeField = removeInput; // first argument is only author
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    /**
     * This an overriding method to execute the removeCmd for the library class.
     * There are different procedures for removal of AUTHOR and TITLE from the list
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if the data contains a null object
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

        switch (getRemoveType(removeField)) {// performing respective removal
            case AUTHOR:
                String author = getRemoveArg(removeField); //  the author of the book to be removed
                int removedBooks = authorRemoval(bookList,author);
                removalOutput.append(removedBooks).append(StdMsg.AUTHOR_REMOVE_MSG.toString()).append(author);
                break;

            case TITLE:
                String title = getRemoveArg(removeField); // the title of the book to be removed

                if (titleRemoval(bookList,title)) {
                    removalOutput.append(title).append(StdMsg.REMOVE_SUCCESS_MSG.toString()); // removed
                }

                else {
                    removalOutput.append(title).append(StdMsg.TITLE_NOT_FOUND_MSG.toString());// title not found
                }
                break;
        }
        System.out.println(removalOutput);//  printing the entire string to reduce coupling
    }

    /**
     * This method is to implement the removal of the author
     * @param books is a list of bookEntry objects
     * @param author is the author to be removed from the list
     * @return an integer with the number of authors that have been removed from the list
     * @throws NullPointerException if the author or the list of books is empty
     */
    public int authorRemoval(List<BookEntry> books, String author) {
        Objects.requireNonNull(books, StdMsg.STD_NULL_MSG.toString());
        Objects.requireNonNull(author, StdMsg.STD_NULL_MSG.toString());
        int removedBooks = 0; // counting the books removed
        Iterator<BookEntry> bookIter = books.iterator(); // declaring iterator
        while (bookIter.hasNext()) {
            BookEntry book = bookIter.next();

                List<String> bookAuthors = Arrays.asList(book.getAuthors());
                if (bookAuthors.contains(author)) {
                    bookIter.remove(); // removing the book
                    removedBooks++;
                }
        }
        return removedBooks; //  returning the removed books
    }

    /**
     * This method is to implement the removal of the book
     * @param books is a list of bookEntry objects
     * @param title is the title of the book to be removed from the list
     * @return true if the book has been successfully removed, else false
     * @throws NullPointerException if the books or the title of the book is null
     */
    public boolean titleRemoval(List<BookEntry> books, String title) {
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
     * Using to split the AUTHOR or TITLE part of the removal String from the author name or title
     * @param bookRemoval from which we parse the arguments
     * @return a string containing the removal type (i.e. AUTHOR or TITLE)
     * @throws NullPointerException if the bookRemoval string is null
     */
    public String getRemoveType(String bookRemoval) {
        Objects.requireNonNull(bookRemoval, StdMsg.STD_NULL_MSG.toString());
        return bookRemoval.substring(0, bookRemoval.indexOf(WHITE_SPACE));
    }

    /**
     * Using to split the author name or title part of the removal String from the AUTHOR or TITLE part of the string
     * @param bookRemoval from which we parse the arguments
     * @return a string containing the removal argument (i.e. author or title of the book)
     * @throws NullPointerException if the bookRemoval string is null
     */
    public String getRemoveArg(String bookRemoval) {
        Objects.requireNonNull(bookRemoval, StdMsg.STD_NULL_MSG.toString());
        return bookRemoval.substring(bookRemoval.indexOf(WHITE_SPACE)+1); // everything after the removal type
    }
}
