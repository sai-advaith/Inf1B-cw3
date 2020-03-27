import java.awt.print.Book;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class RemoveCmd extends LibraryCommand {
    String removeArgument;
    /**Constant string which indicates author removal*/
    public static final String AUTHOR = "AUTHOR";
    /**Constant string which indicates title removal*/
    public static final String TITLE = "TITLE";

    /**
     * public constructor to call the super class constructor
     * @param removeArgument the argument being assigned to the class variable removeArgument
     */
    public RemoveCmd(String removeArgument) {
        super(CommandType.REMOVE,removeArgument);
        this.removeArgument = removeArgument;
    }

    /**
     * This an overriding method is to execute the removeCmd for the library class, where several cases are taken into consideration
     * If author to be removed then respective output is given, if book then respective is given in that case too.
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {
        List<BookEntry> bookList = data.getBookData();
        StringBuilder removalOutput = new StringBuilder(); // creating a string builder which will be printed
        if (getRemoveType(removeArgument).equals(AUTHOR)){
            String author = getRemoveArg(removeArgument); //  the author of the book to be removed
            int removedBooks = authorRemoval(bookList,author);
            if (removedBooks == 0) {
                removalOutput.append(author).append(": not found.").append("\n");
            }
            removalOutput.append(removedBooks).append(" books removed for author: ").append(author); //implemented
        }
        else if (getRemoveType(removeArgument).equals(TITLE)) {
            String title = getRemoveArg(removeArgument); // the title of the book to be removed
            if (titleRemoval(bookList,title)) {
                removalOutput.append(title).append(": removed successfully."); // removing successfully
            }
            else {
                removalOutput.append(title).append(": not found.");
            }
        }
        System.out.println(removalOutput);//  printing the entire string to reduce coupling
    }

    /**
     * This method is to implement the removal of the author
     * @param books is a list of bookEntry objects
     * @param author is the author to be removed from the list
     * @return an integer with the number of authors that have been removed from the list
     */
    public int authorRemoval(List<BookEntry> books, String author) {
        int previous = books.size(); // storing size before removal
        Iterator<BookEntry> bookIter = books.iterator(); // declaring iterator
        while (bookIter.hasNext()) {
            BookEntry book = bookIter.next();
            List<String> bookAuthors = Arrays.asList(book.getAuthors());
            if (bookAuthors.contains(author)) {
                bookIter.remove(); // removing the book
            }
        }
        return previous - books.size(); //  comparing previous size and size after removal
    }

    /**
     * This method is to implement the removal of the book
     * @param books is a list of bookEntry objects
     * @param title is the title of the book to be removed from the list
     * @return true if the book can be removed, else false
     */
    public boolean titleRemoval(List<BookEntry> books, String title) {
        boolean removalSuccess = false; // checking if the removal is successful
        Iterator<BookEntry> bookIter = books.iterator();
        while(bookIter.hasNext()) {
            BookEntry book = bookIter.next();
            if (book.getTitle().equals(title)) {
                removalSuccess = true;
                bookIter.remove(); //  removing
            }
        }
        return removalSuccess;
    }
    /**
     * Used to check if the string argument to be removed is valid. Two aspects are checked,
     * One if the argument is AUTHOR or TITLE, and the other if whatever being removed is not an empty string
     * @param bookRemoval is the argument which contains the string where we are told about what to be removed
     * @return true if the argument being returned is valid and can hence be excuted by us
     */
    @Override
    protected boolean parseArguments(String bookRemoval) {
        Objects.requireNonNull(bookRemoval,"String cannot be null");
        removeArgument = bookRemoval;
        int firstSpace = bookRemoval.indexOf(' '); //  Checking the first occurrence of whitespace
        if (firstSpace != -1){
            String removeType = getRemoveType(bookRemoval);
            String removeArg = getRemoveArg(bookRemoval);
            return (removeType.equals(AUTHOR) || removeType.equals(TITLE)) &&
                    !(removeArg.equals("") || removeArg == null);
        }
        else {
            return false;
        }
    }

    /**
     * Using to split the AUTHOR or TITLE part of the removal String from the author name or title
     * @param bookRemoval from which we parse the arguments
     * @return a string containing the removal type (i.e. AUTHOR or TITLE)
     */
    public String getRemoveType(String bookRemoval) {
        return bookRemoval.substring(0, bookRemoval.indexOf(' '));
    }

    /**
     * Using to split the author name or title part of the removal String from the AUTHOR or TITLE part of the string
     * @param bookRemoval from which we parse the arguments
     * @return a string containing the removal argument (i.e. author or title of the book)
     */
    public String getRemoveArg(String bookRemoval) {
        return bookRemoval.substring(bookRemoval.indexOf(' ')+1);
    }
}
