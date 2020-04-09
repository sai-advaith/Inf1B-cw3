import java.util.*;

public class SearchCmd extends LibraryCommand {
    private String searchField;

    /**
     * This is the constructor which calls the constructor in library command, the parent constructor
     * @param searchField is the word to be searched
     */
    public SearchCmd(String searchField) {
        super(CommandType.SEARCH,searchField);
        Objects.requireNonNull(searchField);
    }

    /**
     * This is an overriding function of the same function LibraryCommand and hence is used to execute the SearchCmd option
     * @param data book data to be considered for command execution.
     * This prints the titleList of the titles which occur in the list
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data,StdMsgs.STD_NULL_MSG.toString());
        List<BookEntry> booksList = data.getBookData();
        List<String> titleList = new ArrayList<>();
        for (BookEntry book : booksList) {
            titleList.add(book.getTitle()); //  storing the title of every book in a list
        }
        if (inString(titleList).size() == 0) {
            System.out.println(StdMsgs.NO_SEARCH_MSG.toString()+searchField);//  the case where the length is zero
        }
        else {
            List<String> matchCases = inString(titleList);
            for (String match : matchCases) {
                System.out.println(match); //  this is the printing of the processed list
            }
        }
    }

    /**
     * This is to use a regex pattern matching for the string to be searched in the titles
     * @param bookTitles is the list of book titles which would searched to determine whether if it search is in the list
     * @return the titles which contain the searchWord in it, even a substring is matched
     */
    public List<String> inString(List<String> bookTitles) {

        List<String> matchCase = new ArrayList<>();
        //  returning the the match cases
        if (bookTitles.size() != 0) {
            for (String title : bookTitles) {
                if (title.toLowerCase().contains(searchField.toLowerCase())) {
                    matchCase.add(title);
                }
            }
        }
        return matchCase;

    }

    /**
     * This is to parse the string based on the requirements of the SEARCH command and is used to check if is a single word
     * @param searchInput is the search word which is later stored in memory
     * @return true if it is a single word and otherwise false.
     */
    @Override
    protected  boolean parseArguments(String searchInput) {
        Objects.requireNonNull(searchInput,StdMsgs.STD_NULL_MSG.toString());
        if (!searchInput.isBlank()) {
            if (searchInput.split(RemoveCmd.WHITE_SPACE, 0).length == 1) {
                searchField = searchInput; // assigning the trimmed instance to the field
                return true;//  This is to check if the length of the array is zero.
            }
        }
        return false;

    }
}
