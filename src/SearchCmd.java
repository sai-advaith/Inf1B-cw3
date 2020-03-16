import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchCmd extends LibraryCommand {
    String searchWord;
    public SearchCmd(String searchWord) {
        super(CommandType.SEARCH,searchWord);
        this.searchWord = searchWord;
    }

    @Override
    public void execute(LibraryData data) {
        List<BookEntry> booksList = data.getBookData();
        List<String> titleList = new ArrayList<>();
        for (BookEntry book : booksList) {
            titleList.add(book.getTitle());
        }
        System.out.println(Arrays.toString(titleList.toArray()));
    }
    public String[] inString(String[] matchCases) {
        return null;
    }
    @Override
    protected  boolean parseArguments(String singleWordSearch) {
        Objects.requireNonNull(singleWordSearch,"Cannot be a null Object");
        searchWord = singleWordSearch;
        if (singleWordSearch.equals("")) {
            return false;
        }
        return singleWordSearch.split(" ",0).length == 1;
    }
}
