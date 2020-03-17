import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchCmd extends LibraryCommand {
    String searchWord;

    /**
     * This is the constructor which calls the constructor in library command, the parent constructor
     * @param searchWord is the word to be searched
     */
    public SearchCmd(String searchWord) {
        super(CommandType.SEARCH,searchWord);
        Objects.requireNonNull(searchWord);
        this.searchWord = searchWord;
    }

    /**
     * This is an overriding function of the same function LibraryCommand and hence is used to execute the SearchCmd option
     * @param data book data to be considered for command execution.
     * This prints the titleList of the titles which occur in the list
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data,"Cannot be of null type");
        List<BookEntry> booksList = data.getBookData();
        List<String> titleList = new ArrayList<>();
        for (BookEntry book : booksList) {
            titleList.add(book.getTitle()); //  storing the title of every book in a list
        }
        if (inString(titleList).size() == 0) {
            System.out.println("No hits found for search term: "+searchWord);//  the case where the length is zero
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
                Pattern match = Pattern.compile(searchWord, Pattern.CASE_INSENSITIVE); //  This is the string regex to be searched. Will make sure substring search is done
                Matcher matchMaker = match.matcher(title); //  This is the matcher in the title of teh loop
                if (matchMaker.find()) {
                    matchCase.add(title); //  adding in list once found and returning true after that.
                }
            }
        }
        return matchCase;

    }

    /**
     * This is to parse the string based on the requirements of the SEARCH command and is used to check if is a single word
     * @param singleWordSearch is the search word which is later stored in memory
     * @return true if it is a single word and otherwise false.
     */
    @Override
    protected  boolean parseArguments(String singleWordSearch) {
        Objects.requireNonNull(singleWordSearch,"Cannot be a null Object");
        searchWord = singleWordSearch;
        if (singleWordSearch.equals("")) { // handling the case where it is a string of length 0
            return false;
        }
        return singleWordSearch.split(" ",0).length == 1; //  This is to check if the length of the array is zero.
    }
}
