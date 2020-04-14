import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
/**
 * This command should allow the user to search for specific books within the library and display them
 */
public class SearchCmd extends LibraryCommand {
    private String searchField; // instance field

    /**
     * constructor which calls the constructor in LibraryCommand
     * @param searchField is the word to be searched for
     */
    public SearchCmd(String searchField) {
        super(CommandType.SEARCH,searchField); // calling the superclass constructor
    }

    /**
     * Parse the string based on the requirements of the SEARCH command
     * and is used to check if input is a single word
     * @param searchInput is the search word which is later stored in an instance field
     * @return if search input is a single word and not blank
     * @throws NullPointerException if the search input is null
     */
    @Override
    protected  boolean parseArguments(String searchInput) {
        Objects.requireNonNull(searchInput, StdMsg.STD_NULL_MSG.toString());
        // if single word and not blank
        if (!searchInput.trim().contains(RemoveCmd.WHITE_SPACE) && !searchInput.isBlank()) {
            searchField = searchInput;
            return true;
        }
        return false;
    }

    /**
     * Executes the SearchCmd option and prints the titles which contain the search input
     * @param data book data to be considered for command execution
     * @throws NullPointerException if the BookEntry list contains a null reference
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, StdMsg.STD_NULL_MSG.toString());
        List<BookEntry> booksList = data.getBookData();
        Objects.requireNonNull(booksList, StdMsg.STD_NULL_MSG.toString()); // making sure the list is not null

        if (booksList.contains(null)) {
            throw new NullPointerException(StdMsg.STD_NULL_MSG.toString()); // should not contain null
        }

        List<String> titleList = listTitles(booksList);
        List<String> matchCases = inTitle(titleList); // list of all match cases

        if (matchCases.isEmpty()) {
            System.out.println("No hits found for search term: "+searchField);//  the case where the length is zero
        }
        else { // if searches found
            for (String match : matchCases) {
                System.out.println(match); //  this is the printing of the processed list
            }
        }

    }

    /**
     * Adding all the titles of the Library in a list
     * @param bookList is the list of BookEntry objects in the library
     * @return a list of titles of the books in the library
     * @throws NullPointerException if the List of BookEntry objects is null
     */
    private List<String> listTitles(List<BookEntry> bookList) {
        Objects.requireNonNull(bookList,StdMsg.STD_NULL_MSG.toString());

        List<String> titleList = new ArrayList<>();
        for (BookEntry book : bookList) {
            titleList.add(book.getTitle()); //  storing the title of every book in a list
        }
        return titleList;
    }

    /**
     * Checks whether the search input is in the list of book titles
     * @param bookTitles is the list of book titles which would searched
     * @return the titles which contain the search input in it
     * @throws NullPointerException if the bookTitles list is null
     */
    private List<String> inTitle(List<String> bookTitles) {
        Objects.requireNonNull(bookTitles, StdMsg.STD_NULL_MSG.toString());
        List<String> matchCase = new ArrayList<>();
        //  returning the the match cases
        if (!bookTitles.isEmpty()) { // loop only if not empty
            for (String title : bookTitles) {
                if (title.toLowerCase().contains(searchField.toLowerCase())) { // case insensitive
                    matchCase.add(title); // all match-cases in a list
                }
            }
        }
        return matchCase;
    }


}
