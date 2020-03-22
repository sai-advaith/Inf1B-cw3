import java.awt.print.Book;
import java.util.*;

public class GroupCmd extends LibraryCommand {
    String groupArgument;
    public GroupCmd(String groupArgument) {
        super(CommandType.GROUP,groupArgument);
        this.groupArgument = groupArgument;
    }

    @Override
    public void execute(LibraryData data) {
        List<BookEntry> books = data.getBookData();
        Map<String, List<String>> groupMap = new HashMap<String, List<String>>();
        if(groupArgument.equals(RemoveCmd.TITLE)) {
            groupMap = titleGroup(books);
        }
    }
    public String mapOutput(Map<String,List<String>> groupArg) {
           StringBuilder output = new StringBuilder();
           if (groupArg.size() == 0) {
               output.append("The library has no book entries.");
           }
           else {
               output.append()
           }
    }
    public Map<String,List<String>> titleGroup(List<BookEntry> books) {
        String nonNumericTitle = "[0-9]";
        Map<String, List<String>> titleMap = new HashMap<String, List<String>>();
        for (BookEntry book : books) {
            if (Character.isAlphabetic(book.getTitle().charAt(0))) { // checking the first letter
                String titleCategory = String.valueOf(book.getTitle().charAt(0));
                if (!titleMap.containsKey(titleCategory)) {
                    titleMap.put(titleCategory, new ArrayList<>());
                }
                titleMap.get(titleCategory).add(book.getTitle());
            }
            else {
                if (!titleMap.containsKey(nonNumericTitle)) {
                    titleMap.put(nonNumericTitle,new ArrayList<>());
                }
                titleMap.get(nonNumericTitle).add(book.getTitle());
            }
        }
        return titleMap;
    }
    @Override
    protected boolean parseArguments(String groupInput) {
        groupArgument = groupInput;
        Objects.requireNonNull(groupInput);
        return groupInput.equals(RemoveCmd.AUTHOR) || groupInput.equals(RemoveCmd.TITLE);
    }
}
