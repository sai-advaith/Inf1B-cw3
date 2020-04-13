import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
/**
 * This command should allow the user to search for specific books within the library
 * and display them
 */
public class SearchCmd extends LibraryCommand {
    private String searchField;

    /**
     * This is the constructor which calls the constructor in library command, the parent constructor
     * @param searchField is the word to be searched
     */
    public SearchCmd(String searchField) {
        super(CommandType.SEARCH,searchField); // calling the baseclass constructor
    }

    /**
     * This is to parse the string based on the requirements of the SEARCH command
     * and is used to check if is a single word
     * @param searchInput is the search word which is later stored in an instance field
     * @return true if it is a single word and otherwise false.
     * @throws NullPointerException if the search input is null
     */
    @Override
    protected  boolean parseArguments(String searchInput) {
        Objects.requireNonNull(searchInput, StdMsg.STD_NULL_MSG.toString());
        if (!searchInput.contains(RemoveCmd.WHITE_SPACE) && !searchInput.isBlank()) {// if single word
            searchField = searchInput;
            return true;
        }
        return false;
    }

    /**
     * This is an overriding function of the same function LibraryCommand and hence is used to execute the SearchCmd option
     * @param data book data to be considered for command execution.
     * This prints the titleList of the titles which occur in the list
     * @throws NullPointerException if the BookEntry list contains a null reference.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, StdMsg.STD_NULL_MSG.toString());
        List<BookEntry> booksList = data.getBookData();
        Objects.requireNonNull(booksList, StdMsg.STD_NULL_MSG.toString()); // making sure the list is not null

        if (booksList.contains(null)) {
            throw new NullPointerException(StdMsg.STD_NULL_MSG.toString()); // should not contain null
        }

        List<String> titleList = new ArrayList<>(); // list of all titles
        List<String> matchCases; // list of all match cases
        for (BookEntry book : booksList) {
                titleList.add(book.getTitle()); //  storing the title of every book in a list
        }

        matchCases = inTitle(titleList);
        if (matchCases.isEmpty()) {
            System.out.println(StdMsg.NO_SEARCH_MSG.toString()+searchField);//  the case where the length is zero
        }
        else {
            for (String match : matchCases) {
                System.out.println(match); //  this is the printing of the processed list
            }
        }

    }

    /**
     * This is to use a regex pattern matching for the string to be searched in the titles
     * @param bookTitles is the list of book titles which would searched to determine whether if it search is in the list
     * @return the titles which contain the searchWord in it, even a substring is matched
     * @throws NullPointerException if the bookTitles list is null
     */
    public List<String> inTitle(List<String> bookTitles) {
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
